package group0642.csc207.fall18.gamecenter;

import android.content.Context;
import android.widget.Toast;


public class MovementController {

    private BoardManager boardManager = null;

    public MovementController() {
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int position, boolean display) {
        if (boardManager.getCurrent_state() >= 999) {
            Toast.makeText(context, "1000 steps had passed! Admit that you can't solve the game. Please restart a new game!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (boardManager.puzzleSolved()) {
            Toast.makeText(context, "Already won! YOU SCORE: " + boardManager.reportScore() + "  If you wish to view scores, press scoreboard", Toast.LENGTH_LONG).show();
            return;
        }
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                GameActivity.addToScoreBoard(boardManager.reportScore());
                Toast.makeText(context, "YOU WIN WITH SCORE " + boardManager.reportScore(), Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
