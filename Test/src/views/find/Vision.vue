<template>
    
    <div style="text-align: right;padding-right: 55px;">
        <el-button type="primary" style="margin: 10px 0px 10px 0px;">诗集添加</el-button>
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
            <el-table-column label="图片" prop="avatar" show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="操作">
                <template #default="{ row }">
                    <el-button type="primary" :icon="Edit" circle />
                    <el-button type="danger" :icon="Delete" circle />
                </template>
            </el-table-column>
        </el-table>

        <div v-if="datas.total" style="margin-top: 20px;">
            <el-pagination background layout="prev, pager, next" @change="load()" v-model:current-page="datas.pageNum" v-model:page-size="datas.pageSize" :total="datas.total" />
        </div>

    </div>
</template>

<script setup>
import {Message,Edit,Delete} from '@element-plus/icons-vue'
import { reactive,ref } from 'vue'
import request from '@/utils/request.js';

const datas = reactive({
    tableData:[],
    total:1,
    pageNum:1,
    pageSize:5,

})



const load = () => {
    request.get('/poetry/find',{
        params:{
            pageNum:datas.pageNum,
            pageSize:datas.pageSize
        }
    }).then(result => {
        datas.tableData = result.data.list
        datas.total = result.data.total
    })
}

load()


</script>
