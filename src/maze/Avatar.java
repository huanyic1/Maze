/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 Shenendehowa High School
  
 File: $(name).java
 Date: $(date)
 Purpose:
 Author: Yicheng Huang
 Secret Sauce Code: 4

 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
package maze;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Avatar implements ActionListener, KeyListener {

    public JFrame jframe;

    public static Avatar avatar;

    public RenderPanel renderPanel;

    public Timer timer = new Timer(20, this);

    public int height = 600, width = 800;
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
    public int ticks = 0, direction = DOWN, score, taillength;
    public static int scale = 20;
    public boolean over = false, paused;
    public Point head, cherry;
    public Random random;
    public int numWalls; 
    public ArrayList<Point> wall = new ArrayList<Point>();
    public int numBombs; 
    public ArrayList<Point> bomb = new ArrayList<Point>();
    public int numMovingBombs; 
    public ArrayList<Point> MovingBomb = new ArrayList<Point>();

    public Avatar() { //constructor for jframe

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        jframe = new JFrame("Snake");
        jframe.setVisible(true);
        jframe.setSize(806, 630);
        jframe.setLocationRelativeTo(null); //centers jframe
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.add(renderPanel = new RenderPanel());
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startGame();
    }

    public void startGame() {
        ticks=0; 
        wall.clear();
        bomb.clear(); 
        MovingBomb.clear();
        over = false;
        paused = false;
        random = new Random();
        score = 0;
        
        /////WALLLLS
        numWalls = 5; 
        
        for(int r = 0; r< height; r+=scale){ //sets walls down entire column every 4 columns (r=x, c=y)
            for(int c= 0; c< width; c+=4*scale){
                wall.add(new Point(c, r)); 
            }
        }    
        for(int i =0; i< wall.size(); i++){ //removes wall to create S shape so head can go through
            if(wall.get(i).x%(8*scale)==0){
                 if(wall.get(i).y>height-4*scale){ // removes bottom walls
                    wall.remove(i);
                    i--; 
                } 
            }
            else{
                if(wall.get(i).y<=2*scale){ //removes top walls
                    wall.remove(i);
                    i--; 
                }
              
            }
        }
//        for(int i =0; i< numWalls; i++){ //randomly creates walls
//            wall.add(new Point(scale * (random.nextInt(width / scale - 10) + 5), scale * (random.nextInt(height / scale - 10) + 5)));
//        }
        
        ////BOMBS
        numBombs= 5; 
//         for(int i =0; i< numBombs; i++){ //moving bombs are just more fun...
//            bomb.add(new Point(scale * (random.nextInt(width / scale - 10) + 5), scale * (random.nextInt(height / scale - 10) + 5)));
//        }
         
         ///MOVING BOMBS
          numMovingBombs= 1; 
       for(int r= 60; r< height; r+=scale*8){ 
         for(int c= 40; c< width; c+=4*scale){
                 MovingBomb.add(new Point(c, r));
            
        } 
       }
         for(int i =0; i< numMovingBombs; i++){ 
           //  MovingBomb.add(new Point(40, 360)); 
           // MovingBomb.add(new Point(scale * (random.nextInt(width / scale - 10) + 5), scale * (random.nextInt(height / scale - 10) + 5)));
        }
         
         //HEAD
        head = new Point(40, 560);
       // head = new Point(scale * (random.nextInt(width / scale - 10) + 5), scale * (random.nextInt(height / scale - 10) + 5));
       
        ///CHERRY
        cherry = new Point(760, 560);
      //  cherry = new Point(scale * random.nextInt(width / scale), scale * random.nextInt(height / scale));

        //avatarParts.add(new Point(head.x, head.y));
        timer.start();

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        renderPanel.repaint();
        ticks++;
//        if (ticks % 1 == 0 && head != null && !over && !paused) {
//
//            if (cherry != null) {

        if(ticks%15==0){  
            if(ticks/30%2==0){//moving bomb covers three spaces
                for(int i=0; i< MovingBomb.size();i++)
                MovingBomb.get(i).setLocation(MovingBomb.get(i).x+scale,MovingBomb.get(i).y ); 
            }
            else{
                 for(int i=0; i< MovingBomb.size();i++)
                 MovingBomb.get(i).setLocation(MovingBomb.get(i).x-scale,MovingBomb.get(i).y ); 
            }
        }
        if (head.x == cherry.x && head.y == cherry.y) {
            score += 10;
            cherry.setLocation(scale * random.nextInt(width / scale), scale * random.nextInt(height / scale));

        }
        if (!noBomb(head.x, head.y)||!noMovingBomb(head.x, head.y)) {//if collide with bomb or moving bomb, obviously lose
            over= true; 

        }
//            }
//        }

    }
     public boolean noWall(int x, int y) { //checks if there is wall ahead
        for (Point point : wall) {
            if (point.equals(new Point(x, y))) {
                return false;
            }
        }
        return true;
    }
     public boolean noBomb(int x, int y) { //checks if head collides with bomb
        for (Point point : bomb) {
            if (point.equals(new Point(x, y))) {
                return false;
            }
        }
        return true;
    }
      public boolean noMovingBomb(int x, int y) { //checks if head collides with moving bomb
        for (Point point : MovingBomb) {
            if (point.equals(new Point(x, y))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int i = ke.getKeyCode();
        if(!over){
        if (i == KeyEvent.VK_UP&&noWall(head.x, head.y-scale)&&head.y!=0) {//moving up

            head.y -= scale;

        }
        if (i == KeyEvent.VK_DOWN&&noWall(head.x, head.y+scale)&&head.y!=height-scale) { //moving down

            head.y += scale;

        }
        if (i == KeyEvent.VK_LEFT&&noWall(head.x-scale, head.y)&&head.x!=0) {//moving left

            head.x -= scale;

        }
        if (i == KeyEvent.VK_RIGHT&&noWall(head.x+scale, head.y)&& head.x!= width-scale) { //moving right
            head.x += scale;

        }
        }
        if (i == KeyEvent.VK_SPACE) {
            if (over) {
                startGame();
            } else {
                paused = !paused;
            }
        }
    
    }

    @Override
    public void keyReleased(KeyEvent ke) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        avatar = new Avatar();
    }

}
