package com.se;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动程序
 *
 * @author se
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })

@MapperScan("com.se.system.mapper")
public class SEApplication
{
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = SpringApplication.run(SEApplication.class, args);
//        IrisMapper irisMapper = context.getBean(IrisMapper.class);
//        System.out.println(irisMapper.selectList(null));

    }


}
