import request from '@/utils/request'

export function getTutorialListApi() {
  return request({
    'url': '/flower/tutorial/getAll',
    'method': 'get'
  })
}

export function delLeaveListApi(messageId) {
	return request({
		'url': `/leave/message/delete/${messageId}`,
		'method': 'get'
	})
}
