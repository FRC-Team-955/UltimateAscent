package core;

import utilities.Vars;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author RaiderPC
 */
public class Compressor {
	
    private DigitalInput m_digInSensor = new DigitalInput(Vars.chnDigiInSensor);
    private Relay m_digOutCompressor = new Relay(Vars.chnDigiOutCompressor);
	
    public void run()
    {
        if(m_digInSensor.get())
            m_digOutCompressor.set(Relay.Value.kForward);

        else
            m_digOutCompressor.set(Relay.Value.kOff);
    }
}
