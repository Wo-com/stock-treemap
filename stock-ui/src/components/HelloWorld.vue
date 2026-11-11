<script setup>
import { getOne } from '../api/modules/marketDataApi'
import { dataType } from './dictData'
import { boardTypeList, colorRepOption, areaRepOption, hhmmOption, colorOption,stockSector } from './dataOption'
import { buildRewindTimeline } from './util'
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'

const chartDom = ref(null)
let myChart = null
const selectDate = ref('')//选择的日期
const selectHhmm = ref('09:30')//时间
const selectStockBoard = ref('hsj')//股票板块
const selectStockSector = ref('BK')//股票分类
const selectColorRep = ref('zdf')//颜色类型
const selectAreaRep = ref('ltsz')//面积类型

const isRewinding =ref(false)//当前是否回溯


var option = {  //指定图表的配置项和数据
  title: {  //配置标题组件
    show: false
    // text: '矩形树图', top: 15,
    // textStyle: { color: 'green' }, left: 270
  },
  tooltip: {
    trigger: 'item',
    triggerOn: 'mousemove',
    // // showDelay: 100, // 添加延迟，避免频繁切换渲染
    // // 自定义数据
    // formatter: function (params) {
    //   const data = params.data
    //   const str = `
    //     <div style="width:140px;">
    //       <div style="display:flex;width:100%;border :1px solid red;">
    //         <div>${data.name || '-'}</div> 
    //         <div>${data.code}</div>
    //       </div>
    //       <div style="display:flex;width:50px;">
    //         <div>值：</div> 
    //         <div>${getValueByCode(data.code)}</div>
    //       </div>
    //     </div>`
    //   return str
    // }
  },

  series: [{
    // name: '全部',
    type: 'treemap',
    visibleMin: 50,//最小面积
    //占满所属区域
    top: 'top',
    left: 'left',
    width: '100%',
    height: '100%',
    breadcrumb: {
      show: false//关闭面包屑导航
    },


    //区域标签
    label: {
      show: true,
      color: '#fff'
    },
    //标签分类
    upperLabel: {
      show: true,
      fontSize: 14,
      height: 16,
      color: '#fff',
    },
    //层级样式
    levels: [
      //最外层
      {
        itemStyle: {
          borderColor: '#262931',//最外层背景颜色
          borderWidth: 4,
        },
        upperLabel: {
          show: false//去除最外层label
        }
      },
      //第二层
      {
        itemStyle: {
          borderColor: '#262931',
          borderWidth: 1,//边框宽度
        },
      },
      //第三层
      {
        itemStyle: {
          borderColor: '#262931',
          borderWidth: 0.5,//边框宽度
        },
      }
    ],
    data: [{
      name: '分类',  //First tree
      value: 24,
      children: [{
        name: 'item',  //First leaf of first tree
        value: 2
      }]
    }],
  }],
};




defineProps({
  msg: String,
})

let dataList = [];
const getData = async () => {
  const date = `${selectDate.value} ${selectHhmm.value}`;
  const { data } = await getOne({ date });
  const array = JSON.parse(data.data)
  const objArray = [];
  for (const item of array) {
    const a = item.split("|")
    const code = a[1];
    const obj = {
      code: code,
      bkName: dataType[code]?.bkName,//有报错
      bkCode: dataType[code]?.bkCode,//有报错
      name: dataType[code]?.name,//有报错
      type: dataType[code]?.type,//有报错

      zdf: (100 * Number.parseFloat(a[2])),//涨跌幅
      zdf_3: (100 * Number.parseFloat(a[6])),//涨跌幅3日
      zdf_5: (100 * Number.parseFloat(a[7])),//涨跌幅5日
      zdf_10: (100 * Number.parseFloat(a[8])),//涨跌幅10日
      zdf_20: (100 * Number.parseFloat(a[9])),//涨跌幅20日
      zdf_60: (100 * Number.parseFloat(a[10])),//涨跌幅60日
      zdf_nc: (100 * Number.parseFloat(a[11])),//涨跌幅年初至今
      ltsz: (Number.parseFloat(a[3]) / 1),//流通市值
      zsz: (Number.parseFloat(a[4]) / 1),//总市值
      cje: (Number.parseFloat(a[15]) / 1e4),//成交额
      lb: (Number.parseFloat(a[13]) / 1),//量比
      zxj: (Number.parseFloat(a[16]) / 1e3 * 100),//最新价
      zde: (Number.parseFloat(a[17]) / 1e3 * 100),//涨跌额
      mark: 1
    }
    objArray.push(obj)
  }
  dataList = objArray
  return objArray;
}

//重新绘图
const changeChart = () => {
  const drawDataList = []
  for (const item of dataList) {
    //过滤 股票板块
    if (!item.type?.includes(selectStockBoard.value)) {
      continue;
    }
    //过滤 股票分类
    if (!item.bkCode?.includes(selectStockSector.value)) {
      continue;
    }
    drawDataList.push(item)
  }

  const optionData = getOption(drawDataList, selectAreaRep.value)
  option.series[0].data = optionData;
  myChart.setOption(option, true)
}




/**
 * 根据涨跌幅比例计算对应的颜色值
 * @param {string|number} value - 涨跌幅参数，可能是字符串（如"-"表示无数据）或数字（涨跌幅百分比，如3.5表示3.5%）
 * @returns {string} 对应的颜色十六进制值
 */
function getColor(e) {
  var t, n = ["#f63538", "#ee373a", "#e6393b", "#df3a3d", "#d73c3f", "#ce3d41", "#c73e43", "#bf4045", "#b64146", "#ae4248", "#a5424a", "#9d434b", "#94444d", "#8b444e", "#824450", "#784551", "#6f4552", "#644553", "#5a4554", "#4f4554", "#414554", "#3f4c53", "#3d5451", "#3b5a50", "#3a614f", "#38694f", "#366f4e", "#35764e", "#347d4e", "#32844e", "#31894e", "#31904e", "#30974f", "#2f9e4f", "#2fa450", "#2faa51", "#2fb152", "#2fb854", "#30be56", "#30c558", "#30cc5a"];
  n = n.reverse();
  t = "-" == e ? 0 : e / 100;
  return t >= 4 ? n[40] : t <= -4 ? n[0] : n[Math.floor((t - -4) * 5)];
}


/**
 * 生成树状Option
 * 
 * 根据bkName进行分类
 * 如果分类已存在则children.push
 * 如果不存在则map.set
 * 
 * @param arrayObj 
 * @param key 
 */

const getOption = (arrayObj, key) => {
  const map = new Map();
  for (const item of arrayObj) {
    const type = item.bkName
    const child = {
      name: item.name,
      value: item[key],
      code: item.code,
      itemStyle: {
        color: getColor(item[selectColorRep.value])
      }
    }
    if (map.has(type)) {
      const typeItem = map.get(type);
      typeItem.value += item[key]
      typeItem.children.push(child);
    } else {
      const obj = {
        name: type,
        value: 0,
        children: [child],
        itemStyle: {
          color: getColor(item[[selectColorRep.value]])
        }
      }
      map.set(type, obj)
    }
  }

  const listOption = []
  map.forEach((value) => {
    listOption.push(value)
  });
  return listOption;
}



// 初始化图表
const initChart = () => {
  myChart = echarts.init(chartDom.value)
  // 配置项对象
  //使用刚指定的配置项和数据显示图表
  myChart.setOption(option);
}

onMounted(() => {
  //挂载后才能操作DOM
  initChart()
})


// 切换日期
const changeDate = async (date) => {
  selectDate.value = date;
  await getData();
  changeChart();
}
//切换时分
const changeHhmm = async (item) => {
  selectHhmm.value = item;
  await getData();
  changeChart();
}
// 切换板块
const changeBoardType = (item) => {
  selectStockBoard.value = item.code;
  changeChart();
}
// 切换色彩值
const changeColorRep = () => {
  changeChart();
}
// 切换面积值
const changeAreaRep = () => {
  changeChart();
}
//切换股票分类
const changeStockSector=()=>{
  changeChart();
}
//监听窗口变化重新绘图（防抖100毫秒）
let resizeTimer = null;
const handleResize = () => {
  if (resizeTimer) {
    clearTimeout(resizeTimer);
  }
  resizeTimer = setTimeout(() => {
    if (myChart && myChart.resize) {
      myChart.resize();
    }
  }, 100);
};

// 回溯定时器
let rewindTimer = null;



const stopRewind = () => {
  isRewinding.value = false;
  if (rewindTimer) {
    clearTimeout(rewindTimer);
    rewindTimer = null;
  }
};

/**
 * 从 selectDate 日开始回溯到当前时间，按 hhmmOption 逐段播放并重绘
 */
const startRewind = () => {
  isRewinding.value = true;
  const timeline = buildRewindTimeline(selectDate.value);
  console.log(timeline)
  let idx = 0;
  const step = async () => {
    if (!isRewinding.value || idx >= timeline.length) {
      stopRewind();
      return;
    }
    const point = timeline[idx++];
    selectDate.value = point.date;
    selectHhmm.value = point.hhmm;
    await getData();
    changeChart();
    rewindTimer = setTimeout(step, 1000);
  };
  step();
};

window.addEventListener("resize", handleResize);

// 组件卸载时清理事件监听器和定时器
onUnmounted(() => {
  window.removeEventListener("resize", handleResize);
  if (resizeTimer) {
    clearTimeout(resizeTimer);
  }
  stopRewind();
});


</script>

<template>

  <!-- 圣杯布局 -->
  <div class="container">
    <div class="top"></div>
    <div class="middle">
      <div class="middle-left">
        <div class="board-type">
          <div class="board-type-item" :class="{ on: selectStockBoard === item.code }" v-for="item in boardTypeList"
            :key="item.code" :index="item.code" @click="changeBoardType(item)">
            <span>{{ item.name }}</span>
          </div>
        </div>
        <div class="select-tiem">股票分类</div>
        <select v-model="selectStockSector" @change="changeStockSector">
          <option v-for="item in stockSector" :key="item.value" :value="item.value">
            {{ item.label }}
          </option>
        </select>
        <div class="select-tiem">色彩代表</div>
        <select v-model="selectColorRep" @change="changeColorRep">
          <option v-for="item in colorRepOption" :key="item.value" :value="item.value">
            {{ item.label }}
          </option>
        </select>
        <div class="select-tiem">面积代表</div>
        <select v-model="selectAreaRep" @change="changeAreaRep">
          <option v-for="item in areaRepOption" :key="item.value" :value="item.value">
            {{ item.label }}
          </option>
        </select>
      </div>
      <div class="middle-right">
        <div class="chart-item">
          <div class="item-left">
            <div class="hhmm-option">
              <input class="time-picker" type="date" v-model="selectDate" @change="changeDate($event.target.value)" />
              <div class="hhmm-option-item" v-for="item in hhmmOption" :key="item" :index="item"
                :class="{ active: selectHhmm == item }" @click="changeHhmm(item)">
                {{ item }}
              </div>
        
 
              <button v-if="!isRewinding" class="rewind" title="开始回放" @click="startRewind">&#9654;</button>
              <button v-else @click="stopRewind" class="rewind" title="停止回放" >⏹</button>
        
            </div>

          

          </div>
          <div class="item-right">
            <div class="color-option">
              <div class="color-option-item" v-for="item in colorOption" :key="item.color" :index="item.color"
                :style="{ backgroundColor: item.color }">
                {{ item.label }}
              </div>
            </div>
          </div>
        </div>
        <div class="chart-box">
          <div ref="chartDom" style="width: 100%; height: 100%;"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 100%;
  height: 100%;
  background-color: #262931;
}

.top {
  /* height: 50px; */
  background-color: #383b44;
}

.middle {
  /* height: calc(100% - 50px); */
  height: 100%;
  display: flex;
}

.middle-left {
  width: 150px;
  padding:0px 10px ;
  color: #bbb;
  font-size: 12px;
}

.middle-right {
  width: calc(100% - 150px);
}

.chart-item {
  height: 25px;
  overflow: hidden;

  display: flex;
  justify-content: space-between;
}

.chart-box {
  position: relative;
  height: calc(100% - 25px);
}

.select-tiem{
  margin-top: 10px
}

.board-type {
  font-size: 14px;
  border-radius: 5px;
  background-color: #3f414b;
  color: white;
}

.board-type-item {
  dateIndex: pointer;
  border-radius: 5px;
  padding: 5px 10px;
  list-style: none;
}

.board-type-item:hover {
  background-color: #585a64;
}

.board-type-item.on {
  background-color: #585a64;
  font-weight: 700;
}

.hhmm-option {
  display: flex;
  align-items: center;
  height: 100%;
  gap: 4px;
}

.hhmm-option-item {
  padding: 4px 12px;
  font-size: 12px;
  color: #fff;
  text-align: center;
  dateIndex: pointer;
  border-radius: 3px;
  background-color: #ff9626;
  transition: background-color 0.3s;
}

.hhmm-option-item:hover {
  background-color: #ffb366;
}

.hhmm-option-item.active {
  background-color: #2949be;
}

.hhmm-option-item.active:hover {
  background-color: #3d5fc7;
}

.color-option {
  display: flex;
  align-items: center;
  height: 100%;
  justify-content: flex-end;
}

.color-option-item {
  width: 40px;
  height: 25px;
  line-height: 25px;
  font-size: 12px;
  color: #fff;
  text-align: center;
  margin-left: 2px;
}

select {
  color: #fff;
  background: #3f414b;
  border: 1px solid #999;
  border-radius: 2px;
  width: 100%;
  padding: 2px 3px 2px 5px;
}

.time-picker {
  text-align: center;
  border: 1px solid #999;
  border-radius: 3px;
  height: 24px;
  width: 100px;
  font-size: 12px;
}

.rewind{
  color: #fff;
  font-size: 20px;
}

</style>
