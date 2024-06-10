<template>
   <div>
      <el-row :gutter="10">
         <el-col :span="12">
           <el-card>
             <div ref="pillar" style="height: 70vh;width: 100%;"></div>
           </el-card>
         </el-col>
         <el-col :span="12">
           <el-card>
            <div ref="pie" style="height: 70vh;width: 100%;"></div>
           </el-card>
         </el-col>
      </el-row>
   </div>
</template>

<script setup>
// 按需引入 echarts
import * as echarts from "echarts";
import { ref, onMounted } from "vue";
import request from "@/utils/request.js";
import { ElMessage } from "element-plus";

const pillar = ref(null);
const pie = ref(null)

function pillars() {
  // 柱状图
  var pillarChart = echarts.init(pillar.value);
  const pillarOption = {
    title: {
       text: '文学类型数量统计图',
       left: 'center'
    },
    tooltip:{
       trigger: 'axis'
    },
    xAxis: {
        type: 'category',
        data: []
    },
    yAxis: {
        type: 'value'
    },
    series: [
      {
        name: '数量',
        data: [],
        type: 'bar',
        barWidth: '50%'
      }
    ]
  }

    request.get('/poetry/pillar').then(result => {
       if(result.data){
          pillarOption.xAxis.data = result.data?.map(v => v.date) || []
          pillarOption.series[0].data = result.data?.map(v => v.value) || []
          pillarChart.setOption(pillarOption);
       }else{
          ElMessage.error('暂无数据')
       }
    }).catch(error => {
        ElMessage.error(error)
    })
  }

  function pies() {
    var pieCharts = echarts.init(pie.value);

    const pieOption = {
    title: {
      text: '社会主体数量统计图',
      subtext: '社会主体个数',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: '社会类型',
        type: 'pie',
        radius: '50%',
        data: [],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
    }

    request.get('/poetry/pie').then(result => {
        if(result.data){
          pieOption.series[0].data = result.data
          pieCharts.setOption(pieOption)
        }else{
          ElMessage.error('暂无数据')
        }
    }).catch(error => {
        ElMessage.error(error)
    })
    
  }


onMounted(() => {
  pillars()
  pies()
});

</script>


