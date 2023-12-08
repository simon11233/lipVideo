import request from '@/utils/request'
const api_name = '/admin/vod/video'

export function removeById(id){
  return request({
    url:`${api_name}/${id}`,
    method: 'DELETE'
  })
}

export function getVideoInfoById(id){
  return request({
    url:`${api_name}/${id}`,
    method: 'GET'
  })
}
