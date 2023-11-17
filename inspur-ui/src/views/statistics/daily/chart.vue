<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-select v-model="searchObj.type" clearable placeholder="请选择">
          <el-option label="学员登录数统计" value="login_num"/>
          <el-option label="学员注册数统计" value="register_num"/>
          <el-option label="课程播放数统计" value="video_view_num"/>
          <el-option label="每日课程数统计" value="course_num"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchObj.begin"
          type="date"
          placeholder="选择开始日期"
          value-format="yyyy-MM-dd"/>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="searchObj.end"
          type="date"
          placeholder="选择结束日期"
          value-format="yyyy-MM-dd"/>
      </el-form-item>
      <el-button type="primary" :disabled="btnDisabled" icon="el-icon-search" @click="showChart()"></el-button>
    </el-form>
    <div class="chart-container">
      <div id="chart" class="chart" style="height:500px;width:100%" />
    </div>
  </div>
</template>
<script>
import echarts from 'echarts'
import daily from '@/api/sta'
export default {
  data(){
    return{
      searchObj:{
        type:'',
        begin:'',
        end:''
      },
      btnDisabled:false,
      chart: null,
      title: '',
      xData: [],
      yData: []
    }
  },
  mounted() {

  },
  methods:{
    showChart(){
      this.initChartData()
    },
    initChartData(){
      daily.showChart(this.searchObj).then(res=>{
        this.yData = res.data.dataList
        this.xData = res.data.dateList

        switch (this.searchObj.type){
          case "register_num":
            this.title='学员注册数统计'
            break;
          case "login_num":
            this.title='学员登录数统计'
            break;
          case "video_view_num":
            this.title='课程播放数统计'
            break;
          case "course_num":
            this.title='每日课程数统计'
            break;
        }
        this.setChart()
      })
    },
    setChart(){
      this.chart = echarts.init(document.getElementById('chart'))
      let options = {
        xAxis: {
          type: 'category',
          data: this.xData
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: this.yData,
          type: 'line'
        }]
      }
      this.chart.setOption(options)
    },
  }
}
</script>
