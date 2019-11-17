import javafx.scene.paint.Color;

import java.util.List;

public class NormalEnemy extends Enemy{
    public NormalEnemy(List<PathPoint> path) {
        super(path);
        setCharacteristics(10,1,1,1);
        setRadius(10);
        setFill(Color.ORANGE);
    }
}
