package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;


public class ArrayListPartitionorTest {
    private ArrayListPartitioner partitioner = new ArrayListPartitioner();

    @Test
    public void testPartitionForNull() {
        ArrayList lst = null;
        ArrayList result = partitioner.partitionByLengthTen(lst);
        ArrayList list = new ArrayList();
        assertEquals(list, result);
    }

    @Test
    public void testPartitionForLengthLessThanTen() {
        ArrayList<Integer> lst = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList expected = new ArrayList<>(Collections.singletonList(lst));
        ArrayList result = partitioner.partitionByLengthTen(lst);
        assertEquals(expected, result);
    }

    @Test
    public void testPartitionForLengthMoreThanTen() {
        ArrayList<Integer> lst = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5));
        ArrayList<ArrayList> result = partitioner.partitionByLengthTen(lst);
        ArrayList expected_1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0));
        ArrayList expected_2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<ArrayList> expected = new ArrayList<>(Arrays.asList(expected_1, expected_2));
        assertEquals(expected, result);
    }
}
