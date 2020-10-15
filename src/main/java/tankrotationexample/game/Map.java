package tankrotationexample.game;

import tankrotationexample.GameConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

public class Map {
    public static ArrayList<GameObjects> objects = new ArrayList<>();
    private BufferedImage background, healthPowerUp, plusLifePowerUp, speedUp;
    BufferedImage breakWall = null;
    BufferedImage unBreakWall = null;
    ArrayList<Wall> walls = new ArrayList<>();
    Map(){

    }

    public void init() throws IOException {

        try {

            background = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Background.bmp")));
            unBreakWall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("unBreak.png")));
            breakWall = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("break.png")));
            healthPowerUp = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Bouncing.gif")));
            plusLifePowerUp = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Weapon.gif")));
            speedUp = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("Explosion_small.gif")));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        InputStreamReader isr = new InputStreamReader(TRE.class.getClassLoader().getResourceAsStream("maps/map1"));
        BufferedReader mapReader = new BufferedReader(isr);

        String row = mapReader.readLine();
        if(row == null){
            throw new IOException("no data in file");
        }
        String[] mapInfo = row.split("\t");
        int numCols = Integer.parseInt(mapInfo[0]);
        int numRows = Integer.parseInt(mapInfo[1]);

        for(int curRow = 0; curRow < numRows; curRow++){
            row = mapReader.readLine();
            mapInfo = row.split("\t");
            for(int curCol = 0; curCol < numCols; curCol++){
                switch(mapInfo[curCol]){
                    case"2":
                        this.walls.add(new BreakWall(curCol*30,curRow*30,breakWall));
                        break;
                    case"3":
                    case"9":
                        this.walls.add(new unBreakWall(curCol*30,curRow*30,unBreakWall));
                        break;
                    case"4":
                        this.objects.add(new HealthPowerUp(healthPowerUp,curCol*30, curRow*30));
                        break;
                    case"5":
                        this.objects.add(new PlusLifePowerUp(plusLifePowerUp,curCol*30, curRow*30));
                        break;
                    case"6":
                        this.objects.add(new SpeedUpPowerUp(speedUp,curCol*30, curRow*30));
                }
            }
        }
    }

    void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < GameConstants.WORLD_WIDTH/background.getWidth()+1; i++){
            for (int j = 0; j <GameConstants.WORLD_HEIGHT/background.getHeight()+1; j++){
                g2d.drawImage(background, i*background.getWidth(), j*background.getHeight(),null);
            }
        }
        this.walls.forEach(wall -> wall.drawImage(g));
        for(int i = 0; i < objects.size(); i++){
            objects.get(i).drawImage(g2d);
        }
    }
}
