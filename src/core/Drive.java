package core;
import utilities.Vars;
import edu.wpi.first.wpilibj.Victor;
import utilities.MyJoystick;

/**
 * This is a comment!
 * @author Jacob Payne
 * #Fauzi owns drive!
 */

public class Drive {        // Ramps motor speed and set motor speed 
    
    private String m_sDriveStatus = "Arcade Drive";
    private Victor m_mtLeft = new Victor(utilities.Vars.chnVicDrvLeft);
    private Victor m_mtRight = new Victor(utilities.Vars.chnVicDrvRight);
    private MyJoystick joy;
    
    /**
     * Initializes drive, with joy and happiness.
     * @param joystick 
     */
    public Drive(MyJoystick joystick) {
        joy = joystick;
    }
    
    public void run() {
        if (Vars.fnCanDrive())  
        {
            arcadeDrive();
            m_sDriveStatus = "Arcade Drive";
        }
        
        else
        {
            setSpeed(0, 0);
            m_sDriveStatus = "Disabled";
        }
        
        Vars.fnPutDashBoardStringBox(Vars.skDriveStatus, m_sDriveStatus);
    }
    
    /**
     * Tank drive. This is not arcade drive.
     */
//    private void tankDrive(){
//        joy.setAxisChannel(MyJoystick.AxisType.kX, 2);
//        joy.setAxisChannel(MyJoystick.AxisType.kY, 4);
//        setSpeed(ramp(mtLeft.get(), joy.getX()), ramp(mtLeft.get(), joy.getX()));
//    } 
	
   /**
    *  Changes the speed such that it isn't higher than it should be.
    * @param curMtSpd
    * @param joySpd
    * @return 
    */ 
    private double ramp(double curMtSpd, double joySpd){   
        
        
            if(curMtSpd-joySpd <= -0.1)
                return curMtSpd + 0.1;
            
            else if(curMtSpd-joySpd >= 0.1)
                return curMtSpd - 0.1;
            
            return joySpd;
	}
    
    /**
     * Arcade drive. This is not tank drive
     */
    private void arcadeDrive(){
        joy.setAxisChannel(MyJoystick.AxisType.kX, 1);
        joy.setAxisChannel(MyJoystick.AxisType.kY, 3);
        double y = joy.getY();
        double x = joy.getX();
        y *= Math.abs(y); // Squared Drive
        x *= Math.abs(x); // Squared Drive
	setSpeed(ramp(m_mtRight.get(), x-y), ramp(m_mtLeft.get(), x+y) );
    }
    
    public double getMotorLeft(){
        // Returns motor speed.
        return m_mtLeft.get();
    }
    
    public double getMotorRight(){
        // Sets motor speed
        return m_mtRight.get();
    }
    
    public void setSpeed(double leftMt, double rightMt){
        // Sets left and right motor speed.
        m_mtLeft.set(leftMt);
        m_mtRight.set(rightMt);
        
    }
}