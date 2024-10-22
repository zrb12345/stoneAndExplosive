package com.ruoyi.system.domain;

import com.alibaba.excel.annotation.ExcelProperty;

public class Iris {
    //@ExcelProperty 是easyexcel给我们提供的一个方法。
//@ExcelProperty 相当于我们表格里面的字段名，如果这里不写这里就是我们的属性名"id"
    @ExcelProperty("SL")
    private String sepalLength;
    @ExcelProperty("SW")
    private String sepalWidth;
    @ExcelProperty("PL")
    private String petalLength;
    @ExcelProperty("PW")
    private String petalWidth;
    @ExcelProperty("Spe")
    private String species;
//    Sepal.Length	Sepal.Width	Petal.Length	Petal.Width

    public Iris(){

    }
    public Iris(String pl, String pw, String sl, String sw,String species) {
        this.petalLength = pl;
        this.petalWidth = pw;
        this.sepalLength = sl;
        this.sepalWidth = sw;
        this.species = species;
    }

    @Override
    public String toString() {
        return "鸢尾花{" +
                "花瓣长度='" + petalLength + '\'' +
                ", 花瓣宽度='" + petalWidth + '\'' +
                ", 花萼长度='" + sepalLength + '\'' +
                ", 花萼宽度=" + sepalWidth + '\'' +
                ", 类型是=" + species +
                '}';
    }

    public String getPetalLength() {
        return petalLength;
    }

    public void setPetalLength(String pl) {
        this.petalLength= pl;
    }

    public String getPetalWidth() {
        return petalWidth;
    }

    public void setPetalWidth(String pw) {
        this.petalWidth = pw;
    }

    public String getSepalLength() {
        return sepalLength;
    }

    public void setSepalLength(String sl) {
        this.sepalLength = sl;
    }

    public String getSepalWidth() {
        return sepalWidth;
    }

    public void setSepalWidth(String sw) {
        this.sepalWidth = sw;
    }
    public String getSpecies(){
        return species;
    }
    public void setSpecies(String species){
        this.species=species;
    }
}




