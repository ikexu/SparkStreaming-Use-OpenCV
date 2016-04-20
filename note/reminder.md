
Runtime Opencv HighGui Error- “HIGHGUI ERROR: V4L/V4L2: VIDIOC_S_CROP” ?

> sudo apt-get install v4l2ucp v4l-utils libv4l-dev

Re compile openCV.

[Link](http://stackoverflow.com/questions/16287488/runtime-opencv-highgui-error-highgui-error-v4l-v4l2-vidioc-s-crop-opencv-c)

---

Select Timeout error in Ubuntu - Opencv ?

```java
    VideoCapture camera = new VideoCapture(0);
    camera.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, 352);
    camera.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, 288);
```

Manually set the `CV_CAP_PROP_FRAME_WIDTH` and `CV_CAP_PROP_FRAME_HEIGHT` property before using camera do some operation.

[Link](http://stackoverflow.com/questions/12715209/select-timeout-error-in-ubuntu-opencv)
