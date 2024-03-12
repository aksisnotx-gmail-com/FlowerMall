import request from '@/utils/request'
import upload from '@/utils/upload'

export function getRequestInfoList(data) {
  return request({
    'url': '/public/request/getRequest',
    'method': 'get',
    data: data
  })
}

export function getLatesInfo() {
  return request({
    'url': '/public/latestInfo/listRequest',
    'method': 'get'
  })
}