package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayToStringTest {
    private ArrayToString subject = new ArrayToString();

    @Test
    public void testGetString() {
        Integer i = 900;
        String s = "name";
        Object[] array = {s, i};
        boolean match = (String.format("%-30s             %3s", s, i))
                .equals(subject.getString(array));
        assertEquals(match, true);
    }

}
