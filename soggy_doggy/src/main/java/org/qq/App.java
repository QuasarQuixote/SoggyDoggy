package org.qq;
import java.awt.event.*;


//import javax.lang.model.type.MirroredTypeException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Devices devices = new Devices();
        ShutdownHook sh = new ShutdownHook(devices);
        Runtime.getRuntime().addShutdownHook(sh);
        
        GUI gui = new GUI(devices);
        gui.setVisible(true);
        KeyBindings keyBindings = new KeyBindings(devices);
        gui.addKeyListener(keyBindings);
        
        System.out.println( "Hello World!" );
    }

    private static class ShutdownHook extends Thread {
   
        // member variable
        protected final Devices devices;

        private ShutdownHook( Devices devices ) {
            this.devices = devices;
        }

        @Override
        public void run() {
            System.out.println( "Process terminating. Cleaning up" );
            this.devices.exit();
        }
    }

    public static class KeyBindings implements KeyListener {

        static final int SPACE_KEY=32;
        static final int LEFT_ARROW = 37;
        static final int RIGHT_ARROW = 39;
        static final int DOWN_ARROW = 40;
        static final int UP_ARROW = 38;
        static final int STEP_X = 35;
        static final int STEP_Y = 5;
        Devices devices;
        KeyBindings(Devices devices){
            this.devices=devices;
        }
        
        /** Handle the key typed event from the text field. */
        @Override
        public void keyTyped(KeyEvent e) {
            displayInfo(e, "KEY TYPED: ");
        }
    
        /** Handle the key pressed event from the text field. */
        @Override

        public void keyPressed(KeyEvent e) {
            try{
                displayInfo(e, "KEY PRESSED: ");
                if(e.getKeyCode()==SPACE_KEY){
                    devices.activateSolenoid();
                }
                if(e.getKeyCode()==RIGHT_ARROW){
                    devices.getM2().move(STEP_X);
                }
                if(e.getKeyCode()==LEFT_ARROW){
                    devices.getM2().move(-STEP_X);
                }
                if(e.getKeyCode()==DOWN_ARROW){
                    devices.getM1().move(                                STEP_X);
                }
                if(e.getKeyCode()==UP_ARROW){
                    devices.getM1().move(-STEP_X);
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    
        /** Handle the key released event from the text field. */
        public void keyReleased(KeyEvent e) {
            displayInfo(e, "KEY RELEASED: ");
            if(e.getKeyCode()==SPACE_KEY){
                devices.deactivateSolenoid();
            }
        }
        
        protected void displayInfo(KeyEvent e, String s){
        char c = e.getKeyChar();
        int keyCode = e.getKeyCode();
        //int modifiers = e.getModifiers();
        //String tmpString = KeyEvent.getKeyModifiersText(modifiers);
    
        System.out.println(s+" "+ e.toString());
        }
    }
}
