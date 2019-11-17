import javafx.scene.paint.Color;
import java.util.List;

public class SmallerEnemy extends Enemy{
    public SmallerEnemy(List<PathPoint> path) {
        super(path);
        setCharacteristics(10,1,1,1);
        setRadius(8);
        setFill(Color.PINK);
    }
}
