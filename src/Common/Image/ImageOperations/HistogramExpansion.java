package Common.Image.ImageOperations;

import Common.Image.Pixel;
import Common.Image.PixelImage;

/**
 * Created by adam on 20.01.17.
 */
public class HistogramExpansion extends PixelOperationBase
{
    private int _min, _max;
    private int _minR, _minG, _minB;
    private int _maxR, _maxG, _maxB;
    private boolean _useGlobal;

    public HistogramExpansion(int _minR, int _minG, int _minB, int _maxR, int _maxG, int _maxB)
    {
        this._minR = _minR;
        this._minG = _minG;
        this._minB = _minB;
        this._maxR = _maxR;
        this._maxG = _maxG;
        this._maxB = _maxB;

        _useGlobal = false;
    }

    public HistogramExpansion(int _min, int _max)
    {
        this._min = _min;
        this._max = _max;
        _useGlobal = true;
    }

    @Override
    public void Do(Pixel pixel, int i, int j, PixelImage out)
    {
        int r = pixel.R();
        int g = pixel.G();
        int b = pixel.B();
        if(_useGlobal)
        {
            r = GetHistogramExpansionForPixel(r, _min, _max);
            g = GetHistogramExpansionForPixel(g, _min, _max);
            b = GetHistogramExpansionForPixel(b, _min, _max);

            pixel.SetPixel(r,g,b);
        }
        else
        {
            r = GetHistogramExpansionForPixel(r, _minR, _maxR);
            g = GetHistogramExpansionForPixel(g, _minG, _maxG);
            b = GetHistogramExpansionForPixel(b, _minB, _maxB);

            pixel.SetPixel(r,g,b);
        }


    }

    private static int GetHistogramExpansionForPixel(int pixelChannel, int min, int max)
    {
        int out = (int)((((float)pixelChannel - min) / ((float)max - min)) * (float)255);
        return out;
    }
}
