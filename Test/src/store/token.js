import { defineStore } from "pinia"
import { ref } from "vue"
   //两个参数:
   //    第一个参数：姓名，唯一性
   //    第二个参数：用来定义token状态的函数
   //   返回值:函数

export const useTokenStore = defineStore('token',()=>{
   //1.响应式变量
   const token = ref('')

   //2.更新token的值
   const setToken = (newToken)=>{
        token.value = newToken
   }

   //3.移除token
   const removeToken = ()=>{
        token.value = null
   }

   return {
      token,setToken,removeToken
   }
  },
   {
    persist:true
   })

export default useTokenStore