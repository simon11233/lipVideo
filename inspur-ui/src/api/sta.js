import request from '@/utils/request'
const api_name = "/admin/staservice/sta"
export default {
  createStatistics(day){
    return request({
      url:`${api_name}/registerCount/${day}`,
      method: 'POST'
    })
  },
  showChart(searchObj){
    return request({
      url:`${api_name}/show-chart/${searchObj.begin}/${searchObj.end}/${searchObj.type}`,
      method: 'get'
    })
  }
}
