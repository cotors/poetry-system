<template>
  <div>
    <el-container>
      <el-aside class="aside">
        <div class="logo">
            <text>新诗选集</text>
        </div>
        <el-menu class="menu"
                  router
                  active-text-color="#87CEFF"
                  default-active="/home"
                  >
           <el-menu-item index="/home">
               <el-icon><House/></el-icon>
               <span>首页</span>
           </el-menu-item>


           <el-sub-menu index="1">
                <template #title>
                   <el-icon><View/></el-icon>
                   <span>数据视觉化</span>
                </template>
                <el-menu-item index="/data">
                      <el-icon><PieChart/></el-icon>
                      <span>信息图表</span>
                </el-menu-item>
                <el-menu-item index="/china">
                      <el-icon><LocationFilled/></el-icon>
                      <span>地理位置图</span>
                </el-menu-item>
           </el-sub-menu>

           <el-sub-menu index="2">
                <template #title>
                   <el-icon><Avatar/></el-icon>
                   <span>社会身份分类</span>
                </template>
                <el-menu-item index="/status">
                  <el-icon><coin/></el-icon>
                  <span>作者身份查询</span>
                </el-menu-item>
                <el-menu-item index="/author">
                  <el-icon><coin/></el-icon>
                  <span>作者信息查询</span>
                </el-menu-item>
           </el-sub-menu>

           <el-sub-menu index="3">
               <template #title>
                   <el-icon><Timer/></el-icon>
                   <span>时间查询分类</span>
               </template>
               <el-menu-item index="/publish">
                   <el-icon><coin/></el-icon>
                   <span>出版时间查询</span>
               </el-menu-item>
               <el-menu-item index="/time">
                   <el-icon><coin/></el-icon>
                   <span>时间段查询</span>
               </el-menu-item>
           </el-sub-menu>

           <el-sub-menu index="4">
               <template #title>
                   <el-icon><LocationInformation/></el-icon>
                   <span>地域查询分类</span>
               </template>
               <el-menu-item index="/vision">
                   <el-icon><coin/></el-icon>
                   <span>国家视野查询</span>
               </el-menu-item>
               <el-menu-item index="/location">
                  <el-icon><coin/></el-icon>
                  <span>地区查询</span>
               </el-menu-item>
           </el-sub-menu>
          
           <el-sub-menu index="5">
               <template #title>
                   <el-icon><Menu/></el-icon>
                   <span>用户信息</span>
               </template>
                <el-menu-item index="/manager">
                   <el-icon><User/></el-icon>
                   <span>用户管理</span>
               </el-menu-item>
               <el-menu-item index="/persion">
                   <el-icon><User/></el-icon>
                   <span>用户信息修改</span>
               </el-menu-item>
               <el-menu-item index="/rePassword">
                   <el-icon><Lock/></el-icon>
                   <span>修改密码</span>
               </el-menu-item>
           </el-sub-menu>
           <el-menu-item>
               <el-icon><Delete/></el-icon>
               <span @click="dig()">退出登录</span>
           </el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <el-header>
            <div class="header">
                <div>
                    <img style="width:80px;height:80px;border-radius:50%;" src="@/assets/images/poetry.png">
                </div>
                <div style="display: flex;align-items: center;">
                    <img v-if="user.avatar" :src="user.avatar" style="width:80px;height:80px;border-radius:50%;">
                    <img v-else style="width: 70px;height: 70px;border-radius: 50%;" src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png">
                    <div>{{ user.name }}</div>
                </div>
            </div>
        </el-header>
        <el-main class="card">
            <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {userStore} from '@/store/user.js'
import {useTokenStore} from '@/store/token.js'
import request from '@/utils/request'
import { onMounted, onUnmounted } from 'vue'

const userStores = userStore()
const useToken = useTokenStore()

const user = userStores.user

const dig = () => {
   useToken.removeToken()
   load()
}

const load = () => {
  request.get('user/all').then(result => {
      console.log(result.data); 
  })
}

load()

function handleBeforeUnload() {
      useToken.removeToken()
    }

onMounted(() => {
    window.addEventListener('beforeunload', handleBeforeUnload)
})

onUnmounted(() => {
    window.removeEventListener('beforeunload', handleBeforeUnload)
})

</script>

<style scoped>
  .logo{
    height: 80px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 24px;
  }
  .aside{
    height: 100vh;
    width: 200px;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0,0,0,.1);
  }
  .el-menu{
    border: none;
    overflow: hidden;
  }
  .card{
    margin: 5px 5px 5px 5px;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0,0,0,.1);
  }
  .el-header{
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0,0,0,.1);
    margin: 5px 5px 0px 5px;
    height: 100px;
  }
  .header{
    display: flex;
    justify-content:space-between;
  }
  .header div{
    margin: 10px 0px 0px 10px;
  }
</style>