package group0642.csc207.fall18.gamecenter;

class ArrayToString {

    /**
     * The default constructor for the ArrayToString Object
     */
    ArrayToString() {
    }

    /**
     * @param array is an object array with the first term being a String and the second term being
     *             an Integer
     * @return a String with a specific format represented below
     * This is a method to convert a special type of array to a specific String format
     */
    String getString(Object[] array) {
        String username = (String) array[0];
        String score = array[1].toString();
        return String.format("%-30s             %3s", username, score);
    }
}
