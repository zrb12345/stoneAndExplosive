package com.se.system.mapper;

import com.se.system.domain.Iris;

import java.util.List;

/**
 * 部门管理 数据层
 *
 * @author se
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

    void insertIris(Iris iris);
}
