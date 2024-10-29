package org.qq;

import java.time.Duration;
import java.util.concurrent.locks.LockSupport;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;

public class Motor {

    public enum Direction { FORWARD, BACKWARD };

    public enum StepSize { FULL, HALF, QUARTER, EIGHTH, SIXTEENTH, THIRTYSECOND };

    // member variables
    protected final DigitalOutput enablePin;
    protected final DigitalOutput directionPin;
    protected final DigitalOutput stepPin;
    protected final DigitalOutput m0Pin;
    protected final DigitalOutput m1Pin;
    protected final DigitalOutput m2Pin;
    protected final Duration pulse;
    
    public Motor( Context pi4J, MotorConfig config, StepSize size, Duration pulse ) {
        this.enablePin = pi4J.dout().create(config.enablePin);
        this.directionPin = pi4J.dout().create(config.directionPin);
        this.stepPin = pi4J.dout().create(config.stepPin);
        this.m0Pin = pi4J.dout().create(config.m0Pin);
        this.m1Pin = pi4J.dout().create(config.m1Pin);
        this.m2Pin = pi4J.dout().create(config.m2Pin);
        this.pulse=pulse;
        setStepSize(size);
        
    }

    public void setStepSize(StepSize size){
        switch (size) {
            case FULL:
                setModePins( 0, 0, 0 );
                break;
            case HALF:
                setModePins(1,0,0);
                break;
            case QUARTER:
                setModePins(0,1,0);
                break;
            case EIGHTH:
                setModePins(1,1,0);
                break;
            case SIXTEENTH:
                setModePins(0,0,1);
                break;
            case THIRTYSECOND:
                setModePins(1,0,1);
                break;
            default:
                throw new IllegalStateException();
        }
        System.out.println("Mode set to " + size );
        System.out.println( "m0: " + this.m0Pin.isHigh() );
        System.out.println( "m1: " + this.m1Pin.isHigh() );
        System.out.println( "m2: " + this.m2Pin.isHigh() );
    }
    protected void setModePins(int m0, int m1, int m2){
        this.m0Pin.setState(m0);
        this.m1Pin.setState(m1);
        this.m2Pin.setState(m2);
    }

    public void move(int steps ) throws InterruptedException{
       if(steps>0) {
        directionPin.high();
       } else {
        directionPin.low();
       }
       enablePin.high();
       for(int i=0; i<Math.abs(steps); i++){
        stepPin.high();
        //Thread.sleep(this.pulse.toMillis(), (int)(this.pulse.toNanos()%1000000));
        LockSupport.parkNanos(this.pulse.toNanos());
        stepPin.low();
        LockSupport.parkNanos(this.pulse.toNanos());
        //Thread.sleep(this.pulse.toMillis(), (int)(this.pulse.toNanos()%1000000));
       }
       enablePin.low();
       directionPin.low();
    }
    
}
