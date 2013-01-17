package utilities;

import core.Drive;
import core.Shooter;
import core.Retrieval;

/**
 * This class encapsulates the robot systems.
 * @author fauzi
 */
public class Robot {
    
    private static Drive m_drive;
    private static Shooter m_shooter;
    private static Retrieval m_retrieval;
    
    public Robot(MyJoystick joytick)
    {
        m_drive = new Drive(joytick);
        m_shooter = new Shooter(joytick);
        m_retrieval = new Retrieval(joytick);
    }
    
    /**
     * Runs the robot code, as in Drive, Shooter.
     */
    public void run()
    {
        m_drive.run();
        m_shooter.run();
        m_retrieval.run();
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
        setRetrieve(false);
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
     * Gets the retrieve status, true means on.
     * @return 
     */
    public boolean getRetrieveStatus()
    {
        return m_retrieval.getRetrieveStatus();
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
    
    /**
     * Sets the retriever, true means active.
     * @param bStatus 
     */
    public void setRetrieve(boolean bStatus)
    {
        m_retrieval.setRetrieve(bStatus);
    }
}