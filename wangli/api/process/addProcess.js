import request from '@/utils/request'
import upload from '@/utils/upload'

export function uploadTag(data) {
  return upload({
    url: '/system/process/uploadTag',
    name: data.name,
    filePath: data.filePath
  })
}

export function uploadFile(data) {
  return upload({
    url: '/system/process/uploadFiles',
    name: data.name,
    filePath: data.filePath
  })
}

export function saveProcess(data) {
  return request({
    url: '/system/process',
    method: 'post',
    data: data
  })
}
