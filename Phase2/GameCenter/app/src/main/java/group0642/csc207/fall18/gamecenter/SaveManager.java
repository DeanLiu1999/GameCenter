package group0642.csc207.fall18.gamecenter;

import android.util.Log;

import java.io.*;

class SaveManager {

    SaveManager(){}

    /**
     * The log tag of this class.
     */
    private static final String TAG = "SaveManager";

    /**
     * Serialize an Object to a file at the given file path, creating non-existent files and
     * directories in the process. Will overwrite files with duplicate file name.
     *
     * @param filePath the path to the file that will be written to
     * @param o        the object that will be serialized and written to the file at filePath
     */
    void writeToFile(String filePath, Object o) {
        new SaveManager().check(filePath);
        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(new FileOutputStream(filePath));
            objectOutputStream.writeObject(o);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }
    }

    /**
     * Read and return the Object from a file specified by its file path.
     *
     * @param filePath the path to the file that will be read
     * @return de-serialized object contained in the file specified by filePath;
     * null if file is empty
     */
    Object loadFromFile(String filePath) {
        new SaveManager().check(filePath);
        Object o = null;
        try {
            File f = new File(filePath);
            if (f.length() != 0) {
                ObjectInputStream objectInputStream =
                        new ObjectInputStream(new FileInputStream(filePath));
                o = objectInputStream.readObject();
                objectInputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "File contained unexpected data type: " + e.toString());
        }
        return o;
    }

    /**
     * Check if the specified directory and file exist, creating any that do not yet exist.
     *
     * @param filePath the file path to be checked
     */
    private void check(String filePath) {
        try {
            File f = new File(filePath);
            f.getParentFile().mkdirs();
            f.createNewFile();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not access file: " + e.toString());
        }
    }
}