  <template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>
    <el-steps :active="3" process-status="wait" align-center style="margin-bottom: 40px">
      <el-step title="填写课程基本信息"></el-step>
      <el-step title="创建课程大纲"></el-step>
      <el-step title="提交审核"></el-step>
    </el-steps>
    <div class="ccInfo">
      <img :src="coursePublish.cover">
      <div class="main">
        <h2>{{coursePublish.title}}</h2>
        <p class="gray"><span>共{{coursePublish.lessonNum}}课时</span></p>
        <p><span>所属分类{{coursePublish.subjectLevelOne}}-{{coursePublish.subjectLevelTwo}}</span></p>
        <p>课程讲师:{{coursePublish.teacherName}}</p>
        <h3 class="red">￥{{coursePublish.price}}</h3>
      </div>
    </div>

    <el-form label-width="120px">
      <el-form-item>
        <el-button @click="previous">返回修改</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="publish">发布课程</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import {getCoursePublishInfoById,publishCourse} from "@/api/edu/publish";
import {saveCourseInfo} from "@/api/edu/course";
import { Message } from 'element-ui'
  export default {
    name:'publish',
    data(){
      return{
        saveBtnDisabled:false,
        courseId: '', // 所属课程
        coursePublish: {}
      }
    },
    mounted() {
      this.init()
    },
    methods:{
      init(){
        if (this.$route.params && this.$route.params.id){
          this.courseId = this.$route.params.id
          console.log(this.courseId)
        }
        getCoursePublishInfoById(this.courseId).then(res=>{
          this.coursePublish = res.data.item
        })
      },
      previous(){
        this.$router.push({path:'/edu/course/chapter/'+this.courseId})
      },
      publish(){
        publishCourse(this.courseId).then(res=>{
          this.$router.push({ path: '/edu/course/list' })
        }).catch(err=>{
          Message.error(err)
        })
      }
    }
  }
</script>
<style scoped>
.ccInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #DDD;
    margin-bottom: 40px;
    position: relative;
}
.ccInfo img {
    background: #d6d6d6;
    width: 500px;
    height: 278px;
    display: block;
    float: left;
    border: none;
}
.ccInfo .main {
  margin-left: 520px;
}
.ccInfo .main h2 {
    font-size: 28px;
    margin-bottom: 30px;
    line-height: 1;
    font-weight: normal;
}
.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}

.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}
.ccInfo .main h3 {
    left: 540px;
    bottom: 20px;
    line-height: 1;
    font-size: 28px;
    color: #d32f24;
    font-weight: normal;
    position: absolute;
 }
</style>
