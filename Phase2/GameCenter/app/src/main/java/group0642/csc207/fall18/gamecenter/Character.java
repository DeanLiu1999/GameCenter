package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;

public class Character  implements Serializable {

    private int health;

    private int attackDamage;

    Character(int health, int attackDamage) {
        this.setHealth(health);
        this.attackDamage = attackDamage;
    }

    boolean isAlive() {
        return getHealth() > 0;
    }

    int getAttackDamage() {
        return attackDamage;
    }

    int getHealth() {
        return health;
    }

    void setHealth(int health) {
        this.health = health;
    }
}
