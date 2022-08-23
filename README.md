# Final-Project-BigData

## Mô hình hoạt động của dữ liệu
![pipeline](https://user-images.githubusercontent.com/69194434/185856280-fe22f278-9754-4274-aa25-a2b1703794ca.PNG)
1. Lấy data bằng API
2. Đẩy dữ liệu lên Kafka
3. Xử lí dữ liệu bằng SparkStreaming
4. Đẩy dữ liệu vào HDFS
5. Visualize dữ liệu

## Các bước thực hiện

#### Chạy file "Fetch data using API.py" để lấy dữ liệu theo giờ từ 1-1-2020 cho đến hiện tại (22-8-2022). Dữ liệu được lưu trong folder data.
#### Chạy file "Producer.java" để đẩy dữ liệu lên Kafka ở server 172.17.80.28 user "hadoop", topic name hoand68_data, dữ liệu được đẩy 1 record/s để giả lập realtime.
#### Mở file "SparkSS.java", nhấp double Ctrl, chọn "mvn package" để gen ra file jar, scp file jar lên server 172.17.80.21 user "hadoop", sau đó chạy spark job bằng câu lệnh:
      spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.0 --deploy-mode client --class spark.SparkSS /home/hadoop/Final_Project_BigData-1.0-SNAPSHOT.jar

#### Câu lệnh start Spark Thrift Server trên server 172.17.80.21:
      start-thriftserver.sh --master yarn --num-executors 1  --driver-memory 512m --executor-memory 512m  --executor-cores 1 --driver-cores 1 --queue queue1  --hiveconf hive.server2.thrift.port=10015 
      Cấu hình Spark Thrift được đặt nhỏ nhất có thể do server yếu
#### Câu lệnh kết nối bằng beeline với khi yêu cầu user password ấn enter
      beeline
      !connect jdbc:hive2://0.0.0.0:10015/
#### Câu lệnh ngắt kết nối với beeline
      !q
#### Câu lệnh create Hive table lấy dữ liệu từ HDFS để truy vấn và visualize trên Superset:
      create external table cryptodata (OpenTime string, ETH_USD_Open string, ETH_USD_High string, ETH_USD_Low string, ETH_USD_Close string, ETH_USD_volume string, CloseTime string, BTC_USD_Open string, BTC_USD_High string, BTC_USD_Low string, BTC_USD_Close string, BTC_USD_volume string) STORED AS PARQUET LOCATION 'hdfs://172.17.80.21:9000/user/hoand68/final_project/output';
      
      Các trường được đặt hết là string do để type khác thì lỗi khi truy vấn :<
#### Biểu đồ visualize trên Superset
![image](https://user-images.githubusercontent.com/69194434/186052728-c8d16733-7d83-4057-b182-03e067081a06.png)
