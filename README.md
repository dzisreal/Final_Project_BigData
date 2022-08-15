# Final-Project-BigData

## Mô hình hoạt động của dữ liệu
Pipeline architecture.png![Pipeline architecture](https://user-images.githubusercontent.com/69194434/184597524-cac45dc8-e4bb-4683-bcb1-1519d4c054ba.png)
1. Lấy data bằng API
2. Đẩy dữ liệu lên Kafka
3. Xử lí dữ liệu bằng SparkStreaming
4. Đẩy dữ liệu vào HDFS
5. Visualize dữ liệu
