import request from '@/utils/request'
import upload from '@/utils/upload'

export function getProductInfoList() {
  return request({
    'url': '/public/product/listProduct',
    'method': 'get'
  })
}