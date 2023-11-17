<template>
  <div class="app-container">
     <el-form label-width="120px">
       <el-form-item label="头像">
         <pan-thumb :image="teacher.avatar"/>
         <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true"></el-button>
         <image-cropper
              v-show="imagecropperShow"
              :width="300"
              :height="300"
              :key="imagecropperKey"
              :url="BASE_API+'/oss/file/upload'"
              field="file"
              @close="close"
              @crop-upload-success="cropSuccess"></image-cropper>
       </el-form-item>
       <el-form-item label="讲师名称">
       <el-input v-model="teacher.name"/>
       </el-form-item>
       <el-form-item label="讲师排序">
       <el-input-number v-model="teacher.sort" controls-position="right" min="0"/>
       </el-form-item>
       <el-form-item label="讲师头衔">
       <el-select v-model="teacher.level" clearable placeholder="请选择">
       <!--
   数据类型一定要和取出的json中的一致，否则没法回填
   因此，这里value使用动态绑定的值，保证其数据类型是number
   -->
       <el-option :value="1" label="高级讲师"/>
       <el-option :value="2" label="首席讲师"/>
       </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>
      <!-- 讲师头像：TODO -->
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary"
                   @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
 import {getById,save,updateById} from "@/api/edu/teacher";
 import ImageCropper from '../../../components/ImageCropper'
 import PanThumb from "../../../components/PanThumb/index.vue";
 const defaultForm = {
   name: '',
   sort: 0,
   level: '',
   career: '',
   intro: '',
   avatar: 'https://inspur-file.oss-cn-beijing.aliyuncs.com/avatar/2023/09/25/beacec54-0565-4e8f-a414-7ed393b2a89d.jpg'
 }
 export default {
   name:'form',
   components: {PanThumb,ImageCropper},
   data(){
     return{
       teacher: {defaultForm},
       saveBtnDisabled: false, // 保存按钮是否禁用,
       BASE_API: "http://localhost:9205", // 接口API地址
       imagecropperShow: false, // 是否显示上传组件
       imagecropperKey: 0 // 上传组件id
     }
   },
   watch:{
    $route(to,from){
      console.log('监视route')
      this.init()
    }
   },
   created(){ // 当页面加载时获取数据
     console.log('created')
     if (this.$route.params && this.$route.params.id) {
       const id = this.$route.params.id
       this.fetchDataById(id)
     }else {
       this.teacher={...defaultForm}
     }
   },
   methods:{
     cropSuccess(data){
        console.log(data)
        this.imagecropperShow = false
        this.teacher.avatar = data.url
        this.imagecropperKey += 1
     },
     close(){
       this.imagecropperShow = false
       this.imagecropperKey += 1
     },
     init(){
       if (this.$route.params && this.$route.params.id) {
         const id = this.$route.params.id
         this.fetchDataById(id)
       }else {
         this.teacher={...defaultForm}
         console.log('11+'+this.teacher.avatar)
       }
     },
     fetchDataById(id) {
       getById(id).then(response => {
         this.teacher = response.data.item
       }).catch((response) => {
         this.$message({
           type: 'error',
           message: '获取数据失败'
         })
       })
     },
     saveOrUpdate() {
       this.saveBtnDisabled = true
       if(!this.teacher.id){
         this.saveData()
       }else {
         this.updateById()
       }
     },
     saveData(){
      save(this.teacher).then(response => {
        return this.$message({
          type:'success',
          message:'保存成功'
        })
      }).then(response => {
        this.$router.push({path:'/edu/teacher'})
      }).catch(response => {
        this.$message({
          type:'error',
          message:'保存失败'
        })
      })
     },
     updateById(){
       this.saveBtnDisabled= true
       updateById(this.teacher).then(response => {
         return  this.$message({
           type:'success',
           message:'修改成功'
         })
       }).then(response => {
         this.$router.push({ path: '/edu/teacher' })
       }).catch((response=>{
         this.$message({
           type:'error',
           message:'修改失败'
         })
       }))
     }
   }
 }
</script>
