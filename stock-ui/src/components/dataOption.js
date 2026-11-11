export const boardTypeList = [
    {
      name: '沪深京A股',
      code: 'hsj',
    },
    {
      name: '上证A股',
      code: 'sh',
    },
    {
      name: '深证A股',
      code: 'sz',
    },
    {
      name: '北证A股',
      code: 'bj',
    },
    {
      name: '科创板',
      code: 'kcb',
    },
    {
      name: '创业板',
      code: 'cyb',
    },
    {
      name: '沪深300',
      code: 'hs300',
    },
    {
      name: '中证500',
      code: 'zz500',
    },
    {
      name: '中证1000',
      code: 'zz1000',
    },
  ]

//颜色代表
export const colorRepOption = [
    {
        value : 'zdf',
        label : '涨跌幅',
    },
    {
        value : 'zdf_3',
        label : '3日涨跌幅',
    },
    {
        value : 'zdf_5',
        label : '5日涨跌幅',
    },
    {
        value : 'zdf_10',
        label : '10日涨跌幅',
    },
    {
        value : 'zdf_20',
        label : '20日涨跌幅',
    },
    {
        value : 'zdf_60',
        label : '60日涨跌幅',
    },
    {
        value : 'zdf_nc',
        label : '年初至今涨跌幅',
    },
]


//面积代表
export const areaRepOption = [
    {
        value : 'ltsz',
        label : '流通市值',
    },
    {
        value : 'zsz',
        label : '总市值',
    },
    {
        value : 'cje',
        label : '成交额',
    },
    {
        value : 'lb',
        label : '量比',
    },
    {
      value : 'zxj',
      label : '最新价',
    },
    {
      value : 'zde',
      label : '涨跌额',
    },
]

//时间段
export const hhmmOption = [
  "09:30",
  "10:00",
  "10:30",
  "11:00",
  "11:30",
  "13:30",
  "14:00",
  "14:30",
  "15:00"
]

//涨跌幅颜色
export const colorOption = [
  {
    color : '#00d641',
    label : '-4%',
  },
  {
    color : '#1aa448',
    label : '-3%',
  },
  {
    color : '#0e6f2f',
    label : '-2%',
  },
  {
    color : '#085421',
    label : '-1%',
  },
  {
    color : '#424453',
    label : '0%',
  },
  {
    color : '#6d1414',
    label : '1%',
  },
  {
    color : '#961010',
    label : '2%',
  },
  {
    color : '#be0808',
    label : '3%',
  },
  {
    color : '#e41414',
    label : '4%',
  }
]

export const stockSector = [
  {
    label : '所有分类',
    value : 'BK',
  },
  {
    label : '银行',
    value : 'BK0475',
  },
  {
    label : '房地产开发',
    value : 'BK0451',
  },
  {
    label : '软件开发',
    value : 'BK0737',
  },
  {
    label : '房地产服务',
    value : 'BK1045',
  },
  {
    label : '交运设备',
    value : 'BK0429',
  },
  {
    label : '综合行业',
    value : 'BK0539',
  },
  {
    label : '工程建设',
    value : 'BK0425',
  },
  {
    label : '玻璃玻纤',
    value : 'BK0546',
  },
  {
    label : '家电行业',
    value : 'BK0456',
  },
  {
    label : '珠宝首饰',
    value : 'BK0734',
  },
  {
    label : '贸易行业',
    value : 'BK0484',
  },
  {
    label : '光学光电子',
    value : 'BK1038',
  },
  {
    label : '消费电子',
    value : 'BK1037',
  },
  {
    label : '电力行业',
    value : 'BK0428',
  },
  {
    label : '医药商业',
    value : 'BK1042',
  },
  {
    label : '汽车零部件',
    value : 'BK0481',
  },
  {
    label : '计算机设备',
    value : 'BK0735',
  },
  {
    label : '环保行业',
    value : 'BK0728',
  },
  {
    label : '通用设备',
    value : 'BK0545',
  },
  {
    label : '农牧饲渔',
    value : 'BK0433',
  },
  {
    label : '电池',
    value : 'BK1033',
  },
  {
    label : '装修装饰',
    value : 'BK0725',
  },
  {
    label : '商业百货',
    value : 'BK0482',
  },
  {
    label : '石油行业',
    value : 'BK0464',
  },
  {
    label : '有色金属',
    value : 'BK0478',
  },
  {
    label : '电子元件',
    value : 'BK0459',
  },
  {
    label : '通信设备',
    value : 'BK0448',
  },
  {
    label : '航运港口',
    value : 'BK0450',
  },
  {
    label : '航空机场',
    value : 'BK0420',
  },
  {
    label : '化学制药',
    value : 'BK0465',
  },
  {
    label : '文化传媒',
    value : 'BK0486',
  },
  {
    label : '工程机械',
    value : 'BK0739',
  },
  {
    label : '证券',
    value : 'BK0473',
  },
  {
    label : '化学原料',
    value : 'BK1019',
  },
  {
    label : '电网设备',
    value : 'BK0457',
  },
  {
    label : '水泥建材',
    value : 'BK0424',
  },
  {
    label : '生物制品',
    value : 'BK1044',
  },
  {
    label : '燃气',
    value : 'BK1028',
  },
  {
    label : '化肥行业',
    value : 'BK0731',
  },
  {
    label : '互联网服务',
    value : 'BK0447',
  },
  {
    label : '多元金融',
    value : 'BK0738',
  },
  {
    label : '化纤行业',
    value : 'BK0471',
  },
  {
    label : '中药',
    value : 'BK1040',
  },
  {
    label : '旅游酒店',
    value : 'BK0485',
  },
  {
    label : '铁路公路',
    value : 'BK0421',
  },
  {
    label : '造纸印刷',
    value : 'BK0470',
  },
  {
    label : '医疗服务',
    value : 'BK0727',
  },
  {
    label : '贵金属',
    value : 'BK0732',
  },
  {
    label : '专用设备',
    value : 'BK0910',
  },
  {
    label : '食品饮料',
    value : 'BK0438',
  },
  {
    label : '农药兽药',
    value : 'BK0730',
  },
  {
    label : '教育',
    value : 'BK0740',
  },
  {
    label : '公用事业',
    value : 'BK0427',
  },
  {
    label : '汽车整车',
    value : 'BK1029',
  },
  {
    label : '煤炭行业',
    value : 'BK0437',
  },
  {
    label : '物流行业',
    value : 'BK0422',
  },
  {
    label : '化学制品',
    value : 'BK0538',
  },
  {
    label : '酿酒行业',
    value : 'BK0477',
  },
  {
    label : '电源设备',
    value : 'BK1034',
  },
  {
    label : '橡胶制品',
    value : 'BK1018',
  },
  {
    label : '美容护理',
    value : 'BK1035',
  },
  {
    label : '装修建材',
    value : 'BK0476',
  },
  {
    label : '钢铁行业',
    value : 'BK0479',
  },
  {
    label : '小金属',
    value : 'BK1027',
  },
  {
    label : '包装材料',
    value : 'BK0733',
  },
  {
    label : '半导体',
    value : 'BK1036',
  },
  {
    label : '航天航空',
    value : 'BK0480',
  },
  {
    label : '医疗器械',
    value : 'BK1041',
  },
  {
    label : '纺织服装',
    value : 'BK0436',
  },
  {
    label : '汽车服务',
    value : 'BK1016',
  },
  {
    label : '能源金属',
    value : 'BK1015',
  },
  {
    label : '工程咨询服务',
    value : 'BK0726',
  },
  {
    label : '风电设备',
    value : 'BK1032',
  },
  {
    label : '通信服务',
    value : 'BK0736',
  },
  {
    label : '光伏设备',
    value : 'BK1031',
  },
  {
    label : '塑料制品',
    value : 'BK0454',
  },
  {
    label : '电机',
    value : 'BK1030',
  },
  {
    label : '家用轻工',
    value : 'BK0440',
  },
  {
    label : '仪器仪表',
    value : 'BK0458',
  },
  {
    label : '非金属材料',
    value : 'BK1020',
  },
  {
    label : '游戏',
    value : 'BK1046',
  },
  {
    label : '采掘行业',
    value : 'BK1017',
  },
  {
    label : '电子化学品',
    value : 'BK1039',
  },
  {
    label : '专业服务',
    value : 'BK1043',
  },
  {
    label : '船舶制造',
    value : 'BK0729',
  },
  {
    label : '保险',
    value : 'BK0474',
  },
]