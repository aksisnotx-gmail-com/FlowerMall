import request from '@/utils/request'
import upload from '@/utils/upload'

export function getProcess() {
  return request({
    url: '/system/process/list',
    'method': 'get'
  })
}

export function getProcessByRequestId(requestId) {
  return request({
    url: '/system/process/listByRequestId/'+requestId,
    'method': 'get'
  })
}

export function deleteProcess(id) {
  return request({
    url: '/system/process/'+id,
    'method': 'delete'
  })
}

export function reviewSuccess(data) {
	return request({
	  url: '/system/process/reviewSuccess',
	  'method': 'put',
	  data: data
	})
}

export function reviewFail(data) {
	return request({
	  url: '/system/process/reviewFail',
	  'method': 'put',
	  data: data
	})
}