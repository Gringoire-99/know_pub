import axios from "axios";
import {nextTick} from "vue";
import app from "@/main";

const http = axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        "Content-type": "application/json"
    }
});

function setInterceptorToken() {
    http.interceptors.request.use(config => {
        config.headers['token'] = app.config.globalProperties.$cookies.get('token') // 请求头带上token
        return config
    }, error => {
        return Promise.reject(error)
    })
}

http.setInterceptorToken = setInterceptorToken
nextTick(() => {
    setInterceptorToken()
}).then(r => {
}, reason => {
    alert("failed in http.js")
})

export default http;
