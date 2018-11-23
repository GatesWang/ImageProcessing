import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import java.util.Random;

public class Gaussian_Filter implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
		return DOES_8G;
	}
    
	public void run(ImageProcessor ip) {
		int M = ip.getWidth();
		int N = ip.getHeight();
		Random r= new Random();

		for (int v = 0; v < N; v++) {
			for (int u = 0; u < M; u++) {
				int a = (int) (r.nextGaussian() * 50 + 150);
				ip.set(u, v, a);
			}
		}
	}
	
}
