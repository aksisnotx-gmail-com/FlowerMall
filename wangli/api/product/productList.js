import request from '@/utils/request'
import upload from '@/utils/upload'

export function getProductInfoList() {
  return request({
    'url': '/system/product/listProduct',
    'method': 'get'
  })
}

export function deleteProductInfo(id) {
  return request({
    'url': '/system/product/'+id,
    'method': 'delete'
  })
}

export function updatePublish(data) {
  return request({
    url: '/system/product/updatePublish',
    method: 'put',
	data: data
  })
}
export function initProductSign(id) {
  return request({
    'url': '/system/sign/sign/'+id,
    'method': 'get'
  })
}