import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class GameField extends AnimationTimer {
    public Group root, enemies, towers, bullets, pathPoints, controls;

    public Button normalTowerButton, sniperTowerButton, machineGunTowerButton;
    public Text gameStatus;

    public Scene scene;
    public Timer timer;

    public List<PathPoint> pathPointList;
    public List<Enemy> enemyList;
    public List<Tower> towerList;
    public List<Bullet> bulletList;

    public int[][] gameMap, gamePath, gameTimeLines;
    public double gameHealth, gameMoney;

    public int enemyType, towerType, numberOfEnemies;
    public boolean releaseTheEnemy, isWin;


    public GameField(int[][] gameMap, int[][] gamePath, int[][] gameTimeLines, double gameHealth, double gameMoney) {
        this.gameMap = gameMap;
        this.gameHealth = gameHealth;
        this.gameMoney = gameMoney;

        this.gamePath = gamePath;
        this.gameTimeLines = gameTimeLines;

        releaseTheEnemy = false;
        isWin = false;

        numberOfEnemies = 0;
        enemyType = 0;
        towerType = 0;

        pathPointList = new LinkedList<>();
        enemyList = new LinkedList<>();
        towerList = new LinkedList<>();
        bulletList = new LinkedList<>();

        initializeScene();
        initializeLevel();
        setupListeners();
        start();
    }

    public void initializeScene() {
        initializeGroups();
        initializeControls();

        root.getChildren().addAll(pathPoints, bullets, enemies , towers, controls);
        scene = new Scene(root, GameStage.getSceneWidth(), GameStage.getSceneHeight());

        scene.setFill(Color.BEIGE);
    }

    public void initializeGroups() {
        root = new Group();
        enemies = new Group();
        towers = new Group();
        bullets = new Group();
        pathPoints = new Group();
    }

    public void initializeControls() {
        controls = new Group();
        double buttonsRadius = 50;

        normalTowerButton = new Button("N");
        normalTowerButton.setShape(new Circle(buttonsRadius));
        normalTowerButton.setMinSize(buttonsRadius, buttonsRadius);
        normalTowerButton.setMaxSize(buttonsRadius, buttonsRadius);

        sniperTowerButton = new Button("S");
        sniperTowerButton.setShape(new Circle(buttonsRadius));
        sniperTowerButton.setMinSize(buttonsRadius, buttonsRadius);
        sniperTowerButton.setMaxSize(buttonsRadius, buttonsRadius);

        machineGunTowerButton = new Button("M");
        machineGunTowerButton.setShape(new Circle(buttonsRadius));
        machineGunTowerButton.setMinSize(buttonsRadius, buttonsRadius);
        machineGunTowerButton.setMaxSize(buttonsRadius, buttonsRadius);

        normalTowerButton.setLayoutX(GameStage.getSceneWidth() / 2 - 3 * buttonsRadius);
        normalTowerButton.setLayoutY(GameStage.getSceneHeight() - 2* buttonsRadius);

        sniperTowerButton.setLayoutX(GameStage.getSceneWidth() / 2);
        sniperTowerButton.setLayoutY(GameStage.getSceneHeight() - 2*buttonsRadius);

        machineGunTowerButton.setLayoutX(GameStage.getSceneWidth() / 2 + 3 * buttonsRadius);
        machineGunTowerButton.setLayoutY(GameStage.getSceneHeight() - 2*buttonsRadius);

        gameStatus = new Text();
        gameStatus.setX(100);
        gameStatus.setY(100);

        controls.getChildren().addAll(normalTowerButton, sniperTowerButton, machineGunTowerButton, gameStatus);
    }

    public void initializeLevel() {
        initializeTimeLines();
        initializePath();
    }

    public void initializeTimeLines() {
        class ReleaseEnemy extends TimerTask {
            @Override
            public void run() {
                enemyType = gameTimeLines[1][numberOfEnemies];
                releaseTheEnemy = true;
                ++numberOfEnemies;
            }
        }

        class Win extends TimerTask {
            @Override
            public void run() {
                isWin = true;
                timer.cancel();
            }
        }

        timer = new Timer();
        for (int i = 1; i < gameTimeLines[0].length; i++) {
            TimerTask task = new ReleaseEnemy();
            timer.schedule(task, gameTimeLines[0][i]);
        }
        TimerTask win = new Win();
        timer.schedule(win, gameTimeLines[0][0]);
    }

    public void initializePath() {
        for (int i = 0; i < gamePath.length; ++i) {
            PathPoint pathPoint = new PathPoint(gamePath[i][0], gamePath[i][1]);
            pathPointList.add(pathPoint);
            pathPoints.getChildren().add(pathPoint);
            System.out.println(gamePath[i][0] +" " +gamePath[i][1]);
        }
    }


    public boolean isLose() {
        return gameHealth <= 0;
    }

    public boolean isWin() {
        return isWin;
    }

    public boolean isMapFree(int row, int column) {
        return gameMap[row][column] == 0;
    }

    public void setMapIsFull(int row, int column) {
        gameMap[row][column] = 1;
    }


    public void createTower(int column, int row) {
        if (isMapFree(row, column)) {
            Tower tower;
            switch (towerType) {
                case 1:
                    tower = new MachineGunTower(column, row);
                    break;
                case 2:
                    tower = new SniperTower(column, row);
                    break;
                default:
                    tower = new NormalTower(column, row);
            }
            if (gameMoney >= tower.getCost()) {
                gameMoney -= tower.getCost();
                setMapIsFull(row, column);
                towers.getChildren().add(tower);
                towerList.add(tower);
            }
        }
    }

    public void createEnemy() {
        Enemy enemy;
        switch (enemyType) {
            case 1:
                enemy = new BossEnemy(pathPointList);
                break;
            case 2:
                enemy = new TankerEnemy(pathPointList);
                break;
            case 3:
                enemy = new SmallerEnemy(pathPointList);
                break;
            default:
                enemy = new NormalEnemy(pathPointList);
        }
        enemies.getChildren().add(enemy);
        enemyList.add(enemy);
    }

    public void createBullet(Tower tower, Enemy enemy) {
        Bullet bullet = new Bullet(tower, enemy);
        bullets.getChildren().add(bullet);
        bulletList.add(bullet);
    }

    public void removeNode(GameEntity node) {
        if(node instanceof Bullet) {
            bullets.getChildren().remove(node);
            bulletList.remove(node);
        }
        else if(node instanceof Enemy) {
            enemies.getChildren().remove(node);
            enemyList.remove(node);
        }
    }


    public void updateTimeLines() {
        if (releaseTheEnemy) {
            createEnemy();
            releaseTheEnemy = false;
        }
    }

    public void updateGameLogic() {
        for (Tower tower : towerList) {
            tower.regenerationHealth();
            for(Enemy enemy : enemyList) {
                if (tower.canAttack(enemy) && !enemy.isDying() && !enemy.hasCompletedThePath()) {
                    enemy.reducesEnergy(tower);
                    createBullet(tower, enemy);
                    tower.resetHealth();
                }
            }
        }
        for (int i = 0; i < bulletList.size(); ++i) {
            Bullet bullet = bulletList.get(i);
            bullet.movesToTheTarget();
            if (bullet.hasMovedToTheTarget()) {
                bullet.reducesHealthOfTarget();
                removeNode(bullet);
                --i;
            }
        }
        for (int i = 0; i < enemyList.size(); ++i) {
            Enemy enemy = enemyList.get(i);
            enemy.movesAlongThePath();
            if (enemy.hasCompletedThePath()) {
                gameHealth -= enemy.getDamage();
                removeNode(enemy);
                --i;
            }
            if (!enemy.isAlive()) {
                gameMoney += enemy.getReward();
                removeNode(enemy);
                --i;
            }
        }
    }

    public void updateGameStatus() {
        gameStatus.setText("HEALTH : " + gameHealth + "\nMONEY : " + gameMoney + "\nPOINT : " + numberOfEnemies);
    }

    public void setupListeners() {
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int column = (int) Math.round(event.getSceneX()/ GameStage.getTileSize());
                int row = (int) Math.round(event.getSceneY()/ GameStage.getTileSize());
                createTower(column, row);
            }
        });

        normalTowerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                towerType = 3;
            }
        });

        machineGunTowerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                towerType = 1;
            }
        });

        sniperTowerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                towerType = 2;
            }
        });
    }

    public void autoPlay() {
        int k = 1;
        boolean loop = true;
        while (loop && k < 10) {
            towerType = 1;
            for (int i = 0; i < GameStage.getRows(); i++) {
                for (int j = 0; j < GameStage.getColumns(); j++) {
                    if (gameMap[i][j] == 2) {
                        if (gameMap[Math.max(i-k,0)][j] == 0) {
                            createTower(j, Math.max(i-k,0));
                            loop = false;
                        }
                        else if (gameMap[Math.min(i+k,GameStage.getRows()-1)][j] == 0) {
                            createTower(j, Math.min(i+k,GameStage.getRows()-1));
                            loop = false;
                        }
                        if (gameMap[i][Math.max(j-k,0)] == 0) {
                            createTower(Math.max(j-k,0), i);
                            loop = false;
                        }
                        if (gameMap[i][Math.min(j+k,GameStage.getColumns()-1)] == 0) {
                            createTower(Math.min(j+k,GameStage.getColumns()-1), i);
                            loop = false;
                        }
                    }
                }
            }
            ++k;
        }
    }
    @Override
    public void handle(long now) {
        updateTimeLines();
        updateGameLogic();
        updateGameStatus();
        //autoPlay();
    }

    public Timer getTimer() {
        return timer;
    }
}
