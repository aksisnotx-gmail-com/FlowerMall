import request from '@/utils/request'
import upload from '@/utils/upload'

export function getProcess(id) {
  return request({
    url: '/system/process/'+id,
    'method': 'get'
  })
}

export function getProcessDetail(id) {
  return request({
    url: '/system/process/detail/'+id,
    'method': 'get'
  })
}
