import request from '@/utils/request'

export default {
  createStatistics(day){
    return request({
      url:`http://localhost:9210/staservice/sta/registerCount/${day}`,
      method: 'POST'
    })
  },
  showChart(searchObj){
    return request({
      url:`http://localhost:9210/staservice/sta/show-chart/${searchObj.begin}/${searchObj.end}/${searchObj.type}`,
      method: 'get'
    })
  }
}
