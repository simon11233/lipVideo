<template>
  <div>
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模板说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="OSS_PATH">点击下载模板</a>
        </el-tag>
      </el-form-item>
      <el-form-item label="选择Excel">
        <el-upload
          :action=BASE_API
          name="file"
          ref="upload"
          accept="application/vnd.ms-excel,.xlsx"
          :on-success=fileUploadSuccess
          :on-error=fileUploadError
          :on-change="showText"
          :disabled=importBtnDisable
          :limit="1"
          :auto-upload="false"
        >
        <el-button slot="trigger" type="primary" size="small">选取文件</el-button>
          <el-button
            v-if="fileUpload"
          :loading="loading"
          style="margin-left: 10px"
          size="small"
          type="success"
          @click="submitUpload">{{fileUploadBtnText}}</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  export default {
    name:'import',
    data(){
      return{
        BASE_API:'http://127.0.0.1:9204/edu/subject/addSubject',
        OSS_PATH:'https://inspur-file.oss-cn-beijing.aliyuncs.com/avatar/2023/09/25/%E8%AF%BE%E7%A8%8B%E5%88%86%E7%B1%BB%E5%88%97%E8%A1%A8%E6%A8%A1%E6%9D%BF.xlsx',
        fileUploadBtnText:'上传文件',
        importBtnDisable:false,
        loading:false,
        fileUpload:false
      }
    },
    methods:{
      fileUploadSuccess(response){
        if(response.success === true){
          this.fileUploadBtnText='导入成功'
          this.loading=false
          this.$message({
            type:'success',
            message:response.message
          })
        }
      },
      fileUploadError(response){
        this.fileUploadBtnText='导入失败'
        this.loading=false
        this.$message({
          type:'error',
          message:'导入失败'
        })
      },
      showText(){
        this.fileUpload=true
      },
      submitUpload(){
        this.fileUploadBtnText='正在上传'
        this.loading=true
        this.importBtnDisable=true
        this.$refs.upload.submit()
      }
    }
  }
</script>
