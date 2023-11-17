const api_name = "/edu/video"
import request from '@/utils/request'

export function saveVideoInfo(videoInfo){
  return request({
    url: `${api_name}/save-video-info`,
    method: 'post',
    data: videoInfo
  })
}

export function getVideoInfoById(id){
  return request({
    url: `${api_name}/video-info/${id}`,
    method: 'get'
  })
}
export function updateVideoInfoById(videoInfo){
  return request({
    url: `${api_name}/update-video-info/${videoInfo.id}`,
    method: 'put',
    data: videoInfo
  })
}
export function removeById(id){
  return request({
    url: `${api_name}/${id}`,
    method: 'delete'
  })
}

export function removeVideo(id){
  return request({
    url: `${api_name}/video/${id}`,
    method: 'delete'
  })
}
