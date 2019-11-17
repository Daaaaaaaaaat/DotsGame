import javafx.scene.shape.Circle;
import java.util.List;

public class Enemy extends Circle implements GameEntity {
    private double health, energy, speed, damage, reward;
    private List<PathPoint> pathPointList;
    private int targetIndex;

    public Enemy(List<PathPoint> path) {
        pathPointList = path;
        targetIndex = 1;
        if(path == null) {
            throw new NullPointerException("The road must have at least one point");
        }
        setCenterX(path.get(0).getCenterX());
        setCenterY(path.get(0).getCenterY());
    }

    public double getReward() {
        return reward;
    }

    public double getSpeed() {
        return speed;
    }

    public double getEnergy() {
        return energy;
    }

    public double getHealth() {
        return health;
    }

    public double getDamage() {
        return damage;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setCharacteristics(double health, double damage, double speed, double reward) {
        this.health = health;
        this.energy = health;
        this.damage = damage;
        this.speed = speed;
        this.reward = reward;
    }

    public boolean isDying() {
        return energy <= 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void reducesHealth(Tower tower) {
        if(tower == null) {
            throw new NullPointerException("Unknown tower");
        }
        health -= tower.getDamage();
    }

    public void reducesEnergy(Tower tower) {
        if(tower == null) {
            throw new NullPointerException("Unknown tower");
        }
        energy -= tower.getDamage();
    }

    public void movesAlongThePath() {
        if(!hasCompletedThePath()) {
            PathPoint targetPoint = pathPointList.get(targetIndex);
            double distanceX = targetPoint.getCenterX() - getCenterX();
            double distanceY = targetPoint.getCenterY() - getCenterY();
            setCenterX(getCenterX() + Math.signum(distanceX) * Math.min(Math.abs(distanceX), speed));
            setCenterY(getCenterY() + Math.signum(distanceY) * Math.min(Math.abs(distanceY), speed));
            if (distanceX == 0 && distanceY == 0) {
                ++targetIndex;
            }
        }
    }

    public boolean hasCompletedThePath() {
        return targetIndex == pathPointList.size();
    }
}

