package CamHacks;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.*;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

public class CameraToPicture implements Runnable{
	CanvasFrame canvas = new CanvasFrame("Camera");
	FrameGrabber grabber;
	static boolean interrupted = false;
	static String fileName;
	public CameraToPicture() {
		grabber = new OpenCVFrameGrabber(0);
		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

		canvas.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent event) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				saveImage();

			}

			@Override
			public void keyTyped(KeyEvent e) {   
			}
		});
	}

	public void run() {

		OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
		IplImage img;
		try {
			grabber.start();
			while (true) {
				if(interrupted){
					break;
				}

				Frame frame = grabber.grab();

				img = converter.convert(frame);
				//    cvSaveImage("image.jpg", img);

				canvas.showImage(converter.convert(img));

				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveImage(){
		try {
			Frame frame = grabber.grab();
			OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

			IplImage img = converter.convert(frame);
			cvSaveImage(fileName, img);
			interrupted = true;
			try{
				Thread.sleep(1000);
			}
			catch(Exception e){}
			grabber.release();
			canvas.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String file) {
		
		interrupted = false;
		CameraToPicture gs = new CameraToPicture();
		fileName = file;
		gs.run();
	}

}
