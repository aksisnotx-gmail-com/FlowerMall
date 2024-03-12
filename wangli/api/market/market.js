import request from '@/utils/request'
export function getProductInfo(name) {
  const data = {
    name
  }
  return request({
    'url': '/system/productInfo/getProduct',
    'method': 'get',
    'data': data
  })
}