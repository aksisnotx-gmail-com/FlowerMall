import request from '@/utils/request'
export function getLeaveListApi() {
  return request({
    'url': '/leave/message/getAllMessage',
    'method': 'get'
  })
}

export function delLeaveListApi(messageId) {
	return request({
		'url': `/leave/message/delete/${messageId}`,
		'method': 'get'
	})
}
