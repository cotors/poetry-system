<template>
    <div>
        <el-input :prefix-icon="Search" style="width: 250px;padding-right: 10px;" v-model="datas.name"></el-input>
        <el-button type="primary" @click="load()">查询</el-button>
        <el-button type="primary" @click="reSet()">重置</el-button>
    </div>
    <div style="text-align: right;padding-right: 55px;">
        <el-button type="primary" style="margin: 10px 0px 10px 0px;" @click="add();title='添加'">添加</el-button>
    </div>
    <div>
        <el-table :data="datas.tableData">
            <el-table-column label="序号" prop="id"></el-table-column>
            <el-table-column label="账号" prop="username"></el-table-column>
            <el-table-column label="用户名" prop="name"></el-table-column>
            <el-table-column label="头像" prop="avatar">
                <template #default="{row}">
                    <img v-if="row.avatar" :src="row.avatar" style="height: 50px;width: 50px;border-radius: 50%;">
                </template>
            </el-table-column>
            <el-table-column label="创建时间" prop="createTime"></el-table-column>
            <el-table-column label="修改时间" prop="updateTime"></el-table-column>
            <el-table-column label="操作">
                <template #default="{ row }">
                    <el-button type="success" @click="edit(row);title='修改'">修改</el-button>
                    <el-button type="danger" @click="deleted(row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div v-if="datas.total" style="margin-top: 20px;">
            <el-pagination background layout="prev, pager, next" @change="load()" v-model:current-page="datas.pageNum" v-model:page-size="datas.pageSize" :total="datas.total" />
        </div>

        <el-drawer class="drawer" v-model="drawer" :model="datas.form" :title="title">
             <el-input placeholder="账号" v-model="datas.form.username"></el-input>
             <el-input placeholder="用户名" v-model="datas.form.name"></el-input>
             <el-upload 
                  class="avatar-uploader"
                  action="http://localhost:8080/user/upload"
                  :headers="{'Authorization':useToken.token}"
                  :on-success="uploadAvatar"
                  >
                <img v-if="datas.form.avatar" :src="datas.form.avatar" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus/></el-icon>
             </el-upload>
             <el-button style="margin: 10px 0px 0px 150px;" type="success" @click="title=='添加'?save():update()">確定</el-button>
             <el-button style="margin: 10px 0px 0px 20px;" type="danger" @click="del()">取消</el-button>
        </el-drawer>

    </div>
</template>

<script setup>
import request from '@/utils/request';
import {Search} from '@element-plus/icons-vue'
import {reactive,ref} from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage,ElMessageBox  } from 'element-plus';
import {useTokenStore} from '@/store/token.js'

const useToken = useTokenStore()

const drawer = ref(false)

const datas = reactive({
    tableData:[],
    total:1,
    pageNum:1,
    pageSize:5,
    name:'',
    display:false,
    form:{
        username:'',
        name:'',
        avatar:''
    },
    title:''
})


const load = () => {
    request.get('user/selectPage',{
        params:{
           pageNum:datas.pageNum,
           pageSize:datas.pageSize,
           name:datas.name
        }
    }).then(res => {
        datas.tableData = res.data.list
        datas.total = res.data.total
    })
}

const del = () => {
    drawer.value = false
}

const reSet = () => {
    datas.name = ''
    load()
}

const add = () => {
    drawer.value = true
    datas.form = {}
}

const edit = (row) => {
    drawer.value = true
    datas.form.username = row.username
    datas.form.name = row.name
    datas.form.id = row.id
    datas.form.avatar = row.avatar
}

const save = () => {
    if(datas.form.username === ''){
        ElMessage.error('賬號不能為空')
    }else if(datas.form.name === ''){
        ElMessage.error('用戶名不能爲空')
    }else{
        request.post('/user/add',datas.form).then(result => {
            if(result.code === 200){
                drawer.value = false
                ElMessage.success('添加成功')
                load()
            }else{
                ElMessage.error(result.msg)
            }
        }).catch(error => {
            ElMessage.error(error)
        })
    }
 }

 const update = () => {
        request.put('/user/update',datas.form).then(result => {
            if(result.code === 200){
                drawer.value = false
                ElMessage.success('修改成功')
                load()
            }else{
                ElMessage.error(result.msg)
            }
        }).catch(error => {
            ElMessage.error(error)
        })
    }

const deleted = (row) => {
    ElMessageBox.confirm(
    '请问是否要删除该用户名?',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(() => {
        request.delete('/user/delete/' + row.id).then(result => {
           if(result.code === 200){
              drawer.value = false
              load()
           }else{
              ElMessage.error(result.msg)
           }
       }).catch(error => {
              ElMessage.error(error)
       })
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
}

const uploadAvatar  = (file) => {
     datas.form.avatar = file.data
}

load()

</script>

<style scoped>
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
  margin: 10px 0px 0px 125px;
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