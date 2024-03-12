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

export function updatePublish(id) {
  return request({
    url: '/system/product/updatePublish/'+id,
    method: 'put',
  })
}

export function updateProduct(data) {
  return request({
    url: '/system/merchant/addProduct/',
    method: 'put',
    data: data
  })
}
