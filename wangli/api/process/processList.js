import request from '@/utils/request'
import upload from '@/utils/upload'

export function getProcess() {
  return request({
    url: '/system/process/listProcess',
    'method': 'get'
  })
}

export function getProcessByRequestId(requestId) {
  return request({
    url: '/system/process/listByRequestId/'+requestId,
    'method': 'get'
  })
}

export function deleteProcess(id) {
  return request({
    url: '/system/process/'+id,
    'method': 'delete'
  })
}
export function initProcessSign(id) {
  return request({
    'url': '/system/sign/sign/'+id,
    'method': 'get'
  })
}