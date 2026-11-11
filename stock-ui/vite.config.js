import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5175,
    proxy: {
      "/api": {
        target: "http://127.0.0.1:8084", // 后台服务地址
        changeOrigin: true, // 是否修改请求源
        rewrite: (path) => path.replace(/^\/api/, ""), // 重写路径
      },
    },
  },
});
