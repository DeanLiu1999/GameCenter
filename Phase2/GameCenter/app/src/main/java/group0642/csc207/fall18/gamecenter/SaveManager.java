package group0642.csc207.fall18.gamecenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.*;


class SaveManager {

    /**
     * The log tag of this class.
     */
    private final String tag = "SaveManager";
    /**
     * The object that is being saved/loaded.
     */
    private Object object;
    /**
     * The context.
     */
    private Context context;
    /**
     * The path to the save directory.
     */
    private String saveDirectory;
    /**
     * The predefined interval to execute an auto-save.
     */
    private int autoSaveInterval = 5;
    /**
     * The tracker for auto-saving.
     */
    private int autoSaveTracker = 0;

    /**
     * A SaveManager with an Object to save/load, Context and a file directory to save to.
     *
     * Builder design pattern based on examples at:
     * https://stackoverflow.com/questions/5007355/builder-pattern-in-effective-java
     * Retrieved Nov. 24, 2018
     *
     * @param b the Builder for this SaveManager
     */
    private SaveManager(Builder b) {
        this.object = b.object;
        this.context = b.context;
        this.saveDirectory = b.saveDirectory;
    }

    /**
     * Serialize an Object to a file with the given file name, creating non-existent files and
     * directories in the process. Will overwrite files with duplicate file name.
     *
     * @param fileName the name of the file that will be written to
     */
    void saveToFile(String fileName) {
        if (object != null) {
            String filePath = (saveDirectory.equals("")) ? fileName : saveDirectory + "/" + fileName;
            verifyDir(saveDirectory);
            File saveFile = new File(filePath);
            try {
                saveFile.createNewFile();

                OutputStream fileStream = new FileOutputStream(saveFile);
                OutputStream bufferStream = new BufferedOutputStream(fileStream);
                ObjectOutputStream outputStream = new ObjectOutputStream(bufferStream);

                outputStream.writeObject(object);
                outputStream.close();
            } catch (IOException e) {
                Log.e(tag, "File write failed: " + e.toString());
            }
        }
    }

    /**
     * Read and return the Object from a file specified by the given file name.
     *
     * @param fileName the name of the file that will be read
     * @return de-serialized object contained in the file specified by fileName;
     * null if file is empty
     */
    Object loadFromFile(String fileName) {
        String filePath = (saveDirectory.equals("")) ? fileName : saveDirectory + "/" + fileName;
        verifyDir(saveDirectory);
        File saveFile = new File(filePath);
        try {
            InputStream fileStream = new FileInputStream(saveFile);
            InputStream bufferStream = new BufferedInputStream(fileStream);
            ObjectInputStream inputStream = new ObjectInputStream(bufferStream);

            object = inputStream.readObject();
            inputStream.close();

        } catch (FileNotFoundException e) {
            Log.e(tag, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(tag, "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e(tag, "File contained unexpected data type: " + e.toString());
        }
        return object;
    }

    /**
     * Returns whether the current save directory has a file of given name.
     *
     * @param fileName the name of the save file
     * @return whether the current save directory has a file of given name
     */
    boolean hasFile(String fileName){
        File saveDir = new File(saveDirectory);
        String[] saveList = saveDir.list();
        for (String save : saveList) {
            if (fileName.equals(save)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reassigns a new object for SaveManager to interact with.
     *
     * @param object the object to be saved
     */
    void newObject(Object object) {
        this.object = object;
    }

    /**
     * Tick autoSaveTracker forward and save the game at predefined autoSaveInterval.
     *
     * @param fileName the name of the save file
     */
    void tickAutoSave(String fileName) {
        Log.d(tag, Integer.toString(autoSaveTracker));
        if (!(++autoSaveTracker < autoSaveInterval)) {
            Log.d(tag, "Auto-saving");
            saveToFile(fileName);
            makeToastSavedText();
            autoSaveTracker = 0;
        }
    }

    /*
    void setAutoSaveInterval(int i) {
        autoSaveInterval = i;
    }
    */

    /**
     * Resets the auto-save tracker.
     */
    void resetAutoSave() {
        autoSaveTracker = -1;
    }

    /**
     * Checks whether the given files directory exists, creating new directories as necessary.
     *
     * @param path the file path to be checked
     */
    private void verifyDir(String path) {
        if (!(path.equals(""))) {
            File directory = new File(path);
            if (!directory.isDirectory()) {
                directory.mkdirs();
            }
        }
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(context, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * A Builder for SaveManager.
     *
     * Builder design pattern based on examples at:
     * https://stackoverflow.com/questions/5007355/builder-pattern-in-effective-java
     * Retrieved Nov. 24, 2018
     */
    static class Builder {
        private Object object = null;
        private Context context = null;
        private String saveDirectory = "";

        /**
         * Assigns the context for a new SaveManager.
         *
         * @param context the context
         * @return this Builder
         */
        Builder context(Context context) {
            this.context = context;
            return this;
        }

        /**
         * Assigns the save directory for a new SaveManager using a given directory path.
         *
         * @param saveDirectory the path to the save directory
         * @return this Builder
         */
        Builder saveDirectory(String saveDirectory) {
            this.saveDirectory = saveDirectory;
            return this;
        }

        /**
         * Assigns the save directory for a new SaveManager using given user and game names.
         *
         * @param user the name of the user
         * @param game the name of the game
         * @return this Builder
         */
        Builder saveDirectory(String user, String game) {
            if (context != null) {
                File filesDir = context.getFilesDir();
                String filesDirStr = filesDir.getPath();
                File saveDir = new File(filesDirStr + "/" + user + "/" + game);
                this.saveDirectory = saveDir.getPath();
            }
            return this;
        }

        /**
         * Builds a new instance of Save Manager.
         *
         * @return a new instance of Save Manager
         */
        SaveManager build() {
            return new SaveManager(this);
        }
    }
}