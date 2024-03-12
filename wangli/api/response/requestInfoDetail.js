import request from '@/utils/request'
import upload from '@/utils/upload'

export function getRequestInfo(id) {
  return request({
    'url': '/system/request/detail/' + id,
    'method': 'get'
  })
}