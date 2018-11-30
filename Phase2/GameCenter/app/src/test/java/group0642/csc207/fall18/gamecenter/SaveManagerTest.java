package group0642.csc207.fall18.gamecenter;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
public class SaveManagerTest {

    private static final String filename = "storage/emulated/0/Android/data/group0642.csc207.fall18.gamecenter/files/SaveManager.ser";

    @Test
    public void testSaveAndLoad(){
        SaveManager s = new SaveManager();
        s.writeToFile(filename,"a");
        String a2;
        a2 = (String) s.loadFromFile(filename);
        assertEquals("a", a2);
    }

    @Test
    public void testUnexpectedCases(){
        SaveManager s = new SaveManager();
        boolean catch1 = false;
        boolean catch2 = false;
        try {
            s.writeToFile("aaaaaa", "a");
            assertEquals(0, 1);
        } catch (NullPointerException e) {
            catch1 = true;
        }
        String a2;
        try {
            a2 = (String) s.loadFromFile("bbbbbbbbbbbbbb");
            assertEquals(0, 1);
        } catch (NullPointerException e) {
            catch2 = true;
        }
        assertTrue(catch1 && catch2);
    }
}
