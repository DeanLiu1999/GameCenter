package group0642.csc207.fall18.gamecenter;

import java.util.Comparator;

/**
 * Comparator for ScoreBoard to sort ArrayList<Object[]> according to the score(which is in index 0
 * of the Object[] in the ArrayList), from highest to lowest. If the users(which is in index 1
 * of the Object[] in the ArrayList) have the same score, they will be sorted alphabetically.
 */
public class SortByScore implements Comparator<Object[]> {

    @Override
    public int compare(Object[] o1, Object[] o2) {
        int a1 = (Integer) o1[1];
        int b1 = (Integer) o2[1];
        if (a1 != b1) {
            return b1 - a1;
        } else {
            String a2 = (String) o1[0];
            String b2 = (String) o2[0];
            return a2.compareTo(b2);
        }

    }
}