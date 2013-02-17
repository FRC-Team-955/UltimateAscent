package core;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import utilities.Vars;
import utilities.MySolenoid;
import utilities.MyJoystick;
import edu.wpi.first.wpilibj.Talon;
import utilities.DifEqControler;

/**
 * This class is responsible for controlling the shooter and feeder.
 * @author Fauzi
 */

// TODO: Find Velocity controller constants.
public class Shooter {
    
    // CONSTANTS
    private double m_dTimePerShot = .75;
    private double m_dSpeedUpTime = 1.5;
    
    private double lastTime;
    private double m_dShootSpeed = 0;//= Vars.shooterValue;
    public double rate = 0;
    public double rateRadians = 0;
    public double m_dTargetSpeed = 5000;//in RPM
    private MySolenoid m_solFeeder = new MySolenoid(Vars.chnSolFeederDown, Vars.chnSolFeederUp, false);
    private Timer m_tmFeeder = new Timer();
    private Timer m_tmPulser = new Timer();
    private Talon m_mtShooter1 = new Talon(Vars.chnVicShooter1);
    private Talon m_mtShooter2 = new Talon(Vars.chnVicShooter2);
    private Encoder m_encShooter = new Encoder(Vars.chnEncShooterA, Vars.chnEncShooterB,false, CounterBase.EncodingType.k4X);
    private MyJoystick m_joy;
    private boolean UseDifEqController = false;
    private double difEqOutput = 0;
    private DifEqControler difEq = new DifEqControler(0.1);
    
    public Shooter(MyJoystick joystick)
    {
        m_joy = joystick;
        m_joy.setSwitch(Vars.btAutoShoot, true);
        m_tmPulser.start();
        m_encShooter.start();
        difEq.freeze();
    }
    
    public void run()
    {
        getRate();

        if(Vars.fnCanShoot())
        {
            // When pressed switches auto shoot 
            if(m_joy.gotPressed(Vars.btAutoShoot))
                m_joy.flipSwitch(Vars.btAutoShoot);

            // If false, manual feed by driver
            if(!m_joy.getSwitch(Vars.btAutoShoot))
            {
                if(m_joy.gotPressed(Vars.btFeedFrisbee))
                    m_joy.flipSwitch(Vars.btFeedFrisbee);

                m_solFeeder.set(m_joy.getSwitch(Vars.btFeedFrisbee));
            }

            // When pressed, starts velocity controller so shooter motor can turn on.
            if(m_joy.gotPressed(Vars.btShootFrisbee))
            {
                m_joy.flipSwitch(Vars.btShootFrisbee);
                m_tmFeeder.start();
            }

            if(!m_joy.getSwitch(Vars.btShootFrisbee))
            {
                m_tmFeeder.stop();
                m_tmFeeder.reset();
                difEq.freeze();
            }

            // Sets the shooter to the specified speed.
            if(m_joy.getSwitch(Vars.btShootFrisbee))
            {
                difEq.unfreeze();

                if(UseDifEqController)
                    m_dShootSpeed = difEqOutput;

                else
                    m_dShootSpeed = m_dTargetSpeed*12.0/DriverStation.getInstance().getBatteryVoltage()/6200.0;

                setShooter(m_dShootSpeed);

                if(m_joy.getSwitch(Vars.btAutoShoot))
                {
                    if((m_tmFeeder.get() < 8 * m_dTimePerShot + m_dSpeedUpTime))
                    {
                        if((m_tmFeeder.get() >= m_dSpeedUpTime))
                            m_solFeeder.set((((int) (m_tmFeeder.get()/(m_dTimePerShot) - m_dSpeedUpTime)) % 2) == 0 ? true : false);
                    }

                    else
                    {
                        m_joy.setSwitch(Vars.btShootFrisbee, false);
                    }
                }
            }
            
            else 
                setShooter(0);
        }
	    
        //System.out.println("Enc:               " + rate);
        Vars.fnPrintToDriverstation(Vars.drShooterSpeed, "Shoot Auto: " + m_joy.getSwitch(Vars.btAutoShoot));
    }
    
    /**
     * Returns the shooter status.
     * @return 
     */
    public double getShooterSpeed()
    {
        return m_dShootSpeed;//m_mtShooter1.get();
    }
    
    /**
     * Returns the current encoder rate.
     * Updates every .10 seconds
     * @return 
     */
    public void getRate() 
    {
        double count;
        double time = m_tmPulser.get();
        
        if((time - lastTime) >= 0.05)
        {
            count = m_encShooter.getDistance();
            rate = 60 * (count/360.0) / (time-lastTime);
            rateRadians = 2 * Math.PI * rate / 60.0; 
            lastTime = time;
            m_encShooter.reset();
                
            if(UseDifEqController)
                difEqOutput = difEq.getOutput(rateRadians, time, 2 * Math.PI * m_dTargetSpeed / 60.0);
        }
    }
    
    /**
     * Returns the feeder status.
     * @return 
     */
    public boolean getFeedStatus()
    {
        return m_solFeeder.getStatus();
    }
    
    /**
     * Sets the shooter to the desired speed.
     * @param dSpeed 
     */
    public void setShooter(double dSpeed)
    {
        m_mtShooter1.set(dSpeed);
        m_mtShooter2.set(dSpeed);
    }
    
    /**
     * Sets the feeder based on the argument, true means feed one frisbee.
     * @param bStatus 
     */
    public void setFeeder(boolean bStatus)
    {
        m_solFeeder.set(bStatus);
    }
    
    /**
     * This function resets some things in the shooter class, like auto shoot
     * to true.
     */
    public void resetShooter()
    {
        m_joy.setSwitch(Vars.btAutoShoot, true);
    }
}