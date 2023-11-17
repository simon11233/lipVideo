const api_name = "/edu/banner"
import request from '@/utils/request'

export function getPage(page, limit){
  return request({
    url: `http://localhost:8004${api_name}/${page}/${limit}`,
    method: 'get'
  })
}

export function save(data){
  return request({
    url: `http://localhost:8004${api_name}/save`,
    method: 'post',
    data: data
  })
}

export function update(data){
  return request({
    url: `http://localhost:8004${api_name}/save`,
    method: 'post',
    data: data
  })
}

export function remove(id){
  return request({
    url: `http://localhost:8004${api_name}/${id}`,
    method: 'delete'
  })
}

