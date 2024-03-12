import request from '@/utils/request'
import upload from '@/utils/upload'

export function getMerchantInfoList(data) {
  return request({
    'url': '/public/merchant/getMerchant',
    'method': 'get',
    data: data
  })
}
