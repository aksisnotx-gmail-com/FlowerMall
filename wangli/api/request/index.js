import request from '@/utils/request'
import upload from '@/utils/upload'

export function getRequestInfoList(data) {
  return request({
    'url': '/system/request/getRequest',
    'method': 'get',
    data: data
  })
}

export function getLatesInfo() {
  return request({
    'url': '/system/latestInfo/listRequest',
    'method': 'get'
  })
}