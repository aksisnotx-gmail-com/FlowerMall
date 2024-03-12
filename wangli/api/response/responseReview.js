import request from '@/utils/request'

export function getResponseInfo(id) {
  return request({
    'url': '/system/response/detail/'+id,
    'method': 'get'
  })
}

export function reviewSuccess(data) {
  return request({
    'url': '/system/response/ownerReviewSuccess',
    'method': 'put',
	'data':data
  })
}
export function reviewFail(data) {
  return request({
    'url': '/system/response/ownerReviewFail',
    'method': 'put',
	'data':data
  })
}