右键Module->Open Module Settings->Libraries

![](http://7xt4et.com2.z0.glb.clouddn.com/13.png)

点击+号新建一个Libraries（选择Java）

![](http://7xt4et.com2.z0.glb.clouddn.com/14.png)

在这里选择Build/bin/opencv-2412.jar（即刚刚生成的jar）
在Choose Modules里面选择你要使用到opencv的Module

![](http://7xt4et.com2.z0.glb.clouddn.com/15.png)

然后在这样Open Module Settings中Apply，jar包就导入了，接下来配置lib路径

![](http://7xt4et.com2.z0.glb.clouddn.com/17.png)

在上图中点HelloOpenCV（选择自己的工程）打开Edit Configurations

![](http://7xt4et.com2.z0.glb.clouddn.com/16.png)

在VN options 设置参数：

	-Djava.library.path=/home/sun/OpenCV2.4.12/build/lib  //选择自己opencv lib库路径
	
测试：

java:

```java
    import org.opencv.core.Core;
    import org.opencv.core.CvType;
    import org.opencv.core.Mat;
    
    public class Demo1
    {
       public static void main( String[] args )
       {
          System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
          Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
          System.out.println( "mat = " + mat.dump() );
       }
    }
```
    
scala:

```scala
    import org.opencv.core.{Core, CvType, Mat}
    
    object Demo1 {
      def main(args: Array[String]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
        val mat=Mat.eye(3, 3, CvType.CV_8UC1)
        println(mat.dump())
    
      }
    }
```

成功输出则配置正确。