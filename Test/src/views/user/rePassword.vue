<template>
    <el-row :gutter="10">
        <el-col :span="24">
            <el-card>
                <el-form :model="data" label-width="auto">
                    <el-form-item label="账号">
                     <el-input v-model="data.username" disabled/>
                    </el-form-item>
                    <el-form-item label="原密码">
                     <el-input v-model="data.oldPassword" type="password" />
                    </el-form-item>
                    <el-form-item label="新密码">
                     <el-input v-model="data.newPassword" type="password"/>
                    </el-form-item>
                    <el-form-item label="确认密码">
                     <el-input v-model="data.rePassword" type="password"/>
                    </el-form-item>
                </el-form>
                <el-button type="info" style="margin: 10px 100px 10px 100px;" @click="reset()">取消</el-button>
                <el-button type="success" @click="rePassword()">确认</el-button>
            </el-card>
        </el-col>
    </el-row>
</template>

<script setup>
import { ElMessage } from 'element-plus';
import { reactive } from 'vue';
import request from '@/utils/request.js';
import { userStore } from '@/store/user.js'
import { ElNotification } from 'element-plus'
import router from '@/router';

const useStore = userStore()

const data = reactive({
    username:'',
    oldPassword:'',
    newPassword:'',
    rePassword:''
})

data.username = useStore.user.username

const reset = () => {
    data.oldPassword = '',
    data.rePassword = '',
    data.newPassword = ''
}

const rePassword = () => {
    data.username = useStore.user.username
    if(data.oldPassword == ''){
        ElMessage.error('原密码为空')
    }else if(data.newPassword == ''){
        ElMessage.error('新密码为空')
    }else if(data.rePassword == ''){
        ElMessage.error('确认密码为空')
    }else if(data.rePassword != data.newPassword){
        ElMessage.error('两次密码不一致')
    }else{
        // let paras = new URLSearchParams()
        // paras.append('username',data.username)
        // paras.append('oldPassword',data.oldPassword)
        // paras.append('newPassword',data.newPassword)
        request.get('/user/updateByPwd',{
            params:{
                username:data.username,
                oldPassword:data.oldPassword,
                newPassword:data.newPassword
            }
        //    paras
            }
        ).then(result => {
          if(result.code === 200){
            ElNotification({
                title: '提示',
                message: '修改成功',
                type: 'success',
            })
            ElMessage.success('请重新登录...')
            router.push('/login')
          }else{
            ElMessage.error('修改失败')
          }
          }).catch(error => {
              ElMessage.error(error)
             })
    }
}

</script>

<style scoped>
 .el-input{
    width: 360px;
 }
</style>