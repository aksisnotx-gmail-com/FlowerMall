import request from '@/utils/request'

// 查询商品信息0列表
export function listOrder() {
    return request({
      url: '/order/admin/getAll',
      method: 'get',
    })
}

export function delOrder (orderId) {
  return request({
    url: `/order/admin/delete/${orderId}`,
    method: 'get'
  })
}