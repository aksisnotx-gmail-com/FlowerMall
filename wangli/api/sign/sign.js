import request from '@/utils/request'

export function initSign(id) {
  return request({
    'url': '/system/sign/sign/'+id,
    'method': 'get'
  })
}

export function sign(id) {
  return request({
    'url': '/public/sign/'+id,
    'method': 'get'
  })
}

export function shareSign(id) {
  return request({
    'url': '/public/sign/share/'+id,
    'method': 'get'
  })
}

export function addAgree(id) {
  return request({
    'url': '/public/sign/addAgree/'+id,
    'method': 'get'
  })
}

export function hasAgree(id) {
  return request({
    'url': '/public/sign/hasAgree/'+id,
    'method': 'get'
  })
}

export function getUserInfo() {
  return request({
    'url': '/public/sign/getUserInfo',
    'method': 'get'
  })
}

export function hasResponse(requestId) {
  return request({
    'url': '/public/sign/hasResponse/'+requestId,
    'method': 'get'
  })
}

export function getResponseInfo(id) {
  return request({
    'url': '/public/sign/getResponse/'+id,
    'method': 'get'
  })
}

export function getResponseDetail(id) {
  return request({
    'url': '/public/sign/getResponseDetail/'+id,
    'method': 'get'
  })
}

export function getRequestBySign(ticket) {
  return request({
    'url': '/public/sign/getRequestBySign/'+ticket,
    'method': 'get'
  })
}

export function getProductBySign(ticket) {
  return request({
    'url': '/public/sign/getProductBySign/'+ticket,
    'method': 'get'
  })
}

export function getMerchantBySign(ticket) {
  return request({
    'url': '/public/sign/getMerchantBySign/'+ticket,
    'method': 'get'
  })
}

export function getResponseBySign(ticket) {
  return request({
    'url': '/public/sign/getResponseBySign/'+ticket,
    'method': 'get'
  })
}

export function getProcessBySign(ticket) {
  return request({
    'url': '/public/sign/getProcessBySign/'+ticket,
    'method': 'get'
  })
}

export function getRequestList() {
  return request({
    'url': '/public/sign/getRequestList',
    'method': 'get'
  })
}

export function getRequestDetail(id) {
  return request({
    'url': '/public/sign/getRequestDetail/'+id,
    'method': 'get'
  })
}

export function getResponseList() {
  return request({
    'url': '/public/sign/getResponseList',
    'method': 'get'
  })
}