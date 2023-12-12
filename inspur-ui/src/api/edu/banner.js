const api_name = "/cms/banner"
import request from '@/utils/request'

export function getPage(page, limit){
  return request({
    url: `${api_name}/${page}/${limit}`,
    method: 'get'
  })
}

export function save(data){
  return request({
    url: `${api_name}/save`,
    method: 'post',
    data: data
  })
}

export function update(data){
  return request({
    url: `${api_name}/save`,
    method: 'post',
    data: data
  })
}

export function remove(id){
  return request({
    url: `${api_name}/${id}`,
    method: 'delete'
  })
}

