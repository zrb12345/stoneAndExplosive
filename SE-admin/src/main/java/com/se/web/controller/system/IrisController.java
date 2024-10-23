package com.se.web.controller.system;

import com.se.common.annotation.Log;
import com.se.common.core.controller.BaseController;
import com.se.common.core.domain.AjaxResult;
import com.se.common.core.page.TableDataInfo;
import com.se.common.enums.BusinessType;
import com.se.system.domain.Iris;
import com.se.system.service.IrisService;
import com.se.system.service.impl.GlobalObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wlld.randomForest.RandomForest;

import java.io.IOException;
import java.util.List;

/**
 * 部门信息
 *
 * @author se
 */
@RestController
@RequestMapping("/system/iris")
public class IrisController extends BaseController
{
    @Autowired
    private IrisService irisService;

    /**
     * 获取数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public TableDataInfo list(Iris iris)
    {
        startPage();
        List<Iris> list = irisService.selectIrisList(iris);
        return getDataTable(list);
//        return success(depts);
    }

//    /**
//     * 查询数据列表（排除节点）
//     */
//    @PreAuthorize("@ss.hasPermi('system:dept:list')")
//    @GetMapping("/list/exclude/{deptId}")
//    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId)
//    {
//        List<Iris> depts = irisService.selectIrisList(new Iris());
//        depts.removeIf(d -> d.getId().intValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getStatus(), ","), deptId + ""));
//        return success(depts);
//    }

    /**
     * 根据数据编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable Long deptId)
    {
//        irisService.checkIrisDataScope(deptId);
        return success(irisService.selectIrisById(deptId));
    }

    /**
     * 新增数据
     */
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "数据管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody List<Iris> iris)
    {
//        if (!irisService.checkIrisNameUnique(iris))
//        {
//            return error("新增数据'" + iris.getType() + "'失败，类型已存在");
//        }
        for (int i = 0; i < iris.size(); i++) {
            Iris item = iris.get(i);
            item.setCreateBy( getUsername());
        }
        return toAjax(irisService.save(iris));
    }

    /**
     * 修改数据
     */
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "数据管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Iris dept)
    {
//        Long deptId = dept.getId();
//        irisService.checkIrisDataScope(deptId);
//        if (!irisService.checkIrisNameUnique(dept))
//        {
//            return error("修改数据'" + dept.getIrisName() + "'失败，数据名称已存在");
//        }
//        else if (dept.getParentId().equals(deptId))
//        {
//            return error("修改数据'" + dept.getIrisName() + "'失败，上级数据不能是自己");
//        }
//        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && irisService.selectNormalChildrenIrisById(deptId) > 0)
//        {
//            return error("该数据包含未停用的子数据！");
//        }
        dept.setUpdateBy(getUsername());
        return toAjax(irisService.updateIris(dept));
    }

    /**
     * 删除数据
     */
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "数据管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable Long deptId)
    {
//        if (irisService.hasChildByIrisId(deptId))
//        {
//            return warn("存在下级数据,不允许删除");
//        }
//        if (irisService.checkIrisExistUser(deptId))
//        {
//            return warn("数据存在用户,不允许删除");
//        }
//        irisService.checkIrisDataScope(deptId);
        return toAjax(irisService.deleteIrisById(deptId));
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport)
    {
        //输入数据
        String operName = getUsername();
        try {
            String message = irisService.importIris(file, updateSupport, operName);
            return success(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //数据的获取已经搞定，需要把模型训练出来
    @Log(title = "模型训练", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/trainAndForecast")
    public AjaxResult  trainAndForecast( )
    {
        GlobalObject globalObject = GlobalObject.getInstance();
        Iris query = new Iris();
        query.setDelFlag("0");
        List<Iris> datas = irisService.selectIrisList(query);
        return globalObject.trainModel(datas);
    }
    //模型训练出来后，根据模型生成判断结果
    @Log(title = "模型使用", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/onlyForecast")
    public AjaxResult  onlyForecast( ){
        StringBuilder str = new StringBuilder("错误信息 : ");
        GlobalObject globalObject = GlobalObject.getInstance();
        RandomForest rf = globalObject.getRf();
        Iris iris = new Iris();
//        iris.setId(1L);
        Iris iris2 = irisService.selectIrisById(1L);
//        rf.forest()
        return success(String.valueOf(str));
    }


}
