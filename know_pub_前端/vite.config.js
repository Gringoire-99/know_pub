import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import Components from 'unplugin-vue-components/vite';
import {AntDesignVueResolver} from 'unplugin-vue-components/resolvers';

export default defineConfig({
    plugins: [
        vue(),
        Components({
            resolvers: [AntDesignVueResolver()],
        }),
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    // devServer: {
    //     port: 8081,
    //     proxy: {
    //         //带有/apit标签的请求向目的主机发生请求
    //         '^/api': {
    //             target: 'http://localhost:8080/',
    //             //将/api/替换为空字符串
    //             pathRewrite: {'/api': ''}
    //         }
    //     }
    // },
})
