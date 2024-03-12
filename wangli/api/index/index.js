import request from '@/utils/request'
import upload from '@/utils/upload'

export function getProductInfoList(data) {
  return request({
    'url': '/system/product/getProduct',
    'method': 'get',
    data: data
  })
}

export function getProductList() {
  return request({
    'url': '/system/product/getProductList',
    'method': 'get'
  })
}

export function getProductInfo(id) {
  return request({
    'url': '/system/product/'+id,
    'method': 'get'
  })
}

export function getProductType(id) {
  return request({
    'url': '/system/dict/data/type/'+id,
    'method': 'get'
  })
}