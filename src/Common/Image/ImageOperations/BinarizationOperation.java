package Common.Image.ImageOperations;

import Common.Image.Pixel;
import Common.Image.PixelImage;

/**
 * Created by adam on 14.01.17.
 */
public class BinarizationOperation extends PixelOperationBase
{
    private int _treshold;

    public BinarizationOperation(int treshold)
    {
        this._treshold = treshold;
    }

    @Override
    public void Do(Pixel pixel, int i, int j, PixelImage out)
    {
        pixel.SetPixel( pixel.R() > _treshold ? Pixel.ToRGB(255) : Pixel.ToRGB(0) );
    }
}
