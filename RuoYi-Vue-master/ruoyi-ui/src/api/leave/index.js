import request from '@/utils/request'

// 查询商品信息0列表
export function listmsg() {
    return request({
      url: '/leave/message/getAllMessage',
      method: 'get',
    })
}

export function delMsg (messageId) {
  return request({
    url: `/leave/message/delete/${messageId}`,
    method: 'get'
  })
}