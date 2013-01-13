package core;
import utilities.Vars;
import edu.wpi.first.wpilibj.Victor;
import utilities.MyJoystick;

/**
 * This is a comment!
 * @author Jacob Payne
 * #Fauzi owns drive!
 */

public class Drive {    
    private Victor mtLeft = new Victor(utilities.Vars.chnVicDrvLeft);
    private Victor mtRight = new Victor(utilities.Vars.chnVicDrvRight);
    private MyJoystick joy;
    private boolean bTank = false;
    
    public Drive(MyJoystick joystick) {
        joy = joystick;
    }
    
    public void run() {
        if (bTank)  
            tankDrive();
        
        else
            arcadeDrive();
    }

    private void tankDrive(){
        joy.setAxisChannel(MyJoystick.AxisType.kX, 2);
        joy.setAxisChannel(MyJoystick.AxisType.kY, 4);
        
       setSpeed(ramp(mtLeft.get(), joy.getX()), ramp(mtLeft.get(), joy.getX()));
    } 
	
    private double ramp(double curMtSpd, double joySpd){    
        
            if(curMtSpd-joySpd <= -0.1)
                return curMtSpd + 0.1;
            
            else if(curMtSpd-joySpd >= 0.1)
                return curMtSpd - 0.1;
            
            return joySpd;
	}
    
    private void arcadeDrive(){
        joy.setAxisChannel(MyJoystick.AxisType.kX, 1);
        joy.setAxisChannel(MyJoystick.AxisType.kY, 3);
        double y = joy.getY();
        double x = joy.getX();
        y *= Math.abs(y); // Squared Drive
        x *= Math.abs(x); // Squared Drive
	setSpeed(ramp(mtRight.get(), x-y), ramp(mtLeft.get(), x+y) );
        }
    
    public double getMotorLeft(){
        return mtLeft.get();
    }
    
    public double getMotorRight(){
        return mtRight.get();
    }
    
    public void setSpeed(double leftMt, double rightMt){
        mtLeft.set(leftMt);
        mtRight.set(rightMt);
        
    }
}