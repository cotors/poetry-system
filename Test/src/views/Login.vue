<template>
   <div class="login">
    <form class="form-control" :model="data.form">
     <p class="title">登录</p>
     <div class="input-field">
      <input class="input" type="text" v-model="data.form.username"/>
      <label class="label" for="input">请输入账户</label>
     </div>
     <div class="input-field">
      <input class="input" type="password" v-model="data.form.password"/>
      <label class="label" for="input">请输入密码</label>
     </div>
     <div class="input-field" style="display: flex;">
        <input class="input" style="width: 60%;" v-model="data.form.tcom"/>
        <div @click="click_tcom" style="display: flex;margin: 15px 0px 0px 10px;">
           <tcom :identifyCode="poro" ref="tc"/>
        </div>
        <label class="label" for="input">请输入验证码</label>
     </div>
     <a href="/register" style="text-decoration: none;">没有账户?请注册</a>
     <button type="button" class="submit-btn" @click="login">登录</button>
    </form>
   </div>
</template>

<script setup>
import {reactive} from 'vue'
import request from '@/utils/request.js'
import { ElMessage } from 'element-plus';
import router from '@/router/index.js';
import {userStore} from '@/store/user.js';
import {useTokenStore} from '@/store/token.js'

import tcom from '@/views/user/tcom.vue'
import {ref,onMounted} from 'vue'
var poro = "1235"

const userStores = userStore()
const useToken = useTokenStore()
const data = reactive({
  form:{
    username:'',
    password:'',
    tcom:''
  }
})

const login = () => {
   if(data.form.username.length == ''){
     ElMessage.error('账号为空')
   }else if(data.form.password.length == ''){
     ElMessage.error('密码为空')
   }else if(data.form.tcom == ''){
     ElMessage.error('验证码为空')
   }else if(data.form.tcom != poro){
     ElMessage.error('验证码错误')
   }else{
    request.post('/user/login',data.form).then(result => {
     if(result.code === 200){
       request.get('/user/select/' + data.form.username).then(result => {
           userStores.setUserStore(result.data)
       })
        useToken.setToken(result.data)
        ElMessage.success('登录成功')
        router.push('/')
     }else{
        ElMessage.error(result.msg?result.msg:'登录失败')
     }
    }).catch(error => {
       ElMessage.error(error)
    })
   }
}



const randomNum=(min, max)=>{
    return Math.floor(Math.random() * (max - min) + min)
}
const tc = ref(null)
const identifyCodes = '1234567890abcdefghijklmnopqrstuvwxyz';//验证码输入字串
const click_tcom=()=>{
    poro=""
    makeCode(identifyCodes, 4);
    tc.value.setIdentifyCode(poro)
}
onMounted(()=>{
    click_tcom()
})
 // 生成随机验证码
const makeCode=(o, l)=> {
    var numCode = ""
    for (let i = 0; i < l; i++) {
        numCode += identifyCodes[
        randomNum(0,identifyCodes.length)
      ]
    }
    poro = numCode
}


</script>

<style scoped>
 .login{
    display: flex;
    justify-content: center;
    align-items: center;
    background-image: url('@/assets/images/1.jpg');
    background-repeat: no-repeat;
    background-size: cover;
    height: 100vh;
 }
.form-control {
  margin: 20px;
  background-color: #ffffff;
  box-shadow: 0 15px 25px rgba(0, 0, 0, 0.6);
  width: 400px;
  display: flex;
  justify-content: center;
  flex-direction: column;
  gap: 10px;
  padding: 25px;
  border-radius: 8px;
  opacity: 0.8;
}
.title {
  font-size: 28px;
  font-weight: 800;
  text-align: center;
}
.input-field {
  position: relative;
  width: 100%;
}

.input {
  margin-top: 15px;
  width: 100%;
  outline: none;
  border-radius: 8px;
  height: 45px;
  border: 1.5px solid #ecedec;
  background: transparent;
  padding-left: 10px;
}
.input:focus {
  border: 1.5px solid #2d79f3;
}
.input-field .label {
  position: absolute;
  top: 25px;
  left: 15px;
  color: #ccc;
  transition: all 0.3s ease;
  pointer-events: none;
  z-index: 2;
}
.input-field .input:focus ~ .label,
.input-field .input:valid ~ .label {
  top: 5px;
  left: 5px;
  font-size: 12px;
  color: #2d79f3;
  background-color: #ffffff;
  padding-left: 5px;
  padding-right: 5px;
}
.submit-btn {
  margin-top: 30px;
  height: 55px;
  background: #f2f2f2;
  border-radius: 11px;
  border: 0;
  outline: none;
  color: #ffffff;
  font-size: 18px;
  font-weight: 700;
  background: linear-gradient(180deg, #363636 0%, #1b1b1b 50%, #000000 100%);
  box-shadow: 0px 0px 0px 0px #ffffff, 0px 0px 0px 0px #000000;
  transition: all 0.3s cubic-bezier(0.15, 0.83, 0.66, 1);
  cursor: pointer;
}

.submit-btn:hover {
  box-shadow: 0px 0px 0px 2px #ffffff, 0px 0px 0px 4px #0000003a;
}

</style>