import { hhmmOption } from './dataOption'

// 构建从 selectDate 到当前时间的时间线（按 hhmmOption 片段）//不包含周末
export const buildRewindTimeline = (startDate) => {
    if (!startDate) return [];
    const result = [];
    const now = new Date();
    now.setHours(0, 0, 0, 0); // 统一为当天0点，避免时分秒干扰
    let dateIndex = new Date(startDate);
    console.log(dateIndex)
    while (dateIndex <= now) {
      const day = dateIndex.getDay();
      // 跳过周六(6)与周日(0)
      if (day !== 0 && day !== 6) {
        const dateStr = dateIndex.toISOString().slice(0, 10);
        // for (const hhmm of hhmmOption) {
            // result.push({ date: dateStr, hhmm });
        // }
        result.push({ date: dateStr, hhmm:'15:00' });
      }
      dateIndex = new Date(dateIndex.setDate(dateIndex.getDate() + 1));//下一天
    }
    return result;
  };