package group0642.csc207.fall18.gamecenter;

import java.util.ArrayList;

class ArrayListPartitioner {
    /**
     * The default constructor of ArrayListPartitioner
     */
    ArrayListPartitioner() {
    }

    /**
     * @param lst is an ArrayList
     * @return an ArrayList of ArrayList(each with length of 10)
     */
    ArrayList<ArrayList> partitionByLengthTen(ArrayList lst) {
        if (lst == null) {
            return new ArrayList<>();
        } else if (lst.size() <= 10) {
            ArrayList<ArrayList> nest_1 = new ArrayList<ArrayList>();
            nest_1.add(lst);
            return nest_1;
        }
        ArrayList<ArrayList> nest = new ArrayList<ArrayList>();
        ArrayList<ArrayList> lst_1 = new ArrayList<ArrayList>();
        ArrayList<ArrayList> lst_2 = new ArrayList<ArrayList>();
        lst_1.addAll(lst.subList(0, 10));
        lst_2.addAll(lst.subList(10, lst.size()));
        nest.add(lst_1);
        nest.addAll(partitionByLengthTen(lst_2));
        return nest;
    }
}
