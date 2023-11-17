const api_name = "/edu/chapter"
import request from '@/utils/request'

export function getNestedTreeList(courseId){
  return request({
    url: `${api_name}/nested-list/${courseId}`,
    method: 'get'
  })
}

export function removeById(id){
  return request({
    url: `${api_name}/${id}`,
    method: 'delete'
  })
}
export function save(chapter){
  return request({
    url: api_name,
    method: 'post',
    data: chapter
  })
}
export function getById(id){
  return request({
    url:  `${api_name}/${id}`,
    method: 'get'
  })
}
export function updateById(chapter) {
  return request({
    url: `${api_name}/${chapter.id}`,
    method: 'put',
    data: chapter
  })
}
