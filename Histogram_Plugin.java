
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class Histogram_Plugin implements PlugInFilter {
  public int setup(String arg, ImagePlus img) {
    return DOES_RGB;
  }
  public void run(ImageProcessor ip) {
    int[] R = new int[256];
    int[] G = new int[256];
    int[] B = new int[256];
    int[] L = new int[256];
    getCount(R, G, B, L, ip);
    plot(R, “Red”, 16000, 50, 8000);
    plot(G, “Green”, 16000, 50, 8000);
    plot(B, “Blue”, 16000, 50, 8000);
    plot(L, “Luminosity”, 16000, 50, 8000);
  }
  public void getCount(int R, int G, int B, int L, ImageProcessor ip){
    int w = ip.getWidth();
    int h = ip.getHeight();
    int r;
    int g;
    int b;
    for (int v = 0; v < h; v++) {
      for (int u = 0; u < w; u++) {
        int[] rgb = new int[3];
        ip.getPixel(u,v,rgb);
        r = rgb[0];
        g = rgb[1];
        b = rgb[2];
        R[r] = R[r] + 1;
        G[g] = G[g] + 1;
        B[b] = B[b] + 1;
        L[(int) (r+g+b)/3] = L[(int) (r+g+b)/3] + 1;
      }
    }
  }

  public void plot(int[] A, String name, int height, int binWidth, int trim){
    int K = A.length;
    int height = 16000;
    ImageProcessor graph = new ByteProcessor(10*K, height);
    graph.setValue(255);  	
    graph.fill();

    //trim the bottom
    for(int i = 0; i< A.length; i++){
      A[i] = A[i] - trim;
    }

    //graph
    int S = width; //width of bin
    for(int i = 0; i < A.length; i+=1){
      for(int j = 0; j < A[i]; j++){
        for(int s = 0; s<S; s++){
        graph.putPixel(i*S + s, height - j, 0);
        }
      }
    }

    // display
    ImagePlus graphIm = new ImagePlus(name, graph);
    graphIm.show();
  }
}

