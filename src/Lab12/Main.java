package Lab12;

import Common.Image.ImageOperations.*;
import Common.Image.PixelImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

/*
    TO DELETE IN FUTURE!
*/


public class Main {
    //helper functions
    private static int getR(int in) {
        return (int)((in << 8) >> 24) & 0xff;
    }
    private static int getG(int in) {
        return (int)((in << 16) >> 24) & 0xff;
    }
    private static int getB(int in) {
        return (int)((in << 24) >> 24) & 0xff;
    }
    private static int toRGB(int r,int g,int b) {
        return (int)((((r << 8)|g) << 8)|b);
    }
    private static int toRGB(int g) {
        return toRGB(g,g,g);
    }
    private static int toRGBGrayscale(int pixel)
    {
        int r = getR(pixel);
        int g = getG(pixel);
        int b = getB(pixel);
        
    	int mean = (r + g + b) / 3;
    	return toRGB(mean, mean, mean);
    }
    
    private static int invertRGB(int pixel) 
    {
        int r = getR(pixel);
        int g = getG(pixel);
        int b = getB(pixel);
        
        int xOut = toRGB(255 - r, 255 - g, 255 - b);
        return xOut;
	}
    
    private static int putInRange(double pixel)
    {
    	int out = (int)pixel;
    	if(pixel > 255)
    		out = 255;
    	else if(pixel < 0)
    		out = 0;
    	
    	return out;
    		
    }
    
    private static int brightRGB(int pixel, int factor)
    {
        int r = putInRange( getR(pixel) + factor );
        int g = putInRange( getG(pixel) + factor );
        int b = putInRange( getB(pixel) + factor );
        
        int xOut = toRGB(r, g, b);
        return xOut;
    }
    
    private static int increaseContrast(int pixel, double factor)
    {
        int r = putInRange( (getR(pixel) - 128) * factor + 128 );
        int g = putInRange( (getG(pixel) - 128 ) * factor+ 128 );
        int b = putInRange( (getB(pixel) - 128) * factor + 128);
        
        int xOut = toRGB(r, g, b);
        return xOut;
    }
    
    //histogram expansion -  make pixels to use all possible values
    private static int getHistogramExpansionForPixel(int pixelChannel, int min, int max)
    {
    	int out = (int)((((float)pixelChannel - min) / ((float)max - min)) * (float)255);
    	return out;
    }
    private static int historgramExpansion(int pixel, int minR, int minG, int minB, int min, int maxR, int maxG, int maxB, int max, boolean useGlobal)
    {
        int r = getR(pixel);
        int g = getG(pixel);
        int b = getB(pixel);
    	if(useGlobal)
    	{
    		r = getHistogramExpansionForPixel(r, min, max);
    		g = getHistogramExpansionForPixel(g, min, max);
    		b = getHistogramExpansionForPixel(b, min, max);
    		
    		return toRGB(r, g, b);
    	}
    	else
    	{
    		r = getHistogramExpansionForPixel(r, minR, maxR);
    		g = getHistogramExpansionForPixel(g, minG, maxG);
    		b = getHistogramExpansionForPixel(b, minB, maxB);
    		
    		return toRGB(r, g, b);
    	}
    }
    
    
    
    //histogram equalization
    int[] getColorsCountForChannel(int[] pixelChannels)
    {
    	int[] out = new int[256];
    	
    	for(int i = 0; i < pixelChannels.length; i++)
    	{
    		out[pixelChannels[i]]++;
    	}
    	
    	return out;
    }
    //n - total number of pixels
    double cdf(int i, int[] colorsCount, int n)
    {
    	double sum = 0;
    	for(int j = 0; j < i; j++)
    	{
    		sum += (double)colorsCount[j] / (double)n;
    	}
    	return sum;
    }
    
    private static int histogramEqualization(int pixel)
    {
    	return 0;
    }
    
    
    //convolution filters
    private static int applyMatrix(int i, int j, BufferedImage img, int[][] matrix, int divisor)
    {
    	int pixel = img.getRGB(i, j);
    	
    	int sumR = 0;
    	int sumG = 0;
    	int sumB = 0;
    	int dim1 = matrix.length;
    	int dim2 = matrix[0].length;

    	for(int x = 0 ; x < matrix.length ; x++)
    	{
    		for(int y = 0; y < matrix[x].length; y++)
    		{
    			int i1 = i +( x - dim1/2);
    			int i2 = j +( y - dim2/2 );

    			if(i1 < 0 || i1 >= img.getWidth() || i2 < 0 || i2 >= img.getHeight() )
    				continue;

    			int pix = img.getRGB(i1, i2);

    			int r1 = getR(pix);
    			int g1 = getG(pix);
    			int b1 = getB(pix);

    			sumR += r1 * matrix[x][y];
    			sumG += g1 * matrix[x][y];
    			sumB += b1 * matrix[x][y];
    		}
    	}
    	
    	sumR = putInRange(sumR / divisor);
    	sumG = putInRange(sumG / divisor);
    	sumB = putInRange(sumB / divisor);
    	
    	return toRGB(sumR, sumG, sumB);	
    }

    /**
     *
     * @param x pixel of first sobel matrix
     * @param y pixel of second sobel matrix
     * @return pixel
     */
    private static int getSobelPixel(int x, int y)
    {
        int xR = getR(x);
        int xG = getG(x);
        int xB = getB(x);

        int yR = getR(y);
        int yG = getG(y);
        int yB = getB(y);

        int oR = putInRange(Math.sqrt(xR*xR + yR * yR));
        int oG = putInRange(Math.sqrt(xG*xG + yG * yG));
        int oB = putInRange(Math.sqrt(xB*xB + yB * yB));

        return toRGB(oR, oG, oB);
    }


    private static int applyMedianFilter(int i, int j, BufferedImage img, int chooseIdx)
    {
        if(i < 1 || j < 1 || i > img.getWidth() - 2 ||  j > img.getHeight() - 2)
            return img.getRGB(i, j);

        ArrayList<Integer> pixels = new ArrayList<>();
        for (int x = 0; x < 3; x++)
        {
            for (int y = 0; y < 3; y++)
            {
                pixels.add(getR(img.getRGB(i - 1 + x, j - 1 + y)));
            }
        }
        pixels.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return i1.compareTo(i2);
            }
        });

        int median = pixels.get(chooseIdx);
        return toRGB(median);

    }

    private static int applyBinarization(int pix, int treshold)
    {
        return getR(pix) > treshold ? toRGB(255) : toRGB(0);
    }


    private static PixelImage ApplyErosion(PixelImage img, boolean toGrayscale)
    {
        if(toGrayscale)
        {
            GrayscaleFilter grayscaleFilter = new GrayscaleFilter();
            img.ApplyOperation(grayscaleFilter, true);
        }
        MedianFilter medianFilter = new MedianFilter(img, 8);
        BinarizationOperation binarizationOperation = new BinarizationOperation(20);


        PixelImage output = new PixelImage(img.GetWidth(), img.GetHeight());

        img.ApplyOperation(binarizationOperation, true);
        img.ApplyOperation(medianFilter, true, output);

        return output;
    }


    private static PixelImage ApplyDilotion(PixelImage img, boolean toGrayscale)
    {
        if(toGrayscale)
        {
            GrayscaleFilter grayscaleFilter = new GrayscaleFilter();
            img.ApplyOperation(grayscaleFilter, true);
        }
        MedianFilter medianFilter = new MedianFilter(img, 0);
        BinarizationOperation binarizationOperation = new BinarizationOperation(20);


        PixelImage output = new PixelImage(img.GetWidth(), img.GetHeight());

        img.ApplyOperation(binarizationOperation, true);
        img.ApplyOperation(medianFilter, true, output);

        return output;
    }



    public static void main(String[] args) {
/*
        try {
            File file = new File("./eye.jpg");
            BufferedImage in=ImageIO.read(file);

            int[] horizontalProjection = new int[in.getHeight()];
            int[] verticalProjection = new int[in.getWidth()];

            //create output file of the exct parameters as input one
            BufferedImage out=new BufferedImage(in.getWidth(),in.getHeight(),BufferedImage.TYPE_INT_RGB);

            int[][] matrix = new int[][] {
                    {1, 2, 1},
                    {2, 4, 2},
                    {1, 2, 1}
            };

            int[][] matrix2 = new int[][] {
                    {1, 1, 1},
                    {1, 1, 1},
                    {1, 1, 1}
            };

            int[][] matrix3 = new int[][] {
                    {-1, -1, -1},
                    {-1, 9, -1},
                    {-1, -1, -1}
            };
            int[][] matrix4 = new int[][] {
                    {0, -1, 0},
                    {-1, 5, -1},
                    {0, -1, 0}
            };
            int[][] matrix5 = new int[][] {
                    {-1, -1, -1},
                    {-1, 8, -1},
                    {-1, -1, -1}
            };
            int[][] sobel1 = new int[][] {
                    {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}
            };
            int[][] sobel2 = new int[][] {
                    {-1, -2, -1},
                    {0, 0, 0},
                    {1, 2, 1}
            };

            int minR = 0, minG = 0, minB = 0, min = 0;
            int maxR = 0, maxG = 0, maxB = 0, max = 0;

            for (int i=0;i<in.getWidth();i++) {
                for (int j=0;j<in.getHeight();j++) {
                    int pixel = in.getRGB(i, j);
                    int r = getR(pixel);
                    int g = getG(pixel);
                    int b = getB(pixel);

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

            //for each pixel
            for (int i=0;i<in.getWidth();i++) {
                for (int j=0;j<in.getHeight();j++) {
                    int x=in.getRGB(i, j); //x means input pixel value //int stores values for RGB and random for alpha

//                    int pOut = historgramExpansion(x, minR, minG, minB, min, maxR, maxG, maxB, max, true);
                    int xOut =applyMatrix(i, j, in, sobel1, 1);
                    int yOut =applyMatrix(i, j, in, sobel2, 1);
                    int pOut = getSobelPixel(xOut, yOut);
                    //int pOut = applyMedianFilter(i, j, in, 0);
//                    int pOut = applyBinarization(x, 20);
//                    if(0 == getR(pOut))
//                    {
//                        horizontalProjection[j]++;
//                        verticalProjection[i]++;
//                    }
//                    int pOut = applyMatrix(i, j, in, matrix5, 1);
                    out.setRGB(i,j, pOut); //we set output image pixel value
                }
            }
//            System.out.println("Horizontal projection");
//            for (int i = 0; i < horizontalProjection.length; i++) {
//                System.out.println(i + ": " + horizontalProjection[i]);
//            }
//            System.out.println("Vertical projection");
//            for (int i = 0; i < verticalProjection.length; i++) {
//                System.out.println(i + ": " + verticalProjection[i]);
//            }
            //write the output image to a out.jpg file
            ImageIO.write(out, "jpeg", new File("out.jpg"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
*/

//        try
//        {
//            PixelImage image = new PixelImage("./eye.jpg");
//
//            InvertFilter invertPixel = new InvertFilter();
//            GrayscaleFilter grayscaleFilter = new GrayscaleFilter();
////
////            image.ApplyOperation(invertPixel, true);
////            image.ApplyOperation(grayscaleFilter, true);
//
//            BinarizationOperation binarizationOperation = new BinarizationOperation(127);
////            image.ApplyOperation(binarizationOperation, true);
//
//            PixelImage output = new PixelImage(image.GetWidth(), image.GetHeight());
//            MedianFilter medianFilter = new MedianFilter(image, 5);
////            image.ApplyOperation(medianFilter, true, output);
//            ContrastFilter increaseContrast = new ContrastFilter(0.3);
////            image.ApplyOperation(increaseContrast, true);
////            image = ApplyErosion(image, false);
//            int[] horProj = image.GetHorizontalProjection();
//            int[] verProj = image.GetVerticalProjection();
//            image = ApplyDilotion(image, false);
//
//            BrightnessFilter brightnessFilter = new BrightnessFilter(-100);
////            image.ApplyOperation(brightnessFilter, true);
//
//            LocalDateTime now = LocalDateTime.now();
//            String fileName = "out" + now.toString() + ".jpg";
//            image.Save("output/" + fileName);
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }

        try
        {
            IrisDetector detector = new IrisDetector("./eye.jpg");
            detector.DetectIris();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
//
    }

}
