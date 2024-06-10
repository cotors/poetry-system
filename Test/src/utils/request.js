import router from "@/router"
import axios from "axios"
import { ElMessage } from "element-plus"
import {useTokenStore} from '@/store/token.js'

const request = axios.create({
    baseURL:'http://localhost:8080',
    timeout:30000
})

request.interceptors.request.use(
    config => {
        const tokenStore = useTokenStore()
        if(tokenStore.token){
            config.headers.Authorization = tokenStore.token
        }
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

request.interceptors.response.use(
    res => {
       if(res.data.code===200){
        return res.data
       }
       return Promise.reject(res.data.msg)
    },
    error => {
        console.log(error.response.status);
        if(error.response.status === 401){
            ElMessage.error("未登录")
            router.push('/login')
        }else{
            ElMessage.error("服务异常")
        }
        return Promise.reject()
    }
)
export default request