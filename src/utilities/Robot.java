package utilities;

import core.Drive;
import core.Feeder;;
import core.Shooter;

/**
 * This class encapsulates the robot systems.
 * @author fauzi
 */
// TODO: Uncomment drive after teaching Jacob about axis joystick stuff.
public class Robot {
    
    private static MyJoystick m_joy;
    private static Drive m_drive;
    private static Feeder m_release;
    private static Shooter m_retrieve;
    
    public Robot(MyJoystick joytick)
    {
        m_joy = joytick;
        // m_drive = new Drive(m_joy);
        m_release = new Feeder(m_joy);
        m_retrieve = new Shooter(m_joy);
    }
    
    public void run()
    {
        m_drive.run();
        m_release.run();
        m_retrieve.run();
    }
    
    /**
     * Stops the robot and sets everything to false or zero, does not disable 
     * the ability to use it though. 
     */
    public void stopRobot()
    {
        setSpeed(0, 0);
        setRetrieve(false);
    }
    
    /**
     * Get the status of the retriever, true means active.
     * @return 
     */
    public boolean getRetrieveStat()
    {
        return m_retrieve.getStatus();
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
    public void setSpeed(double dLeft, double dRight)
    {
        m_drive.setSpeed(dLeft, dRight);
    }
    
    /**
     * Sets the retriever, true means active.
     * @param bRetrieveVal 
     */
    public void setRetrieve(boolean bRetrieveVal)
    {
        m_retrieve.set(bRetrieveVal);
    }
}