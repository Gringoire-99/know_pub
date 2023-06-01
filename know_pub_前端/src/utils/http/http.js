import axios from "axios";

function getCookie(name) {
    // 拆分 cookie 字符串
    var cookieArr = document.cookie.split(";");

    // 循环遍历数组元素
    for (var i = 0; i < cookieArr.length; i++) {
        var cookiePair = cookieArr[i].split("=");

        /* 删除 cookie 名称开头的空白并将其与给定字符串进行比较 */
        if (name === cookiePair[0].trim()) {
            // 解码cookie值并返回
            return decodeURIComponent(cookiePair[1]);
        }
    }
    // 如果未找到，则返回null
    return null;
}


function clearLoginInfo() {
    localStorage.clear()
    document.cookie = ''
}

export const http = axios.create({
    baseURL: "/api",
    headers: {
        Authorization: getCookie('Authorization')
    }
});
export const http_no_token = axios.create({
    baseURL: "/api",
});

http.interceptors.response.use(response => {
    if (response.data && response.data.code === 401) { // 401, token失效
        clearLoginInfo()
        location.reload()
    }
    return response
}, error => {
    return Promise.reject(error)
})

export default http
