const api_name = "/edu/course"
import request from '@/utils/request'

export function getCoursePublishInfoById(id){
  return request({
    url: `${api_name}/course-publish-info/${id}`,
    method: 'get'
  })
}

export function publishCourse(id){
    return request({
      url: `${api_name}/publish-course/${id}`,
      method: 'put'
  })
}


