package Lab3;

import Common.Image.PixelImage;
import Common.Utils.Utils;

import java.io.IOException;

/**
 * Created by adam on 20.01.17.
 */
public class Main
{
    public static void main(String[] args)
    {
        try
        {
            PixelImage image = new PixelImage("./fingerprints/r5m.jpg");
//            PixelImage image = new PixelImage("./fingerprints/fingerprint.jpg");
            ThinningAlgorithm algorithm = new ThinningAlgorithm(image);


            PixelImage out = algorithm.Execute();

            out.Save(Utils.GetFileName("./output/thinning", ".jpg"));

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
