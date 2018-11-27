package group0642.csc207.fall18.gamecenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    private static final String TAG = "GameActivity_Blackjack";

    private Button deal;
    private Button undo;
    private Button hit;
    private Button stand;
    private Button add;
    private Button allin;
    private Button cashOut;

    private Integer[] playerCardsId = {R.id.p1, R.id.p2, R.id.p3, R.id.p4, R.id.p5, R.id.p6};
    private Integer[] computerCardsId = {R.id.c1, R.id.c2, R.id.c3, R.id.c4, R.id.c5, R.id.c6};
    private Integer[] playerCards;
    private Integer[] computerCards;
    private StateManager stateManager;
    private BankManager bankManager;
    ImageView toast;
    ImageView gameOver;

    private String name;
    private String game;

    public static boolean load = false;
    private long d = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUp();
        stateManager = new StateManager();
        bankManager = new BankManager();
        Intent i = getIntent();
        name = i.getStringExtra("name");
        game = i.getStringExtra("game");
        hit.setEnabled(false);
        stand.setEnabled(false);
        if (load) {
            loadGame();
            load = false;
        }

    }

    private void setUp() {
        setContentView(R.layout.activity_blackjack_game);
        dealClickListener();
        undoClickListener();
        saveClickListener();
        hitClickListener();
        standClickListener();
        addClickListener();
        allinClickListener();
        cashOutClickListener();
        toast = findViewById(R.id.toast);
        toast.setVisibility(View.INVISIBLE);
        gameOver = findViewById(R.id.gameover);
        gameOver.setVisibility(View.INVISIBLE);
    }

    private void setInGameButton(boolean inGame) {
        deal.setEnabled(!inGame);
        hit.setEnabled(inGame);
        stand.setEnabled(inGame);
        add.setEnabled(!inGame);
        allin.setEnabled(!inGame);
        cashOut.setEnabled(!inGame);
    }

    private void disableAllButton() {
        deal.setEnabled(false);
        hit.setEnabled(false);
        stand.setEnabled(false);
        add.setEnabled(false);
        allin.setEnabled(false);
        cashOut.setEnabled(false);
    }

    private void refreshMoney() {
        TextView wager = findViewById(R.id.textView21);
        String w = bankManager.getWager().toString();
        wager.setText(w);
        TextView bank = findViewById(R.id.bank2);
        String b = bankManager.getBank().toString();
        bank.setText(b);
    }

    private void refreshMoney(ArrayList<Animator> animators) {
        final TextView wager = findViewById(R.id.textView21);
        final TextView bank = findViewById(R.id.bank2);
        fade(animators, bank, 1f, 0f);
        changeTextOnAnimationEnd(animators.get(animators.size() - 1), bank, bankManager.getBank().toString());
        fade(animators, bank, 0f, 1f);
        fade(animators, wager, 1f, 0f);
        changeTextOnAnimationEnd(animators.get(animators.size() - 1), wager, bankManager.getWager().toString());
        fade(animators, wager, 0f, 1f);
    }

    private void showImage(ArrayList<Animator> animators, int id) {
        toast = findViewById(R.id.toast);
        fade(animators, toast, 0f, 1f);
        changeImageOnAnimationStart(animators.get(animators.size() - 1), toast, id);
        fade(animators, toast, 1f, 1f);
        fade(animators, toast, 1f, 0f);
    }

    private void changeImageOnAnimationStart(Animator animator, final ImageView iv, final int imageId) {
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                iv.setImageResource(imageId);
                iv.setVisibility(View.VISIBLE);
            }
        });
    }

    private void changeImageOnAnimationEnd(Animator animator, final ImageView iv, final int imageId) {
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                iv.setImageResource(imageId);
            }
        });
    }

    private void changeTextOnAnimationEnd(Animator animator, final TextView tv, final String text) {
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                tv.setText(text);
            }
        });
    }

    private void fade(ArrayList<Animator> animators, View iv, float start, float end) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "alpha", start, end);
        animator.setDuration(d);
        animators.add(animator);
    }

    private void setGameOver(ArrayList<Animator> animators) {
        gameOver = findViewById(R.id.gameover);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(gameOver, "alpha", 0f, 1f);
        animator1.setDuration(d);
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                gameOver.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                String leave = "Leave";
                cashOut.setText(leave);
                cashOut.setEnabled(true);
            }
        });
        animators.add(animator1);

    }


    private void gameFinish(ArrayList<Animator> animators, final boolean inGame) {
        if (bankManager.gameOver()) {
            setGameOver(animators);
        } else {
            animators.get(animators.size() - 1).addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    setInGameButton(inGame);
                    save();
                }
            });
        }
    }

    private void refreshScore(ArrayList<Animator> animators, int Id, final int s) {
        final int[] scoreImageId = {R.drawable.s00, R.drawable.s01, R.drawable.s02, R.drawable.s03, R.drawable.s04,
                R.drawable.s05, R.drawable.s06, R.drawable.s07, R.drawable.s08, R.drawable.s09, R.drawable.s10,
                R.drawable.s11, R.drawable.s12, R.drawable.s13, R.drawable.s14, R.drawable.s15, R.drawable.s16,
                R.drawable.s17, R.drawable.s18, R.drawable.s19, R.drawable.s20, R.drawable.s21};
        final ImageView score = findViewById(Id);
        fade(animators, score, 1f, 0f);
        changeImageOnAnimationEnd(animators.get(animators.size() - 1), score, scoreImageId[s]);
        fade(animators, score, 0f, 1f);
    }

    private void playAnimations(ArrayList<Animator> animators) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animators);
        animatorSet.start();
    }

    private void moveCard(ArrayList<Animator> animators, int viewId, int imageId, float x, float y) {
        ImageView card = findViewById(viewId);
        card.setImageResource(imageId);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(card, "y", y);
        animator1.setDuration(d);
        animators.add(animator1);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(card, "x", x);
        animator2.setDuration(d);
        animators.add(animator2);
    }

    private void enableButtonOnAnimationEnd(Animator animator, final Button button1, final Button button2) {
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                button1.setEnabled(true);
                button2.setEnabled(true);
            }
        });
    }

    private void animationBugFixer(ArrayList<Animator> animators) {
        ImageView deck = findViewById(R.id.deck15);
        deck.setVisibility(View.INVISIBLE);
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(d, "alpha", 1f, 0f);
        animators.add(animator0);
    }

    private void save() {
        String saveFileName = name + "_" + game + ".ser";
        saveToFile(stateManager, "stateManager" + saveFileName);
        saveToFile(bankManager, "bankManager" + saveFileName);
        makeToastText("Game Saved");
    }

    private void deal() {
        if (bankManager.wagerIsZero()) {
            makeToastText("Please Add Wager Before Deal");
        } else {
            setUp();
            refreshMoney();
            stateManager = new StateManager();
            playerCards = strToCards(stateManager.getPlayerCardsStr());
            computerCards = strToCards(stateManager.getComputerCardsStr());
            ArrayList<Animator> animations = new ArrayList<>();
            disableAllButton();
            animationBugFixer(animations);
            moveCard(animations, computerCardsId[0], computerCards[0], 0f, 200f);
            refreshScore(animations, R.id.c_score, stateManager.getComputerScore());
            moveCard(animations, computerCardsId[1], R.drawable.cardback, 130f, 200f);
            moveCard(animations, playerCardsId[0], playerCards[0], 0f, 700f);
            moveCard(animations, playerCardsId[1], playerCards[1], 130f, 700f);
            refreshScore(animations, R.id.p_score, stateManager.getPlayerScore());
            enableButtonOnAnimationEnd(animations.get(animations.size() - 1), hit, stand);
            playAnimations(animations);
        }
    }


    private void loadGame() {
        String FileName = name + "_" + game + ".ser";
        loadFromFile("stateManager" + FileName, "bankManager" + FileName);
        playerCards = strToCards(stateManager.getPlayerCardsStr());
        computerCards = strToCards(stateManager.getComputerCardsStr());
        setUp();
        ArrayList<Animator> animations = new ArrayList<>();
        disableAllButton();
        undo.setEnabled(false);
        animationBugFixer(animations);
        for (int i = 0; i <= stateManager.getStageC(); i++) {
            moveCard(animations, computerCardsId[i], computerCards[i], i * 130f, 200f);
        }
        if (stateManager.getStageC() == 0) {
            moveCard(animations, computerCardsId[1], R.drawable.cardback, 130f, 200f);
        }
        refreshScore(animations, R.id.c_score, stateManager.getComputerScore());
        for (int i = 0; i <= stateManager.getStageP(); i++) {
            moveCard(animations, playerCardsId[i], playerCards[i], i * 130f, 700f);
        }
        refreshScore(animations, R.id.p_score, stateManager.getPlayerScore());
        refreshMoney();
        gameFinish(animations, !(stateManager.gameEnd()));
        playAnimations(animations);
        undo.setEnabled(stateManager.initialStage());
    }

    public void saveToFile(StateManager s, String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(s);
            outputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
        }
    }

    public void saveToFile(BankManager b, String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(b);
            outputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "File write failed: " + e.toString());
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
            Log.e(TAG, "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e(TAG, "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "File contained unexpected data type: " + e.toString());
        }
    }


    private void dealClickListener() {
        deal = findViewById(R.id.deal);
        deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deal();
                undo.setEnabled(true);
            }
        });
    }


    private void undoClickListener() {
        undo = findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deal();
                undo.setEnabled(false);
            }
        });
    }

    private void saveClickListener() {
        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }


    private void hitClickListener() {
        hit = findViewById(R.id.hit);
        hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateManager.hit();
                undo.setEnabled(false);
                disableAllButton();
                ArrayList<Animator> animations = new ArrayList<>();
                moveCard(animations, playerCardsId[stateManager.getStageP()],
                        playerCards[stateManager.getStageP()],
                        stateManager.getStageP() * 130f, 700f);
                int p_s = stateManager.getPlayerScore();
                refreshScore(animations, R.id.p_score, p_s);
                if (p_s == 0) {
                    bankManager.checkOut(false);
                    showImage(animations, R.drawable.game_lose);
                    refreshMoney(animations);
                    gameFinish(animations, false);
                } else {
                    enableButtonOnAnimationEnd(animations.get(animations.size() - 1), hit, stand);
                }
                playAnimations(animations);
            }
        });
    }

    private void standClickListener() {
        stand = findViewById(R.id.stand);
        stand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Animator> animations = new ArrayList<>();
                disableAllButton();
                int i = 1;
                while ((stateManager.getComputerScore() < 16) && (0 <
                        stateManager.getComputerScore()) && (stateManager.getStageC() < 5)) {
                    stateManager.stand();
                    if (i == 1) {
                        final ImageView c0 = findViewById(computerCardsId[1]);
                        fade(animations, c0, 1f, 0f);
                        changeImageOnAnimationEnd(animations.get(animations.size() - 1), c0, computerCards[1]);
                        fade(animations, c0, 0f, 1f);
                        refreshScore(animations, R.id.c_score, stateManager.getComputerScore());
                    } else {
                        moveCard(animations, computerCardsId[i], computerCards[i], i * 130f, 200f);
                        refreshScore(animations, R.id.c_score, stateManager.getComputerScore());
                    }
                    i++;
                }
                if (stateManager.gameWin()) {
                    showImage(animations, R.drawable.game_win);
                } else {
                    showImage(animations, R.drawable.game_lose);
                }
                bankManager.checkOut(stateManager.gameWin());
                refreshMoney(animations);
                undo.setEnabled(false);
                gameFinish(animations, false);
                playAnimations(animations);
            }
        });
    }

    private void addClickListener() {
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText w = findViewById(R.id.guess);
                if (w.getText().toString().matches("\\d+")) {
                    add.setEnabled(false);
                    allin.setEnabled(false);
                    ArrayList<Animator> animations = new ArrayList<>();
                    if (!bankManager.addWager(Integer.parseInt(w.getText().toString()))) {
                        makeToastText("Not Enough Money");
                    }
                    refreshMoney(animations);
                    enableButtonOnAnimationEnd(animations.get(animations.size() - 1), add, allin);
                    deal.setEnabled(!bankManager.wagerIsZero());
                    playAnimations(animations);
                }else {
                    makeToastText("Please Enter an Integer");
                }
            }
        });
    }


    private void allinClickListener() {
        allin = findViewById(R.id.allin);
        allin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add.setEnabled(false);
                allin.setEnabled(false);
                ArrayList<Animator> animations = new ArrayList<>();
                bankManager.allIn();
                refreshMoney(animations);
                enableButtonOnAnimationEnd(animations.get(animations.size() - 1), add, allin);
                deal.setEnabled(!bankManager.wagerIsZero());
                playAnimations(animations);
            }
        });
    }

    private void cashOutClickListener() {
        cashOut = findViewById(R.id.cashout);
        cashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int score = bankManager.getBank();
                ScoreBoard.updateScoreBoard(game, name, score);
                Intent goToScore = new Intent(BlackjackGameActivity.this, EndingScore.class);
                goToScore.putExtra("name", name);
                goToScore.putExtra("game", game);
                goToScore.putExtra("score", score);
                BlackjackGameActivity.this.startActivity(goToScore);
            }
        });
    }

    private Integer[] strToCards(String[] cardStr) {
        Integer[] cardsId = {R.drawable.club_a, R.drawable.club_2, R.drawable.club_3, R.drawable.club_4,
                R.drawable.club_5, R.drawable.club_6, R.drawable.club_7, R.drawable.club_8,
                R.drawable.club_9, R.drawable.club_10, R.drawable.club_j, R.drawable.club_q,
                R.drawable.club_k,};
        Integer[] cards = new Integer[6];
        for (int i = 0; i < 6; i++) {
            if (cardStr[i].equals("A")) {
                cards[i] = cardsId[0];
            } else if (cardStr[i].equals("J")) {
                cards[i] = cardsId[10];
            } else if (cardStr[i].equals("Q")) {
                cards[i] = cardsId[11];
            } else if (cardStr[i].equals("K")) {
                cards[i] = cardsId[12];
            } else {
                cards[i] = cardsId[Integer.parseInt(cardStr[i]) - 1];
            }
        }
        return cards;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        save();
    }

    private void makeToastText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}