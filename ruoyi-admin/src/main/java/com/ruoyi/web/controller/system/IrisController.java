package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Iris;
import com.ruoyi.system.service.IrisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门信息
 * 
 * @author ruoyi
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
    public AjaxResult list(Iris dept)
    {
        List<Iris> depts = irisService.selectIrisList(dept);
        return success(depts);
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
}
