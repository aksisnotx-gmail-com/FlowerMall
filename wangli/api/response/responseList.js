import request from '@/utils/request'
import upload from '@/utils/upload'

export function getResponseInfo() {
  return request({
    'url': '/system/response/listResponse',
    'method': 'get'
  })
}

export function deleteBusinessResponseInfo(id) {
  return request({
    'url': '/system/response/'+id,
    'method': 'delete'
  })
}