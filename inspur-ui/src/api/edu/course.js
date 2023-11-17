const api_name = "/edu/course"
import request from '@/utils/request'


export function saveCourseInfo(courseInfo){
    return  request({
      url:api_name+"/save",
      method: 'POST',
      data: courseInfo
    })
}
export function getById(id){
  return  request({
    url:`${api_name}/getById/${id}`,
    method: 'GET'
  })
}
export function getPageList(page, limit, searchObj) {
  return request({
    url: `${api_name}/${page}/${limit}`,
    method: 'get',
    params: searchObj
  })
}

export function removeById(id) {
  return request({
    url:`${api_name}/${id}`,
    method: 'delete'
  })
}
