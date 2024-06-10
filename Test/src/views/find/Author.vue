<template>
    <div>
        <el-input v-model="datas.name" placeholder="请输入作者名" style="width: 240px;margin-right: 10px"></el-input>
        <el-button type="primary" @click="load()">查询</el-button>
        <el-button type="primary" @click="reset()">重置</el-button>
    </div>

    <div>
        <el-table :data="datas.tableData">
            <el-table-column label="序号" prop="id" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="作者" prop="author" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="作品名" prop="workName" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="内容简介" prop="content" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="文学类型" prop="liberary" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="主体类型" prop="subject" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="省份" prop="province" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="市区" prop="city" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="图片" prop="avatar" show-overflow-tooltip="true">
               <template #default="{ row }">
                  <img v-if="row.avatar" :src="row.avatar" style="width: 50px;height: 50px;border-radius: 5px;">
                  <img v-else src="@/assets/images/mo.jpg" style="width: 50px;height: 50px;border-radius: 5px">
               </template>
            </el-table-column>
            <el-table-column label="预览">
                <template #default="{ row }">
                    <el-button type="primary" :icon="Message" circle @click="preview(row)"/> 
                </template>
            </el-table-column>
        </el-table>

        <div v-if="datas.total" style="margin-top: 20px;">
            <el-pagination background layout="prev, pager, next" @change="load()" v-model:current-page="datas.pageNum" v-model:page-size="datas.pageSize" :total="datas.total" />
        </div>

        <el-drawer
            v-model="drawer"
            title="作者信息"
            :direction="direction"
            class="drawer"
        >
          <div>
            <el-input v-model="datas.form.author" placeholder="作者" disabled></el-input>
          </div>
          <div>
            <el-input v-model="datas.form.workName" placeholder="作品名" disabled></el-input>
          </div>
          <div>
            <el-input v-model="datas.form.content" placeholder="内容简介" :autosize="{ minRows: 2, maxRows: 4 }"  type="textarea" disabled></el-input>
          </div>
          <div>
            <el-input v-model="datas.form.liberary" placeholder="文学类型" disabled></el-input>
          </div>
          <div>
            <el-input v-model="datas.form.subject" placeholder="主体" disabled></el-input>
          </div>
          <div>
            <el-input v-model="datas.form.province" placeholder="省份" disabled></el-input>
          </div>
          <div>
            <el-input v-model="datas.form.city" placeholder="市区" disabled></el-input>
          </div>
          <div class="demo-image__preview">
                <el-text class="mx-1" type="primary" style="display: flex">点击查看所有图片</el-text>
                <el-image
                    style="width: 100px; height: 100px"
                    :src="datas.form.avatar"
                    :zoom-rate="1.2"
                    :max-scale="7"
                    :min-scale="0.2"
                    :preview-src-list="srcList"
                    :initial-index="4"
                    fit="cover"
                />
         </div>
        </el-drawer>

    </div>
</template>

<script setup>
import {Message} from '@element-plus/icons-vue'
import { reactive,ref } from 'vue'
import request from '@/utils/request.js';
import { ElMessage } from 'element-plus'


//图片数组,用于展示图片
var srcList = []

console.log(srcList.length);

const datas = reactive({
    tableData:[],
    name:'',
    total:1,
    pageNum:1,
    pageSize:5,
    form:{},
})

const drawer = ref(false)

const preview = (row) => {
    drawer.value = true
    datas.form.literary = row.literary
    datas.form.author = row.author
    datas.form.workName = row.workName
    datas.form.content = row.content
    datas.form.avatar = row.avatar
    datas.form.liberary = row.liberary
    datas.form.subject = row.subject
    datas.form.province = row.province
    datas.form.city = row.city
}

const uploadAvatar = (file) => {
    datas.form.avatar = file.data
}

const load = () => {
    request.get('/poetry/find',{
        params:{
           name:datas.name,
           pageNum:datas.pageNum,
           pageSize:datas.pageSize
        }
    }).then(result => {
        if(result.code == 200){
            datas.tableData = result.data.list
            datas.total = result.data.total
            for(let i=0;i < datas.tableData.length;i++){
                srcList.push(datas.tableData[i].avatar)
            }
            if(datas.tableData == null || datas.tableData == ''){
                ElMessage.error('查询失败,请重新查询...')
            }
        }else{
            ElMessage.error('服务异常')
        }
    })
}

load()




const reset = () => {
    datas.name = ''
    load()
}

</script>

<style scoped>
.drawer div{
    width:300px;
    margin-bottom: 10px;
}
.demo-image__error .image-slot {
  font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
  font-size: 30px;
}
.demo-image__error .el-image {
  width: 100%;
  height: 200px;
}
</style>