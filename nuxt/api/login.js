import request from '@/utils/request'

export default {
  submitLogin(userInfo){
    return request({
      url:'/ucenter/member/login',
      method:'POST',
      data: userInfo
    })
  },
  getLoginInfo(){
    return request({
      url:'/ucenter/member/getMemberInfo',
      method:'GET'
    })
  }
}
