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

    public Integer getWager(){
        return wager;
    }

    public Integer getBank(){
        return bank;
    }

    public boolean addWager(Integer w){
        if(w <= bank){
            bank -= w;
            wager += w;
            return true;
        }
        return false;
    }

    public void checkOut(boolean result){
        if(result){
            bank += 2* wager;
        }
        wager = 0;
        history.add(bank);
    }

    public void allIn(){
        addWager(bank);
    }

    public void undo(){
        if(history.size() > 1){
            history.remove(history.size() - 1);
        }
        bank = history.get(history.size() - 1);
    }

}
