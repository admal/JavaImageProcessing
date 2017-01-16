package Common.Image.ImageOperations;

import Common.Image.Pixel;
import Common.Image.PixelImage;

/**
 * Created by adam on 15.01.17.
 */
public class SetPixelCommand extends PixelOperationBase
{
    private int _pixel;

    public SetPixelCommand(int _pixel)
    {
        this._pixel = _pixel;
    }

    @Override
    public void Do(Pixel pixel, int i, int j, PixelImage out)
    {
        out.SetPixel(i, j, new Pixel(_pixel));
    }
}
