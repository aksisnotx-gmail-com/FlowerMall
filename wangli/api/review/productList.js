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

export function reviewSuccess(data) {
	return request({
	  url: '/system/product/reviewSuccess',
	  'method': 'put',
	  data: data
	})
}

export function reviewFail(data) {
	return request({
	  url: '/system/product/reviewFail',
	  'method': 'put',
	  data: data
	})
}