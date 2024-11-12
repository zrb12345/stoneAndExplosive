import request from '@/utils/request'

// 查询岩石炸药能量利用率列表
export function listExStone(query) {
  return request({
    url: '/system/exStone/list',
    method: 'get',
    params: query
  })
}

// 查询岩石炸药能量利用率详细
export function getExStone(id) {
  return request({
    url: '/system/exStone/' + id,
    method: 'get'
  })
}

// 新增岩石炸药能量利用率
export function addExStone(data) {
  return request({
    url: '/system/exStone',
    method: 'post',
    data: data
  })
}

// 修改岩石炸药能量利用率
export function updateExStone(data) {
  return request({
    url: '/system/exStone',
    method: 'put',
    data: data
  })
}

// 删除岩石炸药能量利用率
export function delExExStone(id) {
  return request({
    url: '/system/exStone/' + id,
    method: 'delete'
  })
}
// 查询岩石炸药能量利用率 并排名
export function getConsumeOfTop5(data) {
  return request({
    url: '/system/exStone/getConsumeOfTop5',
    method: 'post',
    data: data
  })
}
