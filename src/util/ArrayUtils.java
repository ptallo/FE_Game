package util;

public class ArrayUtils {

    public static boolean contains(int[] array, int item) {
        for (int aItem : array) {
            if (aItem == item) {
                return true;
            }
        }
        return false;
    }
}
