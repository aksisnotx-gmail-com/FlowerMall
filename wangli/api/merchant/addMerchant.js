import request from '@/utils/request'
import upload from '@/utils/upload'

export function uploadTag(data) {
  return upload({
    url: '/system/merchant/uploadTag',
    name: data.name,
    filePath: data.filePath
  })
}

export function uploadFile(data) {
  return upload({
    url: '/system/merchant/uploadFiles',
    name: data.name,
    filePath: data.filePath
  })
}

export function saveMerchant(data) {
  return request({
    url: '/system/merchant',
    method: 'post',
    data: data
  })
}

export function getMerchantTag() {
	return request({
	  url: '/system/merchant/merchantType',
	  'method': 'get'
	})
}
