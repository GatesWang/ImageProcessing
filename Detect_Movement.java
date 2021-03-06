import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;

public class Detect_Movement implements PlugIn {
 public void run(String arg) {
  ImagePlus imp1 = IJ.openImage();
  ImagePlus imp2 = IJ.openImage();

  int w = imp1.getWidth();
  int h = imp1.getHeight();

  ImageProcessor differenceP = getDifference(imp1, imp2);
  int[] histogramValues = getHistogram(difference, 255, 8000);
  int threshold = getThreshold(.01, histogramValues);
  ImageProcessor thresholdDifferenceP = getThresholdedDifference(imp1, imp2, threshold);
  ImagePlus thresholdDifference = new ImagePlus("histogram", thresholdDifferenceP);
  thresholdDifference.show();
 }

 public static ImageProcessor getDifference(ImagePlus imp1, ImagePlus imp2) {
  int w = imp1.getWidth();
  int h = imp1.getHeight();

  ImageProcessor differenceP = new ByteProcessor(w, h);
  differenceP.setValue(255);
  differenceP.fill();
  for (int i = 0; i < w; i++) {
   for (int j = 0; j < h; j++) {
    int value = Math.abs(imp1.getPixel(i, j)[0] - imp1.getPixel(i, j)[0]);
    if (value > 255) {
     value = 255;
    } else if (value < 0) {
     value = 0;
    }
    differenceP.putPixel(value);
   }
  }
  return differenceP;
 }
 
 public static int[] getHistogram(ImageProcessor imageP, int K, int height) {
  int histogramValues = new int[k];

  ImageProcessor histogramP = new ByteProcessor(w, h);
  histogramP.setValue(255);
  histogramP.fill();
  //find min and max
  int min = imageP.getPixel(0, 0)[0];
  int max = imageP.getPixel(0, 0)[0];

  for (int i = 0; i < w; i++) {
   for (int j = 0; j < h; j++) {
    int value = imageP.getPixel(i, j)[0];
    if (value > max) {
     max = value;
    } else if (value < min) {
     min = value;
    }
   }
  }
  //stretch values
  for (int i = 0; i < w; i++) {
   for (int j = 0; j < h; j++) {
    int value = imageP.getPixel(i, j)[0];
    value = (int)(value * (K - 1) / (high - low));
    histogramValues[value]++;
   }
  }
  return histogramValues;
 }
 public static void plotHistogram(int[] histogramValues) {
  ImageProcessor histogramP = new ByteProcessor(w, h);
  histogramP.setValue(255);
  histogramP.fill();

  //plot values
  for (int i = 0; i < histogramValues.length; i++) {
   for (int j = 0; j < histogramValues[i]; j++) {
    histogramP.putPixel(i, height - j, 0);
   }
  }
  ImagePlus histogram = new ImagePlus("histogram", histogramP);
  histogram.show();
 }

 public static int getThreshold(double p, int[] histogramValues) {
  int numberOfPoints = (1 - p) * w * h;
  int threshold = 0;
  int sum = 0;
  while (sum < numberOfPoints && threshold < histogramValues.length) {
   sum += histogramValues[threshold];
   threshold++;
  }
  return threshold;
 }
 
 public static ImageProcessor getThresholdedDifference(ImagePlus imp1, ImagePlus imp2, int threshold) {
  int w = imp1.getWidth();
  int h = imp1.getHeight();

  ImageProcessor differenceP = new ByteProcessor(w, h);
  differenceP.setValue(255);
  differenceP.fill();

  for (int i = 0; i < w; i++) {
   for (int j = 0; j < h; j++) {
    int value = Math.abs(imp1.getPixel(i, j)[0] - imp1.getPixel(i, j)[0]);
    if (value > threshold) {
     value = 255;
    } else if (value < threshold) {
     value = 0;
    }
    differenceP.putPixel(value);
   }
  }
  return differenceP;
 }
 
	public static ImageProcessor getMovingPixels(ImagePlus originalImage, ImagePlus mask) {
	 ImageProcessor movingP = new ColorProcessor(w, h);
	 movingP.setValue(255);
	 movingP.fill();

	 int w = originalImage.getWidth();
	 int h = originalImage.getHeight();

	 for (int i = 0; i < w; i++) {
	  for (int j = 0; j < h; j++) {
	   int[] rgbValues = originalImage.getPixel(i, j);
	   if (mask.getPixel(i, j)[0] == 0) {
	    movingP.putPixel(i, j, [0, 0, 0]);
	   } else {
	    movingP.putPixel(i, j, rgbValues);
	   }
	  }
	 }
	 return movingP;
	}
}
