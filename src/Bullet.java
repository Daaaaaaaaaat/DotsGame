import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends Circle implements GameEntity {
    private final double RADIUS = 3;
    private double speed;
    private Tower tower;
    private Enemy enemy;

    public Bullet(Tower base, Enemy target) {
        speed = target.getSpeed() * 5;
        tower = base;
        enemy = target;
        if(tower == null) {
            throw new NullPointerException("Unknown tower");
        }
        setCenterX(tower.getCenterX());
        setCenterY(tower.getCenterY());
        setRadius(RADIUS);
        setFill(Color.RED);
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Tower getTower() {
        return tower;
    }

    public double getRADIUS() {
        return RADIUS;
    }

    public double getSpeed() {
        return speed;
    }

    public void setCharacteristics(Tower tower, Enemy enemy) {
        this.tower = tower;
        this.enemy = enemy;
    }

    public void movesToTheTarget() {
        if(enemy == null) {
            throw new NullPointerException("Unknown enemy");
        }

        double distanceX =  enemy.getCenterX() - getCenterX();
        double distanceY = enemy.getCenterY() - getCenterY();
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (Math.abs(distanceX / distance) * speed < Math.abs(distanceX)) {
            setCenterX(getCenterX() + distanceX / distance * speed);
        }
        else {
            setCenterX(getCenterX() + distanceX);
        }

        if (Math.abs(distanceY / distance) * speed < Math.abs(distanceY)) {
            setCenterY(getCenterY() + distanceY / distance * speed);
        }
        else {
            setCenterY(getCenterY() + distanceY);
        }
    }

    public boolean hasMovedToTheTarget() {
        if(enemy == null) {
            throw new NullPointerException("Unknown enemy");
        }

        double distanceX = getCenterX() - enemy.getCenterX();
        double distanceY = getCenterY() - enemy.getCenterY();
        double minimumDistance = 1;
        return distanceX * distanceX + distanceY * distanceY < minimumDistance * minimumDistance;
    }

    public void reducesHealthOfTarget() {
        if(enemy == null) {
            throw new NullPointerException("Unknown enemy");
        }
        enemy.reducesHealth(tower);
    }
}
