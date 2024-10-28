package com.se.web.controller.system;

import com.se.common.annotation.Log;
import com.se.common.core.controller.BaseController;
import com.se.common.core.domain.AjaxResult;
import com.se.common.enums.BusinessType;
import com.se.system.service.impl.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author se
 */
@RestController
@RequestMapping("/system/qiniu")
public class QiniuController extends BaseController
{
    @Autowired
    private QiniuService qiniuService;


    @Log(title = "上传数据", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(  MultipartFile file)
    {
        return  qiniuService.saveImage(file);
    }


}
