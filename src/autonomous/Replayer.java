package autonomous;

import utilities.BotData;
import utilities.FileReader;
import utilities.Robot;
import utilities.Vars;
import edu.wpi.first.wpilibj.Timer;

/**
 * This class reads data from the specified file from the cRio and then 
 * "replays" it.
 * @author fauzi
 */
class Replayer {
  
    // CONSTANTS
    private final double m_dTimeTolerance = .01;
    
    private int m_iMax = 0;
    private int m_iCounter = 0;
    private boolean m_bRepStarted = false;
    private boolean m_bDoneReplay = false;
	private boolean m_bVerboseMode = true;
    private String m_sFileName = "";
    private Timer m_tmReplay = new Timer();
    private BotData m_botDataAuto = null;
    private BotData[] m_botDataArray = null;
    private FileReader m_fileReader = null;
    private Robot m_bot = null;
    
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
			if(m_bVerboseMode)
				System.out.println("Replaying!!!");
				
            Vars.fnDisableDrive();
            m_sFileName = sFileName;
            readAllData();
            m_botDataAuto = m_botDataArray[m_iCounter++];
            m_tmReplay.start();
            m_bRepStarted = true;
        }

        if(!m_bDoneReplay)
        {                    
            if(getNewData())
                m_botDataAuto.setValues(m_botDataArray[m_iCounter++]);
            
            m_bot.setDriveSpeed(m_botDataAuto.getMotorLeftSpeed(), m_botDataAuto.getMotorRightSpeed());
            m_bot.setShooter(m_botDataAuto.getShooterSpeed());
            m_bot.setFeeder(m_botDataAuto.getFeederStatus());
            
			if(m_bVerboseMode)
				System.out.println("Left: " + m_botDataAuto.getMotorLeftSpeed() + 
						" - Right: " + m_botDataAuto.getMotorRightSpeed() + 
						" - Shooter: " + m_botDataAuto.getShooterSpeed() + 
						" - Feed: " + m_botDataAuto.getFeederStatus()); 
						
			if(EndOfFile())
                m_bDoneReplay = true;
        }

        else
        {
            m_bot.stopRobot();
            m_tmReplay.stop();
            m_tmReplay.reset();
			
			if(m_bVerboseMode)
				System.out.println("Replay Ended");
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
            m_sFileName = "";
            m_botDataAuto = null;
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
        {
            m_botDataArray[index] = new BotData();
            m_botDataArray[index].setValues(readData());
        }
        
        m_fileReader.close();
    }

    /**
     * Determines whether we should update our data.
     * @return 
     */
    private boolean getNewData()
    {
        if(m_tmReplay.get() >= m_botDataAuto.getTimer())
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
    
    /**
     * Reads one set of data and returns a BotData containing it.
     * @return 
     */
    private BotData readData()
    {
        BotData tempBotData = new BotData();
        
        tempBotData.setValues(m_fileReader.readDouble(), m_fileReader.readDouble(), m_fileReader.readDouble(), m_fileReader.readDouble(), m_fileReader.readBoolean());
        return tempBotData;
    }
}