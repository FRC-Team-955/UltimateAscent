package utilities;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;

/**
 * This class has ALL the variables used as channels for FRC objects like 
 * Victors or solenoids and ect. It ONLY has channel variables and useful 
 * functions. We have a separate class just so we can change the channel more 
 * easily rather than searching through the class itself.
 * @author Fauzi
 */
// TODO: SET SHOOTER ENCODER CHANNEL, PID STUFF TOO!
public class Vars {
    
/******************************************************************************/
    // Victors
    public static final int chnVicDrvLeft1 = 4;
	public static final int chnVicDrvLeft2= 5;
	public static final int chnVicDrvLeft3 = 6;
    public static final int chnVicDrvRight1 = 1;
	public static final int chnVicDrvRight2 = 2;
	public static final int chnVicDrvRight3 = 3;
    public static final int chnVicShooter1 = 7;
	public static final int chnVicShooter2 = 8;

	
/******************************************************************************/
    // Solenoids
    // SOLENOID BUMPER CHANNEL 7 OR 8 DOES NOT WORK!!!!!!
    public static final int chnSolFeederUp = 1;
    public static final int chnSolFeederDown = 2;
	
/******************************************************************************/
    // Digital Sidecard 
    public static final int chnEncShooterA = 1;
    public static final int chnEncShooterB = 2;
    public static final int chnEncMotorLeftA = 4;
    public static final int chnEncMotorLeftB = 5;
    public static final int chnEncMotorRightA = 6;
    public static final int chnEncMotorRightB = 7;
    public static final int chnDigiOutCompressor = 3;
    public static final int chnDigiInSensor = 8;

/******************************************************************************/
    // Button channel on the joystick
    public static final int btIncreaseSpeed = 1;
    public static final int btFeedFrisbee = 2;
    public static final int btDecreaseSpeed = 3;
    public static final int btShootFrisbee = 4;
    public static final int btRecord = 9;
    public static final int btAllowEdit = 10;
    public static final int btReplay = 12;
    
/******************************************************************************/
    // DriverStation Autonomous Button Channels
    public static final int stDigInAutoCtr = 1;
    public static final int stDigInAutoLft = 2;
    public static final int stDigInAutoRght = 3;
    public static final int stDigInReg = 4;
    
/******************************************************************************/
    // Dashboard keys
    public static final int drFile = 2;
    public static final int drAutonomous = 3;
    public static final int drShooterSpeed = 4;
    public static final int drCanFeed = 5;
	public static final int drShooterOn = 6;
    
/******************************************************************************/
    // Ints
    public static final int iPs3Port = 1;
    public static final int iPs3Buttons = 13;
    public static final int iMaxFrisbee = 3;
    
/******************************************************************************/
    // Doubles
    public static final double dMinFeedTime = 1.0;
    public static final double dShootTolerance = 10;
    public static final double dDriveTolerance = .1;
    public static final double kDriveP = 1;
    public static final double kDriveI = 1;
    public static final double kDriveD = 1;
    public static final double kShooterP = 1;
    public static final double kShooterI = 1;
    public static final double kShooterD = 1; 
	public static final double shooterValue =  10;
    
/******************************************************************************/
    // Booloeans
    private static boolean bDrive = true;
    public static boolean oneSpot = false;
    
/******************************************************************************/
    // Functions
    
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
     * Checks if the user has the ability to drive the robot.
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
    
     /**
     * Prints specified message to the driver station on the corresponding line
     * 2-6 are available.
     */
    public static void fnPrintToDriverstation(int iLine, String sMessage)
    {
        switch(iLine)
        {
            case 2:
            {
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "                   ");
                DriverStationLCD.getInstance().updateLCD(); 
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, sMessage);
                break;
            }
                
            case 3:
            {
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "                   ");
                DriverStationLCD.getInstance().updateLCD(); 
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, sMessage);
                break;
            }
                
            case 4:
            {
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 1, "                   ");
                DriverStationLCD.getInstance().updateLCD(); 
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 1, sMessage);
                break;
            }
                
            case 5:
            {
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, "                   ");
                DriverStationLCD.getInstance().updateLCD(); 
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser5, 1, sMessage);
                break;
            }
                
            case 6:
            {
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, "                   ");
                DriverStationLCD.getInstance().updateLCD(); 
                DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6, 1, sMessage);
                break;
            }
        }
        
        DriverStationLCD.getInstance().updateLCD(); 
    }
}