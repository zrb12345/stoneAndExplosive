package com.ruoyi.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
@Data
public class Iris extends BaseEntity {
    /** 参数主键 */
    @Excel(name = "参数主键", cellType = Excel.ColumnType.NUMERIC)
    private Integer id;
    @ExcelProperty("SL")
    private String sepalLength;
    @ExcelProperty("SW")
    private String sepalWidth;
    @ExcelProperty("PL")
    private String petalLength;
    @ExcelProperty("PW")
    private String petalWidth;
    @ExcelProperty("Spe")
    private String type;
    private String createBy;
    private String status;
    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;
    public Iris(){

    }
    public Iris(String pl, String pw, String sl, String sw,String type) {
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




