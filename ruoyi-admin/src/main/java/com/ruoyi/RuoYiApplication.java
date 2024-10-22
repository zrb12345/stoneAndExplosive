package com.ruoyi;
import com.alibaba.excel.EasyExcel;
import com.ruoyi.system.domain.Iris;
import com.ruoyi.system.service.IrisService;
import com.ruoyi.system.service.impl.IrisServiceImpl;
import com.ruoyi.web.controller.IrisListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.wlld.randomForest.DataTable;

import java.util.HashSet;
import java.util.Set;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        Set<String> parameter = new HashSet<>();
        parameter.add("color");
        parameter.add("tac");
        parameter.add("stroke");
        parameter.add("good");



        String fileName = "iris.xlsx";
        IrisService IrisService = new IrisServiceImpl();
        IrisListener listener = new IrisListener(IrisService);
        // 使用最简单的写法生成的simpleWrite.xlsx来读取
        // 每次读取500条数据入库
        EasyExcel.read(fileName, Iris.class, listener).sheet().doRead();

        //        EasyExcel.read(fileName, Iris.class, new AnalysisEventListener<Iris>() {
//            int num=0;
//
//            //解析一行运行一次此方法。
//            @Override
//            public void invoke(Iris student, AnalysisContext analysisContext) {
//                num++;
//                System.out.println(student.getSepalLength()+" "+student.getSepalWidth()+" "+student.getPetalLength()+" "+student.getPetalWidth()+" "+student.getSpecies());
//            }
//
//            //解析所有数据完成，运行此方法。
//            @Override
//            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//                System.out.println("读取完成"+"共 "+num+" 条数据");
//            }
//        }).sheet().doRead();

        try {
            DataTable dataTable = new DataTable(parameter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  若依启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");

}


}
