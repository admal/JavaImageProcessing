package Common.Image.ImageOperations;

import Common.Image.Pixel;
import Common.Image.PixelImage;

/**
 * Created by adam on 15.01.17.
 */
public class BrightnessFilter extends PixelOperationBase
{
    private int _factor;

    public BrightnessFilter(int _value)
    {
        this._factor = _value;
    }

    public int GetValue()
    {
        return _factor;
    }

    public void SetValue(int _value)
    {
        this._factor = _value;
    }

    @Override
    public void Do(Pixel pixel, int i, int j, PixelImage out)
    {
        int r = pixel.R() + _factor;
        int g = pixel.G() + _factor;
        int b = pixel.B() + _factor;

        pixel.SetPixel(r, g, b);
    }
}
