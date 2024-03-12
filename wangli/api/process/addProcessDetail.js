import request from '@/utils/request'
import upload from '@/utils/upload'

export function uploadTag(data) {
  return upload({
    url: '/system/processDetail/uploadTag',
    name: data.name,
    filePath: data.filePath
  })
}

export function uploadFile(data) {
  return upload({
    url: '/system/processDetail/uploadFiles',
    name: data.name,
    filePath: data.filePath
  })
}

export function saveProcessDetail(data) {
  return request({
    url: '/system/processDetail',
    method: 'post',
    data: data
  })
}
