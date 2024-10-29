package org.qq;
import javax.imageio.ImageIO;
import javax.swing.*;
//import java.util.concurrent.locks.*;

//import com.pi4j.Pi4J;
//import com.pi4j.context.Context;
//import com.pi4j.io.gpio.digital.DigitalOutput;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.io.File;
//import javax.imageio.ImageIO;


public class GUI extends JFrame {

    protected JPanel panel;
    protected JButton button;
    protected final Devices devices;

    public GUI(Devices devices){
        super("My Application");
        this.devices = devices;
        this.setSize(800, 600);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.panel = new JPanel();
        this.setLocationRelativeTo(null);
        
        this.button = new JButton("Activate Solenoid");
        this.button.setFocusable(false);
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Button clicked");
                //GUI.this.devices.toggleSolenoid();
                GUI.this.devices.activateSolenoid(Duration.ofSeconds(1));

            }
        });
        this.panel.add(button);
        this.add(panel, BorderLayout.WEST);
        ImagePanel imagePanel = new ImagePanel();
        this.add(imagePanel);
        try{
            BufferedImage sample = ImageIO.read(new File("/home/brendan/Test.png"));
            imagePanel.drawImage(sample);
        } catch(Exception e){
            //Do nothing
        }
    }

}
