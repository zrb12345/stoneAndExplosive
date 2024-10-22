package com.ruoyi.system.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.domain.Iris;
import com.ruoyi.system.mapper.IrisMapper;
import com.ruoyi.system.service.IrisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 参数配置 服务层实现
 *
 * @author ruoyi
 */
@Service
public class IrisServiceImpl implements IrisService
{
    @Autowired
    private IrisMapper irisMapper;

    @Autowired
    private RedisCache redisCache;



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
    public String selectIrisById(Long deptId) {
        return "";
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
