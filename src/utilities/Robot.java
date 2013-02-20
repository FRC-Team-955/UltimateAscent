package utilities;

import core.Drive;
import core.Shooter;
import core.Lifter;
import edu.wpi.first.wpilibj.Timer;

/**
 * This class encapsulates the robot systems.
 * @author fauzi
 */
public class Robot {
    
    private static Drive m_drive;
    private static Shooter m_shooter;
    private static Lifter m_lifter;
	private Timer autoTime = new Timer();
    
    public Robot(MyJoystick joytick)
    {
        m_drive = new Drive(joytick);
        m_shooter = new Shooter(joytick);
        m_lifter = new Lifter(joytick);
    }
    
    /**
     * Runs the robot code, as in Drive, Shooter.
     */
    public void run()
    {
        m_drive.run();

        m_shooter.run();
		
        m_lifter.run();
    }
    
    /**
     * Stops the robot and sets everything to false or zero, does not disable 
     * the ability to use it though. 
     */
    public void stopRobot()
    {
        setDriveSpeed(0, 0);
        setShooter(0);
        setFeeder(false);
    }
    
    /**
     * Returns the distance the left encoder has traveled.
     * @return 
     */
    public double getMotorLeftSpeed()
    {
        return m_drive.getMotorLeftSpeed();
    }
    
    /**
     * Returns the distance the right encoder has traveled.
     * @return 
     */
    public double getMotorRightSpeed()
    {
        return m_drive.getMotorRightSpeed();
    }
    
    /**
     * Get the speed of the shooter.
     * @return 
     */
    public double getShooterSpeed()
    {
        return m_shooter.getShooterSpeed();
    }
    
    /**
     * Gets the feeder status, true means active.
     * @return 
     */
    public boolean getFeederStatus()
    {
        return m_shooter.getFeedStatus();
    }
    
    /**
     * Set the speed of the left and right motors, values allowed are -1 to 1.
     * @param dLeft
     * @param dRight 
     */
    public void setDriveSpeed(double dLeft, double dRight)
    {
        m_drive.setSpeed(dLeft, dRight);
    }
    
    /**
     * Sets the shooter to the specified speed.
     * @param dSpeed 
     */
    public void setShooter(double dSpeed)
    {
        m_shooter.setShooter(dSpeed);
    }
    
    /**
     * Sets the feeder to true or false. True means active.
     * @param bStatus 
     */
    public void setFeeder(boolean bStatus)
    {
        m_shooter.setFeeder(bStatus);
    }
}