import { post } from "../index";

// 分页单个数据
export const getOne = (data) => {
  return post(`/marketdata/getOne`, data);
};
