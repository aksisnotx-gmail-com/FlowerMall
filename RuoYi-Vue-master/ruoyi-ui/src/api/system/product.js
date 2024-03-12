import request from '@/utils/request'

// 查询商品信息0列表
export function listInfo(query) {
  return request({
    url: '/system/product/list',
    method: 'get',
    params: query
  })
}

// 查询商品信息0详细
export function getInfo(id) {
  return request({
    url: '/system/product/' + id,
    method: 'get'
  })
}

// 新增商品信息0
export function addInfo(data) {
  return request({
    url: '/system/product',
    method: 'post',
    data: data
  })
}

// 修改商品信息0
export function updateInfo(data) {
  return request({
    url: '/system/product',
    method: 'put',
    data: data
  })
}

// 删除商品信息0
export function delInfo(id) {
  return request({
    url: '/system/product/' + id,
    method: 'delete'
  })
}
