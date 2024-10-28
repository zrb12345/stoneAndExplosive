package com.se.system.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.se.common.core.redis.RedisCache;
import com.se.common.exception.ServiceException;
import com.se.common.service.impl.BaseServiceImpl;
import com.se.common.utils.QueryHelpPlus;
import com.se.system.domain.Se;
import com.se.system.mapper.SeMapper;
import com.se.system.service.SeService;
import com.se.system.service.dto.SeQueryCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Validator;
import java.io.IOException;
import java.util.List;


/**
 * 参数配置 服务层实现
 *
 * @author se
 */

@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SeServiceImpl extends BaseServiceImpl<SeMapper, Se> implements SeService {
    @Autowired
    private SeMapper seMapper;

    @Autowired
    private RedisCache redisCache;
    @Autowired
    protected Validator validator;


    /**
     * 查询参数配置列表
     *
     * @param criteria 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<Se> selectSeList(SeQueryCriteria criteria){
        QueryWrapper queryWrapper =  QueryHelpPlus.getPredicate(Se.class, criteria);
        return seMapper.selectList(queryWrapper);
    }


    @Override
    public int save(List<Se> cachedDataList) {
        String fileName = "Se.xlsx";
//        @Test
//        public void simpleRead() {
        // 使用最简单的写法生成的simpleWrite.xlsx来读取
//            String fileName = "/Users/xuchang/Documents/simpleWrite.xlsx";
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // PageReadListener里面定义了每次返回100行数据
        EasyExcel.read(fileName, Se.class, new PageReadListener<Se>(dataList -> {
//                for (Se item : dataList) {
//                    System.out.println("读取到一条数据" + JSON.toJSONString(employee));
//                HashMap map = new HashMap();
//                map.put("list",dataList);
            seMapper.insertList(dataList);
//                }
        })).sheet().doRead();
//        }

        return 0;
    }

    /**
     * @param deptId
     * @return
     */
    @Override
    public Se selectSeById(Long deptId) {
//        return seMapper.;
        return new Se();
    }

    /**
     * @param dept
     * @return
     */
    @Override
    public int updateSe(Se dept) {
        return 0;
    }

    /**
     * @param deptId
     * @return
     */
    @Override
    public int deleteSeById(Long deptId) {
        return 0;
    }

    /**
     * @param file      数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName      操作用户
     * @return 结果
     */
    @Override
    public String importSe(MultipartFile file  , boolean updateSupport, String operName) throws IOException {
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        List<Se> list = EasyExcel.read(file.getInputStream()).head(Se.class).sheet().doReadSync();
        try {
        seMapper.insertList(list);
            successMsg.append("数据导入成功");
        }catch (Exception e){
            failureMsg.append( "数据导入失败："+e.getMessage());
        }
//        for (int i = 0; i < list.size(); i++) {
//            try {
//                Se Se = list.get(i);
//                Se.setCreateBy(getUsername());
//                seMapper.insertSe(Se);
//                successNum++;
//                successMsg.append("<br/>" + successNum + "、数据 " +  " 导入成功");
//            }catch (Exception e){
//                failureNum++;
//                String msg = "<br/>" + failureNum + "、数据 " +   " 导入失败：";
//                failureMsg.append(msg + e.getMessage());
//            }
//        }
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



}
