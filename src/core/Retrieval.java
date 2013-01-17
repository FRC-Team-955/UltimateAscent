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
    
    private Victor mtRetrieve = new Victor(Vars.chnVicRetriver);
    private MyJoystick joy;
  
    public Retrieval(MyJoystick joystick){
        joy = joystick;
    }
    
    /**
     * Sets retrieve motor on or off with a button press.
     */
    public void run(){
        
        if(joy.gotPressed(Vars.btRetrieve))
            joy.flipSwitch(Vars.btRetrieve);
        
        if(joy.getSwitch(Vars.btRetrieve))
            mtRetrieve.set(m_dRetrieveSpeed);
        
        else
            mtRetrieve.set(0);      
    }
    
    /**
     * Returns if the retriever is on.
     * @return 
     */
    public boolean getRetrieveStatus()
    {
        return joy.getSwitch(Vars.btRetrieve);
    }
    
    /**
     * Sets the retriever to on or off.
     * @param bStatus 
     */
    public void setRetrieve(boolean bStatus)
    {
        if(bStatus)
            mtRetrieve.set(m_dRetrieveSpeed);
        
        else
            mtRetrieve.set(0);
    }
}