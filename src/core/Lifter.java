/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;
import utilities.Vars;
import utilities.MyJoystick;
import utilities.MySolenoid;

/**
 *
 * @author RaiderPC
 */
public class Lifter 
{
	private MySolenoid m_solLifter = new MySolenoid(Vars.chnSolLifterDown, Vars.chnSolLifterUp, false);
	private MyJoystick m_joy;
	
	public Lifter(MyJoystick joy)
	{
		m_joy = joy;
	}
	
	public void run()
	{
		if(m_joy.gotPressed(Vars.btLift))
			m_joy.flipSwitch(Vars.btLift);
		
		m_solLifter.set(m_joy.getSwitch(Vars.btLift));
	}
}
