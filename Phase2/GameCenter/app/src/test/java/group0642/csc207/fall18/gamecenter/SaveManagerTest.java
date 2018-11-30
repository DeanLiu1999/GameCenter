package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
}
