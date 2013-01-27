package utilities;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author fauzi
 */
public class MyPIDVelocity {
   
    // CONSTANTS
    private final double m_kP;
    private final double m_kI;
    private final double m_kD;
    
    private double m_P = 0;
    private double m_I = 0;
    private double m_D = 0;
    private double m_lastP = 0;
    private double m_dCurrentVal = 0;
    private double m_dLastVal = 0;
    private double m_dOutput = 0;
    private boolean m_bIsTimerOn = false;
    private Timer m_tmTimer = new Timer();
    
    public MyPIDVelocity(double dP, double dI, double dD)
    {
        m_kP = dP;
        m_kI = dI;
        m_kD = dD;
    }
    
    /**
     * Gets output value based on arguments.
     * @param dTarget
     * @param dCurrent
     * @return 
     */
    public double getOutput(double dTarget, double dCurrent)
    {        
        startTimer();
        m_dCurrentVal = m_tmTimer.get() - m_dLastVal;
        m_P = dTarget - dCurrent;
        m_I = m_I + m_P * m_dCurrentVal;
        m_D = m_P - m_lastP;
        m_lastP = m_P;
        m_dOutput += (m_kP * m_P) + (m_kI * m_I) + (m_kD * m_D);
        m_dLastVal = m_tmTimer.get();
        
        return m_dOutput;
    }
    
    /**
     * Resets all the values, if argument is true then stop and reset time as
     * well, other wise just leave it alone.
     */
    public void reset(boolean bResetTimer)
    {
        m_P = 0;
        m_I = 0;
        m_D = 0;
        m_lastP = 0;
        m_dCurrentVal = 0;
        m_dLastVal = 0;
        m_dOutput = 0;
        
        if(bResetTimer)
            resetTimer();
    }
    
    /**
     * Starts the timer only if it's not active.
     */
    private void startTimer()
    {
        if(!m_bIsTimerOn)
        {
            m_tmTimer.start();
            m_bIsTimerOn = true;
        }
    }
    
    /**
     * Stops and resets the timer only if it's active.
     */
    private void resetTimer()
    {
        if(m_bIsTimerOn)
        {
            m_tmTimer.stop();
            m_tmTimer.reset();
            m_bIsTimerOn = false;
        }
    }
}