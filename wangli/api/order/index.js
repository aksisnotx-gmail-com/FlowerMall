import request from '@/utils/request'

export function getOrderListApi () {
  return request({
    'url': '/order/get',
    'method': 'get'
  })
}

export function delLeaveListApi(messageId) {
	return request({
		'url': `/leave/message/delete/${messageId}`,
		'method': 'get'
	})
}
