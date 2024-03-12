import request from '@/utils/request'

export function getRequestInfo(id) {
  return request({
    'url': '/public/request/detail/' + id,
    'method': 'get'
  })
}

export function getResponseInfo(id) {
  return request({
    'url': '/public/request/getResponseListByRequestId/'+id,
    'method': 'get'
  })
}