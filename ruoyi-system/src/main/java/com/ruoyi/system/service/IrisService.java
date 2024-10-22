package com.ruoyi.system.service;

import com.ruoyi.system.domain.Iris;

import java.util.List;

/**
 * 参数配置 服务层
 *
 * @author ruoyi
 */
public interface IrisService
{


    /**
     * 查询参数 列表
     *
     * @param config 参数 信息
     * @return 参数 集合
     */
    public List<Iris> selectIrisList(Iris config);
//
//    /**
//     * 新增参数配置
//     *
//     * @param config 参数配置信息
//     * @return 结果
//     */
//    public int insertConfig(SysConfig config);
//
//    /**
//     * 修改参数配置
//     *
//     * @param config 参数配置信息
//     * @return 结果
//     */
//    public int updateConfig(SysConfig config);
//
//    /**
//     * 批量删除参数信息
//     *
//     * @param configIds 需要删除的参数ID
//     */
//    public void deleteConfigByIds(Long[] configIds);


    int save(List<Iris> cachedDataList);

    String selectIrisById(Long deptId);

    int updateIris(Iris dept);

    int deleteIrisById(Long deptId);
}
