package com.se.system.service.impl;

import com.se.common.core.domain.AjaxResult;
import com.se.system.domain.Iris;
import lombok.Data;
import org.wlld.randomForest.DataTable;
import org.wlld.randomForest.RandomForest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.se.common.core.domain.AjaxResult.success;
@Data
public class GlobalObject {

    private static GlobalObject instance;
    private DataObject dataObject;
    //模型初始化是 0 已经训练过了是 1
    private int state;
    private RandomForest rf;
    private GlobalObject() {
        this.dataObject = new DataObject();
        //column代表属性名
        Set<String> column = new HashSet<>();
        column.add("sepalLength");
        column.add("sepalWidth");
        column.add("petalLength");
        column.add("petalWidth");
        column.add("type");
        DataTable dataTable = null;
        try {
            dataTable = new DataTable(column);
            //setKey 代表用那个属性作为要分类的类型
            dataTable.setKey("type");
            //初始化随机森林 设置树的个数,最好是奇数,偶数可能会出现投票结果等票的情况
            //树的个数不宜太多 也不宜太少 最好接近属性的数量
            rf = new RandomForest(5);
            rf.init(dataTable);//唤醒随机森林的树
            state = 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static GlobalObject getInstance() {
        if (instance == null) {
            synchronized (GlobalObject.class) {
                if (instance == null) {
                    instance = new GlobalObject();
                }
            }
        }
        return instance;
    }
    public AjaxResult trainModel(List<Iris> datas){
        try {
            //创建实体类,输入数据
            Iris query = new Iris();
            query.setDelFlag("0");
//            List<Iris> datas = irisService.selectIrisList(query);
            for (Iris data : datas) {
                rf.insert(data);
            }
            rf.study();
//            //以上是学习过程
            StringBuilder str = new StringBuilder("错误信息 : ");
            int errorCount = 0;
            for (int i = 0; i < datas.size(); i++) {
                Iris iris = datas.get(i);
                int point = rf.forest(iris);
                if(point!=iris.getType())
                {
                    errorCount++;
                    //预测出这个是什么花
                    System.out.println("预测错了 "+iris.getId()+" 应该是 "+ iris.getType()+" 预测成了 "+ point);
                    str.append("预测错了 "+iris.getId()+" 应该是 "+ iris.getType()+" 预测成了 "+ point);
                }
            }
            float errorpercent = (float)errorCount/datas.size();
            System.out.println(" 对于"+datas.size()+"个数据,预测错误率是 "+errorpercent);
            str.append(" 对于"+datas.size()+"个数据,预测错误率是 "+errorpercent);
            state=1;
            return success(String.valueOf(str));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}