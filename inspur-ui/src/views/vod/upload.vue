<template>
  <el-card>
    <el-form>
      <el-form-item label="上传视频">
        <el-upload
          :action=BASE_API
          :on-success="handleVodUploadSuccess"
          :on-remove="handleVodRemove"
          :before-remove="beforeVodRemove"
          :on-exceed="handleUploadExceed"
          :file-list="fileList"
          :limit="1"
          class="upload-demo">
          <el-button size="small" type="primary">上传视频</el-button>
          <el-button size="small" type="primary" @click="remove">上传视频</el-button>
          <el-tooltip placement="right-end">
            <div slot="content">最大支持1G，<br>
              支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
              GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
              MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
              SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
            <i class="el-icon-question"/>
          </el-tooltip>
        </el-upload>
      </el-form-item>
    </el-form>
  </el-card>
</template>
<script>
import { Message } from 'element-ui'
import { removeById } from "@/api/vod";
import axios from "axios";
export default {
  name:'uploadVideo',
  data(){
    return{
      video:{
        title:'',
        sort:'',
        free:false,
        videoSourceId:'',
        videoOriginalName:''
      },
      fileList:[],
      BASE_API:'/vod/video/upload',
      dialogVideoFormVisible:false
    }
  },
  methods:{
    handleVodUploadSuccess(response, file, fileList){
      this.video.videoSourceId = response.data.videoId
      this.video.videoOriginalName = file.name
    },
    handleUploadExceed(files, fileList){
      Message.warning('想要重新上传视频，请先删除已上传的视频')
    },
    handleVodRemove(file, fileList){
      removeById(this.video.videoSourceId).then(res=>{
        this.video.videoSourceId = ''
        this.video.videoOriginalName = ''
        this.fileList = []
        Message.info(res.message)
      })
    },
    beforeVodRemove(file, fileList){
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    editVideo(videoId){
      this.dialogVideoFormVisible = true

    }
  }
}
</script>
