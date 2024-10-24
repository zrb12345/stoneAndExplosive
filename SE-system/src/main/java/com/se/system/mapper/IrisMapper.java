package com.se.system.mapper;

import com.se.common.mapper.CoreMapper;
import com.se.system.domain.Iris;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门管理 数据层
 *
 * @author se
 */
@Repository
public interface IrisMapper extends CoreMapper<Iris>
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
