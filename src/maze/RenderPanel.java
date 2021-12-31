/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 Shenendehowa High School
  
 File: $(name).java
 Date: $(date)
 Purpose:
 Author: Yicheng Huang
 Secret Sauce Code: 4

 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
package maze;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JPanel;

public class RenderPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        Avatar avatar = Avatar.avatar;
       
        g.setColor(Color.WHITE); //board/grid
        g.fillRect(0, 0, avatar.width, avatar.height);
        g.setColor(Color.BLACK);
        for (int i = 0; i < avatar.width / Avatar.scale; i++) { ///drawing grid lines
            g.drawLine(i * Avatar.scale, 0, i * Avatar.scale, avatar.height);
        }
        for (int i = 0; i < avatar.height / Avatar.scale; i++) {
            g.drawLine(0, i * avatar.scale, avatar.width, i * avatar.scale);
        }//end board/grid

        g.setColor(Color.BLACK); //avatar
        g.fillRect(avatar.head.x, avatar.head.y, Avatar.scale, Avatar.scale);
        g.setColor(Color.GREEN);
        g.fillRect(avatar.head.x + 2, avatar.head.y + 2, Avatar.scale - 4, Avatar.scale - 4);
        //end avatar

        g.setColor(Color.blue); //walls
        ArrayList<Point> wall = avatar.wall; 
        for (int i = 0; i < wall.size(); i++) {
            g.fillRect(wall.get(i).x, wall.get(i).y, avatar.scale, avatar.scale);
        }//end walls

        g.setColor(Color.RED); //cherry
        g.fillOval(avatar.cherry.x, avatar.cherry.y, Avatar.scale, Avatar.scale);
        g.setColor(Color.GREEN);
        g.drawArc(avatar.cherry.x + avatar.scale / 2, avatar.cherry.y - avatar.scale / 4, avatar.scale / 2, avatar.scale / 2, 180, -90);
        //end cherry
        
        //bomb
         ArrayList<Point> bomb = avatar.bomb; 
          for (int i = 0; i < bomb.size(); i++) {
            g.setColor(Color.BLACK); 
        g.fillOval(bomb.get(i).x, bomb.get(i).y, Avatar.scale, Avatar.scale);
        g.setColor(Color.ORANGE);
        g.drawArc(bomb.get(i).x + avatar.scale / 2, bomb.get(i).y - avatar.scale / 4, avatar.scale / 2, avatar.scale / 2, 180, -90);
        }
       
        //end bomb    
        
          //moving bomb
           ArrayList<Point> movingbomb = avatar.MovingBomb; 
          for (int i = 0; i < movingbomb.size(); i++) {
            g.setColor(Color.PINK); 
        g.fillOval(movingbomb.get(i).x, movingbomb.get(i).y, Avatar.scale, Avatar.scale);
        g.setColor(Color.MAGENTA);
        g.drawArc(movingbomb.get(i).x + avatar.scale / 2, movingbomb.get(i).y - avatar.scale / 4, avatar.scale / 2, avatar.scale / 2, 180, -90);
        }
       
        //end moving bomb    
        g.setColor(Color.BLACK); //score display
        String string = "Score: " + avatar.score + " Points";
//        String string2 = "\n" + "Length: " + avatar.taillength;
        g.setFont(new Font("Arial", Font.CENTER_BASELINE, 14));
        g.drawString(string, 345, 20);
//        g.drawString(string2, 365, 40);
        if (avatar.over) { //game over display
            g.setFont(new Font("Arial", Font.CENTER_BASELINE, 36));
            g.setColor(Color.RED);
            g.drawString("Game Over", 300, 320);
            g.drawString("Press Space To Restart", 200, 360);
        }
    }

}
