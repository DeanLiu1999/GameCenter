package group0642.csc207.fall18.gamecenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This class is responsible for calling methods of other classes to do the game logic of Blackjack,
 * and displaying the information got from other classes.
 */
public class BlackjackGameActivity extends AppCompatActivity {

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
    /**
     * The save manager for bank.
     */
    private SaveManager bankSaveManager;
    /**
     * The save manager for game state.
     */
    private SaveManager stateSaveManager;
    /**
     * The name of the save file.
     */
    private String saveFileName = "save_file.ser";
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

        Intent i = getIntent();
        name = i.getStringExtra("name");
        game = i.getStringExtra("game");

        bankManager = new BankManager();
        bankSaveManager = new SaveManager.Builder()
                .context(this)
                .saveDirectory(name, game)
                .build();
        stateSaveManager = new SaveManager.Builder()
                .context(this)
                .saveDirectory(name, game)
                .build();

        if (load) {
            loadGame();
            load = false;
        }

    }

    /**
     * set up the content view and buttons.
     */
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
        undo.setEnabled(false);
        hit.setEnabled(false);
        stand.setEnabled(false);
    }

    /**
     * enable/disable buttons according to whether it's in game or not
     *
     * @param inGame the current state is in game or not
     */
    private void setInGameButton(boolean inGame) {
        deal.setEnabled(!inGame);
        hit.setEnabled(inGame);
        stand.setEnabled(inGame);
        add.setEnabled(!inGame);
        allin.setEnabled(!inGame);
        cashOut.setEnabled(!inGame);
    }

    /**
     * disable (almost) all buttons, including deal, hit, stand, add, allIn, cashOut
     */
    private void disableAllButton() {
        deal.setEnabled(false);
        hit.setEnabled(false);
        stand.setEnabled(false);
        add.setEnabled(false);
        allin.setEnabled(false);
        cashOut.setEnabled(false);
    }

    /**
     * refresh money without animation.
     */
    private void refreshMoney() {
        TextView wager = findViewById(R.id.wager);
        String w = bankManager.getWager().toString();
        wager.setText(w);
        TextView bank = findViewById(R.id.bank2);
        String b = bankManager.getBank().toString();
        bank.setText(b);
    }

    /**
     * refresh money with animation by adding the corresponding animators to the list of animations.
     *
     * @param animators a list of animators
     */
    private void refreshMoney(ArrayList<Animator> animators) {
        final TextView wager = findViewById(R.id.wager);
        final TextView bank = findViewById(R.id.bank2);
        fade(animators, bank, 1f, 0f);
        changeTextOnAnimationEnd(animators.get(animators.size() - 1), bank, bankManager.getBank().toString());
        fade(animators, bank, 0f, 1f);
        fade(animators, wager, 1f, 0f);
        changeTextOnAnimationEnd(animators.get(animators.size() - 1), wager, bankManager.getWager().toString());
        fade(animators, wager, 0f, 1f);
    }

    /**
     * show image of the input image id (fade in, stay one second , fade out) by adding the
     * corresponding animators to the list of animations.
     *
     * @param animators a list of animators
     * @param id        the id of the image you want to show
     */
    private void showImage(ArrayList<Animator> animators, int id) {
        toast = findViewById(R.id.toast);
        fade(animators, toast, 0f, 1f);
        changeImageOnAnimationStart(animators.get(animators.size() - 1), toast, id);
        fade(animators, toast, 1f, 1f);
        fade(animators, toast, 1f, 0f);
    }

    /**
     * change the image before the animation start
     *
     * @param animator the animator that you want to change image on animation start
     * @param iv       the ImageView you want to change
     * @param imageId  the id of the image you want to change to
     */
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

    /**
     * change the image at the end of the animation
     *
     * @param animator the animator that you want to change image on animation end
     * @param iv       the ImageView you want to change
     * @param imageId  the id of the image you want to change to
     */
    private void changeImageOnAnimationEnd(Animator animator, final ImageView iv, final int imageId) {
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                iv.setImageResource(imageId);
            }
        });
    }

    /**
     * change the text at the end of the animation
     *
     * @param animator the animator that you want to change text on animation end
     * @param tv       the TextView you want to change
     * @param text     the text you want to change to
     */
    private void changeTextOnAnimationEnd(Animator animator, final TextView tv, final String text) {
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                tv.setText(text);
            }
        });
    }

    /**
     * fade in, stay or fade out according to the input value by adding the
     * corresponding animators to the list of animations.
     * 1f and 0f for fading out
     * 1f and 1 f for staying the same
     * 0f and 1f for fading in
     *
     * @param animators a list of animators
     * @param iv        the ImageView you want to change
     * @param start     value1
     * @param end       value2
     */
    private void fade(ArrayList<Animator> animators, View iv, float start, float end) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv, "alpha", start, end);
        animator.setDuration(d);
        animators.add(animator);
    }

    /**
     * display game over by adding the corresponding animators to the list of animations.
     * disable all buttons except save and cashOut, which text will be change to "leave".
     *
     * @param animators a list of animators
     */
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

    /**
     * check whether the whole game is finished or not by calling method from class BankManager.
     * set game over if the game is over , else set the buttons according to the current state.
     *
     * @param animators a list of animators
     * @param inGame    whether the it's in a single game or not
     */
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

    /**
     * refresh score with animation by adding the corresponding animators to the list of animations.
     *
     * @param animators a list of animators
     * @param Id        the id of the ImageView of the score
     * @param s         score
     */
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

    /**
     * play the list of animators
     *
     * @param animators a list of animators
     */
    private void playAnimations(ArrayList<Animator> animators) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animators);
        animatorSet.start();
    }

    /**
     * move card to the input position by adding the corresponding animators to the
     * list of animations.
     *
     * @param animators a list of animators
     * @param viewId    the id of the ImageView of the card
     * @param imageId   the id of the image of the card
     * @param x         x coordinate
     * @param y         y coordinate
     */
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

    /**
     * enable the input buttons at the end of the animation
     *
     * @param animator the animator that you want to enable button on animation end
     * @param button1  button1
     * @param button2  button2
     */
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

    /**
     * fix a animation bug. There is a strange bug that will cause the card(a ImageView) flying from
     * the top instead of coming out of the deck as expected. This bug only happens to the first
     * animation after resetting the content view and the bug is not caused by any of my methods. So
     * by adding this method, which literally displays nothing, to the first index of the list of
     * animators, the actual animations I want to display won't be affected by this bug.
     *
     * @param animators a list of animators
     */
    private void animationBugFixer(ArrayList<Animator> animators) {
        ImageView deck = findViewById(R.id.deck15);
        deck.setVisibility(View.INVISIBLE);
        ObjectAnimator animator0 = ObjectAnimator.ofFloat(d, "alpha", 1f, 0f);
        animators.add(animator0);
    }

    /**
     * save the game
     */
    private void save() {
        bankSaveManager.newObject(bankManager);
        stateSaveManager.newObject(stateManager);
        bankSaveManager.saveToFile("bankManager_" + saveFileName);
        stateSaveManager.saveToFile("stateManager_" + saveFileName);
        makeToastText("Game Saved");
    }

    /**
     * deal the cards
     */
    private void deal() {
        if (bankManager.wagerIsZero()) {
            makeToastText("Please Add Wager Before Deal");
        } else {
            setUp();
            refreshMoney();
            stateManager = new StateManager();
            playerCards = stateManager.getPlayerCardsId();
            computerCards = stateManager.getComputerCardsId();
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

    /**
     * load the game
     */
    private void loadGame() {
        bankManager = (BankManager) bankSaveManager.loadFromFile(
                "bankManager_" + saveFileName);
        stateManager = (StateManager) stateSaveManager.loadFromFile(
                "stateManager_" + saveFileName);
        setUp();
        refreshMoney();
        if (!(stateManager == null)) {
            playerCards = stateManager.getPlayerCardsId();
            computerCards = stateManager.getComputerCardsId();
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
            gameFinish(animations, !(stateManager.gameEnd()));
            playAnimations(animations);
            undo.setEnabled(stateManager.initialStage());
        }
    }

    /**
     * when the deal button is clicked, deal the cards by calling methods of StateManager.
     * Then play a set of animations.
     */
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

    /**
     * when the undo button is clicked, reset the board and give back the player's money by
     * calling the methods of BankManager.Then play the corresponding set of animations.
     */
    private void undoClickListener() {
        undo = findViewById(R.id.undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUp();
                refreshMoney();
                bankManager.undo();
                ArrayList<Animator> animations = new ArrayList<>();
                undo.setEnabled(false);
                refreshMoney(animations);
                disableAllButton();
                animations.get(animations.size() - 1).addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        setInGameButton(false);
                    }
                });
                playAnimations(animations);
            }
        });
    }

    /**
     * when the save button is clicked, save the game.
     */
    private void saveClickListener() {
        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    /**
     * when the hit button is clicked, draw a card by calling the methods of StateManager.
     * Then playing the corresponding set of animations. Auto-save if a single game ends.
     */
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
                if (stateManager.gameEnd()) {
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

    /**
     * when the stand button is clicked, computer draws cards by calling the methods of StateManager.
     * Then play the corresponding set of animations. Auto-save if a single game ends.
     */
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

    /**
     * when the add button is clicked, add the input amount of money from bank to wager by calling
     * methods of BankManager.Then play the corresponding set of animations.
     */
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
                } else {
                    makeToastText("Please Enter an Integer");
                }
            }
        });
    }

    /**
     * when the add button is clicked, add all money from bank to wager by calling
     * methods of BankManager.Then play the corresponding set of animations.
     */
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

    /**
     * when the add button is clicked, end the game with the current amount of money in the bank as
     * score and then show scoreboard. If the amount of money is zero, switch to starting activity
     * of Blackjack instead.
     */
    private void cashOutClickListener() {
        cashOut = findViewById(R.id.cashout);
        cashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bankManager.gameOver()) {
                    switchToStart();
                } else {
                    final int score = bankManager.getBank();
                    switchToScore(score);
                }
            }
        });
    }

    /**
     * switch to starting activity of Blackjack
     */
    private void switchToStart() {
        Intent startOver = new Intent(BlackjackGameActivity.this, StartingActivity.class);
        startOver.putExtra("name", name);
        startOver.putExtra("game", game);
        BlackjackGameActivity.this.startActivity(startOver);
    }

    /**
     * switch to ending score
     *
     * @param score the score of the game
     */
    private void switchToScore(final int score) {
        new ScoreBoard().updateScoreBoard(game, name, score);
        Intent goToScore = new Intent(BlackjackGameActivity.this, EndingScore.class);
        goToScore.putExtra("name", name);
        goToScore.putExtra("game", game);
        goToScore.putExtra("score", score);
        BlackjackGameActivity.this.startActivity(goToScore);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Auto-save when the app is closed during the game.
        save();
    }

    /**
     * make toast
     *
     * @param text text you want to toast
     */
    private void makeToastText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}