/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tankrotationexample.game;

import tankrotationexample.GameConstants;
import tankrotationexample.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;


import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class TRE extends JPanel implements Runnable {

    private BufferedImage world;
    private Tank t1;
    private Tank t2;
    private Launcher lf;
    private long tick = 0;
    ArrayList<Wall> walls;
    private Map background;

    public TRE(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
       try {
           this.resetGame();
           while (true) {
                this.tick++;
                this.t1.update(); // update tank
                this.t2.update();
                this.repaint();   // redraw tankrotationexample.game
               Sound.Music.loop();
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end tankrotationexample.game event
                 * we will do this with by ending the tankrotationexample.game when drawn 2000 frames have been drawn
                 */
    //            if(this.tick > 2000){
    //                this.lf.setFrame("end");
    //                return;
    //            }
            }
       } catch (InterruptedException ignored) {
           System.out.println(ignored);
       }
    }

    /**
     * Reset tankrotationexample.game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        this.t1.setX(300);
        this.t1.setY(300);
        this.t2.setX(1160);
        this.t2.setY(300);
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() throws IOException {
        this.world = new BufferedImage(GameConstants.WORLD_WIDTH,
                                       GameConstants.WORLD_HEIGHT,
                                       BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        walls = new ArrayList<>();
        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            t1img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank1.png")));
            t2img = read(Objects.requireNonNull(TRE.class.getClassLoader().getResource("tank2.png")));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        t1 = new Tank(300, 200, 0, 0, 0, t1img);
        t2 = new Tank(1160, 300, 0, 0, 0, t2img);
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        background = new Map();
        background.init();
        this.lf.getJf().addKeyListener(tc1);
        this.lf.getJf().addKeyListener(tc2);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.WORLD_WIDTH,GameConstants.WORLD_HEIGHT);
        this.background.drawImage(buffer);
        //this.walls.forEach(wall -> wall.drawImage(buffer));
        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        int boundsX = Tank.checkBoundsX(t1);
        int boundsY = Tank.checkBoundsY(t1);
        int boundsX2 = Tank.checkBoundsX(t2);
        int boundsY2 = Tank.checkBoundsY(t2);
        BufferedImage leftHalf = world.getSubimage(boundsX,boundsY,GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage rightHalf = world.getSubimage(boundsX2,boundsY2,GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT);
        BufferedImage mm = world.getSubimage(0,0, GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
        g2.drawImage(leftHalf,0,0,null);
        g2.drawImage(rightHalf,GameConstants.GAME_SCREEN_WIDTH/2,0,null);
        //AffineTransform mm = AffineTransform.getTranslateInstance(GameConstants.GAME_SCREEN_WIDTH/2.5, 0);
        //g2.scale(.10,.10);
        //g2.drawImage(mm,1000,200,null);
        //g2.drawImage(world,0,0,null);

        g2.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        g2.scale(0.58,0.58);
        g2.setColor(Color.WHITE);
        g2.drawString("Lives : " + this.t1.getLives(),300  , 150);
        g2.setColor(Color.WHITE);
        g2.drawString("Player 2 Health: " + this.t1.getHealth(), 400, 150);
        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.t1.getHealth() && i < 100; i++) {
            g2.drawRect(GameConstants.GAME_SCREEN_WIDTH * 27 / 80 + i, 175, 60, 30);
        }

        g2.setColor(Color.WHITE);
        g2.drawString("Lives : " + this.t2.getLives(), 650, 150);
        g2.setColor(Color.WHITE);
        g2.drawString("Player 1 Health: " + this.t2.getHealth(), 750,  150);
        g2.setColor(Color.GREEN);
        for (int i = 0; i < this.t2.getHealth() && i < 100; i++) {
            g2.drawRect(GameConstants.GAME_SCREEN_WIDTH * 50 / 80 + i, 175, 60, 30);
        }

        g2.scale(.15,.15);
        g2.drawImage(mm,GameConstants.GAME_SCREEN_WIDTH/2,200,null);

        this.t1.isWon();
        this.t2.isWon();
    }


}
