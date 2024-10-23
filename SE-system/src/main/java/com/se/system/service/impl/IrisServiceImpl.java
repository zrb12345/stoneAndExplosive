package com.se.system.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.se.common.core.redis.RedisCache;
import com.se.common.exception.ServiceException;
import com.se.system.domain.Iris;
import com.se.system.mapper.IrisMapper;
import com.se.system.service.IrisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validator;
import java.io.IOException;
import java.util.List;

import static com.se.common.utils.SecurityUtils.getUsername;


/**
 * 参数配置 服务层实现
 *
 * @author se
 */
@Service
public class IrisServiceImpl implements IrisService
{
    @Autowired
    private IrisMapper irisMapper;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    protected Validator validator;


    /**
     * 查询参数配置列表
     *
     * @param iris 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<Iris> selectIrisList(Iris iris)
    {

        return irisMapper.selectIrisList(iris);
    }
//
//    /**
//     * 新增参数配置
//     *
//     * @param config 参数配置信息
//     * @return 结果
//     */
//    @Override
//    public int insertConfig(Iris config)
//    {
//        int row = configMapper.insertConfig(config);
//        if (row > 0)
//        {
//            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
//        }
//        return row;
//    }
//
//    /**
//     * 修改参数配置
//     *
//     * @param config 参数配置信息
//     * @return 结果
//     */
//    @Override
//    public int updateConfig(Iris config)
//    {
//        Iris temp = configMapper.selectConfigById(config.getConfigId());
//        if (!StringUtils.equals(temp.getConfigKey(), config.getConfigKey()))
//        {
//            redisCache.deleteObject(getCacheKey(temp.getConfigKey()));
//        }
//
//        int row = configMapper.updateConfig(config);
//        if (row > 0)
//        {
//            redisCache.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
//        }
//        return row;
//    }
//
//    /**
//     * 批量删除参数信息
//     *
//     * @param configIds 需要删除的参数ID
//     */
//    @Override
//    public void deleteConfigByIds(Long[] configIds)
//    {
//        for (Long configId : configIds)
//        {
//            Iris config = new Iris( );// = selectConfigById(configId);
//            if (StringUtils.equals(UserConstants.YES, config.getConfigType()))
//            {
//                throw new ServiceException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
//            }
//            configMapper.deleteConfigById(configId);
//            redisCache.deleteObject(getCacheKey(config.getConfigKey()));
//        }
//    }

    @Override
    public int save(List<Iris> cachedDataList) {
        String fileName = "iris.xlsx";

//        @Test
//        public void simpleRead() {
        // 使用最简单的写法生成的simpleWrite.xlsx来读取
//            String fileName = "/Users/xuchang/Documents/simpleWrite.xlsx";
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // PageReadListener里面定义了每次返回100行数据
        EasyExcel.read(fileName, Iris.class, new PageReadListener<Iris>(dataList -> {
//                for (Iris item : dataList) {
//                    System.out.println("读取到一条数据" + JSON.toJSONString(employee));
//                HashMap map = new HashMap();
//                map.put("list",dataList);
            irisMapper.save(dataList);
//                }
        })).sheet().doRead();
//        }


//        IrisService IrisService = new IrisServiceImpl();
//        IrisListener listener = new IrisListener(IrisService);
//        // 使用最简单的写法生成的simpleWrite.xlsx来读取
//        // 每次读取500条数据入库
//        EasyExcel.read(fileName, Iris.class, listener).sheet().doRead();
//        for (int i = 0; i < cachedDataList.size(); i++) {
//            irisMapper.save(cachedDataList );
//        }

        return 0;
    }

    /**
     * @param deptId
     * @return
     */
    @Override
    public Iris selectIrisById(Long deptId) {
//        return irisMapper.;
        return new Iris();
    }

    /**
     * @param dept
     * @return
     */
    @Override
    public int updateIris(Iris dept) {
        return 0;
    }

    /**
     * @param deptId
     * @return
     */
    @Override
    public int deleteIrisById(Long deptId) {
        return 0;
    }

    /**
     * @param file      数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName      操作用户
     * @return 结果
     */
    @Override
    public String importIris(MultipartFile file  , boolean updateSupport, String operName) throws IOException {
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<Iris> list = EasyExcel.read(file.getInputStream()).head(Iris.class).sheet().doReadSync();

        for (int i = 0; i < list.size(); i++) {
            try {
                Iris iris = list.get(i);
                iris.setCreateBy(getUsername());
                irisMapper.insertIris(iris);
                successNum++;
                successMsg.append("<br/>" + successNum + "、数据 " +  " 导入成功");
            }catch (Exception e){
                failureNum++;
                String msg = "<br/>" + failureNum + "、数据 " +   " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }


    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
//    private String getCacheKey(String configKey)
//    {
//        return CacheConstants.SYS_CONFIG_KEY + configKey;
//    }
}
