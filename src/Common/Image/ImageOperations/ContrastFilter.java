package Common.Image.ImageOperations;

import Common.Image.Pixel;
import Common.Image.PixelImage;

/**
 * Created by adam on 15.01.17.
 */
public class ContrastFilter extends PixelOperationBase
{
    private double _factor;

    public ContrastFilter(double factor)
    {
        this._factor = factor;
    }

    public double GetFactor()
    {
        return _factor;
    }

    public void SetFactor(double factor)
    {
        this._factor = factor;
    }

    @Override
    public void Do(Pixel pixel, int i, int j, PixelImage out)
    {

        pixel.SetPixel(
                (pixel.R() - 128) * _factor + 128,
                (pixel.G() - 128 ) * _factor + 128,
                (pixel.B() - 128) * _factor + 128);
    }
}
