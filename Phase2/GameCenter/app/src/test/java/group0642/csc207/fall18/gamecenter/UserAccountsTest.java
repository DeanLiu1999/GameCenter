package group0642.csc207.fall18.gamecenter;

import org.junit.Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserAccountsTest {

    private void setUpEmptyFile(){
        final String dirPath = "storage/emulated/0/Android/data/group0642.csc207.fall18.gamecenter/files";
        final String fileName = "user_accounts.ser";
        SaveManager saveManager = new SaveManager.Builder()
                .saveDirectory(dirPath)
                .build();
        saveManager.saveToFile(fileName);
    }

    @Test
    public void testUserAccounts(){
        setUpEmptyFile();
        UserAccounts u = new UserAccounts();
        assertFalse(u.resetPassword("dean", "2"));
        assertTrue(u.signUp("lc", "1"));
        assertTrue(u.signIn("lc", "1"));
        assertFalse(u.signUp("lc", "2"));
        assertTrue(u.resetPassword("lc", "2"));
        assertTrue(u.signIn("lc","2"));
        assertFalse(u.signIn("bob", "1"));
    }
}
