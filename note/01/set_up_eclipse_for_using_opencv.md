新建一个Java工程

![](http://7xt4et.com2.z0.glb.clouddn.com/2.png)

右键工程->Build Path->Configure Build Path,进入Java Build Path配置页面，然后点击Add Library

![](http://7xt4et.com2.z0.glb.clouddn.com/3.png)

接下来按照图示操作：

![](http://7xt4et.com2.z0.glb.clouddn.com/4.png)

![](http://7xt4et.com2.z0.glb.clouddn.com/5.png)

![](http://7xt4et.com2.z0.glb.clouddn.com/6.png)

![](http://7xt4et.com2.z0.glb.clouddn.com/7.png)

![](http://7xt4et.com2.z0.glb.clouddn.com/8.png)

![](http://7xt4et.com2.z0.glb.clouddn.com/9.png)

点击Edit后在弹出的对话框中输入OpenCV/build/lib路径

![](http://7xt4et.com2.z0.glb.clouddn.com/10.png)

点击finish结束,创建User Library的目的是为了方便导入OpenCV的依赖。

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




