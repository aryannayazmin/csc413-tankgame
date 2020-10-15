package tankrotationexample.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlusLifePowerUp extends GameObjects{
    //private Rectangle bound;

    PlusLifePowerUp(BufferedImage img, int x, int y) {
        super(x, y, img);
        //this.bound = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
        gameObjects.add(this);
    }
}
