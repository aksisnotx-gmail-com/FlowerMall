import request from '@/utils/request'

export function getRequestInfo(id) {
  return request({
    'url': '/system/request/agreeDetail/' + id,
    'method': 'get'
  })
}
export function getResponseInfo(id) {
  return request({
    'url': '/system/response/getByRequestId/'+id,
    'method': 'get'
  })
}