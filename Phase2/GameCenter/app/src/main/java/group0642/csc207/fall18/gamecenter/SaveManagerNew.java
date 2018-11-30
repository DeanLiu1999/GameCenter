package group0642.csc207.fall18.gamecenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.*;

// TODO
class SaveManagerNew {

    /**
     * The log tag of this class.
     */
    private final String tag = "SaveManager";

    private Object object;
    private Context context;
    private String saveDirectory;

    private int autoSaveInterval = 5;
    private int autoSaveTracker = 0;

    /**
     * A SaveManager with an Object to save/load, Context and a file directory to save to.
     *
     * @param b the Builder for this SaveManager
     */
    private SaveManagerNew(Builder b) {
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
     *
     *
     * @param fileName
     * @return
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
     *
     *
     * @param object
     */
    void newObject(Object object) {
        this.object = object;
    }

    /*
    String getSaveDirectory() {
        return saveDirectory;
    }
    */

    /**
     *
     *
     * @param fileName
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
     *
     */
    void resetAutoSave() {
        autoSaveTracker = -1;
    }

    /**
     *
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
     *
     */
    private void makeToastSavedText() {
        Toast.makeText(context, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     *
     */
    public static class Builder {
        private Object object = null;
        private Context context = null;
        private String saveDirectory = "";

        /*
        public Builder object(Object object) {
            this.object = object;
            return this;
        }
        */

        /**
         *
         *
         * @param context
         * @return
         */
        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        /**
         *
         *
         * @param saveDirectory
         * @return
         */
        public Builder saveDirectory(String saveDirectory) {
            this.saveDirectory = saveDirectory;
            return this;
        }

        /**
         *
         *
         * @param user
         * @param game
         * @return
         */
        public Builder saveDirectory(String user, String game) {
            if (context != null) {
                File filesDir = context.getFilesDir();
                String filesDirStr = filesDir.getPath();
                File saveDir = new File(filesDirStr + "/" + user + "/" + game);
                this.saveDirectory = saveDir.getPath();
            }
            return this;
        }

        /**
         *
         *
         * @return
         */
        // TODO
        public SaveManagerNew build() {
            return new SaveManagerNew(this);
        }
    }
}