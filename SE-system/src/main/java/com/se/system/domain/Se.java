package com.se.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.se.common.annotation.Excel;
import lombok.Data;
@Data
public class Se extends BaseDomain {
    /** 参数主键 */



    @Excel(name = "参数主键", cellType = Excel.ColumnType.NUMERIC)
    private Integer id;
    @ExcelProperty("abs")
    //平均块度
    private Integer averageBlockSize;
    @ExcelProperty("by")
    //大块率
    private Integer boulderYield;
    @ExcelProperty("sc")
    //炸药单耗
    private Integer specificCharge;
    @ExcelProperty("ot")
    //超挖厚度
    private Integer overexcavationThickness;
    @ExcelProperty("Spe")
    private Integer type;
    private String createBy;
    private String status;


    public Se(){

    }
    public Se(Integer pl, Integer pw, Integer sl, Integer sw,Integer type) {
        this.averageBlockSize = pl;
        this.boulderYield = pw;
        this.specificCharge = sl;
        this.overexcavationThickness = sw;
        this.type = type;
    }

    @Override
    public String toString() {
        return "参数{" +
                "平均块度='" + averageBlockSize + '\'' +
                ", 大块率='" + boulderYield + '\'' +
                ", 炸药单耗='" + specificCharge + '\'' +
                ", 超挖厚度=" + overexcavationThickness + '\'' +
                ", 类型是=" + type +
                '}';
    }

}




