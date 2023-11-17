<template>
  <div>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="课程类别">
        <el-select v-model="searchObj.subjectParentId" placeholder="请选择" @change="subjectLevelOneChanged">
          <el-option v-for="subject in subjectNestedList" :key="subject.id" :label="subject.title" :value="subject.id"></el-option>
        </el-select>
        <el-select v-model="searchObj.subjectId" placeholder="请选择">
          <el-option v-for="subject in subSubjectList" :key="subject.id" :label="subject.title" :value="subject.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="课程标题">
        <el-input v-model="searchObj.title" placeholder="课程标题"></el-input>
      </el-form-item>
      <el-form-item label="教师">
          <el-select placeholder="请选择教师" v-model="searchObj.teacherId">
            <el-option v-for="teacher in teacherList" :key="teacher.id" :label="teacher.name" :value="teacher.id">
            </el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
      row-class-name="myClassList">
      <el-table-column label="序号" type="index" width="70" align="center" />
      <el-table-column label="课程信息" width="470" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.cover" :alt="scope.row.title">
            </div>
            <div class="title">
              <a href="">{{scope.row.title}}</a>
              <p>总课时：{{scope.row.lessonNum}}</p>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="gmtCreate" label="创建时间" align="center" :formatter="fmtTime" />
      <el-table-column prop="gmtModified" label="发布时间" align="center" :formatter="fmtTime"/>
      <el-table-column prop="price" label="价格" align="center" width="100" :formatter="fmtPrice"/>
      <el-table-column prop="buyCount" label="付费学员" align="center" width="100" :formatter="fmtBuyCount"/>
      <el-table-column label="操作" align="center" width="150">
        <template slot-scope="scope">
          <router-link :to="'/edu/course/info/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程信息</el-button>
          </router-link>
          <router-link :to="'/edu/course/chapter/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程大纲</el-button>
          </router-link>
          <el-button type="text" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      layout="total, prev, pager, next, jumper"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      @current-change="fetchData"
    />
  </div>
</template>
<script>
import {getPageList,removeById} from '@/api/edu/course'
import {getList} from '@/api/edu/teacher'
import {getNestedTreeList} from '@/api/edu/subject'
export default {
  name:'list',
  data(){
    return{
      listLoading:true,
      list:[],
      total:0,
      page:1,
      limit:10,
      searchObj:{
        subjectParentId:'',
        subjectId:'',
        title:'',
        teacherId:''
      },
      teacherList:[],// 讲师列表
      subjectNestedList:[],// 一级分类列表
      subSubjectList:[], // 二级分类列表,
    }
  },
  created() {
    this.fetchData()
    this.initSubjectList()
    // 获取讲师列表
    this.initTeacherList()
  },
  methods:{
    fetchData(page=1){
      this.page=page
      this.listLoading=true
      getPageList(this.page,this.limit,this.searchObj).then(res=>{
        if (res.success === true) {
          this.list = res.data.rows
          this.total = res.data.total
        }
        this.listLoading = false
      })
    },
    initTeacherList(){
      getList().then(res=>{
        this.teacherList = res.data.rows
      })
    },
    initSubjectList(){
      getNestedTreeList().then(res=>{
        this.subjectNestedList = res.data.items
      })
    },
    subjectLevelOneChanged(value){
      for (let i = 0; i < this.subjectNestedList.length; i++) {
        if (this.subjectNestedList[i].id === value) {
          this.subSubjectList = this.subjectNestedList[i].children
          this.searchObj.subjectId = ''
        }
      }
    },
    removeDataById(id){
      console.log(id)
      this.$confirm('此操作将永久删除该课程，以及该课程下的章节和视频，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        return removeById(id)
      }).then(() => {
        this.fetchData()
        this.$message({
           type: 'success',
           message: '删除成功!'
         })
      }).catch((response) => { // 失败
         if (response === 'cancel') {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
         }
      })
    },
    resetData(){
      this.searchObj = {}
      this.subSubjectList = []
      this.fetchData()
    },
    fmtTime(row, column, value){
      return value.substring(0,10)
    },
    fmtPrice(row, column, value){
      return value === 0 ? '免费' : '¥'+value.toFixed(2)
    },
    fmtBuyCount(row, column, value){
      return value + '人'
    }
  }
}
</script>
<style scoped>
.myClassList .info {
  width: 450px;
  overflow: hidden;
}
.myClassList .info .pic {
  width: 150px;
  height: 90px;
  overflow: hidden;
  float: left;
}
.myClassList .info .pic a {
  display: block;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}
.myClassList .info .pic img {
  display: block;
  width: 100%;
}
.myClassList td .info .title {
  width: 280px;
  float: right;
  height: 90px;
}
.myClassList td .info .title a {
  display: block;
  height: 48px;
  line-height: 24px;
  overflow: hidden;
  color: #00baf2;
  margin-bottom: 12px;
}
.myClassList td .info .title p {
  line-height: 20px;
  margin-top: 5px;
  color: #818181;
}
.demo-form-inline{
  display: block;
  text-align: center;
  margin: 10px;
}
</style>
