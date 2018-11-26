package group0642.csc207.fall18.gamecenter;

public class Character {
    private String name;

    private int health;

    private int attackDamage;

    Character(String name, int health, int attackDamage) {
        this.name = name;
        this.setHealth(health);
        this.setAttackDamage(attackDamage);
    }

    boolean isAlive() {
        return getHealth() > 0;
    }

    int getAttackDamage() {
        return attackDamage;
    }

    void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    String getName() {
        return name;
    }

    int getHealth() {
        return health;
    }

    void setHealth(int health) {
        this.health = health;
    }
}
