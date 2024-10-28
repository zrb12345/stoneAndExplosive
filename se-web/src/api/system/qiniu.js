import request from '@/utils/request'
import { parseStrEmpty } from "@/utils/se";
//用户头像上传
export function uploadFile(data) {
  return request({
    url: '/system/qiniu/uploadFile',
    method: 'post',
    data: data
  })
}