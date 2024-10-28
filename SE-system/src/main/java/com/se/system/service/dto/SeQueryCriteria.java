package com.se.system.service.dto;

import com.se.annotation.Query;
import lombok.Data;

import java.util.List;

/**
 * @author wyr
 * @date 2020-05-12
 */
@Data
public class SeQueryCriteria {

    // 模糊
    @Query(type = Query.Type.UNIX_TIMESTAMP)
    private List<String> createTime;

    // 模糊
    @Query
    private Integer id;

    // 模糊
    @Query
    private Integer averageBlockSize;

    // 模糊
    @Query
    private Integer boulderYield;

    @Query
    private Integer specificCharge;

    @Query
    private Integer overexcavationThickness;




    @Query
    private Integer state;

    @Query
    private Integer isDel;

    @Query(propName="combinationId",type = Query.Type.NOT_EQUAL)
    private Integer newCombinationId;



}
