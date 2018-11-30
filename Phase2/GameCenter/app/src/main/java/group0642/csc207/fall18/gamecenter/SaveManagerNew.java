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

    /*
    SaveManager(Object object = null, String saveDirectory) {
        this.object = object;
        this.context = null;
        this.saveDirectory = saveDirectory;

        verifyDir(this.saveDirectory);
    }

    SaveManager(Object object = null, Context context, String user, String game) {
        this.object = object;
        this.context = context;

        File filesDir = context.getFilesDir();
        String filesDirStr = filesDir.getPath();
        File saveDir = new File(filesDirStr + "/" + user + "/" + game);
        this.saveDirectory = saveDir.getPath();

        verifyDir(this.saveDirectory);
    }
    */

    // TODO
    private SaveManagerNew(Builder b) {
        this.object = b.object;
        this.context = b.context;
        this.saveDirectory = b.saveDirectory;
    }

    /**
     *
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
     *
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

    void newObject(Object object) {
        this.object = object;
    }

    String getSaveDirectory() {
        return saveDirectory;
    }

    void tickAutoSave(String fileName) {
        Log.d(tag, Integer.toString(autoSaveTracker));
        if (!(++autoSaveTracker < autoSaveInterval)) {
            Log.d(tag, "Auto-saving");
            saveToFile(fileName);
            makeToastSavedText();
            autoSaveTracker = 0;
        }
    }

    void setAutoSaveInterval(int i) {
        autoSaveInterval = i;
    }

    void resetAutoSave() {
        autoSaveTracker = -1;
    }

    /**
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

    private void makeToastSavedText() {
        Toast.makeText(context, "Game Saved", Toast.LENGTH_SHORT).show();
    }

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

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder saveDirectory(String saveDirectory) {
            this.saveDirectory = saveDirectory;
            return this;
        }

        public Builder saveDirectory(String user, String game) {
            if (context != null) {
                File filesDir = context.getFilesDir();
                String filesDirStr = filesDir.getPath();
                File saveDir = new File(filesDirStr + "/" + user + "/" + game);
                this.saveDirectory = saveDir.getPath();
            }
            return this;
        }

        // TODO
        public SaveManagerNew build() {
            return new SaveManagerNew(this);
        }
    }
}