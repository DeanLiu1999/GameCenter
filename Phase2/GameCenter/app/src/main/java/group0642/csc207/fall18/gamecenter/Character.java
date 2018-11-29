package group0642.csc207.fall18.gamecenter;

import java.io.Serializable;

class Character implements Serializable {

    private int health;

    private int attackDamage;

    /**
     * A new character.
     *
     * @param health the health of the character
     * @param attackDamage the attack damage of the character
     */
    Character(int health, int attackDamage) {
        this.setHealth(health);
        this.attackDamage = attackDamage;
    }

    /**
     * Return true iff the character is alive.
     *
     * @return isAlive as boolean
     */
    boolean isAlive() {
        return getHealth() > 0;
    }

    /**
     * Return the attack damage
     *
     * @return attackDamage
     */
    int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Return the health
     *
     * @return health
     */
    int getHealth() {
        return health;
    }

    /**
     * Change the health of the character
     *
     * @param health the new health of the character
     */
    void setHealth(int health) {
        this.health = health;
    }
}
