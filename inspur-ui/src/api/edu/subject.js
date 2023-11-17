const api_name = 'edu/subject'
import request from '@/utils/request'


export function getNestedTreeList() {
   return  request({
     url:api_name,
     method: 'get'
   })
}
