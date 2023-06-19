import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
    plugins: [
        vue(),
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    css: {
        preprocessorOptions: {
            scss: {
                additionalData: `@import "./src/scss/global.scss";`
            },
        }

    },
    server: { //主要是加上这段代码
        host: 'localhost',
        port: 8081,
        proxy: {
            '/api': {
                target: 'http://localhost:88',	//实际请求地址
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, '/main')
            },
        }
    }


})
