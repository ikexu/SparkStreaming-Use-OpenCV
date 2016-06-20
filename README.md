## SparkStreamingUseOpenCV

#introduction
Spark Streaming与OpenCV传感器数据实时获取:熟悉OpenCV的使用，获取本机的摄像头的图像数据，并通过SparkStreaming做实时分析

#Client
client使用OpenCV抓取摄像头数据，按照一定的时间，捕捉每帧图像，将图像数据重新编码后按照JSON格式通过kafka发送到Server上

#Server
Spark Streaming实时接收来自kafka的多客户端的图像数据，并对数据做以下处理：将图像数据解码后重新还原成图像，对图像调用OpenCV的人脸识别模块，标记出人脸区域，将重新构建的图像存进HBase数据库做原始数据备份，同时将图像数据传给web socket监听端口。图像处理的模块采用的是松耦合的结构
，可以替换为其它图像处理模块。

#Display 
web socket服务将接受的图像数据按照client不同，分别显示在页面上



