package model;


import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.swing.JPanel;

public class ImageGenerator extends JPanel {


    Transaction transaction;

    /*public void paint(Graphics g){
        String s="";
        int y=0;
        Image img = createImageWithText(s, y);
        g.drawImage(img, 20, 20, this);
    }*/

    public static BufferedImage createImageWithText(char[] s,int x, int y){
        BufferedImage bufferedImage = new BufferedImage(2500,y,BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();

        g.drawChars(s, 0, x, 20,20);

        return bufferedImage;
    }

}
