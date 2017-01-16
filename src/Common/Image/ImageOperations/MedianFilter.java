package Common.Image.ImageOperations;

import Common.Image.Pixel;
import Common.Image.PixelImage;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by adam on 14.01.17.
 */
public class MedianFilter extends PixelOperationBase
{
    private PixelImage _image;
    private int _index;

    public MedianFilter(PixelImage image, int index)
    {
        this._image = image;
        this._index = index;

    }


    @Override
    public void Do(Pixel pixel, int i, int j, PixelImage out)
    {
        //TODO: throw some exception
        if(out == null)
            return;

        if(i < 1 || j < 1 || i > _image.GetWidth() - 2 ||  j > _image.GetHeight() - 2)
        {
            out.SetPixel(i, j, _image.At(i, j));
            return;
        }
        ArrayList<Integer> pixelsValues = new ArrayList<>();
        for (int x = 0; x < 3; x++)
        {
            for (int y = 0; y < 3; y++)
            {
                pixelsValues.add(_image.At(i - 1 + x, j - 1 + y).R());
            }
        }
        pixelsValues.sort(Comparator.naturalOrder());

        int median = pixelsValues.get(_index);

        out.SetPixel(i, j, new Pixel(Pixel.ToRGB(median)));
    }
}
