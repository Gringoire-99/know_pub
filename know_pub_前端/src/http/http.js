import axios from "axios";

const http = axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        "Content-type": "application/json"
    }
});
http.defaults.headers.common['Authorization'] = localStorage.getItem('token');
export default http;
