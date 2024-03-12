import request from '@/utils/request'
import upload from '@/utils/upload'

export function getMerchantInfo() {
	return request({
	  url: '/system/merchant/detail',
	  'method': 'get'
	})
}

export function getMerchantList() {
	return request({
	  url: '/system/merchant/listMerchant',
	  'method': 'get'
	})
}

export function deleteMerchantInfo(id) {
	return request({
	  url: '/system/merchant/'+id,
	  'method': 'delete'
	})
}

export function initMerchantSign(id) {
  return request({
    'url': '/system/sign/sign/'+id,
    'method': 'get'
  })
}