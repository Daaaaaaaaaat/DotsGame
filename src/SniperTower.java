import javafx.scene.paint.Color;

public class SniperTower extends Tower {
    public SniperTower(double column, double row) {
        super(column, row);
        setCharacteristics(10,1,1, 100,1);
        setRadius(10);
        setFill(Color.DEEPPINK);
    }
}