package org.qq;

import java.io.File;
import java.io.InputStream;
//import java.net.URL;
//import javax.swing.border.LineBorder;
import org.photonvision.raspi.LibCameraJNI;
import org.photonvision.vision.frame.Frame;
import org.photonvision.vision.frame.FrameStaticProperties;
import org.photonvision.vision.frame.FrameThresholdType;
import org.photonvision.vision.opencv.CVMat;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
//import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.tracking.legacy_MultiTracker;

//import com.nativeutils.NativeUtils;

public class Demo {

   public static BufferedImage toImage(Mat mat) throws IOException{
     // decode image into   
    //Encoding the image
      MatOfByte matOfByte = new MatOfByte();
      Imgcodecs.imencode(".jpg", mat, matOfByte);
      //Storing the encoded Mat in a byte array
      byte[] byteArray = matOfByte.toArray();
      //Preparing the Buffered Image
      InputStream in = new ByteArrayInputStream(byteArray);
      BufferedImage bufImage = ImageIO.read(in);
      return bufImage;
   }

    public static void main(String[] args) throws Exception {
        System.load("/home/brendan/jni/libphotonlibcamera.so");
        //System.load("/home/brendan/jni/libopencv_java480.so");       
        System.load("/usr/local/share/java/opencv4/libopencv_java480.so");
        System.out.println("System Cameras:");
        String[] cameras = LibCameraJNI.getCameraNames();
        for (String name : cameras) {
            System.out.println("  " + name);
        }

        long cameraId = LibCameraJNI.createCamera(cameras[0], 640, 480, 0);
        LibCameraJNI.startCamera( cameraId );
        LibCameraJNI.setExposure(cameraId, 800);
        LibCameraJNI.setBrightness(cameraId, 51 );
        LibCameraJNI.setAnalogGain(cameraId, 4.2f );
        LibCameraJNI.setFramesToCopy(cameraId, true, true);
        LibCameraJNI.setGpuProcessType(cameraId, 1);
        LibCameraJNI.setAwbGain(cameraId, 13, 15);
        FrameStaticProperties frameProps = new FrameStaticProperties(640, 480, 45, null );

        long start = System.currentTimeMillis();
        long elapsed = 0;
        long sequenceId = 0l;
        while ( elapsed < 1000 ) {   
            //LibCameraJNI.startCamera(cameraId);
            LibCameraJNI.setFramesToCopy(cameraId, true, true);
            LibCameraJNI.getSensorModelRaw(cameraId);
            long pairId = LibCameraJNI.awaitNewFrame(cameraId);
            if ( pairId == 0 ) {
                System.out.println( "bad frame received" );
                Thread.sleep(1000);
                continue;
            }

            long frameId = LibCameraJNI.takeColorFrame(pairId);
            if ( frameId == 0 ) {
                System.out.println( "bad color frame received" );
                Thread.sleep(1000);
                continue;
            }
            Mat mat = new Mat(frameId);
            if ( mat.empty() ) {
                System.out.println( "empty mat" );
                continue;
            }
            long processed = LibCameraJNI.takeProcessedFrame(pairId); 
            int itype = LibCameraJNI.getGpuProcessType(pairId);
            long now = LibCameraJNI.getLibcameraTimestamp();
            long capture = LibCameraJNI.getFrameCaptureTime(pairId);
            long latency = (now - capture);

            String fileName = "/tmp/frames/" + frameId + ".png";
            // Mat decoded = Imgcodecs.imdecode(mat, 4);
            Imgcodecs.imwrite( fileName, mat );
            elapsed = System.currentTimeMillis()-start;
            long size = new File(fileName).length();
            System.out.println( "Created frame: " + fileName + " bytes: " + size + " latency: " + latency );
            LibCameraJNI.releasePair(pairId);
            // LibCameraJNI.stopCamera(cameraId);
                  /*
            int rows = mat.rows();
            int cols = mat.cols();
            int nonZero = 0;
            for ( int row=0; row<rows; row++ ) {
                for ( int col=0; col<cols; col++) {
                    double[] values = mat.get(row, col);
                    for ( int i=0; i<values.length; i++ ) {
                        System.out.println( "[" + row + "," + col + "," + i + "]: " + values[i] );
                    }
                }
            }
            System.out.println( "Frame contains " + nonZero + " values" );
            CVMat colorMat = new CVMat(mat);
            CVMat processedMat = new CVMat(new Mat(processed));
            Frame frame = new Frame(colorMat, processedMat, FrameThresholdType.NONE, 200l, frameProps);
            
             "Frame " + frameId + " size: " + frame.colorImage.getMatCount() );
            // BufferedImage image = toImage(mat);
            // ImageIO.write(image, "png", new File("/tmp/frames/"+frameId+".png"));
            ///ecorder.record(rotatedFrame);
            
            frame.release();
            */
            
       
        }

        LibCameraJNI.destroyCamera(cameraId);
    }
}
