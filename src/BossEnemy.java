import javafx.scene.paint.Color;
import java.util.List;

public class BossEnemy extends Enemy{
    public BossEnemy(List<PathPoint> path) {
        super(path);
        setCharacteristics(10,1,1,1);
        setRadius(20);
        setFill(Color.BLACK);
    }
}
