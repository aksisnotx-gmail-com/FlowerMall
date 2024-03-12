import request from '@/utils/request'
import upload from '@/utils/upload'

export function getProductInfoList(data) {
  return request({
    'url': '/system/product/getProduct',
    'method': 'get',
    data: data
  })
}
