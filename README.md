# Final-Project-BigData

## Mô hình hoạt động của dữ liệu
=======
Pipeline architecture.png![Pipeline architecture]
![pipeline](https://user-images.githubusercontent.com/69194434/185856280-fe22f278-9754-4274-aa25-a2b1703794ca.PNG)
>>>>>>> 094bbd35e0afe158fd08efcd302247f1a42cb65a
1. Lấy data bằng API
2. Đẩy dữ liệu lên Kafka
3. Xử lí dữ liệu bằng SparkStreaming
4. Đẩy dữ liệu vào HDFS
5. Visualize dữ liệu
