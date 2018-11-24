import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PieceWiseLinearDistribution {
 private ArrayList < Point2D.Double > kPts; //list of coordinates
 public PieceWiseLinearDistribution(ArrayList < Point2D.Double > kPts) {
  this.kPts = kPts;
 }

 double P_I(double i, int K) {
  if (i == K - 1) {
   return 1;
  } else {
   int m = 0;
   for (int a = 0; a < kPts.size() - 1; a++) { //find m
    Point2D.Double p = kPts.get(a);
    if (p.x < i) {
     m = a;
    }
   }
   return
   kPts.get(m).y + (i - kPts.get(m).x) * ((kPts.get(m + 1).y - kPts.get(m).y) / (kPts.get(m + 1).x - kPts.get(m).x));
  }
 }
 
 double P_B(double b, int K) {
  if (b >= 0 && b < kPts.get(0).y) {
   return 0;
  } else if (b >= 1) {
   return K - 1;
  } else {
   int m = 0;
   for (int a = 0; a < kPts.size() - 1; a++) { //find m
    Point2D.Double p = kPts.get(a);
    if (p.y < b) {
     m = a;
    }
   }
   return
   kPts.get(m).x + (b - kPts.get(m).y) * ((kPts.get(m + 1).x - kPts.get(m).x) / (kPts.get(m + 1).y - kPts.get(m).y));
  }
 }

 int[] transform(int[] h) {
  int K = h.length;
  int[] result = new int[K];
  for (int a = 0; a < K - 1; a++) {
   double b = P_I(h[a], K);
   result[a] = (int) P_B(b, K);
  }
  return result;
 }


}
