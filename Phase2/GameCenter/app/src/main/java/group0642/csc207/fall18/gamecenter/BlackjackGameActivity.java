package group0642.csc207.fall18.gamecenter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import java.util.ArrayList;

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
    private long d = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();
        stateManager = new StateManager();
        bankManager = new BankManager();

        hit.setEnabled(false);
        stand.setEnabled(false);
        if(load){
            loadGame();
            load = false;
        }

    }

    private void setUp(){
        setContentView(R.layout.activity_blackjack_game);
        dealClickListener();
        undoClickListener();
        saveClickListener();
        hitClickListener();
        standClickListener();
        addClickListener();
        allinClickListener();
        cashOutClickListener();
    }

    private void refreshMoney(){
        TextView wager = (TextView) findViewById(R.id.textView21);
        wager.setText(bankManager.getWager().toString());
        TextView bank = (TextView) findViewById(R.id.bank2);
        bank.setText(bankManager.getBank().toString());
    }

    private void setInGameButton(boolean inGame){
        deal.setEnabled(!inGame);
        hit.setEnabled(inGame);
        stand.setEnabled(inGame);
        add.setEnabled(!inGame);
        allin.setEnabled(!inGame);
        cashOut.setEnabled(!inGame);
    }

    private void loadGame(){
        loadFromFile("statemanager.ser", "bankmanager.ser");
//        stateManager = (StateManager) SaveManager.loadFromFile("storage/emulated/0/Android/data/group0642.csc207.fall18.gamecenter/files/state.ser");
//        stateManager = new StateManager();
//        bankManager = (BankManager) SaveManager.loadFromFile("storage/emulated/0/Android/data/group0642.csc207.fall18.gamecenter/files/bank.ser");
        playerCards = strToCards(stateManager.getPlayerCardsStr());
        computerCards = strToCards(stateManager.getComputerCardsStr());
        setUp();
        ArrayList<Animator> animations = new ArrayList<>();
        for(int i = 0; i <= stateManager.getStageC();i++){
            ImageView p = (ImageView) findViewById(computerCardsId[i]);
            p.setImageResource(computerCards[i]);
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(p, "y", 200f);
            animator1.setDuration(d);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(p, "x", i * 130f);
            animator2.setDuration(d);
            animations.add(animator1);
            animations.add(animator2);
        }
        for(int i = 0; i <= stateManager.getStageP();i++){
            ImageView p = (ImageView) findViewById(playerCardsId[i]);
            p.setImageResource(playerCards[i]);
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(p, "y", 700f);
            animator1.setDuration(d);
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(p, "x", i * 130f);
            animator2.setDuration(d);
            animations.add(animator1);
            animations.add(animator2);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animations);
        animatorSet.start();

        refreshMoney();
        TextView p_score = (TextView) findViewById(R.id.p_score);
        Integer p_s = stateManager.getPlayerScore();
        p_score.setText(p_s.toString());
        TextView c = (TextView) findViewById(R.id.c_score);
        Integer c_s = stateManager.getComputerScore();
        c.setText(c_s.toString());

        setInGameButton(!(stateManager.getPlayerScore() == 0 || stateManager.getStageC() > 0));
//        if(stateManager.getPlayerScore() == 0 || stateManager.getStageC() > 0){
//            deal.setEnabled(true);
//            hit.setEnabled(false);
//            stand.setEnabled(false);
//            add.setEnabled(true);
//            allin.setEnabled(true);
//            cashOut.setEnabled(true);
//        }else{
//            deal.setEnabled(false);
//            hit.setEnabled(true);
//            stand.setEnabled(true);
//            add.setEnabled(false);
//            allin.setEnabled(false);
//            cashOut.setEnabled(false);
//        }
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
                    setUp();
                    refreshMoney();
                    stateManager = new StateManager();
                    playerCards = strToCards(stateManager.getPlayerCardsStr());
                    computerCards = strToCards(stateManager.getComputerCardsStr());
                    ArrayList<Animator> animations = new ArrayList<>();

                    ImageView c1 = (ImageView) findViewById(computerCardsId[0]);
                    c1.setImageResource(computerCards[0]);
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(c1, "y", 200f);
                    animator1.setDuration(d);
                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(c1, "x", 0f);
                    animator2.setDuration(d);

                    ImageView p1 = (ImageView) findViewById(playerCardsId[0]);
                    p1.setImageResource(playerCards[0]);
                    ImageView p2 = (ImageView) findViewById(playerCardsId[1]);
                    p2.setImageResource(playerCards[1]);
                    TextView c = (TextView) findViewById(R.id.c_score);


                    ObjectAnimator animator3 = ObjectAnimator.ofFloat(p1, "y", 700f);
                    animator3.setDuration(d);
                    ObjectAnimator animator4 = ObjectAnimator.ofFloat(p1, "x", 0f);
                    animator4.setDuration(d);
                    ObjectAnimator animator5 = ObjectAnimator.ofFloat(p2, "y", 700f);
                    animator5.setDuration(d);
                    ObjectAnimator animator6 = ObjectAnimator.ofFloat(p2, "x", 130f);
                    animator6.setDuration(d);

                    ImageView d = (ImageView) findViewById(R.id.deck15);
                    d.setVisibility(View.INVISIBLE);
                    ObjectAnimator animator0 = ObjectAnimator.ofFloat(d,"alpha",0f,1f);
                    animations.add(animator0);
                    animations.add(animator1);
                    animations.add(animator2);
                    animations.add(animator3);
                    animations.add(animator4);
                    animations.add(animator5);
                    animations.add(animator6);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playSequentially(animations);
                    animatorSet.start();

                    Integer c_s = stateManager.getComputerScore();
                    c.setText(c_s.toString());
                    TextView p = (TextView) findViewById(R.id.p_score);
                    Integer p_s = stateManager.getPlayerScore();
                    p.setText(p_s.toString());

                   setInGameButton(true);
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
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(p, "y", 700f);
                animator1.setDuration(d);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(p, "x", stateManager.getStageP()*130f);
                animator1.setDuration(d);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(animator1, animator2);
                animatorSet.start();

                TextView p_score = (TextView) findViewById(R.id.p_score);
                Integer p_s = stateManager.getPlayerScore();
                p_score.setText(p_s.toString());
                if(p_s.equals(0)){
                    bankManager.checkOut(false);
                    refreshMoney();
                    setInGameButton(false);
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
                int i = 1;
                ArrayList<Animator> animations = new ArrayList<>();
                while ((stateManager.getComputerScore() < 16) && (0 <
                        stateManager.getComputerScore()) && (stateManager.getStageC() < 5)) {
                    stateManager.stand();
                    ImageView p = (ImageView) findViewById(computerCardsId[i]);
                    p.setImageResource(computerCards[i]);

                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(p, "y", 200f);
                    animator1.setDuration(d);
                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(p, "x", i * 130f);
                    animator2.setDuration(d);
                    animations.add(animator1);
                    animations.add(animator2);
                    i++;
                }
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(animations);
                animatorSet.start();

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
                refreshMoney();
                setInGameButton(false);
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
                refreshMoney();
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
                refreshMoney();
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
            if (cardStr[i].equals("A")){
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