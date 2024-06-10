<template>
    <div>
        <el-text type="primary" style="text-align: center;font-size: 36px;margin-left: 80px;">用户信息</el-text>
    </div>
    <div class="one">
        账号:<el-input v-model="user.username" placeholder="账号" disabled></el-input><br/>
        用户名:<el-input v-model="user.name" placeholder="用户名"></el-input>
        <el-upload 
                  class="avatar-uploader"
                  action="http://localhost:8080/user/upload"
                  :headers="{'Authorization':useToken.token}"
                  :on-success="uploadAvatar"
                  >
                <img v-if="user.avatar" :src="user.avatar" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
        <el-button type="primary" @click="flush()">重置</el-button>
        <el-button type="success" @click="update()">提交</el-button> 
    </div>
</template>

<script setup>
import {userStore} from '@/store/user.js'
import request from '@/utils/request';
import { ElMessage } from 'element-plus';
import {useTokenStore} from '@/store/token.js'

const useToken = useTokenStore()

const userStores = userStore()

const user = userStores.user

const uploadAvatar = (file) => {
   user.avatar = file.data
}

const update = () => {
    request.put('/user/update',user).then(result => {
        if(result.code === 200){
            ElMessage.success('修改成功')
        }else{
            ElMessage.error(result.error)
        }
    }).catch(error => {
         ElMessage.error(error)
    })
}

const flush = () => {
    user.name = '',
    user.avatar = ''
}

</script>

<style scoped>
.one .el-button{
    margin: 10px 50px 0px 50px;
}
.one .el-input{
      width: 300px;
      margin: 10px 0px 10px 10px;
  }
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.drawer .el-input{
    margin: 10px 0px 10px 10px;
    width: 400px;
 }
</style>

<style>
 .avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
  margin: 10px 0px 0px 70px;
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>