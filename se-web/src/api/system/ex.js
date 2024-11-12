import request from '@/utils/request'

// 查询炸药列表
export function listEx(query) {
  return request({
    url: '/system/ex/list',
    method: 'get',
    params: query
  })
}

// 查询炸药详细
export function getEx(id) {
  return request({
    url: '/system/ex/' + id,
    method: 'get'
  })
}

// 新增炸药
export function addEx(data) {
  return request({
    url: '/system/ex',
    method: 'post',
    data: data
  })
}

// 修改炸药
export function updateEx(data) {
  return request({
    url: '/system/ex',
    method: 'put',
    data: data
  })
}

// 删除炸药
export function delEx(id) {
  return request({
    url: '/system/ex/' + id,
    method: 'delete'
  })
}
