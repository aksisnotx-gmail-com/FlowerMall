import request from '@/utils/request'
import upload from '@/utils/upload'

export function getMerchantInfoList(data) {
  return request({
    'url': '/system/merchant/getMerchant',
    'method': 'get',
    data: data
  })
}
