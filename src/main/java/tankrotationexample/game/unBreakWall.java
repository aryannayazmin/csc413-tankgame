package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class unBreakWall extends GameObjects implements Wall{
    int x,y;
    int state = 2;
    BufferedImage wallImage;

    public unBreakWall(int x, int y, BufferedImage wallImage){
        super(x,y,wallImage);
        this.x = x;
        this.y = y;
        this.wallImage = wallImage;
        gameObjects.add(this);
    }

    @Override
    public void drawImage(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.wallImage, x, y, null);
    }
}
