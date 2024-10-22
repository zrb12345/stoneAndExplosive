package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Iris;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 部门管理 数据层
 *
 * @author ruoyi
 */
public interface IrisMapper
{

    /**
     * 新增  信息
     *
     *  @Param(sysUserList) List<SysUser> sysUserList
     * @return 结果
     */
    public int save(List<Iris> list );

    public List<Iris> selectIrisList(Iris iris);
}
