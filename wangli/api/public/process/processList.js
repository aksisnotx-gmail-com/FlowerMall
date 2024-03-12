import request from '@/utils/request'
import upload from '@/utils/upload'

export function getProcessByRequestId(requestId) {
  return request({
    url: '/public/process/listByRequestId/'+requestId,
    'method': 'get'
  })
}