import request from '@/utils/request'
const api_name = '/edu/teacher'
export function getPageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'get',
      params: searchObj
    })
}

export function getList(){
    return request({
      url: `${api_name}/getList`,
      method: 'get'
    })
}


export function removeById(teacherId) {
    return request({
      url: `${api_name}/${teacherId}`,
      method: 'delete'
    })
}

export function save(teacher) {
     return request({
       url: api_name,
       method: 'post',
       data: teacher
     })
}

export function getById(id) {
     return request({
       url: `${api_name}/${id}`,
       method: 'get'
     })
}

export function updateById(teacher) {
     return request({
       url: `${api_name}/${teacher.id}`,
       method: 'put',
       data: teacher
     })
}

