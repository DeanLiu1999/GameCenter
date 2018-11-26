package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;
import java.util.ArrayList;

public class BankManager implements Serializable {
    private ArrayList<Integer> history;
    private Integer wager;
    private Integer bank;

    public BankManager(){
        history = new ArrayList();
        bank = 800;
        wager = 0;
        history.add(bank);
    }

    Integer getWager(){
        return wager;
    }

    Integer getBank(){
        return bank;
    }

    boolean addWager(Integer w){
        if(w <= bank){
            bank -= w;
            wager += w;
            return true;
        }
        return false;
    }

    void checkOut(boolean result){
        if(result) {
            bank += 2* wager;
        }
        wager = 0;
        history.add(bank);
    }

    void allIn(){
        addWager(bank);
    }
}
