package Common.Image.ImageOperations;

import Common.Image.Pixel;
import Common.Image.PixelImage;

/**
 * Created by adam on 14.01.17.
 */
public class InvertFilter extends PixelOperationBase {
    @Override
    public void Do(Pixel pixel, int i, int j, PixelImage out) {
        int r = pixel.R();
        int g = pixel.G();
        int b = pixel.B();

        pixel.SetPixel(255 - r, 255 - g, 255 - b);
    }
}
