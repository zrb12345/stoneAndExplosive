package com.se.system.service.dto;

import com.se.annotation.Query;
import lombok.Data;

import java.util.List;

/**
 * @author wyr
 * @date 2020-05-12
 */
@Data
public class IrisQueryCriteria {

    // 模糊
    @Query(type = Query.Type.UNIX_TIMESTAMP)
    private List<String> createTime;

    // 模糊
    @Query
    private Integer id;

    // 模糊
    @Query
    private Integer sepalLength;

    // 模糊
    @Query
    private Integer sepalWidth;

    @Query
    private Integer petalLength;

    @Query
    private Integer petalWidth;

    @Query
    private Integer state;

    @Query
    private Integer isDel;

    @Query(propName="combinationId",type = Query.Type.NOT_EQUAL)
    private Integer newCombinationId;



}
