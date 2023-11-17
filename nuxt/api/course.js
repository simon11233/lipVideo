import request from '@/utils/request'

export default {
  getPageList(page, limit, searchObj) {
    return request({
      url: `/edu/course/cms/${page}/${limit}`,
      method: 'post',
      data:searchObj
    })
  },
  getNestedTreeList(){
    return request({
      url:'/edu/subject',
      method: 'get'
    })
  },
  getInfoById(courseId){
    return request({
      url:`/edu/course/cms/${courseId}`,
      method: 'get'
    })
  }
}
