import request from '@/utils/request'
const api_name = "/admin/acl"
// 登录
export function login(username, password) {
 // debugger
  return request({
    url: `${api_name}/login`,
    method: 'post',
    data: {
      username,
      password
    }
  })
}

// 获取用户信息
export function getInfo(token) {
  return request({
    url: `${api_name}/index/info`,
    method: 'get',
    params: { token }
  })
}

// 登出
export function logout() {
  //debugger
  return request({
    url: `${api_name}/index/logout`,
    method: 'post'
  })
}

// 获取菜单权限数据
export function getMenu() {
  return request({
    url: `${api_name}/index/menu`,
    method: 'get'
  })
}
