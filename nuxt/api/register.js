import request from '@/utils/request'

export default {
  getMobile(mobile){
    return request({
      url: `/edu/msm/send/${mobile}`,
      method: 'get'
    })
  },
  submitRegister(formItem){
    return request({
      url: '/ucenter/member/register',
      method: 'post',
      data: formItem
    })
  }
}
