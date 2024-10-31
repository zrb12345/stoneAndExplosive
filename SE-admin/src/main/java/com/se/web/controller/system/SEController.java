package com.se.web.controller.system;

import com.se.common.annotation.Log;
import com.se.common.core.controller.BaseController;
import com.se.common.core.domain.AjaxResult;
import com.se.common.core.page.TableDataInfo;
import com.se.common.enums.BusinessType;
import com.se.system.domain.Se;
import com.se.system.service.SeService;
import com.se.system.service.dto.SeQueryCriteria;
import com.se.system.service.impl.GlobalObject;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.RandomForestRegressor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wlld.randomForest.RandomForest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门信息
 *
 * @author se
 */
@RestController
@RequestMapping("/system/se")
public class SEController extends BaseController
{
    @Autowired
    private SeService seService;

    /**
     * 获取数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeQueryCriteria Se)
    {
        startPage();
        List<Se> list = seService.selectSeList(Se);

        return getDataTable(list);
//        return success(depts);
    }

//    /**
//     * 查询数据列表（排除节点）
//     */
//    @PreAuthorize("@ss.hasPermi('system:dept:list')")
//    @GetMapping("/list/exclude/{deptId}")
//    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId)
//    {
//        List<Se> depts = SeService.selectSeList(new Se());
//        depts.removeIf(d -> d.getId().intValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getStatus(), ","), deptId + ""));
//        return success(depts);
//    }

    /**
     * 根据数据编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{deptId}")
    public AjaxResult getInfo(@PathVariable Long deptId)
    {
//        SeService.checkSeDataScope(deptId);
        return success(seService.selectSeById(deptId));
    }

    /**
     * 新增数据
     */
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "数据管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody List<Se> Se)
    {
//        if (!seService.checkSeNameUnique(Se))
//        {
//            return error("新增数据'" + Se.getType() + "'失败，类型已存在");
//        }
        for (int i = 0; i < Se.size(); i++) {
            Se item = Se.get(i);
            item.setCreateBy( getUsername());
        }
        return toAjax(seService.save(Se));
    }

    /**
     * 修改数据
     */
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "数据管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Se dept)
    {
//        Long deptId = dept.getId();
//        seService.checkSeDataScope(deptId);
//        if (!seService.checkSeNameUnique(dept))
//        {
//            return error("修改数据'" + dept.getSeName() + "'失败，数据名称已存在");
//        }
//        else if (dept.getParentId().equals(deptId))
//        {
//            return error("修改数据'" + dept.getSeName() + "'失败，上级数据不能是自己");
//        }
//        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && seService.selectNormalChildrenSeById(deptId) > 0)
//        {
//            return error("该数据包含未停用的子数据！");
//        }
        dept.setUpdateBy(getUsername());
        return toAjax(seService.updateSe(dept));
    }

    /**
     * 删除数据
     */
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "数据管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    public AjaxResult remove(@PathVariable Long deptId)
    {
        return toAjax(seService.deleteSeById(deptId));
    }

    @Log(title = " ", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport)
    {
        //输入数据
        String operName = getUsername();
        try {
            String message = seService.importSe(file, updateSupport, operName);
            return success(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    //数据的获取已经搞定，需要把模型训练出来
    @Log(title = "模型训练", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/trainAndForecast")
    public AjaxResult  trainAndForecast( )
    {
        GlobalObject globalObject = GlobalObject.getInstance();
        SeQueryCriteria criteria = new SeQueryCriteria();
        criteria.setIsDel(0);
        List<Se> datas = seService.selectSeList(criteria);
        return globalObject.trainModel(datas);
    }


    //模型训练出来后，根据模型生成判断结果
    @Log(title = "模型使用", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/onlyForecast")
    public AjaxResult  onlyForecast(@RequestBody Se Se ) throws IOException {
//       ApacheFG.getGF2();
        // Load and parse the data file, converting it to a DataFrame.
        SparkSession spark = SparkSession.builder().appName("RandomForestRegression").master("local").getOrCreate();
        Dataset<Row> data = spark.read().format("csv")
                .option("header","true")
                .option("inferSchema","true")
                .load("data.csv");
        String [] features = {"small", "medimu","large","larger"};
// Automatically identify categorical features, and index them.
// Set maxCategories so features with > 4 distinct values are treated as continuous.
        VectorAssembler featureIndexer = new VectorAssembler()
                .setInputCols(features)
                .setOutputCol("label1");
// Split the data into training and test sets (30% held out for testing)
        Dataset<Row>[] splits = data.randomSplit(new double[] {0.9, 0.1});
        Dataset<Row> trainingData = splits[0];
        Dataset<Row> testData = splits[1];

        String modelPath = "C:/"+getUsername()+"/model";
        File file = new File(modelPath);
//        File[] tempList1 = file2.listFiles();
        if(!file.exists()) {
            // Train a RandomForest model.
            RandomForestRegressor rf = new RandomForestRegressor()
                    .setLabelCol("label")
                    .setFeaturesCol("label1")
                    .setNumTrees(10); // 设置树的数量，默认是10;
// Chain indexer and forest in a Pipeline
            Pipeline pipeline = new Pipeline()
                    .setStages(new PipelineStage[]{featureIndexer, rf});
// Train model. This also runs the indexer.
            PipelineModel model = pipeline.fit(trainingData);
// Make predictions.
            Dataset<Row> predictions = model.transform(testData);
            Map<String, String> writeOpts = new HashMap<>();
//        file_name = os.path.basename(item)  # 带了扩展名（.CSV）的文件名
//# 将归一化后的数据存到新的路径下
//                inter_path = os.path.join(root1, cla,file_name)
//        df1.to_csv(inter_path, mode='w', index=False)
            model.save(modelPath);
            List<Double> types = predictions.limit(1).select("prediction").as(Encoders.DOUBLE()).collectAsList();
            return success("预测结果是"+types.get(0));
        }else{
            // 模型存储地址
            PipelineModel pipelineModel = PipelineModel.load(modelPath);// 加载存储的模型
            Dataset<Row> predictionDF = pipelineModel.transform(testData);      // 对用户数据进行预测
            List<Double> types = predictionDF.limit(1).select("prediction").as(Encoders.DOUBLE()).collectAsList();
            return success("预测结果是"+types.get(0));
        }
    }

    //模型训练出来后，根据模型生成判断结果
    @Log(title = "模型使用", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/onlyForecastEasyAIType")
    public AjaxResult  onlyForecastEasyAIType(@RequestBody Se Se ){
        String str = "预测结果是 : ";
        GlobalObject globalObject = GlobalObject.getInstance();
        RandomForest rf = globalObject.getRf();
        if(globalObject.getState()!=1){
            return error("模型还没有训练完成,请先训练模型");
        }
        try {
            int type =  rf.forest(Se);
            switch (type){
                case 1: str+="setosa";break;
                case 2: str+="versicolor";break;
                case 3: str+="virginica";break;
            }
            return success(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//    //模型训练出来后，根据模型生成判断结果
//    @Log(title = "模型使用", businessType = BusinessType.IMPORT)
//    @PreAuthorize("@ss.hasPermi('system:user:import')")
//    @PostMapping("/onlyForecastEasyAIRegression")
//    public AjaxResult  onlyForecastEasyAIRegression(@RequestBody Se Se ){
//        String str = "预测结果是 : ";
//        GlobalObject globalObject = GlobalObject.getInstance();
//        RegressionForest gf = globalObject.getGf();
//        if(globalObject.getState()!=1){
//            return error("模型还没有训练完成,请先训练模型");
//        }
//        try {
//            int type =  gf.forest(Se);
//            switch (type){
//                case 1: str+="setosa";break;
//                case 2: str+="versicolor";break;
//                case 3: str+="virginica";break;
//            }
//            return success(str);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:post:export')")
    @PostMapping("/export")
    public AjaxResult export(Se post) throws IOException {
        return success();
    }
}
