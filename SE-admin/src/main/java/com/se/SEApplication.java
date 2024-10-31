package com.se;

import org.apache.catalina.Pipeline;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动程序
 *
 * @author se
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class   })
@EnableAutoConfiguration
@MapperScan("com.se.system.mapper")
public class SEApplication
{
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = SpringApplication.run(SEApplication.class, args);


//		System.out.print("Learned regression forest model:" + rfModel.toDebugString());

//
//// 加载数据
//        Dataset<Row> data = jsc.read().format("libsvm").load("iris.libsvm");
//
//// 准备特征
//        VectorAssembler assembler = new VectorAssembler()
//                .setInputCols(data.columns())
//                .setOutputCol("features");
//        Dataset<Row> assembledData = assembler.transform(data);
//
//// 创建随机森林分类器
//        RandomForestClassifier rf = new RandomForestClassifier()
//                .setLabelCol("class")
//                .setFeaturesCol("features")
//                .setNumTrees(100);
//
//// 训练模型
//        Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] {rf});
//        PipelineModel model = pipeline.fit(assembledData);
//
//// 预测
//        Dataset<Row> predictions = model.transform(assembledData);
//
//// 评估模型
//        MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
//                .setLabelCol("class")
//                .setPredictionCol("prediction")
//                .setMetricName("accuracy");
//        double accuracy = evaluator.evaluate(predictions);
//        System.out.println("Accuracy = " + accuracy);
//
//// 关闭Spark上下文
//        jsc.stop();
////                // 创建SparkConf对象
////                SparkConf conf = new SparkConf()
////                        .setAppName("WordCount")
////                        .setMaster("local");
////
////                // 创建JavaSparkContext对象
////                JavaSparkContext sc = new JavaSparkContext(conf);
////
////                // 读取文本文件
////                JavaRDD<String> lines = sc.textFile("input.txt");
////
////                // TODO: 编写应用程序代码
////                System.out.println(lines.toString());
////                // 关闭JavaSparkContext对象
////                sc.close();
//
//
//
////        java版本
//        //java版本
////    SparkConf
////        SparkConf conf = new SparkConf();
////        conf.setMaster("local");    //本地单线程运行
////        conf.setAppName("testJob");
////        JavaSparkContext sc = new JavaSparkContext(conf);
////        IrisMapper irisMapper = context.getBean(IrisMapper.class);
////        System.out.println(irisMapper.selectList(null));
//
//        SparkSession spark = SparkSession.builder()
//                .appName("SparkDemo")
//                .master("local[*]")
//                .getOrCreate();
//
//
////        SparkSession spark = SparkSession
////                .builder()
////                .appName("Java Spark SQL basic example")
////                .config("spark.some.config.option", "some-value")
////                .getOrCreate();
//        // Load and parse the data file, converting it to a DataFrame.
//        Dataset<Row> data = spark.read().format("libsvm").load("data/mllib/sample_libsvm_data.txt");
//        // Automatically identify categorical features, and index them.
//        // Set maxCategories so features with > 4 distinct values are treated as continuous.
////        VectorIndexerModel featureIndexer = new VectorIndexer()
////                .setInputCol("features")
////                .setOutputCol("indexedFeatures")
////                .setMaxCategories(4)
////                .fit(data);
////        // Split the data into training and test sets (30% held out for testing)
////        Dataset<Row>[] splits = data.randomSplit(new double[] {0.7, 0.3});
////        Dataset<Row> trainingData = splits[0];
////        Dataset<Row> testData = splits[1];
////        // Train a RandomForest model.
////        RandomForestRegressor rf = new RandomForestRegressor()
////                .setLabelCol("label")
////                .setFeaturesCol("indexedFeatures");
////        // Chain indexer and forest in a Pipeline
////        Pipeline pipeline = new Pipeline()
////                .setStages(new PipelineStage[] {featureIndexer, rf});
////        // Train model. This also runs the indexer.
////        PipelineModel model = pipeline.fit(trainingData);
////        // Make predictions.
////        Dataset<Row> predictions = model.transform(testData);
////        // Select example rows to display.
////        predictions.select("prediction", "label", "features").show(5);
////
////        // Select (prediction, true label) and compute test error
////        RegressionEvaluator evaluator = new RegressionEvaluator()
////                .setLabelCol("label")
////                .setPredictionCol("prediction")
////                .setMetricName("rmse");
////        double rmse = evaluator.evaluate(predictions);
////        System.out.println("Root Mean Squared Error (RMSE) on test data = " + rmse);
////
////        RandomForestRegressionModel rfModel = (RandomForestRegressionModel)(model.stages()[1]);
////        System.out.println("Learned regression forest model:\n" + rfModel.toDebugString());
    }


}
