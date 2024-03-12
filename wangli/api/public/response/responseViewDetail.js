import request from '@/utils/request'
import upload from '@/utils/upload'

export function getRequestInfo(requestId) {
  return request({
    'url': '/public/request/' + requestId,
    'method': 'get'
  })
}
export function getResponseInfo(id) {
  return request({
    'url': '/public/response/detail/'+id,
    'method': 'get'
  })
}
export function uploadFile(data) {
  return upload({
    url: '/public/response/uploadFiles',
    name: data.name,
    filePath: data.filePath
  })
}
export function saveResponseInfo(data) {
  return request({
    url: '/public/response',
    method: 'post',
    data: data
  })
}