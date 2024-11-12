import request from '@/utils/request'

// 查询岩石列表
export function listStone(query) {
  return request({
    url: '/system/stone/list',
    method: 'get',
    params: query
  })
}

// 查询岩石详细
export function getStone(id) {
  return request({
    url: '/system/stone/' + id,
    method: 'get'
  })
}

// 新增岩石
export function addStone(data) {
  return request({
    url: '/system/stone',
    method: 'post',
    data: data
  })
}

// 修改岩石
export function updateStone(data) {
  return request({
    url: '/system/stone',
    method: 'put',
    data: data
  })
}

// 删除岩石
export function delStone(id) {
  return request({
    url: '/system/stone/' + id,
    method: 'delete'
  })
}
