package utilities;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class has ALL the variables used as channels for FRC objects like 
 * Victors or solenoids and ect. It ONLY has channel variables and useful 
 * functions. We have a separate class just so we can change the channel more 
 * easily rather than searching through the class itself.
 * @author Fauzi
 */
// TODO: SET SHOOTER ENCODER CHANNEL.
public class Vars {
    
    // Victors
    public static final int chnVicDrvLeft = 1;
    public static final int chnVicDrvRight = 2;
    public static final int chnVicShooter = 3;
	
    // Solenoids
    // SOLENOID BUMPER CHANNEL 7 OR 8 DOES NOT WORK!!!!!!
    public static final int chnSolFeederUp = 1;
    public static final int chnSolFeederDown = 2;
	
    // Digital Sidecard 
    public static final int chnEncShooter = 1;
    public static final int chnEncMotorLeft = 2;
    public static final int chnEncMotorRight = 3;
    public static final int chnDigiOutCompressor = 4;
    public static final int chnDigiInSensor = 5;

    // Button channel on the joystick
    public static final int btIncreaseSpeed = 1;
    public static final int btFeedFrisbee = 2;
    public static final int btDecreaseSpeed = 3;
    public static final int btShootFrisbee = 4;
    public static final int btRecord = 9;
    public static final int btAllowEdit = 10;
    public static final int btReplay = 12;
    public static final int btTrack = 13;
    
    // DriverStation Autonomous Button Channels
    public static final int stDigInAutoCtr = 1;
    public static final int stDigInAutoLft = 2;
    public static final int stDigInAutoRght = 3;
    public static final int stDigInReg = 4;
    
    // Printing to Driverstation lines, 2-6 are available only
//    public static final int prCodeVersionLine = 2;
//    public static final int prDriveStatusLine = 3;
//    public static final int prVisionStatusLine = 4;
//    public static final int prEditAutoModeLine = 5;
//    public static final int prAutonomousStatLine = 6;
    
    // Smartdashboard keys
    public static final String skCodeVersion = "Code Version";
    public static final String skDriveStatus = "Drive Status";
    public static final String skEditFileStatus = "File Status";
    public static final String skAutonomousStatus = "Autonomous Status";
    public static final String skShooterSpeed = "Shooter speed";
    public static final String skCanFeed = "Can we Feed?";
    
    // Other
    public static final int iPs3Buttons = 13;
    public static final double dCameraCenterX = 0;
    public static final double dCameraCenterY = 0;
    private static boolean bDrive = true;
    
    /**
     * Sets the double to be only at the hundreth's place, ex. 12.34.
     */
    public static double fnSetPrecision(double dDouble)
    {
        return (Double.valueOf(Math.floor(dDouble * 10 + 0.5) / 10)).doubleValue();
    }
    
    /**
     * Disables ability for user to drive robot.
     */
    public static void fnDisableDrive()
    {
        bDrive = false;
    }
    
    /**
     * Enables ability for the user to drive the robot.
     */
    public static void fnEnableDrive()
    {
        bDrive = true;
    }
    
    /**
     * Checks if the user has as the ability to drive the robot.
     */
    public static boolean fnCanDrive()
    {
        return bDrive;
    }
    
    /**
     * Gets the button status from the driverstation, 1 - 8 available.
     * @param iChan
     * @return 
     */
    public static boolean fnDriverGetDigitalIn(int iChan)
    {
        return DriverStation.getInstance().getDigitalIn(iChan);
    }
    
    public static void fnPutDashBoardStringBox(String sName, String sData)
    {
        SmartDashboard.putString(sName, sData);
    }
    
    public static void fnPutDashBoardNumberBox(String sName, double dDefaultVal)
    {
        SmartDashboard.putNumber(sName, dDefaultVal);
    }
    
    public static double fnGetDashBoardNumberBox(String sName)
    {
        return SmartDashboard.getNumber(sName);
    }
    
    public static void fnPutDashBoardButton(String sName, boolean bDefaultVal)
    {
        SmartDashboard.putBoolean(sName, bDefaultVal);
    }
    
    public static boolean fnGetDashBoardButton(String sName)
    {
        return SmartDashboard.getBoolean(sName);
    }
}
