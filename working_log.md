## Working Log

### 01-(04.14-04.17)

    1.搭建OpenCV环境(eclipse & idea)
    2.确定实现方案

出入

    无

开发环境，IDE都配置好了。版本暂定使用jdk 1.7,opencv 2.4.12,scala 2.11(kafka及spark待定)。实现方案确定为利用opencv抓取本地(远程?)摄像头数据(矩阵)，发送到kafka(生产者)。spark streaming从kafka中取出消息(消费者)，并进行实时分析(展示?入库?)。这种方案网上已有的资料较多，减少学习成本。

### 02-(04.18-04.24)

    1.学习opencv，拿到摄像头数据,完成人脸检测、字符分割等
    2.学习kafka，完成消息的存取
    3.学习spark streaming & scala
    4.调通三者，完成单机的scala & java例子程序
    
