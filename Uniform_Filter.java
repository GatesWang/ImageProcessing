import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Uniform_Filter implements PlugInFilter {

	public int setup(String arg, ImagePlus img) {
		return DOES_8G;
	}
    
	public void run(ImageProcessor ip) {
		int M = ip.getWidth();
		int N = ip.getHeight();

		for (int v = 0; v < N; v++) {
			for (int u = 0; u < M; u++) {
				int a = (int) (255 * Math.random());
				ip.set(u, v, a);
			}
		}

		//int[] H = ip.getHistogram();
	}
	
}
