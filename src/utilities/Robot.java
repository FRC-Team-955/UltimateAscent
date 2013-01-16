package utilities;

import core.Drive;
import core.Shooter;

/**
 * This class encapsulates the robot systems.
 * @author fauzi
 */
public class Robot {
    
    private static MyJoystick m_joy;
    private static Drive m_drive;
    private static Shooter m_shooter;
    
    public Robot(MyJoystick joytick)
    {
        m_joy = joytick;
        m_drive = new Drive(m_joy);
        m_shooter = new Shooter(m_joy);
    }
    
    /**
     * Runs the robot code, as in Drive, Shooter.
     */
    public void run()
    {
        m_drive.run();
        m_shooter.run();
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
     * Get the status of the retriever, true means active.
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
     * Returns the speed of the left motor.
     * @return 
     */
    public double getMotorLeft()
    {
        return m_drive.getMotorLeft();
    }
    
    /**
     * Returns the speed of the right motor.
     * @return 
     */
    public double getMotorRight()
    {
        return m_drive.getMotorRight();
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