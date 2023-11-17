import request from '@/utils/request'

export default {
  getPageList(page, limit) {
    return request({
      url: `/edu/teacher/cms/${page}/${limit}`,
      method: 'get',
    })
  },

  getById(id) {
    return request({
      url: `/edu/teacher/cms/${id}`,
      method: 'get'
    })
  }
}
