import request from '@/utils/request'
import upload from '@/utils/upload'

export function getProcessInfoList(data) {
  return request({
    'url': '/public/process/getProcess',
    'method': 'get',
    data: data
  })
}
