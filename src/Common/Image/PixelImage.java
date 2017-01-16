package Common.Image;

import Common.Image.ImageOperations.LoadPixelCommand;
import Common.Image.ImageOperations.PixelOperationBase;
import Common.Image.ImageOperations.CopyPixelCommand;
import Common.Image.ImageOperations.SetPixelCommand;
import Common.Utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by adam on 14.01.17.
 */
public class PixelImage
{
    public static PixelImage CreateWhiteImage(int width, int height)
    {
        PixelImage out = new PixelImage(width, height);
        out.ApplyOperation(new SetPixelCommand(Pixel.ToRGB(255)), true, out);
        return out;
    }
    private Pixel[][] _pixels;

    public int GetWidth()
    {
        return _pixels.length;
    }

    public int GetHeight()
    {
        return _pixels[0].length;
    }

    public Pixel At(int i, int j)
    {
        return _pixels[i][j];
    }

    public PixelImage(int width, int height)
    {
        this._pixels = new Pixel[width][height];
    }

    public PixelImage(Pixel[][] _pixels) {
        this._pixels = _pixels;
    }


    public PixelImage(String path) throws IOException
    {
        Load(path);
    }

    /**
     * Applies given operation to the image and return new image.
     * If apply flag is set then the function operates and changes the object.
     * @param operation
     * @param apply
     * @return
     */
    public PixelImage ApplyOperation(PixelOperationBase operation, boolean apply)
    {
        Pixel[][] array;
        if(apply)
            array = _pixels;
        else
            array = Utils.Copy2dArray(_pixels);
        Utils.ForEach(array, operation, null);

        if (apply)
            return this;
        else
            return new PixelImage(array);
    }

    public PixelImage ApplyOperation(PixelOperationBase operation, boolean apply, PixelImage output)
    {
        Pixel[][] array;
        if(apply)
            array = _pixels;
        else
            array = Utils.Copy2dArray(_pixels);
        Utils.ForEach(array, operation, output);

        if (apply)
            return this;
        else
            return new PixelImage(array);
    }



    public void Save(String path) throws IOException
    {
        BufferedImage out = new BufferedImage(_pixels.length,_pixels[0].length ,BufferedImage.TYPE_INT_RGB);
        CopyPixelCommand setPixel =  new CopyPixelCommand(out);
        Utils.ForEach(_pixels, setPixel, null);
        ImageIO.write(out, "jpeg", new File(path));
    }

    public void Load(String path) throws IOException
    {
        File file = new File(path);
        BufferedImage in = ImageIO.read(file);

        _pixels = new Pixel[in.getWidth()][in.getHeight()];

        LoadPixelCommand loadPixel = new LoadPixelCommand(in, _pixels);
        Utils.ForEach(_pixels, loadPixel, null);
    }

    public void SetPixel(int i, int j, Pixel pixel)
    {
        _pixels[i][j] = pixel;
    }

    public int[] GetVerticalProjection()
    {
        int[] verticalProjection = new int[GetWidth()];
        for (int i = 0; i < _pixels.length; i++) {
            for (int j = 0; j < _pixels[i].length; j++) {
                if(_pixels[i][j].R() == 0)
                    verticalProjection[i]++;
            }
        }
        return verticalProjection;
    }

    public int[] GetHorizontalProjection()
    {
        int[] horizontalProjection = new int[GetHeight()];
        for (int i = 0; i < _pixels.length; i++) {
            for (int j = 0; j < _pixels[i].length; j++) {
                if(_pixels[i][j].R() == 0)
                    horizontalProjection[j]++;
            }
        }
        return horizontalProjection;
    }

    public PixelImage GetHorizontalProjectionImage()
    {
        int[] values = GetHorizontalProjection();
        PixelImage out = PixelImage.CreateWhiteImage(GetWidth(), GetHeight());

        for (int i = 0; i < values.length; i++)
        {
            out.DrawHorizontalLine(i, values[i]);
        }
        return out;
    }

    public PixelImage GetVerticalProjectionImage()
    {
        int[] values = GetVerticalProjection();
        PixelImage out = PixelImage.CreateWhiteImage(GetWidth(), GetHeight());

        for (int i = 0; i < values.length; i++)
        {
            out.DrawVerticalLine(i, values[i]);
        }
        return out;
    }

    public void DrawVerticalLine(int column)
    {
        DrawVerticalLine(column, GetHeight());
    }
    public void DrawHorizontalLine(int row)
    {
        DrawHorizontalLine(row, GetWidth());
    }

    public void DrawHorizontalLine(int row, int length)
    {
        for (int i = 0; i < length; i++)
        {
            _pixels[i][row].SetPixel(0);
        }
    }

    public void DrawVerticalLine(int column, int length)
    {
        for (int i = 0; i < length; i++)
        {
            _pixels[column][i].SetPixel(0);
        }
    }
}
