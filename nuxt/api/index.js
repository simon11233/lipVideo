import request from '@/utils/request'

export default {
  getList(){
    return request({
      url: '/edu/index/index',
      method: 'GET'
    })
  }
}
