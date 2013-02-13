package core;
import utilities.Vars;
import utilities.MyJoystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Encoder;

/**
 * This is a comment!
 * @author Jacob Payne
 * #Fauzi owns drive!
 */

public class Drive {       
    
    private Talon m_mtLeft1 = new Talon(utilities.Vars.chnVicDrvLeft1);
	private Talon m_mtLeft2 = new Talon(utilities.Vars.chnVicDrvLeft2);
	private Talon m_mtLeft3 = new Talon(utilities.Vars.chnVicDrvLeft3);
	
    private Talon m_mtRight1 = new Talon(utilities.Vars.chnVicDrvRight1);
	private Talon m_mtRight2 = new Talon(utilities.Vars.chnVicDrvRight2);
	private Talon m_mtRight3 = new Talon(utilities.Vars.chnVicDrvRight3);

    private Encoder m_encMotorLeft = new Encoder(Vars.chnEncMotorLeftA, Vars.chnEncMotorLeftB);
    private Encoder m_encMotorRight = new Encoder(Vars.chnEncMotorRightA, Vars.chnEncMotorRightB);
    private MyJoystick joy;
    
    /**
     * Initializes drive, with joy and happiness.
     * @param joystick 
     */
    public Drive(MyJoystick joystick) {
        joy = joystick;
		m_encMotorRight.start();
		m_encMotorLeft.start();

    }
    
    public void run()
    {
        if (Vars.fnCanDrive()) 
            arcadeDrive();
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
        joy.setAxisChannel(MyJoystick.AxisType.kX, 3);
        joy.setAxisChannel(MyJoystick.AxisType.kY, 2);
        double y = joy.getY();
        double x = joy.getX();
        y *= Math.abs(y); // Squared Drive
        x *= Math.abs(x); // Squared Drive
        setSpeed(y - x, y + x);
	//setSpeed(ramp(m_mtRight.get(), x-y), ramp(m_mtLeft.get(), x+y) );
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
    public void setSpeed(double leftMt, double rightMt)
    {
        // Sets left and right motor speed.
		rightMt = -rightMt;
		leftMt = leftMt;  
		
		m_mtLeft1.set(leftMt);
		m_mtLeft2.set(leftMt);
		m_mtLeft3.set(leftMt);

        m_mtRight1.set(rightMt);
		m_mtRight2.set(rightMt);
		m_mtRight3.set(rightMt);
    }
	public void print() {
		System.out.println("Raw Value:  " +m_encMotorLeft.get() + "		" + m_encMotorRight.get( ));
	}
	
	public void printDistance() {
		System.out.println("Distance:  " +m_encMotorLeft.getDistance() + "		" + m_encMotorRight.getDistance( ));
	}
}