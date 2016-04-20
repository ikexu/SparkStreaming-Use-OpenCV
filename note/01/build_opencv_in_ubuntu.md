下载依赖:
	
> sudo apt-get install cmake ant
	
[OpenCV2.4.12下载地址](http://opencv.org/downloads.html),解压下载OpenCV的文件在您选择的位置,例如$HOME/OpenCV2.4.12，进入OpenCV2.4.12目录，并在目录下创建build文件夹(名字随意)。

> cd $HOME/OpenCV2.4.12/ 
> mkdir build
> cd build
	
执行cmake命令(在上面创建的目录中执行，**..**指上级目录)

> cmake - DBUILD_SHARED_LIBS = OFF ..
	
然后检查输出信息To be build里面有没有java

![](http://7xt4et.com2.z0.glb.clouddn.com/1.png)

如果没有则应该少了依赖，请检查cmake jdk等是否配置正确，如果有则


> make -j8

执行完后，查看/build/bin/下面是否存在opencv_2.4.12.jar，存在则编译成功。