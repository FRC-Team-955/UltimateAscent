/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;
import utilities.Vars;
import utilities.MyJoystick;
import edu.wpi.first.wpilibj.Victor;


/**
 *
 * @author Jacob Payne, Parker, Kushal, Gil, Ryan, Cheri, Fauzi, Fauzi, Arian(X2), Ryan, Ryan, Ryan, Ryan, Ryan, Ryan, Arian(X24), Ryan
 */
public class Retrieval {
    
    // CONSTANTS
    private double m_dRetrieveSpeed = 0.5;
    
    private Victor m_mtRetrieve = new Victor(Vars.chnVicRetriver);
    private MyJoystick m_joy;
  
    public Retrieval(MyJoystick joystick){
        m_joy = joystick;
    }
    
    /**
     * Sets retrieve motor on or off with a button press.
     */
    public void run(){
        
        if(m_joy.gotPressed(Vars.btRetrieve))
            m_joy.flipSwitch(Vars.btRetrieve);
        
        if(m_joy.getSwitch(Vars.btRetrieve))
            m_mtRetrieve.set(m_dRetrieveSpeed);
        
        else
            m_mtRetrieve.set(0);      
    }
    
    /**
     * Returns if the retriever is on.
     * @return 
     */
    public boolean getRetrieveStatus()
    {
        return m_joy.getSwitch(Vars.btRetrieve);
    }
    
    /**
     * Sets the retriever to on or off.
     * @param bStatus 
     */
    public void setRetrieve(boolean bStatus)
    {
        if(bStatus)
            m_mtRetrieve.set(m_dRetrieveSpeed);
        
        else
            m_mtRetrieve.set(0);
    }
}