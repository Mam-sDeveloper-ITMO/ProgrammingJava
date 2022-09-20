import java.util.Arrays;
import static java.lang.Math.*;

public class Lab0 {
    public static void main(String[] args) {
        short[] c = new short[16];
        for (short i = 2; i <= 17; i++) {
            c[i - 2] = i;
        }

        float[] xx = new float[20];
        for (int i = 0; i < 20; i++) {
            xx[i] = (float) random() * 18 - 9;
        }

        double[][] cc = new double[16][20];
        short[] corr_c = { 2, 3, 6, 7, 10, 13, 14, 15 };

        for (int i = 0; i < 16; i++) {
            System.out.print("[");
            for (int j = 0; j < 20; j++) {
                float x = xx[j];
                if (c[i] == 9) {
                    cc[i][j] = pow((pow((pow((1 / 4.0) / ((1 / 3.0) / (0.5 + x)), 3)), 2) / 3.0 / 4.0), 2);
                } else if (Arrays.binarySearch(corr_c, c[i]) >= 0) {
                    cc[i][j] = pow((3.0 / (asin(1 / 3.0 * x / 18.0) + (1 / 4.0))), (atan(cos(x))));
                } else {
                    cc[i][j] = log(pow((4.0 + abs(asin(cos(x)))), 2));
                }
                System.out.printf("%.2f ", cc[i][j]);
            }
            System.out.print("]\n");
        }
    }
}