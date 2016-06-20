package com.server.scala


import java.util.Random
import javax.xml.bind.DatatypeConverter

import com.google.gson.{JsonObject, JsonParser}
import kafka.serializer.StringDecoder
import nu.pattern.OpenCV
import org.apache.hadoop.hbase.client.{Put, HTable}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{TableName, HBaseConfiguration}
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.opencv.core._
import org.opencv.highgui.Highgui
import org.opencv.objdetect.CascadeClassifier
import sun.misc.BASE64Encoder

import scalaj.http.Http

/**
 * Created by wendell and kexu on 16-4-27.
 */
object StreamingServer {

  //System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
  OpenCV.loadShared
  var faceDetector = new CascadeClassifier("/home/kexu/桌面/haarcascade_frontalface_alt.xml")
  def main(args: Array[String]) {



    // Create context with 2 second batch interval
    val sparkConf = new SparkConf().setMaster("local[3]").setAppName("KafkaTest")

    val ssc = new StreamingContext(sparkConf, Milliseconds(190))
    val topics = Set("test")
    val brokers = "localhost:9092"

    // Create direct kafka stream with brokers and topics
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers,"serializer.class" -> "kafka.serializer.StringEncoder")
    val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topics)



      messages.foreachRDD { rdd =>


        //myTable.setAutoFlush(false, false)//
        //myTable.setWriteBufferSize(3*1024*1024)
        rdd.foreachPartition {
          partition =>

            val myConf = HBaseConfiguration.create()
            myConf.addResource("hbase-site.xml")
            val myTable = new HTable(myConf, TableName.valueOf("iamgedata"))
            partition.foreach {

              record =>
                val jsonParse=new JsonParser()
                val obj=jsonParse.parse(record._2).getAsJsonObject
                val matJson=obj.get("mat").getAsString
                val ip=obj.get("ip").getAsString

                val image=matFromJson(matJson)

                val faceDetections = new MatOfRect()
                faceDetector.detectMultiScale(image, faceDetections)

                for(rect<-faceDetections.toArray){
                  Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
                }

                val bytemat=new MatOfByte();
                Highgui.imencode(".jpg", image, bytemat);
                val imgBase64 = new sun.misc.BASE64Encoder().encode(bytemat.toArray)
                println(imgBase64)
                Http("http://localhost:4444/send").postForm(Seq("id"-> record._1,"content" -> imgBase64,"ip"->ip)).asString

                //将数据写入hbase
               /*
                val patition: Int = new Random().nextInt(3)
                val putkey: String = patition + System.currentTimeMillis().toString
                val p = new Put(Bytes.toBytes(putkey))
                p.add("image".getBytes, "data".getBytes, Bytes.toBytes(imgBase64))
                myTable.put(p)*/




            }

        }
      }
    // Start the computation
    ssc.start()
    ssc.awaitTermination()
  }

  def matFromJson(json: String): Mat = {
    val parser: JsonParser = new JsonParser
    val JsonObject: JsonObject = parser.parse(json).getAsJsonObject
    val rows: Int = JsonObject.get("rows").getAsInt
    val cols: Int = JsonObject.get("cols").getAsInt
    val `type`: Int = JsonObject.get("type").getAsInt
    val dataString: String = JsonObject.get("data").getAsString
    val data: Array[Byte] = DatatypeConverter.parseBase64Binary(dataString)
    val mat: Mat = new Mat(rows, cols, `type`)
    mat.put(0, 0, data)
    return mat
  }
}
