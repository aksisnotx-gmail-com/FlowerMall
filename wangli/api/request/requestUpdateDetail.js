import request from '@/utils/request'
import upload from '@/utils/upload'

export function getRequestInfo(id) {
  return request({
    'url': '/system/request/' + id,
    'method': 'get'
  })
}
export function uploadTag(data) {
  return upload({
    url: '/system/request/uploadTag',
    name: data.name,
    filePath: data.filePath
  })
}

export function uploadFile(data) {
  return upload({
    url: '/system/request/uploadFiles',
    name: data.name,
    filePath: data.filePath
  })
}
export function updateRequestInfo(data) {
  return request({
    url: '/system/request',
    method: 'put',
    data: data
  })
}