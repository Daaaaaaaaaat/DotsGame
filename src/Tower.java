import javafx.scene.shape.Circle;

public class Tower extends Circle implements GameTile {
    private double health, regeneration, range, damage, cost;
    private final double HEALTH = 50;

    public Tower(double column, double row) {
        setCenterX(column * GameStage.getTileSize());
        setCenterY(row * GameStage.getTileSize());
    }

    public double getHealth() {
        return health;
    }

    public double getRange() {
        return range;
    }

    public double getRegeneration() {
        return regeneration;
    }

    public double getCost() {
        return cost;
    }

    public double getDamage() {
        return damage;
    }

    public double getHEALTH() {
        return HEALTH;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setRegeneration(double regeneration) {
        this.regeneration = regeneration;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setCharacteristics(double health, double regeneration, double cost, double range, double damage) {
        this.cost = cost;
        this.health = health;
        this.range = range;
        this.regeneration = regeneration;
        this.damage = damage;
    }

    public boolean isFullOfHealth() {
        return health == HEALTH;
    }

    public boolean canTouch(Enemy enemy) {
        if(enemy == null) {
            throw new NullPointerException("Unknown enemy");
        }
        double distanceX = getCenterX() - enemy.getCenterX();
        double distanceY = getCenterY() - enemy.getCenterY();
        return distanceX * distanceX + distanceY * distanceY < range * range;
    }

    public boolean canAttack(Enemy enemy) {
        return isFullOfHealth() && canTouch(enemy);
    }

    public void regenerationHealth() {
        health += regeneration;
        if(health > HEALTH) {
            health = HEALTH;
        }
    }

    public void resetHealth() {
        health = 0;
    }
}
