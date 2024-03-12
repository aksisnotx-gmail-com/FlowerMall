import request from '@/utils/request'
import upload from '@/utils/upload'

export function getProductInfoList(data) {
  return request({
    'url': '/public/product/getProduct',
    'method': 'get',
    data: data
  })
}
