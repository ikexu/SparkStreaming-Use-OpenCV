import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;
/**
 * 采集摄像头数据（或本地图片），筛选出图像的人脸部分并用方框标记
 * @author kexu
 *
 */
public class FaceTest {

	public static void detectFace() throws Exception

	{

		System.out.println("\nRunning DetectFaceDemo");

		// 导入opencv的库

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录中

		CascadeClassifier faceDetector = new CascadeClassifier(
				"/home/kexu/work/opencv2412/data/haarcascades/haarcascade_frontalface_default.xml");
		// 调用摄像头捕获图片信息

		Mat image = new Mat();
		VideoCapture webcam = new VideoCapture(0);
		Thread.sleep(10000);
		if (!webcam.isOpened()) {
			System.out.println("Did not connect to camera");
		} else
			System.out.println("found webcam: " + webcam.toString());

		webcam.read(image);

		// Mat image = Highgui.imread("/home/kexu/桌面/p.jpg");
		// 在图片中检测人脸

		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);
		System.out.println(String.format("Detected %s faces",

		faceDetections.toArray().length));

		// 在每一个识别出来的人脸周围画出一个方框

		for (org.opencv.core.Rect rect : faceDetections.toArray()) {
			Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
					+ rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		}

		// 将结果保存到文件

		String filename = "faceDetection.png";
		System.out.println(String.format("Writing %s", filename));
		Highgui.imwrite(filename, image);
	}

	public static void main(String[] args) throws Exception {
		FaceTest.detectFace();

	}
}
