import request from '@/utils/request'

export default {
  getList(){
    return request({
      url: 'http://localhost:8004/cms/banner/getAllBanner',
      method: 'GET',
    })
  },

}
