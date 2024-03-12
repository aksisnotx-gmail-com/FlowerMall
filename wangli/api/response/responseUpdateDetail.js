import request from '@/utils/request'
import upload from '@/utils/upload'

export function getRequestInfo(businessId) {
  return request({
    'url': '/system/request/' + businessId,
    'method': 'get'
  })
}
export function getResponseInfo(id) {
  return request({
    'url': '/system/response/'+id,
    'method': 'get'
  })
}
export function uploadFile(data) {
  return upload({
    url: '/system/response/uploadFiles',
    name: data.name,
    filePath: data.filePath
  })
}
export function saveResponseInfo(data) {
  return request({
    url: '/system/response',
    method: 'post',
    data: data
  })
}