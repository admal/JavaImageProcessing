package Common.Image.ImageOperations;

import Common.Image.Pixel;
import Common.Image.PixelImage;

import java.awt.image.BufferedImage;

/**
 * Created by adam on 14.01.17.
 */
public class LoadPixelCommand extends PixelOperationBase
{
    private BufferedImage _in;
    private Pixel[][] _pixels;

    public LoadPixelCommand(BufferedImage _in, Pixel[][] pixels)
    {
        this._in = _in;
        _pixels = pixels;
    }


    @Override
    public void Do(Pixel pixel, int i, int j, PixelImage out)
    {
        _pixels[i][j] = new Pixel(_in.getRGB(i, j));
    }
}
