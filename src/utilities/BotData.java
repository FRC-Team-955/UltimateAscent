package utilities;

/**
 * Holds data corresponding to the values of the robot.
 * @author Fauzi
 */ 
public class BotData{
    
    private double m_dEncoderLeft = 0;
    private double m_dEncoderRight = 0;
    private double m_dShooterSpeed = 0;
    private boolean m_bFeederStatus = false;
    
    /**
     * Sets the values.
     * @param dEncoderLeft
     * @param dMtRight
     * @param bFeedStatus 
     */
    public void setValues(double dEncoderLeft, double dEncoderRight, double dShootSpeed, boolean bFeedStatus)
    {
        m_dEncoderLeft = dEncoderLeft;
        m_dEncoderRight = dEncoderRight;
        m_dShooterSpeed = dShootSpeed;
        m_bFeederStatus = bFeedStatus;
    }
    
    /**
     * Sets the values.
     * @param bot 
     */
    public void setValues(Robot bot)
    {
        setValues(bot.getEncoderLeft(), bot.getEncoderRight(), bot.getShooterSpeed(), bot.getFeederStatus());
    }
    
    /**
     * Sets the values.
     * @param emu 
     */
    public void setValues(BotData emu)
    {
        setValues(emu.getEncoderLeft(), emu.getEncoderRight(), emu.getShooterSpeed(), emu.getFeederStatus());
    }
    
    /**
     * Gets the motor value on the left side
     * @return 
     */
    public double getEncoderLeft()
    {
        return m_dEncoderLeft;
    }
    
    /**
     * Gets the motor value on the right side.
     * @return 
     */
    public double getEncoderRight()
    {
        return m_dEncoderRight;
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