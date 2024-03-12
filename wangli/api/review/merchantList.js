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
	  url: '/system/merchant/myList',
	  'method': 'get'
	})
}

export function reviewSuccess(data) {
	return request({
	  url: '/system/merchant/reviewSuccess',
	  'method': 'put',
	  data: data
	})
}

export function reviewFail(data) {
	return request({
	  url: '/system/merchant/reviewFail',
	  'method': 'put',
	  data: data
	})
}