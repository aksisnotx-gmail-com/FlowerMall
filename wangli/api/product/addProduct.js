import request from '@/utils/request'
import upload from '@/utils/upload'

export function uploadTag(data) {
  return upload({
    url: '/system/product/uploadTag',
    name: data.name,
    filePath: data.filePath
  })
}

export function uploadFile(data) {
  return upload({
    url: '/system/product/uploadFiles',
    name: data.name,
    filePath: data.filePath
  })
}

export function saveProduct(data) {
  return request({
    url: '/system/product',
    method: 'post',
    data: data
  })
}
