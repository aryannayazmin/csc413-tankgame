package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpeedUpPowerUp extends GameObjects{
    //private Rectangle bound;

    SpeedUpPowerUp(BufferedImage img, int x, int y){
        super(x,y,img);
        //this.bound = new Rectangle(x,y, this.img.getWidth(), this.img.getHeight());
        gameObjects.add(this);
    }
}
