package core;

import edu.wpi.first.wpilibj.Encoder;
import utilities.Vars;
import utilities.MySolenoid;
import utilities.MyPIDVelocity;
import utilities.MyJoystick;
import edu.wpi.first.wpilibj.Victor;

/**
 * This class is responsible for controlling the shooter and feeder.
 * @author Fauzi
 */

// TODO: Find Velocity controller constants.
public class Shooter {
    
    // CONSTANTS
    private final double m_dP = 0;
    private final double m_dI = 0;
    private final double m_dK = 0;
    private final double m_dSpeedIncrease = 100;
    private final double m_dSpeedTolerance = 10;
    
    private boolean m_bGoodToShoot = false;
    private double m_dShootSpeed = 0;
    private MySolenoid m_solFeeder = new MySolenoid(Vars.chnSolFeederDown, Vars.chnSolFeederUp, false);
    private MyPIDVelocity m_PIDShooter = new MyPIDVelocity(m_dP, m_dI, m_dK);
    private Victor m_mtShooter = new Victor(Vars.chnVicShooter);
    private Encoder m_encShooter = new Encoder(1, Vars.chnEncShooter);
    private MyJoystick m_joy;
    
    public Shooter(MyJoystick joystick)
    {
        m_joy = joystick;
    }
    
    public void run()
    {
        // When pressed, starts velocity controller so shooter motor can turn on.
        if(m_joy.gotPressed(Vars.btShootFrisbee))
        {
            m_joy.flipSwitch(Vars.btShootFrisbee);
            
            if(m_joy.getSwitch(Vars.btShootFrisbee))
                m_PIDShooter.startTimer();
            
            else
                m_PIDShooter.reset(true);
        }
        
        // Sets the shooter to the specified speed.
        if(m_joy.getSwitch(Vars.btShootFrisbee))
        {
            m_mtShooter.set(m_PIDShooter.getOutput(m_dShootSpeed, m_encShooter.getRate()));
            
            if(Math.abs(m_dShootSpeed - m_encShooter.getRate()) <= m_dSpeedTolerance)
                m_bGoodToShoot = true;
        
            else
                m_bGoodToShoot = false;

            // Feeds one frisbee to the shooter if shooter is at the right speed.
            if(m_joy.gotPressed(Vars.btFeedFrisbee) && m_bGoodToShoot)
            {
                m_bGoodToShoot = false;
                m_solFeeder.turnOn();
                m_solFeeder.turnOff();
            }
        }
        
        // If we're not shooting, shooter should be zero.
        else if(!m_joy.getSwitch(Vars.btShootFrisbee))
            m_mtShooter.set(0);
          
        // Sets the shooter speed when the increase or decrease button is pressed
        if(m_joy.gotPressed(Vars.btIncreaseSpeed))
            m_dShootSpeed += m_dSpeedIncrease;
        
        if(m_joy.gotPressed(Vars.btDecreaseSpeed))
            m_dShootSpeed -= m_dSpeedIncrease;
        
        Vars.fnPutDashBoardNumberBox(Vars.skShooterSpeed, m_dShootSpeed);
        Vars.fnPutDashBoardButton(Vars.skCanFeed, m_bGoodToShoot);
    }
    
    /**
     * Returns the shooter status.
     * @return 
     */
    public double getShooterSpeed()
    {
        return m_dShootSpeed;
    }
    
    /**
     * Returns the feeder status.
     * @return 
     */
    public boolean getFeedStatus()
    {
        return m_joy.getSwitch(Vars.btFeedFrisbee);
    }
    
    /**
     * Sets the shooter to the desired speed.
     * @param dSpeed 
     */
    public void setShooter(double dSpeed)
    {
        m_mtShooter.set(dSpeed);
    }
    
    /**
     * Sets the feeder based on the argument, true means feed one frisbee.
     * @param bStatus 
     */
    public void setFeeder(boolean bStatus)
    {
        m_solFeeder.set(bStatus);
    }
}