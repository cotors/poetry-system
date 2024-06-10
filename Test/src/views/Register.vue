<template>
    <div class="register">
     <form class="form-control" :model="data.form">
      <p class="title">注册</p>
      <div class="input-field">
       <input class="input" type="text" v-model="data.form.username"/>
       <label class="label" for="input">请输入账户</label>
      </div>
      <div class="input-field">
       <input class="input" type="password" v-model="data.form.password"/>
       <label class="label" for="input">请输入密码</label>
     </div>
     <div class="input-field">
       <input class="input" type="password" v-model="data.form.rePassword"/>
       <label class="label" for="input">请再次输入密码</label>
     </div>
      <a href="/login" style="text-decoration: none;">已有账户?请登录</a>
      <button type="button" class="submit-btn" @click="register()">确认</button>
     </form>
    </div>
 </template>

 <script setup>
 import request from '@/utils/request';
import { ElMessage } from 'element-plus';
import {reactive} from 'vue'
import { ElNotification } from 'element-plus'
import router from '@/router';

 const data = reactive({
  form:{
    username:'',
    password:'',
    rePassword:''
  }
 })
 const register = () => {
   if(data.form.username === ''){
     ElMessage.error('账号为空')
   }else if(data.form.password === ''){
     ElMessage.error('密码为空')
   }else if(data.form.rePassword === ''){
     ElMessage.error('确认密码为空')
   }else if(data.form.username.length < 3 || data.form.username.length > 10){
    ElMessage.error('账号长度在3~10位数之间')
   }else if(data.form.password.length < 3 || data.form.password.length > 12){
    ElMessage.error('密码长度在3~12位数之间')
   }else if(data.form.password != data.form.rePassword){
    ElMessage.error('两次密码不一致')
   }else{
    request.post('/user/register',data.form).then(result => {
      if(result.code === 200){
           router.push('/login')
           success()
      }else{
           ElMessage.error(result.msg)
      }
    }).catch(error => {
       ElMessage.error(error)
    })
   }
 }

 const success = () => {
  ElNotification({
    title: '温馨提示',
    message: '注册成功',
    type: 'success',
  })
}

</script>
 
 <style scoped>
 .register{
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