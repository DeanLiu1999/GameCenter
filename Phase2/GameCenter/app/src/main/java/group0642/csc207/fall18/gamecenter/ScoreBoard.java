package group0642.csc207.fall18.gamecenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * A ScoreBoard class for storing scores and reporting them as requested.
 */
class ScoreBoard {
    /**
     * Directory and file name for saving and writing scoreboard.
     */
    private static final String sc = "storage/emulated/0/Android/data/group0642.csc207.fall18.gamecenter/files/scoreboard.txt";

    /**
     * Update scoreboard, save changes to text file.
     *
     * @param game   the name of the game
     * @param userId userId
     * @param score  score
     */
    static void updateScoreBoard(String game, String userId, int score) {
        HashMap<String, HashMap<String, ArrayList>> scoreBoard = (HashMap) SaveManager.loadFromFile(sc);
        if (scoreBoard == null) {
            scoreBoard = new HashMap<String, HashMap<String, ArrayList>>();
        }
        if (!scoreBoard.containsKey(userId)) {
            HashMap gameToScores = new HashMap();
            ArrayList scores = new ArrayList();
            scores.add(score);
            gameToScores.put(game, scores);
            scoreBoard.put(userId, gameToScores);
        } else if (!scoreBoard.get(userId).containsKey(game)) {
            ArrayList scores = new ArrayList();
            scores.add(score);
            scoreBoard.get(userId).put(game, scores);
        } else {
            scoreBoard.get(userId).get(game).add(score);
        }
        SaveManager.writeToFile(sc, scoreBoard);
    }

    /**
     * Get the scores of all the games that the user played before.
     *
     * @param userId userId
     * @return return a HashMap where the key is the game name and value is the a
     * corresponding list of scores.
     */
    private static HashMap getScorePerUser(String userId) {
        HashMap<String, HashMap<String, ArrayList>> scoreBoard = (HashMap) SaveManager.loadFromFile(sc);
        if (scoreBoard == null || !scoreBoard.containsKey(userId)) {
            return null;
        }
        for (String game : scoreBoard.get(userId).keySet()) {
            Collections.sort(scoreBoard.get(userId).get(game));
            Collections.reverse(scoreBoard.get(userId).get(game));
        }
        return scoreBoard.get(userId);
    }

    /**
     * Get all the scores of a particular user of the given game.
     *
     * @param game   the name of the game
     * @param userId userId
     * @return return an ArrayList of all the scores of a particular user of the given game.
     */
    static ArrayList getScoreGameUser(String game, String userId) {
        HashMap<String, ArrayList> userScoreBoard = getScorePerUser(userId);
        if (userScoreBoard == null) {
            return null;
        } else if (!userScoreBoard.containsKey(game)) {
            return null;
        } else {
            return userScoreBoard.get(game);
        }
    }

    /**
     * Get all the scores, along with corresponding users, of the given game.
     *
     * @param game the name of the game
     * @return return an ArrayList of Object[] of length 2, where each Object[] has user name in
     * index 0 and score in index 1. The ArrayList is sorted by the scores, from highest to lowest.
     * If the user has the same score, they will be sorted alphabetically.
     */
    static ArrayList<Object[]> getScorePerGame(String game) {
        HashMap<String, HashMap<String, ArrayList>> scoreBoard = (HashMap) SaveManager.loadFromFile(sc);
        if (scoreBoard == null) {
            return null;
        }
        ArrayList<Object[]> scorePerGame = new ArrayList<>();
        for (String user : scoreBoard.keySet()) {
            if (scoreBoard.get(user).containsKey(game)) {
                for (Object score : getScoreGameUser(game, user)) {
                    Object[] o = {user, score};
                    scorePerGame.add(o);
                }
            }
        }
        Collections.sort(scorePerGame, new SortByScore());
        return scorePerGame;
    }

    /**
     * Get all the scores, along with corresponding users and rank, of the given game.
     *
     * @param game the name of the game
     * @return return an ArrayList of Object[] of length 3, where each Object[] has rank
     * in index 0 ,userId in index 1 and score in index 2. The ArrayList is sorted by
     * the scores, from highest to lowest.If the users have the same score, they will have
     * the same rank but will be sorted alphabetically.
     */
    private static ArrayList<Object[]> getRankedScorePerGame(String game) {
        ArrayList<Object[]> scorePerGame = getScorePerGame(game);
        if (scorePerGame == null) {
            return null;
        }
        ArrayList<Object[]> rankedScorePerGame = new ArrayList<>();
        Object[] temp = {1, scorePerGame.get(0)[0], scorePerGame.get(0)[1]};
        rankedScorePerGame.add(temp);
        for (int i = 1; i < scorePerGame.size(); i++) {
            if (scorePerGame.get(i - 1)[1].equals(scorePerGame.get(i)[1])) {
                Object[] temp2 = {rankedScorePerGame.get(i - 1)[0], scorePerGame.get(i)[0], scorePerGame.get(i)[1]};
                rankedScorePerGame.add(temp2);
            } else {
                Object[] temp3 = {i + 1, scorePerGame.get(i)[0], scorePerGame.get(i)[1]};
                rankedScorePerGame.add(temp3);
            }
        }
        return rankedScorePerGame;
    }

    /**
     * Get the rank of the this game's score played by the user, along with the 4 scores before
     * and after this game's score.
     *
     * @param game   the name of the game
     * @param userId userId
     * @param score  score of this game
     * @return return an ArrayList of Object[] of length 3, where each Object[] has rank
     * in index 0 ,userId in index 1 and score in index 2. The ArrayList is sorted by
     * the scores, from highest to lowest.If the users have the same score, they will have
     * the same rank but will be sorted alphabetically. The returned ArrayList contains
     * the rank, give userId, given score and , with 4 ranks, userIds, scores before and after
     * the given score, if they exist.
     */
    static ArrayList<Object[]> getAfterGameScore(String game, String userId, int score) {
        ArrayList<Object[]> scorePerGame = getScorePerGame(game);
        ArrayList<Object[]> rankedScorePerGame = ScoreBoard.getRankedScorePerGame(game);
        if (scorePerGame == null || rankedScorePerGame == null) {
            return null;
        }
        int index = -5;
        for (int i = scorePerGame.size() - 1; i >= 0; i--) {
            if (scorePerGame.get(i)[0].equals(userId) && scorePerGame.get(i)[1].equals(score)) {
                index = i;
            }
        }
        ArrayList<Object[]> afterGameScore = new ArrayList<>();
        for (int i = index - 4; i <= index + 4; i++) {
            if (i >= 0 && i < rankedScorePerGame.size()) {
                afterGameScore.add(rankedScorePerGame.get(i));
            }
        }
        return afterGameScore;
    }

}
