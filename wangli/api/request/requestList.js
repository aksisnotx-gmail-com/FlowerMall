import request from '@/utils/request'
import upload from '@/utils/upload'

export function getRequestInfo() {
  return request({
    'url': '/system/request/listRequest',
    'method': 'get'
  })
}

export function deleteRequestInfo(id) {
  return request({
    'url': '/system/request/'+id,
    'method': 'delete'
  })
}

export function updateRequestInfo(data) {
  return request({
    url: '/system/request',
    method: 'put',
    data: data
  })
}

export function initRequestSign(id) {
  return request({
    'url': '/system/sign/sign/'+id,
    'method': 'get'
  })
}