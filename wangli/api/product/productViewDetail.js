import request from '@/utils/request'

export function getProductInfo(id) {
  return request({
    'url': '/system/product/detail/' + id,
    'method': 'get'
  })
}