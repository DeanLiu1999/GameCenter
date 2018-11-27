package group0642.csc207.fall18.gamecenter;

import static java.lang.Math.max;

public class Battle {

    private Character computer;

    private Character player;

    Battle() {
        this.computer = new Character("Monster", 1000, 200);
        this.player = new Character("You", 1400, 200);
    }

    Battle(int playerLevel, int boss) {
        this.computer = new Character("Monster", 1000 + 200 * boss,
                200 + 50 * boss);
        this.player = new Character("You", 1400 + 150 * playerLevel,
                200 + 50 * playerLevel);
    }

    boolean makeMove(boolean correctness) {
        if (correctness)
            computer.setHealth(max(0, (computer.getHealth() - player.getAttackDamage())));
        else player.setHealth(max(0, (player.getHealth() - computer.getAttackDamage())));

        return player.isAlive() && computer.isAlive();
    }

    int[] getInfo() {
        int[] singleIndicator = {0};
        int[] info = {computer.getHealth(), computer.getAttackDamage(), player.getHealth(),
                player.getAttackDamage()};
        if (info[0] + info[2] > 0)
            return info;
        return singleIndicator;
    }

    String[] getNames() {
        String[] s;
        s = new String[]{computer.getName(), player.getName()};
        return s;
    }
}
