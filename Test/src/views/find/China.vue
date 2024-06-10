<template>
    <div class="location">
      <el-row style="width: 100%;height: 75vh;">
        <div class="map" id="mapEchart" style="width:100%;height:100%"></div>
    </el-row>
    </div>
</template>

<script setup>
// 引入工具
import geoJson from "@/assets/guangdong.json"; //该文件路径改成自己项目中的文件路径即可
import * as echarts from "echarts";
import "echarts-gl"; //3D地图插件
import { onMounted } from "vue";
import request from "@/utils/request.js";

// 定义echarts方法
const chartMap = () => {
  var myChart = echarts.init(document.getElementById("mapEchart"));
  // 重点：不要遗漏这句代码！！
  echarts.registerMap("guangdong", geoJson);
  // 图表配置项
  let option = {
    tooltip: {
      show: true,
    },
    //热力图配置项
    visualMap: [
      {
        type: "continuous",
        text: ["地区诗集数量"],
        calculable: true,
        max: 5,
        inPange: {
          color: ["#87aa66", "#eba438", "#d94d4c"],
        },
      },
    ],
    //3D地图配置项
    geo3D: {
      map: "guangdong",
      roam: true,
      itemStyle: {
        color: "#007aff",
        opacity: 0.8,
        borderWidth: 0.4,
        borderColor: "#000",
        // areaColor: '#fff'
      },
      viewControl: {
        // autoRotate: true,
        autoRotateAfterStill: 3,
        distance: 120,
        minAlpha: 5, // 上下旋转的最小 alpha 值。即视角能旋转到达最上面的角度。[ default: 5 ]
        maxAlpha: 90, // 上下旋转的最大 alpha 值。即视角能旋转到达最下面的角度。[ default: 90 ]
        minBeta: -360, // 左右旋转的最小 beta 值。即视角能旋转到达最左的角度。[ default: -80 ]
        maxBeta: 360, // 左右旋转的最大 beta 值。即视角能旋转到达最右的角度。[ default: 80 ]
        animation: true, // 是否开启动画。[ default: true ]
        animationDurationUpdate: 1000, // 过渡动画的时长。[ default: 1000 ]
        animationEasingUpdate: "cubicInOut", // 过渡动画的缓动效果。[ default: cubicInOut ]
      },

      emphasis: {
        disabled: true, //是否可以被选中
        label: {
          //移入时的高亮文本
          show: true,
          color: "#333", //显示字体颜色变淡
          fontSize: 18, //显示字体变大
        },
        itemStyle: {
          color: "#ff7aff", //显示移入的区块变粉色
        },
      },
      label: {
        show: true,
        position: "top",
        color: "#111", //地图初始化区域字体颜色
        fontSize: 14,
        lineHeight: 16,
      },
      shading: "lambert",
      light: {
        //光照阴影
        main: {
          // color: "#fff", //光照颜色
          intensity: 1, //光照强度
          //shadowQuality: 'high', //阴影亮度
          shadow: true, //是否显示阴影
          shadowQuality: "medium", //阴影质量 ultra //阴影亮度
          alpha: 55,
          beta: 10,
        },
        ambient: {
          intensity: 0.7,
        },
      },
      },
      series: [
      //3D柱状图配置项
        {
          name: "诗集数量(alt)",
          type: "bar3D",
          coordinateSystem: "geo3D",
          barSize: 3,
          shading: "lambert",
          opacity: 1,
          bevelSize: 0.2,
          label: {
            show: false,
            formatter: "{a}",
          },
          //自定义的data数组 value中数组的含义:[广东的经度or纬度，要展示的3d柱状图数值大小]
          data: [
            { name: '茂名', value: [110.919229, 22.059751,5] },
            { name: '珠海', value: [113.30,22.1000,5] },
            { name: '广州', value: [113.5553,23.2632,5] },
            { name: '深圳', value: [114.085947, 22.547,5] },
            { name: '湛江', value: [110.00,20.85,5] },
            { name: '韶关', value: [113.591544, 24.801322,5] },
            { name: '汕头', value: [116.708463, 23.37102,5] },
            { name: '佛山', value: [113.022717, 23.028762,5] },
            { name: '江门', value: [112.694942, 22.290431,5] },
            { name: '肇庆', value: [112.272529, 23.551546,5] },
            { name: '梅州', value: [116.112599, 24.279404,5] },
            { name: '汕尾', value: [115.564238, 22.974485,5] },
            { name: '河源', value: [114.897802, 23.946266,5] },
            { name: '阳江', value: [111.875107, 22.059222,5] },
            { name: '清远', value: [112.951227, 24.285022,5] },
            { name: '东莞', value: [113.746262, 22.946237,5] },
            { name: '中山', value: [113.382391, 22.501113,5] },
            { name: '潮州', value: [116.762301, 23.831701,5] },
            { name: '揭阳', value: [116.055733, 23.343778,5] },
            { name: '云浮', value: [111.944439, 22.829801,5] },
            { name: '惠州', value: [114.412599, 23.279404,5] }
          ],
        },
      ],
   };

   request.get('/poetry/location').then(result => {

       for(let i=0;i < option.series[0].data.length;i++){
           for(let j=0;j < result.data.length;j++){
                 if(option.series[0].data[i].name == result.data[j].name){
                      option.series[0].data[i].value[2] = result.data[j].value
                 }
           }
       }
       myChart.setOption(option);
  })

  //让可视化地图跟随浏览器大小缩放
  window.addEventListener("resize", () => {
     myChart.resize();
  });
};



onMounted(() => {
  // 挂载echart
  chartMap();
});
</script>

<style scoped>
.location{
  height: 100%;
  width: 100%;
  /* background-image: url('@/assets/images/back.jpg');
  background-repeat: no-repeat;
  background-size: cover; */
  border-radius: 5px;
  background-color: #FFF5EE;
}
</style>