import request from '@/utils/request'
import upload from '@/utils/upload'

export function getRequestInfo() {
  return request({
    'url': '/system/request/listRequest',
    'method': 'get'
  })
}

export function deleteRequestInfo(id) {
  return request({
    'url': '/system/request/'+id,
    'method': 'delete'
  })
}

export function updateRequestInfo(data) {
  return request({
    url: '/system/request',
    method: 'put',
    data: data
  })
}

export function reviewSuccess(data) {
	return request({
	  url: '/system/request/reviewSuccess',
	  'method': 'put',
	  data: data
	})
}

export function reviewFail(data) {
	return request({
	  url: '/system/request/reviewFail',
	  'method': 'put',
	  data: data
	})
}