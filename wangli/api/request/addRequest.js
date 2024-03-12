import request from '@/utils/request'
import upload from '@/utils/upload'

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

export function saveRequestInfo(data) {
  return request({
    url: '/system/request',
    method: 'post',
    data: data
  })
}
