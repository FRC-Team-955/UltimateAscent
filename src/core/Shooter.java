package core;

import utilities.Vars;
import utilities.MyJoystick;
import edu.wpi.first.wpilibj.Victor;

/**
 * @author Fauzi
 */
public class Shooter {
    
    private boolean m_bShooting = false;
    private double m_dShootSpeed = -1;
    private Victor m_mtRetrieve = new Victor(Vars.chnVicShooter);
    private MyJoystick m_joy;
    
    public Shooter(MyJoystick joystick)
    {
        m_joy = joystick;
    }
    
    public void run()
    {
        if(m_joy.gotPressed(Vars.btShootFrisbee))
            m_bShooting = !m_bShooting;
        
        if(m_bShooting)
            m_mtRetrieve.set(m_dShootSpeed);
        
        else
            m_mtRetrieve.set(0);
    }
    
    public boolean getStatus()
    {
        return m_bShooting;
    }
    
    public void set(boolean bStatus)
    {
        if(bStatus)
            m_mtRetrieve.set(m_dShootSpeed);
        
        else
            m_mtRetrieve.set(0);
    }
}
