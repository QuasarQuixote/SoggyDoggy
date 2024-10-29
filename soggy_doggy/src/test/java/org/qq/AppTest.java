package org.qq;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test.
     */
    //@Test
    void testApp() {
        assertEquals(1, 1);
    }

    @Test
    void testDevices() throws InterruptedException{
        Devices devices = new Devices();
        //devices.toggleSolenoid();
        //Thread.sleep(500);
        //devices.getM1().move(200);
        for(int i=0; i<3; i++){
            devices.getM2().move(1600);
            Thread.sleep(1000);
            devices.getM2().move(-1600);
            devices.exit();
        }
    }

    //@Test
    void MotorSpeed() throws InterruptedException{
        Devices devices = new Devices();
        devices.getM1().move(4000);
    }
}
