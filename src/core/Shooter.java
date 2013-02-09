package core;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import utilities.Vars;
import utilities.MySolenoid;
import utilities.MyPIDVelocity;
import utilities.MyJoystick;
import edu.wpi.first.wpilibj.Talon;

/**
 * This class is responsible for controlling the shooter and feeder.
 * @author Fauzi
 */

// TODO: Find Velocity controller constants.
public class Shooter {
    
    // CONSTANTS
    private final double m_dSpeedIncrease = 100;
    private final int m_iPulses = 500;
    
    private boolean m_bGoodToShoot = false;
    private int m_iFrisbeeShot = 0;
    private double m_dShootSpeed = 0;
    private double m_dPrevPulseTime = 0;
    private MySolenoid m_solFeeder = new MySolenoid(Vars.chnSolFeederDown, Vars.chnSolFeederUp, false);
    private MyPIDVelocity m_PIDShooter = new MyPIDVelocity(Vars.kShooterP, Vars.kShooterI, Vars.kShooterD);
    private Timer m_tmFeeder = new Timer();
    private Timer m_tmPulser = new Timer();
    private Talon m_mtShooter = new Talon(Vars.chnVicShooter);
    private Encoder m_encShooter = new Encoder(1, Vars.chnEncShooter);
    private MyJoystick m_joy;
    
    public Shooter(MyJoystick joystick)
    {
        m_joy = joystick;
        m_tmPulser.start();
    }
    
    public void run()
    {
        if(Vars.fnCanShoot())
        {
            // When pressed, starts velocity controller so shooter motor can turn on.
            if(m_joy.gotPressed(Vars.btShootFrisbee))
            {
                
                m_joy.flipSwitch(Vars.btShootFrisbee);
                m_tmPulser.start();
                if(!m_joy.getSwitch(Vars.btShootFrisbee))
                {
                    m_PIDShooter.reset(true);
                    m_tmPulser.stop();
                    m_tmPulser.reset();
                }
            }

            // Sets the shooter to the specified speed.
            if(m_joy.getSwitch(Vars.btShootFrisbee))
            {
                m_mtShooter.set(m_PIDShooter.getOutput(m_dShootSpeed, m_encShooter.getRate()));

                if(Math.abs(m_dShootSpeed - m_encShooter.getRate()) <= Vars.dShootTolerance)
                    m_bGoodToShoot = true;

                else
                    m_bGoodToShoot = false;

                // Feeds one frisbee to the shooter if shooter is at the right speed.
                if(m_joy.gotPressed(Vars.btFeedFrisbee) && m_bGoodToShoot)
                    m_joy.flipSwitch(Vars.btFeedFrisbee);
                
                if(m_joy.getSwitch(Vars.btFeedFrisbee))
                {    
                    if(!m_solFeeder.getStatus() && m_bGoodToShoot)
                    {
                        m_tmFeeder.start();
                        m_solFeeder.turnOn();
                    }

                    if(m_tmFeeder.get() >= Vars.dMinFeedTime)
                    {
                        m_solFeeder.turnOff();
                        m_tmFeeder.stop();
                        m_tmFeeder.reset();
                        m_iFrisbeeShot++;
                        m_bGoodToShoot = false;
                    }
                    
                    if(m_iFrisbeeShot >= Vars.iMaxFrisbee)
                    {
                        m_iFrisbeeShot = 0;
                        m_joy.setSwitch(Vars.btFeedFrisbee, false);
                    }
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
     * Returns the shooters encoder rate.
     * @return 
     */
    public double getShooterEncoder()
    {
        double dReturn = 0;
        double dCurrent = m_tmPulser.get();
        int iCount = m_encShooter.get();

        m_encShooter.reset();
        // NOTE:  60 seconds per minute;   250 counts per rotation
        dReturn = (60.0 / 250.0) * iCount / (dCurrent - m_dPrevPulseTime);
        m_dPrevPulseTime = dCurrent;
        return dReturn;
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