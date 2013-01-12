package core;
import utilities.Vars;
import edu.wpi.first.wpilibj.*;

/**
 * This is a comment!
 * @author Jacob Payne
 */

public class Drive {    
    private Victor mtLeft = new Victor(utilities.Vars.chnVicDrvLeft);
    private Victor mtRight = new Victor(utilities.Vars.chnVicDrvRight);
    private Joystick leftJoy;
    private Joystick rightJoy;
    private boolean bTank = false;
    //private double increaseSpdRight;
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
		mtLeft.set(ramp(mtLeft.get(), leftJoy.getY()));
        mtRight.set(ramp(mtRight.get(), rightJoy.getY()));
    } 
	
    private double ramp(double curMtSpd, double joySpd){    
			double speed = 0;
            if(curMtSpd-joySpd >= 0.1){
                //mtLeft.set(curLeftMtSpd+0.1);
                speed = 0.1;
            }
            else if(curMtSpd-joySpd <= -0.1){
                //mtRight.set(curRightMtSpd-0.1);
                speed = -0.1;
            }
            else{
               //Do nothing!
            }
            return speed;
	}
    
    private void arcadeDrive(){
        double y = rightJoy.getY();
        double x = leftJoy.getX();
        y *= Math.abs(y); // Squared Drive
        x *= Math.abs(x); // Squared Drive
		mtRight.set(ramp(mtRight.get(), x-y));
		mtLeft.set(ramp(mtRight.get(), x+y));
    }
    
    // JACOB YOU'LL HAVE TO RE-WRTIE THESE BELOW!!! SO DO IT ~Fauzi
    public double getMotorLeft()
    {
        return mtLeft.get();
    }
    
    public double getMotorRight()
    {
        return mtRight.get();
    }
    
    public void setSpeed(double dLeft, double dRight)
    {
        mtLeft.set(dLeft);
        mtRight.set(dRight);
    }
}