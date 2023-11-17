import request from '@/utils/request'

export default {
  getPageList(page, limit, courseId){
    return request({
      url:`/ucenter/comment/${page}/${limit}`,
      method: 'GET',
      params:{courseId}
    })
  },
  addComment(comment){
    return request({
      url:'/ucenter/comment/save',
      method: 'POST',
      data:comment
    })
  }
}
