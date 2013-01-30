/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import edu.wpi.first.wpilibj.Timer;
import utilities.Robot;
import utilities.MyCamera;
import utilities.MyJoystick;
import utilities.MyPIDPosition;
import utilities.MyPIDVelocity;
import utilities.Vars;

/**
 *
 * @author Fauzi
 */
// TODO: FIND MINIUM DISTANCE!
public class Tracking 
{
    // CONSTANTS
    private final double m_dMiniumDistance = 0;

    private boolean m_bGoodToShoot = true;
    private boolean m_bLinedUp = true;
    private double m_dDriveSpeed = 0;
    private double m_dShootSpeed = 0;
    private int m_iFrisbeeCount = 0;
    private Timer m_tmFeeder = new Timer();
    private MyPIDPosition m_PIDDrive = new MyPIDPosition(Vars.kDriveP, Vars.kDriveI, Vars.kDriveD);
    private MyPIDVelocity m_PIDShooter = new MyPIDVelocity(Vars.kShooterP, Vars.kShooterI, Vars.kShooterD);
    private MyCamera m_Camera = new MyCamera();
    private MyJoystick m_joy;
    private Robot m_bot;
    
    public Tracking(MyJoystick joy, Robot bot)
    {
        m_joy = joy;
        m_bot = bot;
    }
    
    public void run()
    {
        // If we're not Replaying, Recording, and the button got pressed, flip vision tracking switch.
        if(m_joy.gotPressed(Vars.btTrack) && !m_joy.getSwitch(Vars.btRecord) && !m_joy.getSwitch(Vars.btReplay))
            m_joy.flipSwitch(Vars.btTrack);
        
        // If true, it means we're doing auto tracking.
        if(m_joy.getSwitch(Vars.btTrack))
        {
            // Gets images, calculates x, y and Ditance of the image.
            m_Camera.run();
            
            // If we're within range, commence auto tracking.
            if(m_Camera.getDistance() < m_dMiniumDistance)
            {
                // Disable the ability for the user to controll the robot.
                Vars.fnDisableDrive();
                Vars.fnDisableShooting();
                
                // Get shoot and drive speed from PID controllers
                m_dDriveSpeed = m_PIDDrive.getOutput(m_Camera.getPicX(), Vars.dCameraCenterX);
                m_dShootSpeed = m_PIDShooter.getOutput(m_dShootSpeed, m_bot.getEncoderShooter());
                
                // Check if we're not lined up yet.
                if(Math.abs(Vars.dCameraCenterX - m_Camera.getPicX()) >= Vars.dDriveTolerance)
                    m_bot.setDriveSpeed(m_dDriveSpeed, -m_dDriveSpeed);
                
                // Check if the shooter is not up to speed yet.
                if(Math.abs(m_bot.getEncoderShooter() - m_dShootSpeed) >= Vars.dShootTolerance)
                    m_bot.setShooter(m_dShootSpeed);
                
                // If we're lined up AND shooter is up to speed.
                else if((Math.abs(Vars.dCameraCenterX - m_Camera.getPicX()) < Vars.dDriveTolerance) && (Math.abs(m_bot.getEncoderShooter() - m_dShootSpeed) < Vars.dShootTolerance))
                {
                    // Set motors to 0, reset PID controllers.
                    m_bot.setDriveSpeed(0, 0);
                    m_PIDDrive.reset(true);
                    m_PIDShooter.reset(true);
                    
                    // If pressed turn on set feeder switch on.
                    if(m_joy.gotPressed(Vars.btFeedFrisbee))
                        m_joy.flipSwitch(Vars.btFeedFrisbee);
                    
                    if(m_joy.getSwitch(Vars.btFeedFrisbee))
                    {    
                        if(!m_bot.getFeederStatus())
                        {
                            m_tmFeeder.start();
                            m_bot.setFeeder(true);
                        }

                        if(m_tmFeeder.get() >= Vars.dMinFeedTime)
                        {
                            m_bot.setFeeder(false);
                            m_tmFeeder.stop();
                            m_tmFeeder.reset();
                            m_iFrisbeeCount++;
                        }

                        if(m_iFrisbeeCount >= Vars.iMaxFrisbee)
                        {
                            m_iFrisbeeCount = 0;
                            m_joy.setSwitch(Vars.btFeedFrisbee, false);
                        }
                    }
                }
            }
            
            // Allow user able to drive and shoot.
            else
            {    
                Vars.fnEnableDrive();
                Vars.fnEnableShooting();
            }
        }
    }
}
