package org.qq;

public class MotorConfig {

    public static final MotorConfig M1 = new MotorConfig("M1", 12, 13, 19, 16, 17, 20);
    public static final MotorConfig M2 = new MotorConfig("M2", 4, 24, 18, 21, 22, 27);
    protected final String name;
    protected final int enablePin, directionPin, stepPin, m0Pin, m1Pin, m2Pin;
    
    public MotorConfig(String name, int enablePin, int directionPin, int stepPin, int m0Pin, int m1Pin, int m2Pin){
        this.name = name;
        this.enablePin=enablePin;
        this.directionPin=directionPin;
        this.stepPin=stepPin;
        this.m0Pin=m0Pin;
        this.m1Pin=m1Pin;
        this.m2Pin=m2Pin;
    }
    
}
