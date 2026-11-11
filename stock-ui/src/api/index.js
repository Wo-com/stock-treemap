import axios from "axios";

const service = axios.create({
    // 此处的 '/api' 和 vite.config.js 的配置相关联
    baseURL: "/api",
    timeout: 50000,
});

// 请求拦截器（可按需扩展 token 等）
service.interceptors.request.use(
    (config) => {
        return config;
    },
    (error) => Promise.reject(error)
);

// 响应拦截器（统一返回 data）
service.interceptors.response.use(
    (response) => response.data,
    (error) => Promise.reject(error)
);

/**
 * @description 常用请求方法封装
 */
export function get(url, params, config = {}) {
    return service.get(url, { params, ...config });
}
export function post(url, data, config = {}) {
    return service.post(url, data, config);
}
export function put(url, data, config = {}) {
    return service.put(url, data, config);
}
export function del(url, params, config = {}) {
    return service.delete(url, { params, ...config });
}
export function download(url, data, config = {}) {
    return service.post(url, data, { ...config, responseType: "blob" });
}

export default service;