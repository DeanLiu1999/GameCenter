package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;

import static java.lang.Math.max;

class Battle implements Serializable {

    private Character computer;

    private Character player;

    /**
     * Manage a battle by default setting.
     */
    Battle() {
        this.computer = new Character(1000, 200);
        this.player = new Character(1400, 200);
    }

    /**
     * Manage a battle.
     *
     * @param playerLevel the level of player
     * @param boss the level of boss
     */
    Battle(int playerLevel, int boss) {
        this.computer = new Character(1000 + 200 * boss,
                200 + 50 * boss);
        this.player = new Character(1400 + 150 * playerLevel,
                200 + 50 * playerLevel);
    }

    /**
     * Return whether both the monster and the player is alive.
     *
     * @param correctness whether user make a correct guess
     * @return bothAlive
     */
    boolean makeMove(boolean correctness) {
        if (correctness)
            computer.setHealth(max(0, (computer.getHealth() - player.getAttackDamage())));
        else player.setHealth(max(0, (player.getHealth() - computer.getAttackDamage())));

        return player.isAlive() && computer.isAlive();
    }

    /**
     * Return the info required for the battle mode, return single indicator to indicate states
     * that should not be reached.
     *
     * @return info or single indicator
     */
    int[] getInfo() {
        int[] singleIndicator = {0};
        int[] info = {computer.getHealth(), computer.getAttackDamage(), player.getHealth(),
                player.getAttackDamage()};
        if (info[0] + info[2] > 0)
            return info;
        return singleIndicator;
    }
}
