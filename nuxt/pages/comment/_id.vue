<template>

</template>
<script>
import course from '@/api/course'
import comment from '@/api/commonedu'
export default {
  asyncData({ params, error }) {
     return {courseId: params.id}
  },
  data(){
    return{
      data:{},
      page:1,
      limit:4,
      total:10,
      comment:{
        content:'',
        courseId:''
      },
      courseInfo:{},
      chapterVideoList:[],
      isbuyCourse:false
    }
  },
  created() {
    this.initCourseInfo()
    this.initComment()
  },
  methods:{

  }
}
</script>
