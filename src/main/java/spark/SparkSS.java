package spark;

import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.StructType;

import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class SparkSS {
    public static void main(String[] args) throws TimeoutException, StreamingQueryException {
        SparkSession spark = SparkSession
                .builder()
                .appName("Spark SS_Kafka")
//                .master("local")
                .config("spark.dynamicAllocation.enabled","false")
                .config("spark.scheduler.mode", "FAIR")
                .getOrCreate();

        spark.sparkContext().setLogLevel("ERROR");
        ReadStream(spark);
    }

    private static void ReadStream(SparkSession spark) throws TimeoutException, StreamingQueryException {

        Dataset<Row> df = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "172.17.80.28:9092")
                .option("subscribe", "hoand68_data")
                .option("group.id","group2")
                .option("startingOffsets","earliest")
                .option("auto.offset.reset","true")
                .option("failOnDataLoss", "false")
                .load();

        Dataset<Row> df1 = df.selectExpr("CAST (value AS STRING)").alias("csv").select("csv.*");
        Dataset<Row> df2 = df1.selectExpr("split(value,',')[1] as OpenTime",
                                          "split(value,',')[2] as ETH_USD_Open",
                                          "split(value,',')[3] as ETH_USD_High",
                                          "split(value,',')[4] as ETH_USD_Low",
                                          "split(value,',')[5] as ETH_USD_Close",
                                          "split(value,',')[6] as ETH_USD_volume",
                                          "split(value,',')[7] as CloseTime",
                                          "split(value,',')[8] as BTC_USD_Open",
                                          "split(value,',')[9] as BTC_USD_High",
                                          "split(value,',')[10] as BTC_USD_Low",
                                          "split(value,',')[11] as BTC_USD_Close",
                                        "split(value,',')[12] as BTC_USD_volume"

                                            );

        df2.printSchema();

        StreamingQuery query = df2
                .writeStream()
                .format("parquet")
                .outputMode("append")
                .option("checkpointLocation", "/user/hoand68/final_project/checkpoint")
                .option("path", "/user/hoand68/final_project/output")
                .start();
        query.awaitTermination();
    }
}
