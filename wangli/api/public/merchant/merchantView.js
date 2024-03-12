import request from '@/utils/request'
import upload from '@/utils/upload'

export function uploadTag(data) {
  return upload({
    url: '/public/merchant/uploadTag',
    name: data.name,
    filePath: data.filePath
  })
}

export function uploadFile(data) {
  return upload({
    url: '/public/merchant/uploadFiles',
    name: data.name,
    filePath: data.filePath
  })
}

export function saveMerchant(data) {
  return request({
    url: '/public/merchant',
    method: 'post',
    data: data
  })
}

export function getMerchantTag() {
	return request({
	  url: '/public/merchant/merchantType',
	  'method': 'get'
	})
}

export function getMerchantInfo(id) {
	return request({
	  url: '/public/merchant/detail/'+id,
	  'method': 'get'
	})
}

export function getProductInfo(id) {
	return request({
	  url: '/public/merchant/product/'+id,
	  'method': 'get'
	})
}