package autonomous;

import utilities.BotData;
import utilities.FileReader;
import utilities.Robot;
import utilities.Vars;
import utilities.MyPIDController;
import edu.wpi.first.wpilibj.Timer;

/**
 * This class reads data from the specified file from the cRio and then 
 * "replays" it.
 * @author fauzi
 */
// TODO: Set kp, ki, kd and tolerance variables
class Replayer {
    
    //Constants
    private final double m_KP = 1;
    private final double m_kI = 1;
    private final double m_kD = 1; 
    private final double m_dGyroTolerance = 2.0;
    private final double m_dTimeTolerance = .1;
    
    private int m_iMax = 0;
    private int m_iCounter = 0;
    private boolean m_bRepStarted = false;
    private boolean m_bDoneReplay = false;
    private String m_sFileName = "";
    private MyPIDController m_PIDLeft = new MyPIDController(m_KP, m_kI, m_kD);
    private MyPIDController m_PIDRight = new MyPIDController(m_KP, m_kI, m_kD);
    private Timer m_tmReplay = new Timer();
    private BotData m_botDataAuto = new BotData();
    private BotData[] m_botDataArray;
    private FileReader m_fileReader;
    private Robot m_bot;
    
    public Replayer(Robot robot)
    {
        m_bot = robot;
    }
    
    /**
     * Replays data from desired file.
     * @param sFileName
     */
    public void replay(String sFileName)
    {        
        if(!m_bRepStarted)
        {
            Vars.fnDisableDrive();
            m_sFileName = sFileName;
            m_bot.resetGyros();
            readAllData();
            m_botDataAuto.setValues(m_botDataArray[m_iCounter++]);
            m_PIDLeft.startTimer();
            m_PIDRight.startTimer();
            m_tmReplay.start();
            m_bRepStarted = true;
        }

        if(!m_bDoneReplay)
        {                    
            if(getNewData())
            {
                m_botDataAuto.setValues(m_botDataArray[m_iCounter++]);
                m_PIDLeft.reset(false);
                m_PIDRight.reset(false);
            }
            
            double dMotorSpeedLeft = m_PIDLeft.getOutput(m_botDataAuto.getMtLeft(), m_bot.getGyroLeft());
            double dMotorSpeedRight = m_PIDRight.getOutput(m_botDataAuto.getMtRight(), m_bot.getGyroRight());
            m_bot.setSpeed(dMotorSpeedLeft, dMotorSpeedRight);
            m_bot.setRetrieve(m_botDataAuto.getRetrieve());
            
            if(EndOfFile())
                m_bDoneReplay = true;
        }

        else
        {
            m_bot.stopRobot();
            m_tmReplay.stop();
            m_tmReplay.reset();
        }
    }
    
    /**
     * Resets the replayer so it can properly replay next time.
     */
    public void reset()
    {
        if(m_bRepStarted)
        {
            Vars.fnEnableDrive();
            m_tmReplay.stop();
            m_tmReplay.reset();
            m_PIDLeft.reset(true);
            m_PIDRight.reset(true);
            m_botDataArray = null;
            m_iCounter = 0;
            m_iMax = 0;
            m_bDoneReplay = false;
            m_bRepStarted = false;
        }
    }
    
    /**
     * Gets the value of the recording, as in how long it has been replaying.
     * Returns -1 when done, or if it's not replaying
     * @return 
     */
    public double getReplayTime()
    {      
        if(m_tmReplay.get() < m_dTimeTolerance)
            return -1;
        
        return Vars.fnSetPrecision(m_tmReplay.get());
    }
    
    /**
     * Stops replay from replaying.
     */
    public void stop()
    {
        m_bRepStarted = true;
        m_bDoneReplay = true;
    }
    
    /**
     * Reads all the data from the file at once and stores them into an array
     * that we can access.
     */
    private void readAllData()
    {
        m_fileReader = new FileReader(m_sFileName);
        m_iMax = m_fileReader.readInt();
        m_botDataArray = new BotData[m_iMax];
        
        for(int index = 0; index < m_iMax; index++)
            m_botDataArray[index].setValues(m_fileReader.readAll());
        
        m_fileReader.close();
    }

    /**
     * Determines whether we should update our data.
     * @return 
     */
    private boolean getNewData()
    {
        if(Math.abs(Math.abs(m_botDataAuto.getMtLeft()) - Math.abs(m_bot.getGyroLeft())) <= m_dGyroTolerance && 
            Math.abs(m_botDataAuto.getMtRight() - Math.abs(m_bot.getGyroRight())) <= m_dGyroTolerance)
                return true;
        
        return false;
    }
    
    /**
     * Determines whether we're at the end of the file.
     * @return 
     */
    private boolean EndOfFile()
    {
        if(m_iCounter > 0 && m_iCounter < m_iMax)
            return false;
        
        return true;
    }
}