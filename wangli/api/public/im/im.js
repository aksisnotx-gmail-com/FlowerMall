import request from '@/utils/request'

export function getMessage(id) {
  return request({
    'url': '/public/im/getMessage/' + id,
    'method': 'get'
  })
}

export function sendMessage(data) {
  return request({
    'url': '/public/im/sendMessage/',
    'method': 'post',
	data: data
  })
}