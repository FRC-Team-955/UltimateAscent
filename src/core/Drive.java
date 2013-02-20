package core;
import utilities.Vars;
import utilities.MyJoystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Encoder;

/**
 * This is a comment!
 * @author Jacob Payne
 * #Fauzi owns drive!
 */

public class Drive {        // Ramps motor speed and set motor speed 
    
    private String m_sDriveStatus = "Arcade Drive";
    private Victor m_mtLeft1 = new Victor(utilities.Vars.chnVicDrvLeft1);
    private Victor m_mtLeft2 = new Victor(utilities.Vars.chnVicDrvLeft2);
    private Victor m_mtLeft3 = new Victor(utilities.Vars.chnVicDrvLeft3);
    private Victor m_mtRight1 = new Victor(utilities.Vars.chnVicDrvRight1);
    private Victor m_mtRight2 = new Victor(utilities.Vars.chnVicDrvRight2);
    private Victor m_mtRight3 = new Victor(utilities.Vars.chnVicDrvRight3);
    private Encoder m_encMotorLeft = new Encoder(1, Vars.chnEncMotorLeft);
    private Encoder m_encMotorRight = new Encoder(1, Vars.chnEncMotorRight);
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
            arcadeDrive();
        
        else
            m_sDriveStatus = "Disabled";
        
        Vars.fnPutDashBoardStringBox(Vars.skDriveStatus, m_sDriveStatus);
    }
    public void setLeft (double set){
        m_mtLeft1.set(set);
        m_mtLeft2.set(set);
        m_mtLeft3.set(set);
    }
     public void setRight (double set){
        m_mtRight1.set(set);
        m_mtRight2.set(set);
        m_mtRight3.set(set);
    }
    
	
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
	setSpeed(ramp(m_mtRight1.get(), x-y), ramp(m_mtLeft1.get(), x+y) );
        m_sDriveStatus = "Arcade Drive";
    }
    
    /**
     * Returns the encoder distance on the left side of the robot.
     * @return 
     */
    public double getEncoderLeftDistance(){
        return m_encMotorLeft.getDistance();
    }
    
    /**
     * Returns the encoder distance on the right side of the robot.
     * @return 
     */
    public double getEncoderRightDistance(){
        return m_encMotorRight.getDistance();
    }
    
    public void resetEncoders()
    {
        m_encMotorLeft.reset();
        m_encMotorRight.reset();
    }
    /**
     * Sets the motor's speed to the desired speed.
     * @param leftMt
     * @param rightMt 
     */
    public void setSpeed(double leftMt, double rightMt){
        // Sets left and right motor speed.
        setLeft(leftMt);
        setRight(rightMt);
    }
}