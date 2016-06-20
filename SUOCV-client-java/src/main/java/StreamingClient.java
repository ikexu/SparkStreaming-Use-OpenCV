import java.util.Properties;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nu.pattern.OpenCV;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;


import javax.xml.bind.DatatypeConverter;

/**
 * Created by kexu on 16-5-23.
 */



public class StreamingClient {
    private static Random rand = new Random();

    public static void main(String[] args) throws Exception {


       System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //System.loadLibrary("opencv_java2412");
        //OpenCV.loadShared();
        //OpenCV.loadLocally();
        System.out.println("Start Run!");



        VideoCapture camera = new VideoCapture(0);
        camera.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, 200);
        camera.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, 200);

        //For wait camera start
        Thread.sleep(5000);
        if (!camera.isOpened()) {
            System.out.println("Camera Error!");
        } else {
            System.out.println("Camera OK!");
        }
        Properties props = new Properties();

        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        if(true) {
            {
                Thread.sleep(200);
                 Mat frame= new Mat();
                camera.read(frame);

                String matJson= matToJson(frame);
                JsonObject jo=new JsonObject();
                jo.addProperty("mat",matJson);
                jo.addProperty("ip", "45.32.75.110");
                System.out.println(matJson);
                producer.send(new ProducerRecord<String, String>("test", rand.nextInt(2) + "", jo.toString()));

            }
        }


    }


    public static String matToJson(Mat mat){
        //Log.d(TAG, "matToJson");

        JsonObject obj = new JsonObject();

        if(mat.isContinuous()){
            int cols = mat.cols();
            int rows = mat.rows();
            int elemSize = (int) mat.elemSize();

            byte[] data = new byte[cols * rows * elemSize];

            mat.get(0, 0, data);
            System.out.println("rows "+mat.rows());
            System.out.println("cols "+mat.cols());
            System.out.println("type "+mat.type());

            obj.addProperty("rows", mat.rows());
            System.out.println("rowsobj " + obj.toString());
            obj.addProperty("cols", mat.cols());
            System.out.println("colsobj" + obj.toString());
            obj.addProperty("type", mat.type());
            System.out.println("typeobj" + obj.toString());

            // We cannot set binary data to a json object, so:
            // Encoding data byte array to Base64.
            String dataString = new String(DatatypeConverter.printBase64Binary(data));

            //Log.d(TAG, "matToJson - dataString: "+dataString);

            obj.addProperty("data", dataString);


            Gson gson = new Gson();
            String json = gson.toJson(obj);

            // Log.d(TAG, "matToJson - json: "+json);
          // System.out.println(json);
            return json;
        } else {
             System.out.println("else");

            // Log.e(TAG, "Mat not continuous.");
        }
        return "{}";
    }


}
