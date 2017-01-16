package Common.Image;

/**
 * Created by adam on 14.01.17.
 */
public class Pixel
{
    private int _pixel;

    public Pixel(int pixel) {
        this._pixel = pixel;
    }
    public Pixel(int r, int g, int b)
    {
        SetPixel(r, g, b);
    }
    public Pixel(Pixel pixel)
    {
        _pixel = pixel.GetPixel();
    }

    public int R()
    {
        return (int)((_pixel << 8) >> 24) & 0xff;
    }
    public int G()
    {
        return (int)((_pixel << 16) >> 24) & 0xff;
    }
    public int B()
    {
        return (int)((_pixel << 24) >> 24) & 0xff;
    }

    public int GetPixel()
    {
        return _pixel;
    }

    public void SetPixel(int r, int g, int b)
    {
        int r1 = PutInRange(r);
        int g1 = PutInRange(g);
        int b1 = PutInRange(b);
        _pixel = (int)((((r1 << 8)|g1) << 8)|b1);
    }

    public void SetPixel(double r, double g, double b)
    {
        SetPixel(
                PutInRange(r),
                PutInRange(g),
                PutInRange(b)
        );
    }

    private int PutInRange(double val)
    {
        int out = (int)val;
        if(val > 255)
            out = 255;
        else if(val < 0)
            out = 0;

        return out;
    }

    public void SetPixel(int all)
    {
        SetPixel(all, all, all);
    }

    public static int ToRGB(int r, int g, int b)
    {
        return (int)((((r << 8)|g) << 8)|b);
    }
    public static int ToRGB(int val)
    {
        return ToRGB(val, val, val);
    }


}
