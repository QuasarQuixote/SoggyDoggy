package org.qq;

import java.time.Duration;
//import java.util.concurrent.locks.*;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;

public class Devices {
    
    // member variables
    protected final Context context;
    protected final Motor m1;
    protected final Motor m2;
    protected final DigitalOutput solenoid;

    /**
     * Constructor
     */
    public Devices() {
        context = Pi4J.newAutoContext();
        m1 = new Motor(context, MotorConfig.M1, Motor.StepSize.HALF, Duration.ofNanos(350000));
        m2 = new Motor(context, MotorConfig.M2, Motor.StepSize.HALF, Duration.ofNanos(350000));
        solenoid = context.dout().create(23);
        solenoid.low();
    }

    public void exit(){
        solenoid.low();
    }

    public void toggleSolenoid(){
        solenoid.setState(solenoid.isLow()); //toggles solenoid
    }

    public void activateSolenoid(final Duration pulse){
        Thread worker = new Thread() {
            public void run() {
                Devices.this.solenoid.high();
                try {
                    sleep(pulse.toMillis());
                } catch( InterruptedException e ) {
                    // do nothing
                }
                Devices.this.solenoid.low();
            }
        };
        worker.start();
    }

    public void activateSolenoid(){
        solenoid.high();
    }

    public void deactivateSolenoid(){
        solenoid.low();
    }
    
    public boolean solenoidIsActive(){
        return solenoid.isHigh();
    }

    public Motor getM1(){
        return this.m1;
    }

    public Motor getM2(){
        return this.m2;
    }

    public DigitalOutput getSolenoid(){
        return this.solenoid;
    }

}
