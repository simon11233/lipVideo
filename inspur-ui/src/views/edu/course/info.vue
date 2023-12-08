<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>
    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px">
      <el-step title="填写课程基本信息"></el-step>
      <el-step title="创建课程大纲"></el-step>
      <el-step title="提交审核"></el-step>
    </el-steps>
    <el-form ref="infoForm" label-width="120px" :model="courseInfo" :rules="infoRules">
      <el-form-item label="课程标题" prop="title">
        <el-col :span="4">
          <el-input v-model="courseInfo.title" placeholder="请输入课程标题"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="课程分类" prop="subjectId">
        <el-select v-model="courseInfo.subjectParentId" @change="subjectLevelOneChanged">
          <el-option v-for="sub in subjectNestedList" :key="sub.id" :label="sub.title" :value="sub.id">
          </el-option>
        </el-select>
        <el-select v-model="courseInfo.subjectId">
          <el-option v-for="subject in subSubjectList" :key="subject.value" :label="subject.title" :value="subject.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="教师" prop="teacherId">
        <el-select v-model="courseInfo.teacherId">
          <el-option v-for="teacher in teacherList" :key="teacher.id" :label="teacher.name" :value="teacher.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="总课时" prop="lessonNum">
          <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请输入总课时"></el-input-number>
      </el-form-item>
      <el-form-item label="课程简介" prop="description">
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>
      <el-form-item label="课程封面" prop="cover">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_URL"
          :auto-upload="true"
          class="avatar-uploader"
          >
          <img alt="" :src="courseInfo.cover">
        </el-upload>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="请输入课程定价"></el-input-number>
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并进入下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import {saveCourseInfo,getById} from "@/api/edu/course";
import {getNestedTreeList} from "@/api/edu/subject";
import {getList} from "@/api/edu/teacher";
import Tinymce from "@/components/Tinymce"
import { Message } from 'element-ui'
  export default {
    name:'info',
    components:{ Tinymce },
    data(){
      return{
        courseInfo:{
          title: '',
          subjectParentId:'',
          subjectId: '',
          teacherId: '',
          lessonNum: 0,
          description: '',
          cover: 'https://inspur-file.oss-cn-beijing.aliyuncs.com/cover/defalut.jpg',
          price: 0
        },
        courseId:'',
        teacherList: [],
        subjectNestedList: [],//一级分类列表
        subSubjectList: [], //二级分类列表
        saveBtnDisabled:false,
        BASE_URL: '/oss/file/upload?host=cover',
        infoRules: {
          title:[{required: true, message: '请输入课程标题'}],
          subjectId:[{required: true, message: '请选择课程分类'}],
          teacherId:[{required: true, message: '请选择教师'}],
        }
      }
    },
    mounted() {
      if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id
        getById(id).then(res=>{
          this.courseInfo = res.data.item
        })
      }
      this.loadData()
    },
    methods:{
      loadData(){
        getNestedTreeList().then(res=>{
          this.subjectNestedList = res.data.items
        })
        getList().then(res=>{
          this.teacherList = res.data.rows
        })
      },
      subjectLevelOneChanged(value){
        let list = ''
        this.subjectNestedList.forEach(function(item){
            if(item.id===value){
              list = item.children
            }
        })
        this.subSubjectList = list
        this.courseInfo.subjectId = ''
      },
      handleAvatarSuccess(res,file){
        console.log(res)
        console.log(URL.createObjectURL(file.raw))
        this.courseInfo.cover = res.data.url
      },
      beforeAvatarUpload(file){
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
          }
        if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
          }
          return isJPG && isLt2M
      },
      next(){
        this.$refs.infoForm.validate((valid)=>{
          if(valid){
            saveCourseInfo(this.courseInfo).then(res =>{
              this.courseId = res.data.courseId
              this.$router.push({path:'/edu/course/chapter/'+this.courseId})
            })
          }else {
            console.log(valid)
            alert("表单信息错误")
          }
        })
      }
    }
  }
</script>
<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>
