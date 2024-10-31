//package com.se.web.controller.system;
//
//import lombok.val;
//import org.apache.spark.ml.classification.RandomForestClassifier;
//import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
//import org.apache.spark.ml.feature.VectorAssembler;
//import org.apache.spark.ml.tuning.ParamGridBuilder;
//import org.apache.spark.ml.tuning.TrainValidationSplit;
//import org.apache.spark.sql.SparkSession;
//
//public class RawTest {
//
//    package com.yyds
//
//
//import org.apache.log4j.{Level, Logger}
//import org.apache.spark.sql.{DataFrame, SparkSession}
//import org.apache.spark.ml.feature.VectorAssembler
//import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
//import org.apache.spark.ml.{Model, Pipeline, PipelineModel, Transformer}
//import org.apache.spark.ml.tuning.{ParamGridBuilder, TrainValidationSplit, TrainValidationSplitModel}
//import org.apache.spark.ml.classification.RandomForestClassifier
//import org.apache.spark.ml.classification.RandomForestClassificationModel
//import scala.util.Random
//
//
//    object ForestModelTest {
//
//        Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
//
//        def main(args: Array[String]): Unit = {
//
//
//                // 构建SparkSession实例对象，通过建造者模式创建
//                val spark: SparkSession = {
//                SparkSession
//                        .builder()
//                        .appName(this.getClass.getSimpleName.stripSuffix("$"))
//                        .master("local[1]")
//                        .config("spark.sql.shuffle.partitions", "3")
//                        .getOrCreate()
//        }
//
//
//        // 利用Spark内置的读取CSV数据功能
//        val dataWithHeader: DataFrame = spark.read
//                .option("inferSchema", "true") // 数据类型推断
//                .option("header", "true") // 表头解析
//                .csv("D:\\kaggle\\covtype\\covtype.csv")
//
//
//        // 划分训练集和测试集
//        val Array(trainData, testData) = dataWithHeader.randomSplit(Array(0.9, 0.1))
//        trainData.cache()
//        testData.cache()
//
//        // 输入的特征列
//        val inputCols: Array[String] = trainData.columns.filter(_ != "Cover_Type")
//
//
//        val newAssembler = new VectorAssembler()
//                .setInputCols(inputCols)
//                .setOutputCol("featureVector")
//
//
//        // 随机森林分类器
//        val newClassifier = new RandomForestClassifier()
//                .setSeed(Random.nextLong())
//                .setLabelCol("Cover_Type")
//                .setFeaturesCol("featureVector")
//                .setPredictionCol("prediction")
//
//
//        // 组合为Pipeline
//        val pipeline = new Pipeline().setStages(Array(newAssembler, newClassifier))
//
//        // 使用 SparkML API 内建支持的 ParamGridBuilder 来测试超参数的组合
//        val paramGrid = new ParamGridBuilder() // 4个超参数来说，每个超参数的两个值都要构建和评估一个模型，共计16种超参数组合，会训练出16个模型
//                .addGrid(newClassifier.impurity, Seq("gini", "entropy"))
//                .addGrid(newClassifier.maxDepth, Seq(1, 20))
//                .addGrid(newClassifier.maxBins, Seq(40, 300))
//                .addGrid(newClassifier.numTrees, Seq(10, 20)) // 要构建的决策树的个数
//                .build()
//
//        // 设定评估指标  准确率
//        val multiclassEval = new MulticlassClassificationEvaluator()
//                .setLabelCol("Cover_Type")
//                .setPredictionCol("prediction")
//                .setMetricName("accuracy")
//
//        // 这里也可以用 CrossValidator 执行完整的 k 路交叉验证，但是要额外付出 k 倍的代价，并且在大数据的情况下意义不大。
//        // 所以在这里 TrainValidationSplit 就够用了
//        val validator = new TrainValidationSplit()
//                .setSeed(Random.nextLong())
//                .setEstimator(pipeline)           // 管道
//                .setEvaluator(multiclassEval)     // 评估器
//                .setEstimatorParamMaps(paramGrid) // 超参数组合
//                .setTrainRatio(0.9)               // 训练数据实际上被TrainValidationSplit 划分成90%与10%的两个子集
//
//        val validatorModel: TrainValidationSplitModel = validator.fit(trainData)
//
//        // validator 的结果包含它找到的最优模型。
//        val bestModel = validatorModel.bestModel
//
//        // 打印最优模型参数
//        // 手动从结果 PipelineModel 中提取 DecisionTreeClassificationModel 的实例，然后提取参数
//        println(bestModel.asInstanceOf[PipelineModel].stages.last.extractParamMap)
//
//        // 随机森林分类器有另外一个超参数：要构建的决策树的个数。
//        // 与超参数 maxBins 一样，在某个临界点之前，该值越大应该就能获得越好的效果。然而，代价是构造多棵决策树的时间比建造一棵的时间要长很多倍。
//        val forestModel = bestModel.asInstanceOf[PipelineModel].stages.last.asInstanceOf[RandomForestClassificationModel]
//
//        // 我们对于特征的理解更加准确了
//        println("特征重要性：")
//        println(forestModel.featureImportances.toArray.zip(inputCols).sorted.reverse.foreach(println))
//
//
//        // 这个模型在交叉验证集中达到的准确率是多少？最后，在测试集中能达到什么样的准确率？
//        println("交叉验证集上最大准确率：" + validatorModel.validationMetrics.max)
//        println("测试集上的准确率：" + multiclassEval.evaluate(bestModel.transform(testData)))
//
//
//        // 预测
//        // 得到的“最优模型”实际上是包含所有操作的整个管道，其中包括如何对输入进行转换以适于模型处理，以及用于预测的模型本身。
//        // 它可以接受新的 DataFrame 作为输入。它与我们刚开始时使用的 DataFrame 数据的唯一区别就是缺少“Cover_Type”列
//        bestModel
//                .transform(testData.drop("Cover_Type"))
//                .select("prediction")
//                .show(10)
//
//  }
//
//    }
//
//
//}
// 创建SparkSession
//SparkSession spark = SparkSession.builder().appName("RandomForestRegression").getOrCreate();
//
//// 假设我们有一个数据集df，其中包含特征列（features）和标签列（label）
//Dataset<Row> data = spark.read().format("csv") // 根据实际情况选择合适的数据读取方式
//        .option("header", "true")
//        .option("inferSchema", "true")
//        .load("data.csv"); // 替换为实际文件路径
//
//// 将特征列和标签列合并为一个向量列
//String featuresCol = "features";
//String labelCol = "label";
//VectorAssembler assembler = new VectorAssembler()
//        .setInputCols(Arrays.asList(featuresCol))
//        .setOutputCol("features_vec");
//
//// 构建随机森林回归模型
//RandomForestRegressor rf = new RandomForestRegressor()
//        .setLabelCol(labelCol)
//        .setFeaturesCol("features_vec")
//        .setNumTrees(10); // 设置树的数量，默认是10
//
//// 构建Pipeline来处理整个流程
//Pipeline pipeline = new Pipeline()
//        .setStages(new Stage[] {assembler, rf});
//
//// 拟合模型
//Dataset<Row> modelData = pipeline.fit(data);
//
//// 预测新数据
//Dataset<Row> predictions = modelData.transform(data.sample(False, 0.2)); // 测试集采样
//
//// 输出预测结果
//        predictions.select("prediction", "label").show(); // 显示预测值和真实标签
//
//// 关闭SparkSession
//        spark.stop();
//    }