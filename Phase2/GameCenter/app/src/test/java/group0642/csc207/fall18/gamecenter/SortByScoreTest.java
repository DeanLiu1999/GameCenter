package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SortByScoreTest {

    @Test
    public void testCompare(){
        SortByScore c = new SortByScore();
        Object[] nameAndScore1 = {"a", 1};
        Object[] nameAndScore2 = {"b", 2};
        Object[] nameAndScore3 = {"a", 2};
        Object[] nameAndScore4 = {"b", 2};
        assertEquals(c.compare(nameAndScore1, nameAndScore2), 1);
        assertEquals(c.compare(nameAndScore3, nameAndScore4), -1);
    }
}


