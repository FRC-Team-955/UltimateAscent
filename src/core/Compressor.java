package core;

import utilities.Vars;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 *
 * @author RaiderPC
 */
public class Compressor {
	
    private DigitalInput m_digInSensor = new DigitalInput(Vars.chnDigiInSensor);
    private DigitalOutput m_digOutCompressor = new DigitalOutput(Vars.chnDigiOutCompressor);
	
    public void run()
    {
        if(m_digInSensor.get())
            m_digOutCompressor.set(true);

        else
            m_digOutCompressor.set(false);
    }
}
