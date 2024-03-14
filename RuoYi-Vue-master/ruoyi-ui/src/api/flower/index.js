import request from '@/utils/request'

// 查询商品信息0列表
export function listTutorial() {
    return request({
      url: '/flower/tutorial/getAll',
      method: 'get',
    })
}

export function uploadFileApi (data) {
  return request({
    url: '/file/upload',
    method: 'post',
    data
  })
}

export function addTutorialpi (data) {
  return request({
    url: '/flower/tutorial/publish',
    method: 'post',
    data
  })
}

export function delTutorial (tutorialId) {
  return request({
    url: `/flower/tutorial/admin/delete/${tutorialId}`,
    method: 'get'
  })
}