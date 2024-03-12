import request from '@/utils/request'

export function getBusinessInfo(name) {
  const data = {
    name
  }
  return request({
    'url': '/system/request/listRequest',
    'method': 'get',
    'data': data
  })
}

// 获取用户详细信息
export function getLatesInfo() {
  return request({
    'url': '/system/latestInfo/listRequest',
    'method': 'get'
  })
}
