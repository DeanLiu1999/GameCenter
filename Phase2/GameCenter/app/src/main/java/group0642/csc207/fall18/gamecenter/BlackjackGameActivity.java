package group0642.csc207.fall18.gamecenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

    }

    private void dealClickListener() {
        deal = findViewById(R.id.deal);
        deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              stateManager = new StateManager();
                playerCards = strToCards(stateManager.getPlayerCardsStr());
                computerCards = strToCards(stateManager.getComputerCardsStr());
                for(int i = 1; i<= 5; i++){
                    ImageView card = (ImageView) findViewById(computerCardsId[i]);
                    card.setImageResource(R.drawable.cardback);
                }
                for(int i = 2; i<= 5; i++){
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
                    bankManager.checkOut(p_s >= c_s );
                    TextView wager = (TextView) findViewById(R.id.textView21);
                    wager.setText(bankManager.getWager().toString());
                    TextView bank = (TextView) findViewById(R.id.bank2);
                    bank.setText(bankManager.getBank().toString());
            }
        });
    }

    private void addClickListener() {
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText w = findViewById(R.id.editText);
                bankManager.addWager(Integer.parseInt(w.getText().toString()));
                TextView wager = (TextView) findViewById(R.id.textView21);
                wager.setText(bankManager.getWager().toString());
                TextView bank = (TextView) findViewById(R.id.bank2);
                bank.setText(bankManager.getBank().toString());
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
}
