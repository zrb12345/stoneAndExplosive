package com.se;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动程序
 *
 * @author se
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableAutoConfiguration
@MapperScan("com.se.system.mapper")
public class SEApplication
{
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = SpringApplication.run(SEApplication.class, args);

//        java版本
//    SparkConf
        SparkConf conf = new SparkConf();
        conf.setMaster("local");    //本地单线程运行
        conf.setAppName("testJob");
        JavaSparkContext sc = new JavaSparkContext(conf);
//        IrisMapper irisMapper = context.getBean(IrisMapper.class);
//        System.out.println(irisMapper.selectList(null));

    }


}
