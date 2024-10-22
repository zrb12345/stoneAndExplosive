package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.system.domain.Iris;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * @param dept 部门信息
     * @return 结果
     */
    public int save(Iris data);


}
