package Common.Image.ImageOperations;

import Common.Image.Pixel;
import Common.Image.PixelImage;

/**
 * Created by adam on 14.01.17.
 */
public class GrayscaleFilter extends PixelOperationBase
{
    @Override
    public void Do(Pixel pixel, int i, int j , PixelImage out)
    {
        int r = pixel.R();
        int g = pixel.G();
        int b = pixel.B();

        int mean = (r + g + b) / 3;

        pixel.SetPixel(mean);
    }
}
