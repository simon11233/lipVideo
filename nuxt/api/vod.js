import request from '@/utils/request'

export default {
  getPlayAuth(vid){
    return request({
      url:`/vod/video/${vid}`,
      method: 'GET'
    })
  },

  getPageList(page, limit) {
    return request({
      url: `/edu/video/${page}/${limit}`,
      method: 'get',
    })
  },
}
