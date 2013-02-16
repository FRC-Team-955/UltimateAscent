package utilities;

/**
 * Holds data corresponding to the values of the robot.
 * @author Fauzi
 */ 
public class BotData
{
    private double m_dTime = 0;
    private double m_dLeftSpeed = 0;
    private double m_dRightSpeed = 0;
    private double m_dShooterSpeed = 0;
    private boolean m_bFeederStatus = false;
    
    /**
     * Sets the values.
     * @param dLeftSpeed
     * @param dMtRight
     * @param bFeedStatus 
     */
    public void setValues(double dTimer, double dLeftSpeed, double dRightSpeed, double dShootSpeed, boolean bFeedStatus)
    {
        m_dTime = dTimer;
        m_dLeftSpeed = dLeftSpeed;
        m_dRightSpeed = dRightSpeed;
        m_dShooterSpeed = dShootSpeed;
        m_bFeederStatus = bFeedStatus;
    }
    
    /**
     * Sets the values.
     * @param bot 
     */
    public void setValues(double dTime, Robot bot)
    {
        setValues(dTime, bot.getMotorLeftSpeed(), bot.getMotorRightSpeed(), bot.getShooterSpeed(), bot.getFeederStatus());
    }
    
    /**
     * Sets the values.
     * @param emu 
     */
    public void setValues(BotData emu)
    {
        setValues(emu.getTimer(), emu.getMotorLeftSpeed(), emu.getMotorRightSpeed(), emu.getShooterSpeed(), emu.getFeederStatus());
    }
    
    /**
     * Returns time stamp of the data.
     * @return 
     */
    public double getTimer()
    {
        return m_dTime;
    }
    
    /**
     * Gets the motor value on the left side
     * @return 
     */
    public double getMotorLeftSpeed()
    {
        return m_dLeftSpeed;
    }
    
    /**
     * Gets the motor value on the right side.
     * @return 
     */
    public double getMotorRightSpeed()
    {
        return m_dRightSpeed;
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