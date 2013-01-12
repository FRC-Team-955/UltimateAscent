package core;

import utilities.Vars;
import utilities.MySolenoid;
import utilities.MyJoystick;

/**
 *
 * @author fauzi
 */
public class Feeder {
    
    private boolean m_bReleaseStat = false;
    private MySolenoid m_solRelease = new MySolenoid(Vars.chnSolReleaseDown, Vars.chnSolReleaseUp, false);
    private MyJoystick m_Joy;
    
    public Feeder(MyJoystick joystick)
    {
       m_Joy = joystick;
    }
    
    public void run()
    {
        if(m_Joy.gotPressed(Vars.btFeedFrisbee))
            m_bReleaseStat = !m_bReleaseStat;
        
        m_solRelease.set(m_bReleaseStat);
    }
}
