package Common.Image.ImageOperations;

import Common.Image.Pixel;
import Common.Image.PixelImage;
import Common.Image.Point;

import java.util.List;

/**
 * Created by adam on 20.01.17.
 */
public class MarkBorderPixels extends PixelOperationBase
{
    private PixelImage _image;
    private List<Point> _borderPixels;
    private List<Integer> _lookupTable;

    private int[][] N = {
            {128, 1, 2},
            {64, 0, 4},
            {32, 16, 8}
    };

    public MarkBorderPixels(PixelImage _image, List<Point> _borderPixels, List<Integer> _lookupTable)
    {
        this._image = _image;
        this._borderPixels = _borderPixels;
        this._lookupTable = _lookupTable;
    }

    public List<Point> getBorderPixels()
    {
        return _borderPixels;
    }

    @Override
    public void Do(Pixel pixel, int i, int j, PixelImage out)
    {
        if(pixel.R() == 255) //it is white so ignore it
            return;
        if (i-1 < 0 || j-1 < 0 || i + 2 > _image.GetWidth() || j + 2 > _image.GetHeight())
            return;

        Integer sum = 0;
        for (int x = -1; x < 2; x++)
        {
            for (int y = -1; y < 2; y++)
            {
                int black = _image.At(x + i, y + j).R() == 0 ? 1 : 0;

                sum += N[x+1][y+1] * black;
            }
        }

        if(_lookupTable.contains(sum))
        {
            _borderPixels.add(new Point(i, j));
        }
    }
}
