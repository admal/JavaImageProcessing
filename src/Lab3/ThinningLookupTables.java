package Lab3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by adam on 20.01.17.
 */
public class ThinningLookupTables
{
    private static Integer[] A0 = {3, 6, 7, 12, 14, 15, 24, 28, 30, 31, 48, 56, 60,
        62, 63, 96, 112, 120, 124, 126, 127, 129, 131, 135,
        143, 159, 191, 192, 193, 195, 199, 207, 223, 224,
        225, 227, 231, 239, 240, 241, 243, 247, 248, 249,
        251, 252, 253, 254},

    A1 = {7, 14, 28, 56, 112, 131, 193, 224},

    A2 = {7, 14, 15, 28, 30, 56, 60, 112, 120, 131, 135,
        193, 195, 224, 225, 240},

    A3 = {7, 14, 15, 28, 30, 31, 56, 60, 62, 112, 120,
        124, 131, 135, 143, 193, 195, 199, 224, 225, 227,
        240, 241, 248},

    A4 = {7, 14, 15, 28, 30, 31, 56, 60, 62, 63, 112, 120,
        124, 126, 131, 135, 143, 159, 193, 195, 199, 207,
        224, 225, 227, 231, 240, 241, 243, 248, 249, 252},

    A5 = {7, 14, 15, 28, 30, 31, 56, 60, 62, 63, 112, 120,
        124, 126, 131, 135, 143, 159, 191, 193, 195, 199,
        207, 224, 225, 227, 231, 239, 240, 241, 243, 248,
        249, 251, 252, 254},

    A1pix = {3, 6, 7, 12, 14, 15, 24, 28, 30, 31, 48, 56,
        60, 62, 63, 96, 112, 120, 124, 126, 127, 129, 131,
        135, 143, 159, 191, 192, 193, 195, 199, 207, 223,
        224, 225, 227, 231, 239, 240, 241, 243, 247, 248,
        249, 251, 252, 253, 254};


    public static List<Integer> GetLookupTable(int no)
    {
        switch (no)
        {
            case 0:
                return Arrays.asList(A0);
            case 1:
                return Arrays.asList(A1);

            case 2:
                return Arrays.asList(A2);

            case 3:
                return Arrays.asList(A3);

            case 4:
                return Arrays.asList(A4);

            case 5:
                return Arrays.asList(A5);

            case 6:
                return Arrays.asList(A1pix);

            default:
                return new ArrayList<>();
        }
    }
}
