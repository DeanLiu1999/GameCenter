package group0642.csc207.fall18.gamecenter;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ScoreBoardTest {

    private void setUpEmptyFile(){
        final String dirPath = "storage/emulated/0/Android/data/group0642.csc207.fall18.gamecenter/files";
        final String fileName = "scoreboard.ser";
        SaveManager saveManager = new SaveManager.Builder()
                .saveDirectory(dirPath)
                .build();
        saveManager.saveToFile(fileName);
    }

    @Test
    public void testGetScoreGameUser(){
        setUpEmptyFile();
        ScoreBoard sc = new ScoreBoard();
        assertNull(sc.getScoreGameUser("SlidingTiles", "lc"));
        sc.updateScoreBoard("SlidingTiles", "lc", 1000);
        sc.updateScoreBoard("Blackjack", "lc", 1000);
        sc.updateScoreBoard("SlidingTiles", "lc", 999);
        assertNull(sc.getScoreGameUser("Hangman", "lc"));
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1000);
        a.add(999);
        assertEquals(sc.getScoreGameUser("SlidingTiles", "lc"), a);
    }

    @Test
    public void testGetScorePerGame(){
        setUpEmptyFile();
        ScoreBoard sc = new ScoreBoard();
        assertNull(sc.getScorePerGame("SlidingTiles"));
        sc.updateScoreBoard("SlidingTiles", "lc", 1000);
        assertEquals(sc.getScorePerGame("SlidingTiles").get(0)[0], "lc");
        assertEquals(sc.getScorePerGame("SlidingTiles").get(0)[1], 1000);
    }

    @Test
    public void testGetAfterGameScore(){
        setUpEmptyFile();
        ScoreBoard sc = new ScoreBoard();
        assertNull(sc.getAfterGameScore("SlidingTiles", "lc", 1000));
        sc.updateScoreBoard("SlidingTiles", "lc", 1000);
        sc.updateScoreBoard("SlidingTiles", "lc2", 1000);
        sc.updateScoreBoard("SlidingTiles", "lc3", 999);
        assertEquals(sc.getAfterGameScore("SlidingTiles", "lc", 1000).get(0)[0], 1);
        assertEquals(sc.getAfterGameScore("SlidingTiles", "lc", 1000).get(0)[1], "lc");
        assertEquals(sc.getAfterGameScore("SlidingTiles", "lc", 1000).get(0)[2], 1000);
        assertEquals(sc.getAfterGameScore("SlidingTiles", "lc2", 1000).get(1)[0], 1);
        assertEquals(sc.getAfterGameScore("SlidingTiles", "lc2", 1000).get(1)[1], "lc2");
        assertEquals(sc.getAfterGameScore("SlidingTiles", "lc2", 1000).get(1)[2], 1000);
        assertEquals(sc.getAfterGameScore("SlidingTiles", "lc3", 999).get(2)[0], 3);
        assertEquals(sc.getAfterGameScore("SlidingTiles", "lc3", 999).get(2)[1], "lc3");
        assertEquals(sc.getAfterGameScore("SlidingTiles", "lc3", 999).get(2)[2], 999);
    }
}
