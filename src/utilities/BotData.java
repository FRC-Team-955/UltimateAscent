package utilities;

/**
 * Holds data corresponding to the values of the robot.
 * @author Fauzi
 */ 
public class BotData{
    
    private double m_dMtLeft = 0;
    private double m_dMtRight = 0;
    private boolean m_bRtrvStat = false;
    
    /**
     * Sets the values
     * @param dMtLeft
     * @param dMtRight
     * @param bRtrvStat 
     */
    public void setValues(double dMtLeft, double dMtRight, boolean bRtrvStat)
    {
        m_dMtLeft = dMtLeft;
        m_dMtRight = dMtRight;
        m_bRtrvStat = bRtrvStat;
    }
    
    /**
     * Sets the values.
     * @param bot 
     */
    public void setValues(Robot bot)
    {
        setValues(bot.getDistanceLeft(), bot.getDistanceRight(), bot.getRetrieveStat());
    }
    
    /**
     * Sets the values.
     * @param emu 
     */
    public void setValues(BotData emu)
    {
        setValues(emu.getMtLeft(), emu.getMtRight(), emu.getRetrieve());
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
     * Gets the retrieve status.
     * @return 
     */
    public boolean getRetrieve()
    {
        return m_bRtrvStat;
    }
}