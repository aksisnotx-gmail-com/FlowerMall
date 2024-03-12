import request from '@/utils/request'
import upload from '@/utils/upload'

export function getResponseInfo() {
  return request({
    'url': '/system/response/myResponse',
    'method': 'get'
  })
}

export function deleteBusinessResponseInfo(id) {
  return request({
    'url': '/system/response/'+id,
    'method': 'delete'
  })
}

export function reviewSuccess(data) {
	return request({
	  url: '/system/response/reviewSuccess',
	  'method': 'put',
	  data: data
	})
}

export function reviewFail(data) {
	return request({
	  url: '/system/response/reviewFail',
	  'method': 'put',
	  data: data
	})
}