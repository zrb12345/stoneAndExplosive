import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/se";

// 查询用户列表
export function listIris(query) {
  return request({
    url: '/system/iris/list',
    method: 'get',
    params: query
  })
}

// 查询用户详细
export function getIris(irisId) {
  return request({
    url: '/system/iris/' + parseStrEmpty(irisId),
    method: 'get'
  })
}

// 新增数据
export function addiris(data) {
  return request({
    url: '/system/iris',
    method: 'post',
    data: data
  })
}
// 训练模型和预测
export function trainAndForecast(data) {
  return request({
    url: '/system/iris/trainAndForecast',
    method: 'post',
    data: data
  })
}
//  模型 预测
export function onlyForecast(data) {
  return request({
    url: '/system/iris/onlyForecast',
    method: 'post',
    data: data
  })
}


// 修改用户
export function updateIris(data) {
  return request({
    url: '/system/iris',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delIris(irisId) {
  return request({
    url: '/system/iris/' + irisId,
    method: 'delete'
  })
}

// 用户密码重置
export function resetIrisPwd(irisId, password) {
  const data = {
    irisId,
    password
  }
  return request({
    url: '/system/iris/resetPwd',
    method: 'put',
    data: data
  })
}

// 用户状态修改
export function changeIrisStatus(irisId, status) {
  const data = {
    irisId,
    status
  }
  return request({
    url: '/system/iris/changeStatus',
    method: 'put',
    data: data
  })
}

// 查询用户个人信息
export function getIrisProfile() {
  return request({
    url: '/system/iris/profile',
    method: 'get'
  })
}

// 修改用户个人信息
export function updateIrisProfile(data) {
  return request({
    url: '/system/iris/profile',
    method: 'put',
    data: data
  })
}

// 用户密码重置
export function updateIrisPwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return request({
    url: '/system/iris/profile/updatePwd',
    method: 'put',
    params: data
  })
}

// 用户头像上传
export function uploadAvatar(data) {
  return request({
    url: '/system/iris/profile/avatar',
    method: 'post',
    data: data
  })
}

// 查询授权角色
export function getAuthRole(irisId) {
  return request({
    url: '/system/iris/authRole/' + irisId,
    method: 'get'
  })
}

// 保存授权角色
export function updateAuthRole(data) {
  return request({
    url: '/system/iris/authRole',
    method: 'put',
    params: data
  })
}

// 查询部门下拉树结构
export function deptTreeSelect() {
  return request({
    url: '/system/iris/deptTree',
    method: 'get'
  })
}
