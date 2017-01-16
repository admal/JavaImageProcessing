package Common.Utils;

import java.util.ArrayList;

/**
 * Created by adam on 14.01.17.
 */
public class Utils
{
    public static void ForEach(Object[][] array, IForEachCommand command, Object out )
    {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                command.Do(array[i][j], i, j, out);
            }
        }
    }
    public static <T> T[][] Copy2dArray(T[][] src)
    {
        return src;
    }

    public static int GetMinIndex(int[] array, int start, int end )
    {
        int min = array[start];
        int minIdx = start;
        for (int i = start; i < end; i++)
        {
            if(min > array[i])
            {
                min = array[i];
                minIdx = i;
            }
        }
        return minIdx;
    }

    public static int GetMaxIndex(int[] array, int start, int end )
    {
        int max = array[start];
        int maxIdx = start;
        for (int i = start; i < end; i++)
        {
            if(max < array[i])
            {
                max = array[i];
                maxIdx = i;
            }
        }
        return maxIdx;
    }
}
