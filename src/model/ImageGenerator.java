package model;


import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.swing.JPanel;

public class ImageGenerator extends JPanel {

    // Generates an extremely wide BufferedImage in order to be able to display
    // more items in receipt than user has patience to add to transaction.
    // This hardcode is a biproduct if newline chars not working as intended in Transaction.printReceipt()
    public static BufferedImage createImageWithText(char[] s,int x, int y){
        BufferedImage bufferedImage = new BufferedImage(2500,y,BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();

        g.drawChars(s, 0, x, 20,20);

        return bufferedImage;
    }

}
