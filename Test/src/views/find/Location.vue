<template>
    <div>
        <el-input v-model="datas.province" placeholder="请输入省份" style="width: 240px;margin-right: 10px;"></el-input>
        <el-input v-model="datas.city" placeholder="请输入市区" style="width: 240px;margin-right: 10px;"></el-input>

        <el-button type="primary" @click="load()">查询</el-button>
        <el-button type="primary" @click="reset()">重置</el-button>
    </div>
    <div style="text-align: right;padding-right: 55px;">
      <el-button type="primary" style="margin: 10px 0px 10px 0px;" @click="save();datas.title='添加诗集'">诗集添加</el-button>
    </div>
    <div>
        <el-table :data="datas.tableData">
            <el-table-column label="报刊名" prop="paperName" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="期数" prop="periods" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="日期" prop="time" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="专栏名" prop="columnName" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="版面" prop="board" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="副刊名" prop="supplementName" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="文体" prop="literary" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="作者" prop="author" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="作品名" prop="workName" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="内容简介" prop="content" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="图片" prop="avatar" show-overflow-tooltip="true">
               <template #default="{ row }">
                  <img v-if="row.avatar" :src="row.avatar" style="width: 50px;height: 50px;border-radius: 5px;">
                  <img v-else src="@/assets/images/mo.jpg" style="width: 50px;height: 50px;border-radius: 5px">
               </template>
            </el-table-column>
            <el-table-column label="操作">
                <template #default="{ row }">
                  <el-button type="primary" :icon="Edit" circle @click="update(row);datas.title='修改诗集'"/>
                    <el-button type="danger" :icon="Delete" circle @click="cancel(row)"/>
                </template>
            </el-table-column>
        </el-table>

        <div v-if="datas.total" style="margin-top: 20px;">
            <el-pagination background layout="prev, pager, next" @change="load()" v-model:current-page="datas.pageNum" v-model:page-size="datas.pageSize" :total="datas.total" />
        </div>

        <el-dialog v-model="disviable" style="width: 800px;height: 1300px;margin-top: 30px;">
            <div class="font">
                <text>{{datas.title}}</text>
            </div>
            <div>
            <el-input v-model="datas.form.paperName" placeholder="请输入报刊名"></el-input>
            </div>
            <div>
            <el-input v-model="datas.form.periods" placeholder="请输入期数(数字)"></el-input>
            </div>
            <div>
            <el-input v-model="datas.form.time" placeholder="请输入日期(年-月-日)"></el-input>
            </div>
            <div>
            <el-input v-model="datas.form.columnName" placeholder="请输入专栏名"></el-input>
            </div>
            <div>
            <el-input v-model="datas.form.board" placeholder="请输入版面"></el-input>
            </div>
            <div>
            <el-input v-model="datas.form.supplementName" placeholder="请输入副刊名"></el-input>
            </div>
            <div>
            <el-input v-model="datas.form.literary" placeholder="请输入文体"></el-input>
            </div>
            <div>
            <el-input v-model="datas.form.author" placeholder="请输入作者"></el-input>
            </div>
            <div>
            <el-input v-model="datas.form.workName" placeholder="请输入作品名"></el-input>
            </div>
            <div>
          <span style="display: flex;justify-content: center;align-items: center;">文学类型:</span>
          <el-radio-group v-model="datas.form.liberary" style="margin-bottom: 10px;margin-left: 5px">
            <el-radio value="文学" border>文学</el-radio>
            <el-radio value="非文学" border>非文学</el-radio>
          </el-radio-group>
        </div>
        <div>
          <span style="display: flex;justify-content: center;align-items: center;"></span>
          <el-radio-group v-model="datas.form.subject" style="margin-left: 5px">
            <el-radio value="个人编写" border style="margin-bottom: 5px">个人编写(文学)</el-radio>
            <el-radio value="团队" border>团队(文学)</el-radio>
            <el-radio value="工人" border>工人(非文学)</el-radio>
            <el-radio value="农民" border>农民(非文学)</el-radio>
            <el-radio value="政府主导" border>政府主导(非文学)</el-radio>
          </el-radio-group>
        </div>
        <div>
           <el-input v-model="datas.form.province" placeholder="请输入省份"></el-input>
        </div>
        <div>
           <el-input v-model="datas.form.city" placeholder="请输入城市"></el-input>
        </div>
        
            <div class="area">
            <el-input v-model="datas.form.content" placeholder="内容简介" :autosize="{ minRows: 2, maxRows: 6 }" type="textarea"></el-input>
            </div>
            <el-upload 
                  class="avatar-uploader"
                  action="http://localhost:8080/poetry/upload"
                  :on-success="uploadAvatar"
                  >
                <img v-if="datas.form.avatar" :src="datas.form.avatar" class="avatar">
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
           </el-upload>
            <el-button type="info" style="margin: 10px 150px 0px 150px" @click="stop()">取消</el-button>
            <el-button type="success" style="margin: 10px 100px 0px 150px" @click="datas.title=='添加诗集'?insert():renew()">确认</el-button>
        </el-dialog>

    </div>
</template>

<script setup>
import {Message,Edit,Delete} from '@element-plus/icons-vue'
import { reactive,ref } from 'vue'
import request from '@/utils/request.js';
import { ElMessage,ElMessageBox,ElNotification } from 'element-plus';

const disviable = ref(false)

const datas = reactive({
    tableData:[],
    total:1,
    pageNum:1,
    pageSize:5,
    province:'',
    city:'',
    form:{}
})

const load = () => {
  request.get('/location/find',{
    params:{
      province:datas.province,
      city:datas.city,
      pageNum:datas.pageNum,
      pageSize:datas.pageSize
    }
  }).then(result => {
     if(result.code === 200){
        datas.tableData = result.data.list
        datas.total = result.data.total
        if(datas.tableData == null || datas.tableData == ''){
          ElMessage.error("查询失败,请重新查询...")
        }
     }else{
          ElMessage.error("查询失败,请重新查询...")
     } 
  })
}

load()

const reset = () => {
  datas.province = '',
  datas.city = '',
  load()
}

const save = () => {
  datas.form = {}
  disviable.value = true
}

const stop = () => {
   disviable.value = false
   ElNotification({
    title: '提示',
    message: '取消编辑',
    type: 'info',
  })
}

const uploadAvatar = (file) => {
  datas.form.avatar = file.data
}

const update = (row) => {
  disviable.value = true
  datas.form.id = row.id
  datas.form.paperName = row.paperName
  datas.form.periods = row.periods
  datas.form.time = row.time
  datas.form.columnName = row.columnName
  datas.form.board = row.board
  datas.form.supplementName = row.supplementName
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

const insert = () => {
  request.post('/location/insert',datas.form).then(result => {
     if(result.code === 200){
      ElNotification({
        title: '提示',
        message: '添加成功',
        type: 'success',
      })
      disviable.value = false
      load()
     }else{
       ElMessage.err('添加失败')
     }
  }).catch(err => {
    ElNotification({
    title: '提示',
    message: '添加失败',
    type: 'error',
    })
    disviable.value = false
  })
}

const renew = () => {
    request.put('/location/update',datas.form).then(result => {
      if(result.code === 200){
        ElNotification({
          title: '提示',
          message: '修改成功',
          type: 'success',
        })
        disviable.value = false
        load()
      }else{
        ElNotification({
          title: '提示',
          message: '修改失败',
          type: 'error',
        })
      }
    }).catch(err => {
      ElNotification({
        title: '提示',
        message: '修改失败',
        type: 'error',
      })
    })
}


const cancel = (row) => {
  ElMessageBox.confirm(
    '是否确认要删除该诗集吗?',
    'Warning',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(() => {
      request.delete('/location/delete/' + row.id).then(result => {
        if(result.code === 200){
          ElNotification({
            title: '提示',
            message: '删除成功',
            type: 'success',
          })
          disviable.value = false
          load()
        }else{
          ElNotification({
            title: '提示',
            message: '添加失败',
            type: 'error',
          })
        }
      }).catch(err => {
          ElMessage.error(err)
      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '取消删除',
      })
    })
}

</script>

<style scoped>
.el-dialog  .el-input{
   width: 360px;
   margin: 10px 0px;
}
.font{
  font-size: 24px;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}
.el-dialog  div{
  display: flex;
  margin-left: 25px;
}
.area{
  margin: 10px 50px 10px 0px !important;
}
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
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




