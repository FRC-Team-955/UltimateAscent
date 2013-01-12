package core;
import utilities.Vars;
import edu.wpi.first.wpilibj.*;

/**
 * This is a comment!
 * @author Fauzi Kliman, Jacob Payne
 */


public class Drive {    
    
    // TODO: Find encoder channels!
    private Encoder m_encoderRight = new Encoder(1,1);
    private Encoder m_encoderLeft = new Encoder(1, 1);
    private Victor mtLeft = new Victor(utilities.Vars.chnVicDrvLeft);
    private Victor mtRight = new Victor(utilities.Vars.chnVicDrvRight);
    private Joystick leftJoy;
    private Joystick rightJoy;
    private double curLeftMtSpd;
    private double curRightMtSpd; 
    private boolean bTank = false;
    private double increaseSpdRight;
    private double increaseSpdLeft;
    private boolean bRampCheck = true; //FYI True is right
    
    public Drive(Joystick joyLeft, Joystick joyRight) {
        leftJoy = joyLeft;
        rightJoy = joyRight;
    }
    public void run() {
        
        if (bTank){
            tankDrive();
        }
        else{
            arcadeDrive();
        }
    }

    private void tankDrive(){     
        ramp(bRampCheck,0 ,0);
        bRampCheck = !bRampCheck;
        ramp(bRampCheck,0 ,0);
        mtLeft.set(increaseSpdLeft); // Will this work?
        mtRight.set(increaseSpdRight);
     
    } 
//    tank() {
//        left target = left.get; // left target =  Y + X
//        current = leffmotor.get;
//        mtleft.set(ramp(target,current));
//    }
//    
    private double ramp(boolean bRampCheck, double right, double left){    
    
        curRightMtSpd = mtRight.get(); 
        curLeftMtSpd = mtLeft.get();
        right = rightJoy.getY();
        left = leftJoy.getY();
        
        if(bRampCheck){
            if(curRightMtSpd-right >= 0.1){
                //mtLeft.set(curLeftMtSpd+0.1);
                increaseSpdRight = 0.1;
            }
            else if(curRightMtSpd-right <= -0.1){
                //mtRight.set(curRightMtSpd-0.1);
                increaseSpdRight = -0.1;
            }
            else{
               //Do nothing!
            }
            return increaseSpdLeft;
        }
        else{
            if(curLeftMtSpd-left >= 0.1){
                //mtLeft.set(curLeftMtSpd+0.1);
                increaseSpdLeft = 0.1;
            }
            else if(curLeftMtSpd-left <= -0.1){
                //mtRight.set(curRightMtSpd-0.1);
                increaseSpdLeft = -0.1;
            }
            else{
               //Do nothing!
            }
            return increaseSpdRight;
        }
          
    }
    
    private void arcadeDrive(){
        double y = rightJoy.getY();
        double x = leftJoy.getX();
        ramp(bRampCheck, x, y);
        bRampCheck = !bRampCheck;
        ramp(bRampCheck, x, y);
        x = increaseSpdRight;
        y = increaseSpdLeft;
        y *= Math.abs(y); // Squared Drive
        x *= Math.abs(y); // Squared Drive
        mtLeft.set(y+x);
        mtRight.set(y-x);
    }
    
    /**
     * Gets the the amount of times the left motor has fully rotated.
     * @return 
     */
    public double getDistanceLeft()
    {
        return m_encoderLeft.getDistance();
    }
    
    /**
     * Gets the the amount of times the right motor has fully rotated.
     * @return 
     */
    public double getDistanceRight()
    {
        return m_encoderRight.getDistance();
    }
    
    /**
     * Resets both the encoders distance to 0
     */
    public void resetEncoders()
    {
        m_encoderLeft.reset();
        m_encoderRight.reset();
    }
}