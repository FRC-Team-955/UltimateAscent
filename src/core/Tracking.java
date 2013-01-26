/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

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
public class Tracking 
{
    // CONSTANTS
    private double m_dDriveP = 0;
    private double m_dDriveI = 0;
    private double m_dDriveD = 0;
    private double m_dShootP = 0;
    private double m_dShootI = 0;
    private double m_dShootD = 0;
    private double m_dMiniumDistance = 0;
    private double m_dDriveTolerance = 0;
    private double m_dShootTolerance = 0;

    private double m_dDriveSpeed = 0;
    private double m_dShootSpeed = 0;
    private boolean m_bJustShoot = false;
    private MyPIDPosition m_PIDDrive = new MyPIDPosition(m_dDriveP, m_dDriveI, m_dDriveD);
    private MyPIDVelocity m_PIDShooter = new MyPIDVelocity(m_dShootP, m_dShootI, m_dShootD);
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
        if(m_joy.gotPressed(Vars.btTrack) && !m_joy.getSwitch(Vars.btRecord) && !m_joy.getSwitch(Vars.btReplay))
            m_joy.flipSwitch(Vars.btTrack);
        
        // If true, it means we're doing auto tracking.
        if(m_joy.getSwitch(Vars.btTrack))
        {
            // Gets images, calculates x, y and Ditance of the image.
            m_Camera.run();
            
            // If we're witiing range, commence auto tracking.
            if(m_Camera.getDistance() < m_dMiniumDistance)
            {
                // Disable the ability for the user to controll the robot.
                Vars.fnDisableDrive();
                Vars.fnDisableShooting();
                m_dDriveSpeed = m_PIDDrive.getOutput(m_Camera.getPicX(), Vars.dCameraCenterX);
                m_dShootSpeed = m_PIDShooter.getOutput(m_dShootSpeed, m_bot.getEncoderShooter());
                
                if(Math.abs(m_dDriveSpeed) >= m_dDriveTolerance)
                    m_bot.setDriveSpeed(m_dDriveSpeed, -m_dDriveSpeed);
                
                if(Math.abs(m_bot.getEncoderShooter()-m_dShootSpeed) >= m_dShootTolerance)
                    m_bot.setShooter(m_dShootSpeed);
                
                else if((Math.abs(m_dDriveSpeed) < m_dDriveTolerance) && (Math.abs(m_bot.getEncoderShooter()-m_dShootSpeed) < m_dShootTolerance))
                {
                    m_bot.setDriveSpeed(0, 0);
                    m_PIDDrive.reset(true);
                    m_PIDShooter.reset(true);
                    
                }
            }
        }
        
        else
        {    
            Vars.fnEnableDrive();
            Vars.fnEnableShooting();
        }
    }
}
