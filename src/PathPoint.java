import javafx.scene.shape.Circle;

public class PathPoint extends Circle implements GameTile {
    public PathPoint(double row, double column) {
        setCenterX(column * GameStage.getTileSize());
        setCenterY(row * GameStage.getTileSize());
        setRadius(10);
    }
}