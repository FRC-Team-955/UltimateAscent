package core;

import utilities.Vars;
import utilities.MyJoystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Gyro;

/**
 * @author fauzi
 */
// TODO: Find Gyro channels
public class Drive {
    
    private double m_dRightSpeed = 0;
    private double m_dLeftSpeed = 0;
    private String m_sDriveStatus = "";
    private Victor m_mtLeft = new Victor(Vars.chnVicDrvLeft);
    private Victor m_mtRight = new Victor(Vars.chnVicDrvRight);
    private Gyro m_gyroRight = new Gyro(1);
    private Gyro m_gyroLeft = new Gyro(1);
    private MyJoystick m_joy;
    
    public Drive(MyJoystick joystick)
    {
        m_joy = joystick;
    }
    
    public void run()
    {      
        if(Vars.fnCanDrive())
            regDrive();
        
        else
            m_sDriveStatus = "Drive Disabled";
        
        Vars.fnPrintToDriverstation(Vars.prDriveStatusLine, m_sDriveStatus);
    }

    private void regDrive()
    {
        // Setting to get regular drive Working properly on the ps3 Controller
        // should be 3, 2
        m_joy.setAxisChannel(MyJoystick.AxisType.kX, 3);
        m_joy.setAxisChannel(MyJoystick.AxisType.kY, 2);

        m_sDriveStatus = "Regular Drive";
        double y = m_joy.getY() * Math.abs(m_joy.getY());
        double x = m_joy.getX() * Math.abs(m_joy.getX());
        
        m_dRightSpeed = (-y+x);
        m_dLeftSpeed = (y+x); 
        setSpeed(m_dLeftSpeed, m_dRightSpeed);
    }
    
    public void setSpeed(double setMtLeft, double setMtRight)
    {
        m_mtLeft.set(setMtLeft);
        m_mtRight.set(setMtRight);
    }
    
    public double getGyroLeft()
    {
        return m_gyroLeft.getAngle();
    }
    
    public double getGyroRight()
    {
        return m_gyroRight.getAngle();
    }
    
    public void resetGyros()
    {
        m_gyroLeft.reset();
        m_gyroRight.reset();
    }
}