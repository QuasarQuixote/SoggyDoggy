package org.qq;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
    private BufferedImage image;

    public ImagePanel() {
        super.setSize(640, 480);
        image=null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(image!=null){
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters    
        }        
    }

    public void drawImage(BufferedImage image){
        this.image = image;
        super.invalidate();
    }
}