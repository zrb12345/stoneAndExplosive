package com.se.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.se.common.annotation.Excel;
import com.se.common.core.domain.BaseEntity;
import lombok.Data;
@Data
public class Iris extends BaseEntity {
    /** 参数主键 */
    @Excel(name = "参数主键", cellType = Excel.ColumnType.NUMERIC)
    private Integer id;
    @ExcelProperty("SL")
    private Integer sepalLength;
    @ExcelProperty("SW")
    private Integer sepalWidth;
    @ExcelProperty("PL")
    private Integer petalLength;
    @ExcelProperty("PW")
    private Integer petalWidth;
    @ExcelProperty("Spe")
    private Integer type;
    private String createBy;
    private String status;
    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Iris(){

    }
    public Iris(Integer pl, Integer pw, Integer sl, Integer sw,Integer type) {
        this.petalLength = pl;
        this.petalWidth = pw;
        this.sepalLength = sl;
        this.sepalWidth = sw;
        this.type = type;
    }

    @Override
    public String toString() {
        return "鸢尾花{" +
                "花瓣长度='" + petalLength + '\'' +
                ", 花瓣宽度='" + petalWidth + '\'' +
                ", 花萼长度='" + sepalLength + '\'' +
                ", 花萼宽度=" + sepalWidth + '\'' +
                ", 类型是=" + type +
                '}';
    }

}




