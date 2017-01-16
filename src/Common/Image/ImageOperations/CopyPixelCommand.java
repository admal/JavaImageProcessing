package Common.Image.ImageOperations;

import Common.Image.Pixel;
import Common.Image.PixelImage;

import java.awt.image.BufferedImage;

/**
 * Created by adam on 14.01.17.
 */
public class CopyPixelCommand extends PixelOperationBase
{
    private BufferedImage _out;

    public CopyPixelCommand(BufferedImage out) {
        this._out = out;
    }

    @Override
    public void Do(Pixel pixel, int i, int j, PixelImage out) {
        _out.setRGB(i, j, pixel.GetPixel());
    }
}
