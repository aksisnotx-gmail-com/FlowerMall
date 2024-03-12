import request from '@/utils/request'

export function getProductInfo(id) {
  return request({
    'url': '/public/product/detail/' + id,
    'method': 'get'
  })
}