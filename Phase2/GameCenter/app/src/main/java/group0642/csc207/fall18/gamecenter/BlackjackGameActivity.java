package group0642.csc207.fall18.gamecenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BlackjackGameActivity extends AppCompatActivity {


    private Button deal;
    private Button undo;
    private Button save;
    private Button hit;
    private Button stand;
    private Button add;
    private Button allin;
    private Button cashOut;

    private Integer[] playerCardsId= {R.id.p1, R.id.p2, R.id.p3, R.id.p4, R.id.p5, R.id.p6};
    private Integer[] computerCardsId= {R.id.c1, R.id.c2, R.id.c3, R.id.c4, R.id.c5, R.id.c6};
    private Integer[] playerCards;
    private Integer[] computerCards;
    private StateManager stateManager;
    private BankManager bankManager;

    public static boolean load = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blackjack_game);
        bankManager = new BankManager();

        dealClickListener();
        undoClickListener();
        saveClickListener();
        hitClickListener();
        standClickListener();
        addClickListener();
        allinClickListener();
        cashOutClickListener();
        stateManager = new StateManager();


        hit.setEnabled(false);
        stand.setEnabled(false);
        if(load){
            loadGame();
            load = false;
        }

    }

    private void loadGame(){
        loadFromFile("statemanager.ser", "bankmanager.ser");
//        stateManager = (StateManager) SaveManager.loadFromFile("storage/emulated/0/Android/data/group0642.csc207.fall18.gamecenter/files/state.ser");
//        stateManager = new StateManager();
//        bankManager = (BankManager) SaveManager.loadFromFile("storage/emulated/0/Android/data/group0642.csc207.fall18.gamecenter/files/bank.ser");
        playerCards = strToCards(stateManager.getPlayerCardsStr());
        computerCards = strToCards(stateManager.getComputerCardsStr());

        for(int i = 0; i<= 5; i++){
            ImageView card = (ImageView) findViewById(computerCardsId[i]);
            card.setImageResource(R.drawable.cardback);
        }
        for(int i = 0; i<= 5; i++){
            ImageView card = (ImageView) findViewById(playerCardsId[i]);
            card.setImageResource(R.drawable.cardback);
        }
        for(int i = 0; i <= stateManager.getStageC();i++){
            ImageView p = (ImageView) findViewById(computerCardsId[i]);
            p.setImageResource(computerCards[i]);
        }
        for(int i = 0; i <= stateManager.getStageP();i++){
            ImageView p = (ImageView) findViewById(playerCardsId[i]);
            p.setImageResource(playerCards[i]);
        }
        TextView wager = (TextView) findViewById(R.id.textView21);
        wager.setText(bankManager.getWager().toString());
        TextView bank = (TextView) findViewById(R.id.bank2);
        bank.setText(bankManager.getBank().toString());
        TextView p_score = (TextView) findViewById(R.id.p_score);
        Integer p_s = stateManager.getPlayerScore();
        p_score.setText(p_s.toString());
        TextView c = (TextView) findViewById(R.id.c_score);
        Integer c_s = stateManager.getComputerScore();
        c.setText(c_s.toString());
        if(stateManager.getPlayerScore() == 0 || stateManager.getStageC() > 0){
            deal.setEnabled(true);
            hit.setEnabled(false);
            stand.setEnabled(false);
            add.setEnabled(true);
            allin.setEnabled(true);
            cashOut.setEnabled(true);
        }else{
            deal.setEnabled(false);
            hit.setEnabled(true);
            stand.setEnabled(true);
            add.setEnabled(false);
            allin.setEnabled(false);
            cashOut.setEnabled(false);
        }
    }

    public void saveToFile(StateManager s, String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(s);
            outputStream.close();
        } catch (IOException e) {
//            Log.e(TAG, "File write failed: " + e.toString());
        }
    }

    public void saveToFile(BankManager b, String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(b);
            outputStream.close();
        } catch (IOException e) {
//            Log.e(TAG, "File write failed: " + e.toString());
        }
    }

    private void loadFromFile(String fileName1, String fileName2) {

        try {
            InputStream inputStream1 = this.openFileInput(fileName1);
            if (inputStream1 != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream1);
                stateManager = (StateManager) input.readObject();
                inputStream1.close();
            }
            InputStream inputStream2 = this.openFileInput(fileName2);
            if (inputStream2 != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream2);
                bankManager = (BankManager) input.readObject();
                inputStream2.close();
            }
        } catch (FileNotFoundException e) {
//            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
//            Log.e(TAG, "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
//            Log.e(TAG, "File contained unexpected data type: " + e.toString());
        }
    }


    private void dealClickListener() {
        deal = findViewById(R.id.deal);
        deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bankManager.getWager().equals(0)){
                    makeToastText("Please Add Wager Before Deal");
                }else {
                    stateManager = new StateManager();
                    playerCards = strToCards(stateManager.getPlayerCardsStr());
                    computerCards = strToCards(stateManager.getComputerCardsStr());
                    for (int i = 1; i <= 5; i++) {
                        ImageView card = (ImageView) findViewById(computerCardsId[i]);
                        card.setImageResource(R.drawable.cardback);
                    }
                    for (int i = 2; i <= 5; i++) {
                        ImageView card = (ImageView) findViewById(playerCardsId[i]);
                        card.setImageResource(R.drawable.cardback);
                    }
                    ImageView c1 = (ImageView) findViewById(computerCardsId[0]);
                    c1.setImageResource(computerCards[0]);
                    ImageView p1 = (ImageView) findViewById(playerCardsId[0]);
                    p1.setImageResource(playerCards[0]);
                    ImageView p2 = (ImageView) findViewById(playerCardsId[1]);
                    p2.setImageResource(playerCards[1]);
                    TextView c = (TextView) findViewById(R.id.c_score);
                    Integer c_s = stateManager.getComputerScore();
                    c.setText(c_s.toString());
                    TextView p = (TextView) findViewById(R.id.p_score);
                    Integer p_s = stateManager.getPlayerScore();
                    p.setText(p_s.toString());

                    hit.setEnabled(true);
                    stand.setEnabled(true);
                    add.setEnabled(false);
                    allin.setEnabled(false);
                    cashOut.setEnabled(false);
                    deal.setEnabled(false);
                }
            }
        });
    }



    private void undoClickListener() {
        undo = findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankManager.undo();
                TextView bank = (TextView) findViewById(R.id.bank2);
                bank.setText(bankManager.getBank().toString());
            }
        });
    }

    private void saveClickListener() {
        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToFile(stateManager, "statemanager.ser");
                saveToFile(bankManager, "bankmanager.ser");
//                SaveManager.writeToFile("storage/emulated/0/Android/data/group0642.csc207.fall18.gamecenter/files/state.ser", stateManager);
//                SaveManager.writeToFile("storage/emulated/0/Android/data/group0642.csc207.fall18.gamecenter/files/bank.ser", bankManager);
            }
        });
    }


    private void hitClickListener() {
        hit = findViewById(R.id.hit);
        hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateManager.hit();
                ImageView p = (ImageView) findViewById(playerCardsId[stateManager.getStageP()]);
                p.setImageResource(playerCards[stateManager.getStageP()]);
                TextView p_score = (TextView) findViewById(R.id.p_score);
                Integer p_s = stateManager.getPlayerScore();
                p_score.setText(p_s.toString());
                if(p_s.equals(0)){
                    bankManager.checkOut(false);
                    TextView wager = (TextView) findViewById(R.id.textView21);
                    wager.setText(bankManager.getWager().toString());
                    TextView bank = (TextView) findViewById(R.id.bank2);
                    bank.setText(bankManager.getBank().toString());
                    deal.setEnabled(true);
                    hit.setEnabled(false);
                    stand.setEnabled(false);
                    add.setEnabled(true);
                    allin.setEnabled(true);
                    cashOut.setEnabled(true);
                    makeToastText("You Lose");
                }
            }
        });
    }

    private void standClickListener() {
        stand = findViewById(R.id.stand);
        stand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateManager.stand();
                for(int i = 1; i <= stateManager.getStageC();i++){
                    ImageView p = (ImageView) findViewById(computerCardsId[i]);
                    p.setImageResource(computerCards[i]);
                }

                TextView c = (TextView) findViewById(R.id.c_score);
                Integer c_s = stateManager.getComputerScore();
                Integer p_s = stateManager.getPlayerScore();
                c.setText(c_s.toString());
                if(p_s >= c_s){
                    makeToastText("You Win!");
                }else{
                    makeToastText("You Lose");
                }
                    bankManager.checkOut(p_s >= c_s );
                    TextView wager = (TextView) findViewById(R.id.textView21);
                    wager.setText(bankManager.getWager().toString());
                    TextView bank = (TextView) findViewById(R.id.bank2);
                    bank.setText(bankManager.getBank().toString());
                deal.setEnabled(true);
                hit.setEnabled(false);
                stand.setEnabled(false);
                add.setEnabled(true);
                allin.setEnabled(true);
                cashOut.setEnabled(true);
            }
        });
    }

    private void addClickListener() {
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText w = findViewById(R.id.guess);
                if (!w.getText().toString().equals("")){
                    if (!bankManager.addWager(Integer.parseInt(w.getText().toString()))) {
                        makeToastText("Not Enough Money");
                    }
                ;
                TextView wager = (TextView) findViewById(R.id.textView21);
                wager.setText(bankManager.getWager().toString());
                TextView bank = (TextView) findViewById(R.id.bank2);
                bank.setText(bankManager.getBank().toString());
                if (bankManager.getWager() > 0) {
                    deal.setEnabled(true);
                }
            }
            }
        });
    }

    private void allinClickListener() {
        allin = findViewById(R.id.allin);
        allin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bankManager.allIn();
                TextView wager = (TextView) findViewById(R.id.textView21);
                wager.setText(bankManager.getWager().toString());
                TextView bank = (TextView) findViewById(R.id.bank2);
                bank.setText(bankManager.getBank().toString());
                if(bankManager.getWager() > 0){
                    deal.setEnabled(true);
                }
            }
        });
    }

    private void cashOutClickListener() {
        cashOut = findViewById(R.id.cashout);
        cashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScoreBoard.updateScoreBoard("Blackjack", "lc", bankManager.getBank());
            }
        });
    }

    private Integer[] strToCards(String[] cardStr){
        Integer[] cardsId = {R.drawable.club_a, R.drawable.club_2, R.drawable.club_3, R.drawable.club_4,
                R.drawable.club_5, R.drawable.club_6, R.drawable.club_7, R.drawable.club_8,
                R.drawable.club_9, R.drawable.club_10, R.drawable.club_j, R.drawable.club_q,
                R.drawable.club_k, };
        Integer[] cards = new Integer[6];
        for(int i = 0; i < 6; i++){
            if(cardStr[i].equals("A")){
                cards[i] = cardsId[0];
            }else if(cardStr[i].equals("J")) {
                cards[i] = cardsId[10];
            }else if(cardStr[i].equals("Q")){
                cards[i] = cardsId[11];
            }else if(cardStr[i].equals("K")){
                cards[i] = cardsId[12];
            }else{
                cards[i] = cardsId[Integer.parseInt(cardStr[i]) - 1];
            }
        }
        return cards;
    }

    private void makeToastText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
