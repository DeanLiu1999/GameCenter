package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;
import java.util.ArrayList;

public class BankManager implements Serializable {
    private Integer wager;
    private Integer bank;

    public BankManager(){
        bank = 800;
        wager = 0;
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
    }

    void allIn(){
        addWager(bank);
    }
}
