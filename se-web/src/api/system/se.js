import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/se";

// 查询用户列表
export function listSe(query) {
  return request({
    url: '/system/se/list',
    method: 'get',
    params: query
  })
}

// 查询用户详细
export function getSe(seId) {
  return request({
    url: '/system/se/' + parseStrEmpty(seId),
    method: 'get'
  })
}

// 新增数据
export function addse(data) {
  return request({
    url: '/system/se',
    method: 'post',
    data: data
  })
}
// 训练模型和预测
export function trainAndForecast(data) {
  return request({
    url: '/system/se/trainAndForecast',
    method: 'post',
    data: data
  })
}
//  模型 预测 大块率
export function onlyForecast1(data) {
  return request({
    url: '/system/se/onlyForecastDkl',
    method: 'post',
    data: data
  })
}

//  模型 预测 块度
export function onlyForecast2(data) {
  return request({
    url: '/system/se/onlyForecastKd',
    method: 'post',
    data: data
  })
}

//  模型 预测 类型
export function onlyForecast3(data) {
  return request({
    url: '/system/se/usePython',
    method: 'post',
    data: data
  })
}

//  模型 预测 超大
export function onlyForecast4(data) {
  return request({
    url: '/system/se/onlyForecastLarger',
    method: 'post',
    data: data
  })
}

//  模型 预测 平均粒度
export function onlyForecast5(data) {
  return request({
    url: '/system/se/onlyForecastLabel',
    method: 'post',
    data: data
  })
}
// 修改用户
export function updateSe(data) {
  return request({
    url: '/system/se',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delSe(seId) {
  return request({
    url: '/system/se/' + seId,
    method: 'delete'
  })
}

// 用户密码重置
export function resetSePwd(seId, password) {
  const data = {
    seId,
    password
  }
  return request({
    url: '/system/se/resetPwd',
    method: 'put',
    data: data
  })
}

// 用户状态修改
export function changeSeStatus(seId, status) {
  const data = {
    seId,
    status
  }
  return request({
    url: '/system/se/changeStatus',
    method: 'put',
    data: data
  })
}

// 查询用户个人信息
export function getSeProfile() {
  return request({
    url: '/system/se/profile',
    method: 'get'
  })
}

// 修改用户个人信息
export function updateSeProfile(data) {
  return request({
    url: '/system/se/profile',
    method: 'put',
    data: data
  })
}

// 用户密码重置
export function updateSePwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return request({
    url: '/system/se/profile/updatePwd',
    method: 'put',
    params: data
  })
}



// 查询授权角色
export function getAuthRole(seId) {
  return request({
    url: '/system/se/authRole/' + seId,
    method: 'get'
  })
}

// 保存授权角色
export function updateAuthRole(data) {
  return request({
    url: '/system/se/authRole',
    method: 'put',
    params: data
  })
}

// 查询部门下拉树结构
export function deptTreeSelect() {
  return request({
    url: '/system/se/deptTree',
    method: 'get'
  })
}
