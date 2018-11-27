package group0642.csc207.fall18.gamecenter;

import java.util.HashMap;

/**
 * A UserAccounts class for storing userId and password, along with methods signUp, signIn,
 * resetPassword.
 */
class UserAccounts {
    /**
     * Directory and file name for saving and writing user accounts.
     */
    private static final String ua = "storage/emulated/0/Android/data/group0642.csc207.fall18.gamecenter/files/useraccounts.txt";


    UserAccounts(){}

    /**
     * return whether the password is correct for the given userId.
     *
     * @param userId   userId
     * @param password password
     * @return return true if the the password is the same as the one stored in user accounts
     * for the given userId. Return false otherwise.
     */
    boolean signIn(String userId, String password) {
        HashMap userAccounts = (HashMap) SaveManager.loadFromFile(ua);
        if (userAccounts == null || !userAccounts.containsKey(userId)) {
            return false;
        }
        return userAccounts.get(userId).equals(password);
    }

    /**
     * Reset the password of the given userId. Return false if the userId does not exist.
     *
     * @param userId      userId
     * @param newPassword a new password
     * @return return false if the userId does not exist. Return true otherwise.
     */
    boolean resetPassword(String userId, String newPassword) {
        HashMap userAccounts = (HashMap) SaveManager.loadFromFile(ua);
        if (userAccounts == null || !userAccounts.containsKey(userId)) {
            return false;
        }
        userAccounts.replace(userId, newPassword);
        SaveManager.writeToFile(ua, userAccounts);
        return true;
    }

    /**
     * Store the userId and password into a text file. If the userId already existed, return false.
     *
     * @param userId   userId
     * @param password password
     * @return return false if the userId already existed. Return true otherwise.
     */
    boolean signUp(String userId, String password) {
        HashMap userAccounts = (HashMap) SaveManager.loadFromFile(ua);
        if (userAccounts == null) {
            userAccounts = new HashMap();
        } else if (userAccounts.containsKey(userId)) {
            return false;
        }
        userAccounts.put(userId, password);
        SaveManager.writeToFile(ua, userAccounts);
        return true;
    }
}
