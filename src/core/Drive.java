//package core;

//import utilities.Vars;
//import utilities.MyJoystick;
//import edu.wpi.first.wpilibj.Victor;
//import edu.wpi.first.wpilibj.Gyro;
//
///**
// * @author fauzi
// */
//// TODO: Find Gyro channels
//public class Drive {
//    
//    private double m_dRightSpeed = 0;
//    private double m_dLeftSpeed = 0;
//    private String m_sDriveStatus = "";
//    private Victor m_mtLeft = new Victor(Vars.chnVicDrvLeft);
//    private Victor m_mtRight = new Victor(Vars.chnVicDrvRight);
//    private Gyro m_gyroRight = new Gyro(1);
//    private Gyro m_gyroLeft = new Gyro(1);
//    private MyJoystick m_joy;
//    
//    public Drive(MyJoystick joystick)
//    {
//        m_joy = joystick;
//    }
//    
//    public void run()
//    {      
//        if(Vars.fnCanDrive())
//            regDrive();
//        
//        else
//            m_sDriveStatus = "Drive Disabled";
//        
//        Vars.fnPrintToDriverstation(Vars.prDriveStatusLine, m_sDriveStatus);
//    }
//
//    private void regDrive()
//    {
//        // Setting to get regular drive Working properly on the ps3 Controller
//        // should be 3, 2
//        m_joy.setAxisChannel(MyJoystick.AxisType.kX, 3);
//        m_joy.setAxisChannel(MyJoystick.AxisType.kY, 2);
//
//        m_sDriveStatus = "Regular Drive";
//        double y = m_joy.getY() * Math.abs(m_joy.getY());
//        double x = m_joy.getX() * Math.abs(m_joy.getX());
//        
//        m_dRightSpeed = (-y+x);
//        m_dLeftSpeed = (y+x); 
//        setSpeed(m_dLeftSpeed, m_dRightSpeed);
//    }
//    
//    public void setSpeed(double setMtLeft, double setMtRight)
//    {
//        m_mtLeft.set(setMtLeft);
//        m_mtRight.set(setMtRight);
//    }
//    
//    public double getGyroLeft()
//    {
//        return m_gyroLeft.getAngle();
//    }
//    
//    public double getGyroRight()
//    {
//        return m_gyroRight.getAngle();
//    }
//    
//    public void resetGyros()
//    {
//        m_gyroLeft.reset();
//        m_gyroRight.reset();
//    }
//}
package core;
import utilities.Vars;
import edu.wpi.first.wpilibj.*;

/**
 * This is a comment!
 * @author Jacob Payne
 */

public class Drive {    
    private Victor mtLeft = new Victor(utilities.Vars.chnVicDrvLeft);
    private Victor mtRight = new Victor(utilities.Vars.chnVicDrvRight);
    private Joystick leftJoy;
    private Joystick rightJoy;
    private double curLeftMtSpd;
    private double curRightMtSpd; 
    private boolean bTank = false;
    private double increaseSpdRight;
    private double increaseSpdLeft;
    private boolean bRampCheck = true; //FYI True is right
    
    public Drive(Joystick joyLeft, Joystick joyRight) {
        leftJoy = joyLeft;
        rightJoy = joyRight;
    }
    public void run() {
        
        if (bTank){
            tankDrive();
        }
        else{
            arcadeDrive();
        }
    }

    private void tankDrive(){     
        ramp(bRampCheck,0 ,0);
        bRampCheck = !bRampCheck;
        ramp(bRampCheck,0 ,0);
        mtLeft.set(increaseSpdLeft); // Will this work?
        mtRight.set(increaseSpdRight);
     
    } 
//    tank() {
//        left target = left.get; // left target =  Y + X
//        current = leffmotor.get;
//        mtleft.set(ramp(target,current));
//    }
//    
    private double ramp(boolean bRampCheck, double right, double left){    
    
        curRightMtSpd = mtRight.get(); 
        curLeftMtSpd = mtLeft.get();
        right = rightJoy.getY();
        left = leftJoy.getY();
        
        if(bRampCheck){
            if(curRightMtSpd-right >= 0.1){
                //mtLeft.set(curLeftMtSpd+0.1);
                increaseSpdRight = 0.1;
            }
            else if(curRightMtSpd-right <= -0.1){
                //mtRight.set(curRightMtSpd-0.1);
                increaseSpdRight = -0.1;
            }
            else{
               //Do nothing!
            }
            return increaseSpdLeft;
        }
        else{
            if(curLeftMtSpd-left >= 0.1){
                //mtLeft.set(curLeftMtSpd+0.1);
                increaseSpdLeft = 0.1;
            }
            else if(curLeftMtSpd-left <= -0.1){
                //mtRight.set(curRightMtSpd-0.1);
                increaseSpdLeft = -0.1;
            }
            else{
               //Do nothing!
            }
            return increaseSpdRight;
        }
          
    }
    
    private void arcadeDrive(){
        double y = rightJoy.getY();
        double x = leftJoy.getX();
        ramp(bRampCheck, x, y);
        bRampCheck = !bRampCheck;
        ramp(bRampCheck, x, y);
        x = increaseSpdRight;
        y = increaseSpdLeft;
        y *= Math.abs(y); // Squared Drive
        x *= Math.abs(y); // Squared Drive
        mtLeft.set(y+x);
        mtRight.set(y-x);
    }
}