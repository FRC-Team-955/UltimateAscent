package utilities;

/**
 * Holds data corresponding to the values of the robot.
 * @author Fauzi
 */ 
public class BotData{
    
    private double m_dTime = 0;
    private double m_dMtLeft = 0;
    private double m_dMtRight = 0;
    private double m_dShooterSpeed = 0;
    private boolean m_bFeederStatus = false;
    
    /**
     * Sets the values.
     * @param dMtLeft
     * @param dMtRight
     * @param bFeedStatus 
     */
    public void setValues(double dTimer, double dMtLeft, double dMtRight, double dShootSpeed, boolean bFeedStatus)
    {
        m_dTime = dTimer;
        m_dMtLeft = dMtLeft;
        m_dMtRight = dMtRight;
        m_dShooterSpeed = dShootSpeed;
        m_bFeederStatus = bFeedStatus;
    }
    
    /**
     * Sets the values.
     * @param bot 
     */
    public void setValues(double dTimer, Robot bot)
    {
        setValues(dTimer, bot.getMotorLeft(), bot.getMotorRight(), bot.getShooterSpeed(), bot.getFeederStatus());
    }
    
    /**
     * Sets the values.
     * @param emu 
     */
    public void setValues(BotData emu)
    {
        setValues(emu.getTime(), emu.getMtLeft(), emu.getMtRight(), emu.getShooterSpeed(), emu.getFeederStatus());
    }
    
    /**
     * Returns the time stamp of the data.
     * @return 
     */
    public double getTime()
    {
        return m_dTime;
    }
    
    /**
     * Gets the motor value on the left side
     * @return 
     */
    public double getMtLeft()
    {
        return m_dMtLeft;
    }
    
    /**
     * Gets the motor value on the right side.
     * @return 
     */
    public double getMtRight()
    {
        return m_dMtRight;
    }
    
    /**
     * Gets the shooter speed.
     * @return 
     */
    public double getShooterSpeed()
    {
        return m_dShooterSpeed;
    }
    
    /**
     * Gets the feeder status.
     * @return 
     */
    public boolean getFeederStatus()
    {
        return m_bFeederStatus;
    }
}