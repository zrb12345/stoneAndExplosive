package com.se.system.service;

import com.se.common.service.BaseService;
import com.se.system.domain.Iris;
import com.se.system.service.dto.IrisQueryCriteria;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 参数配置 服务层
 *
 * @author se
 */




public interface IrisService extends BaseService<Iris>
{


    /**
     * 查询参数 列表
     *
     * @param criteria 参数 信息
     * @return 参数 集合
     */
    List<Iris> selectIrisList(IrisQueryCriteria criteria);

    int save(List<Iris> cachedDataList);

    Iris selectIrisById(Long deptId);

    int updateIris(Iris dept);

    int deleteIrisById(Long deptId);
    /**
     * 导入 数据
     *
     * @param file 数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    String importIris(MultipartFile file, boolean updateSupport, String operName) throws IOException;
}
