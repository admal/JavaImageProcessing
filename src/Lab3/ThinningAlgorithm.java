package Lab3;

import Common.Image.ImageOperations.*;
import Common.Image.Pixel;
import Common.Image.PixelImage;
import Common.Image.Point;
import Common.Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam on 20.01.17.
 */
public class ThinningAlgorithm
{
    private PixelImage _image;
    private List<Point> _borderPixels;

    private int[][] N = {
            {128, 1, 2},
            {64, 0, 4},
            {32, 16, 8}
    };

    public ThinningAlgorithm(PixelImage _image)
    {
        this._image = _image;
        _borderPixels = new ArrayList<>();
    }

    public boolean ExecutePhase(int phaseNo)
    {
        if (phaseNo == 0)
        {
            ExecutePhase0();
            return true;
        }

        boolean wasChange = false;
        int i = 0;
        for(Point p : _borderPixels)
        {
            Integer weight = GetWeight(p);
            if(ThinningLookupTables.GetLookupTable(phaseNo).contains(weight))
            {
                _image.SetPixel(p.X, p.Y, new Pixel(255,255,255));
                _borderPixels.get(i).Y = -1;
                _borderPixels.get(i).X = -1; //it will be removed later
                wasChange = true;
            }
            i++;
        }


        _borderPixels.removeIf(point -> point.X == -1 && point.Y == -1);
        return wasChange;

    }

    private int GetWeight(Point p)
    {
        int sum = 0;
        for (int x = -1; x < 2; x++)
        {
            for (int y = -1; y < 2; y++)
            {
                int black = _image.At(x + p.X, y + p.Y).R() == 0 ? 1 : 0;
                sum += N[x+1][y+1] * black;
            }
        }

        return sum;
    }

    private void ExecutePhase0()
    {
        MarkBorderPixels markBorderPixels = new MarkBorderPixels(_image, _borderPixels, ThinningLookupTables.GetLookupTable(0));
        _image.ApplyOperation(markBorderPixels, true);
    }

    public PixelImage Execute() throws IOException
    {

        PrepareImage();

        boolean wasChange = false;
        do
        {
            wasChange = false;
            for (int phase = 0; phase < 6; phase++)
            {
                if(ExecutePhase(phase) && phase != 0)
                {
                    wasChange = true;
                }

                System.out.println("Ended phase: " + phase);
            }
            System.out.println("Was change: " + wasChange);
        }while (wasChange);

        ExecutePhase(6);

        return _image;
    }


    private void PrepareImage() throws IOException
    {
//TMP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        int minR = 0, minG = 0, minB = 0, min = 0;
        int maxR = 0, maxG = 0, maxB = 0, max = 0;

        for (int i=0; i<_image.GetWidth(); i++) {
            for (int j=0; j<_image.GetHeight(); j++) {
                Pixel pixel = _image.At(i, j);
                int r = pixel.R();
                int g = pixel.G();
                int b = pixel.B();

                if(minR > r)
                    minR = r;
                if(minG > g)
                    minG = g;
                if(minB > b)
                    minB = b;

                if(maxR < r)
                    maxR = r;
                if(maxG < g)
                    maxG = g;
                if(maxB < b)
                    maxB = b;
            }
        }

        if(min > minR)
            min = minR;
        if(min > minG)
            min = minG;
        if(min > minB)
            min = minB;

        if(max < maxR)
            max = maxR;
        if(max < maxG)
            min = maxG;
        if(max < maxB)
            max = maxB;


        GrayscaleFilter grayscaleFilter = new GrayscaleFilter();
        ContrastFilter contrastFilter = new ContrastFilter(1.5);
        HistogramExpansion histogramExpansion = new HistogramExpansion(min, max);
        BinarizationOperation binarizationOperation = new BinarizationOperation(180);

        _image.ApplyOperation(grayscaleFilter, true);

        _image.ApplyOperation(histogramExpansion, true);
        _image.ApplyOperation(contrastFilter, true);
        _image.ApplyOperation(binarizationOperation, true);

        _image.Save(Utils.GetFileName("./output/prepare", ".jpg"));
    }
}
