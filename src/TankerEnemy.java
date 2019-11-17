import javafx.scene.paint.Color;
import java.util.List;

public class TankerEnemy extends Enemy{
    public TankerEnemy(List<PathPoint> path) {
        super(path);
        setCharacteristics(10,1,1, 1);
        setRadius(15);
        setFill(Color.ORCHID);
    }
}