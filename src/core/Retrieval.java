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
 * @author Jacob Payne
 */
public class Retrieval {
    
    // CONSTANTS
    private double m_dFeedSpeed = 0.5;
    private Victor mtFeed = new Victor(7);
    private MyJoystick joy;
    
    
    public Retrieval(){
        if(joy.gotPressed(Vars.btFeed))
            mtFeed.set(m_dFeedSpeed);
        else
            mtFeed.set(0);
    }
}
