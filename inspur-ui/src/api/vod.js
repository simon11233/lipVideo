import request from '@/utils/request'
const api_name = '/vod/video'

export function removeById(id){
  return request({
    url:`http://localhost:9206${api_name}/${id}`,
    method: 'DELETE'
  })
}

export function getVideoInfoById(id){
  return request({
    url:`http://localhost:9206${api_name}/${id}`,
    method: 'GET'
  })
}
