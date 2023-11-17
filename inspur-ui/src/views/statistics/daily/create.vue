<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="日期">
        <el-date-picker
          type="date"
          placeholder="选择统计日期"
          v-model="day"
          value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-button type="primary" :disabled="btnDisabled" @click="create()">
        生成
      </el-button>
    </el-form>
  </div>
</template>
<script>
import daily from '@/api/sta'
  export default {
    data(){
      return{
        day:'',
        btnDisabled:false
      }
    },
    methods:{
      create(){
        this.btnDisabled=true
        daily.createStatistics(this.day).then(res=>{
          this.btnDisabled = false
          this.$message({
            type: 'success',
            message: '生成成功'
            })
        })
      }
    }
  }
</script>
