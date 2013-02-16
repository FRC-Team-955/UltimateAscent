package core;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import utilities.Vars;
import utilities.MySolenoid;
import utilities.MyJoystick;
import edu.wpi.first.wpilibj.Talon;

/**
 * This class is responsible for controlling the shooter and feeder.
 * @author Fauzi
 */

// TODO: Find Velocity controller constants.
public class Shooter {
    
    // CONSTANTS
    private final double m_dSpeedIncrease = 500;
    
    private boolean m_bGoodToShoot = false;
	private double m_dTimePerShot = 1.3;
	private double m_dSpeedUpTime = 1.5;
	private double lastTime;
    private double m_dShootSpeed = Vars.shooterValue;
	public double rate = 0;
	public double rateRadians = 0;
	public double m_dTargetSpeed = 6000;//in RPM
    private double m_dPrevPulseTime = 0;
    private MySolenoid m_solFeeder = new MySolenoid(Vars.chnSolFeederDown, Vars.chnSolFeederUp, false);
    private Timer m_tmFeeder = new Timer();
    private Timer m_tmPulser = new Timer();
    private Talon m_mtShooter1 = new Talon(Vars.chnVicShooter1);
	 private Talon m_mtShooter2 = new Talon(Vars.chnVicShooter2);
    private Encoder m_encShooter = new Encoder(Vars.chnEncShooterA, Vars.chnEncShooterB,false, CounterBase.EncodingType.k4X);
    private MyJoystick m_joy;
	private boolean UseDifEqController = false;
	private double difEqOutput = 0;
	private utilities.DifEqControler difEq = new utilities.DifEqControler(0.1);
    
    public Shooter(MyJoystick joystick)
    {
        m_joy = joystick;
        m_tmPulser.start();
        m_encShooter.start();
		difEq.freeze();
    }
    
    public void run()
    {
		getRate();
		
		// When pressed switches auto shoot 
		if(m_joy.gotPressed(Vars.btAutoShoot))
			m_joy.flipSwitch(Vars.btAutoShoot);
	
		// If false, manual feed by driver
		if(!m_joy.getSwitch(Vars.btAutoShoot))
		{
			if(m_joy.gotPressed(Vars.btFeedFrisbee))
				m_joy.flipSwitch(Vars.btFeedFrisbee);

			m_solFeeder.set(m_joy.getSwitch(Vars.btFeedFrisbee));
		}
		
        // When pressed, starts velocity controller so shooter motor can turn on.
        if(m_joy.gotPressed(Vars.btShootFrisbee))
        {
            m_joy.flipSwitch(Vars.btShootFrisbee);
            m_tmFeeder.start();
        }

		if(!m_joy.getSwitch(Vars.btShootFrisbee))
		{
			m_tmFeeder.stop();
			m_tmFeeder.reset();
			difEq.freeze();
		}
		
        // Sets the shooter to the specified speed.
        if(m_joy.getSwitch(Vars.btShootFrisbee))
        {
			System.out.println("in swith true");
			difEq.unfreeze();
			
			if(UseDifEqController)
				setShooter(difEqOutput);
			else
				setShooter(m_dTargetSpeed*12.0/DriverStation.getInstance().getBatteryVoltage()/6200.0);
			
			if(m_joy.getSwitch(Vars.btAutoShoot))
			{
				if((m_tmFeeder.get() < 8 * m_dTimePerShot + m_dSpeedUpTime))
				{
					if((m_tmFeeder.get() >= m_dSpeedUpTime))
						m_solFeeder.set((((int) (m_tmFeeder.get()/(m_dTimePerShot) - m_dSpeedUpTime)) % 2) == 0 ? true : false);
				}

				else
				{
					m_joy.setSwitch(Vars.btShootFrisbee, false);
				}
			}
        }

		else 
			setShooter(0);
	    
		System.out.println("Enc:               " + rate);
        Vars.fnPrintToDriverstation(Vars.drCanFeed, "Can Feed: " + m_bGoodToShoot);
		System.out.println("Timer Int: " + (int)m_tmPulser.get() + "Divided by 2: " + (((int) m_tmPulser.get()) % 2));
    }
    
    /**
     * Returns the shooter status.
     * @return 
     */
    public double getShooterSpeed()
    {
        return rate;
    }
    
	/**
	 * Returns the current encoder rate.
	 * Updates every .10 seconds
	 * @return 
	 */
	public void getRate() {
		double count;
		double time = m_tmPulser.get();
		if((time - lastTime) >= 0.05){
			count = m_encShooter.getDistance();
			rate = 60 * (count/360.0) / (time-lastTime);
			rateRadians = 2 * Math.PI * rate / 60.0; 
			lastTime = time;
			m_encShooter.reset();
			if(UseDifEqController)
				difEqOutput = difEq.getOutput(rateRadians, time, 2 * Math.PI * m_dTargetSpeed / 60.0);
		}
	}
    
    /**
     * Returns the feeder status.
     * @return 
     */
    public boolean getFeedStatus()
    {
        return m_solFeeder.getStatus();//m_joy.getSwitch(Vars.btFeedFrisbee);
    }
    
    /**
     * Sets the shooter to the desired speed.
     * @param dSpeed 
     */
    public void setShooter(double dSpeed)
    {
        m_mtShooter1.set(dSpeed);
		m_mtShooter2.set(dSpeed);
    }
    
    /**
     * Sets the feeder based on the argument, true means feed one frisbee.
     * @param bStatus 
     */
    public void setFeeder(boolean bStatus)
    {
        m_solFeeder.set(bStatus);
    }
}


//package core;
//
//import edu.wpi.first.wpilibj.CounterBase;
//import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.Timer;
//import utilities.Vars;
//import utilities.MySolenoid;
//import utilities.MyJoystick;
//import edu.wpi.first.wpilibj.Talon;
//
///**
// * This class is responsible for controlling the shooter and feeder.
// * @author Fauzi
// */
//
//// TODO: Find Velocity controller constants.
//public class Shooter {
//    
//    // CONSTANTS
//    private final double m_dSpeedIncrease = 500;
//    
//    private boolean m_bGoodToShoot = false;
//	private boolean m_bShooterOn = false;
//    private int m_iFrisbeeShot = 0;
//	private double m_dTimePerShot = 1.3;
//	private double m_dSpeedUpTime = 1.5;
//	private double lastTime;
//    private double m_dShootSpeed = Vars.shooterValue;
//	public double rate = 0;
//	public double rateRadians = 0;
//	public double m_dTargetSpeed = 6000;//in RPM
//    private double m_dPrevPulseTime = 0;
//    private MySolenoid m_solFeeder = new MySolenoid(Vars.chnSolFeederDown, Vars.chnSolFeederUp, false);
//    private Timer m_tmFeeder = new Timer();
//    private Timer m_tmPulser = new Timer();
//    private Talon m_mtShooter1 = new Talon(Vars.chnVicShooter1);
//	 private Talon m_mtShooter2 = new Talon(Vars.chnVicShooter2);
//    private Encoder m_encShooter = new Encoder(Vars.chnEncShooterA, Vars.chnEncShooterB,false, CounterBase.EncodingType.k4X);
//    private MyJoystick m_joy;
//	private boolean UseDifEqController = false;
//	private double difEqOutput = 0;
//	private utilities.DifEqControler difEq = new utilities.DifEqControler(0.1);
//    
//    public Shooter(MyJoystick joystick)
//    {
//        m_joy = joystick;
//        m_tmPulser.start();
//        m_encShooter.start();
//		difEq.freeze();
//    }
//    
//    public void run()
//    {
////		if(m_joy.gotPressed(Vars.btFeedFrisbee))
////			m_joy.flipSwitch(Vars.btFeedFrisbee);
////		
////		m_solFeeder.set(m_joy.getSwitch(Vars.btFeedFrisbee));
//		
//		getRate();
//        // When pressed, starts velocity controller so shooter motor can turn on.
//        if(m_joy.gotPressed(Vars.btShootFrisbee))
//        {
//            m_joy.flipSwitch(Vars.btShootFrisbee);
//            m_tmFeeder.start();
//        }
//
//        // Sets the shooter to the specified speed.
//        if(m_joy.getSwitch(Vars.btShootFrisbee))
//        {
//			System.out.println("in swith true");
//            m_bShooterOn = true;
//			difEq.unfreeze();
//			if(UseDifEqController)
//				setShooter(difEqOutput);
//			else
//				setShooter(m_dTargetSpeed*12.0/DriverStation.getInstance().getBatteryVoltage()/6200.0);
//			
//			if((m_tmFeeder.get() < 8 * m_dTimePerShot + m_dSpeedUpTime)){
//				if((m_tmFeeder.get() >= m_dSpeedUpTime))
//					m_solFeeder.set((((int) (m_tmFeeder.get()/(m_dTimePerShot) - m_dSpeedUpTime)) % 2) == 0 ? true : false);
//			}
//			else{
//				m_joy.setSwitch(Vars.btShootFrisbee, false);
//				m_bShooterOn = false;
//                //m_PIDShooter.reset(true);
//                m_tmFeeder.stop();
//                m_tmFeeder.reset();
//				difEq.freeze();
//				//m_iFrisbeeShot = 0;
//			}
//			
//			
//            //System.out.println("Shooting NOW!!!");
////            if(Math.abs(m_dShootSpeed - rate) <= Vars.dShootTolerance)
////                m_bGoodToShoot = true;
////
////            else
////                m_bGoodToShoot = false;
//
//            // Feeds one frisbee to the shooter if shooter is at the right speed.
////            if(m_joy.gotPressed(Vars.btFeedFrisbee) && m_bGoodToShoot)
////                m_joy.flipSwitch(Vars.btFeedFrisbee);
////
////            if(m_joy.getSwitch(Vars.btFeedFrisbee))
////            {    
////                if(!m_solFeeder.getStatus() && m_bGoodToShoot)
////                {
////                    m_tmFeeder.start();
////                    m_solFeeder.turnOn();
////                }
////
////                if(m_tmFeeder.get() >= Vars.dMinFeedTime)
////                {
////                    m_solFeeder.turnOff();
////                    m_tmFeeder.stop();
////                    m_tmFeeder.reset();
////                    m_iFrisbeeShot++;
////                    m_bGoodToShoot = false;
////                }
////
////                if(m_iFrisbeeShot >= Vars.iMaxFrisbee)
////                {
////                    m_iFrisbeeShot = 0;
////                    m_joy.setSwitch(Vars.btFeedFrisbee, false);
////                }
////            }
//        }
//
//		else 
//			setShooter(0);
//		
//        // Turns on/off the feeder
////        if (m_joy.gotPressed(Vars.btFeedFrisbee))
////                    m_joy.flipSwitch(Vars.btFeedFrisbee);
//			
////		if(m_iFrisbeeShot < Vars.iMaxFrisbee);
////		{
////			m_solFeeder.set(m_tmPulser.get() % 2 == 0 ? false : true);
////			m_iFrisbeeShot++;
////		}
//            
//        // If we're not shooting, shooter should be zero.
////        else if(!m_joy.getSwitch(Vars.btShootFrisbee))
////        {   
////            setShooter(0);
////            
////		if (!Vars.oneSpot)
////                {
////			// Sets the shooter speed when the increase or decrease button is pressed
////			if(m_joy.gotPressed(Vars.btIncreaseSpeed))
////				m_dShootSpeed += m_dSpeedIncrease;
////
////			if(m_joy.gotPressed(Vars.btDecreaseSpeed) && (m_dShootSpeed - m_dSpeedIncrease) > 0)
////				m_dShootSpeed -= m_dSpeedIncrease;
////		}
////        }
//        
//		System.out.println("Enc:               " + rate);
//        Vars.fnPrintToDriverstation(Vars.drCanFeed, "Can Feed: " + m_bGoodToShoot);
//		System.out.println("Timer Int: " + (int)m_tmPulser.get() + "Divided by 2: " + (((int) m_tmPulser.get()) % 2));
//    }
//    
//    /**
//     * Returns the shooter status.
//     * @return 
//     */
//    public double getShooterSpeed()
//    {
//        return rate;
//    }
//    
//	/**
//	 * Returns the current encoder rate.
//	 * Updates every .10 seconds
//	 * @return 
//	 */
//	public void getRate() {
//		double count;
//		double time = m_tmPulser.get();
//		if((time - lastTime) >= 0.05){
//			count = m_encShooter.getDistance();
//			rate = 60 * (count/360.0) / (time-lastTime);
//			rateRadians = 2 * Math.PI * rate / 60.0; 
//			lastTime = time;
//			m_encShooter.reset();
//			if(UseDifEqController)
//				difEqOutput = difEq.getOutput(rateRadians, time, 2 * Math.PI * m_dTargetSpeed / 60.0);
//		}
//	}
//    
//    /**
//     * Returns the feeder status.
//     * @return 
//     */
//    public boolean getFeedStatus()
//    {
//        return m_solFeeder.getStatus();//m_joy.getSwitch(Vars.btFeedFrisbee);
//    }
//    
//    /**
//     * Sets the shooter to the desired speed.
//     * @param dSpeed 
//     */
//    public void setShooter(double dSpeed)
//    {
//        m_mtShooter1.set(dSpeed);
//		m_mtShooter2.set(dSpeed);
//    }
//    
//    /**
//     * Sets the feeder based on the argument, true means feed one frisbee.
//     * @param bStatus 
//     */
//    public void setFeeder(boolean bStatus)
//    {
//        m_solFeeder.set(bStatus);
//    }
//	public void print() {
//		System.out.println(rate);
//	}
////	public void varaibleSet() {
////		getRate();
////		m_joy.setAxisChannel(MyJoystick.AxisType.kX, 3);
////        m_joy.setAxisChannel(MyJoystick.AxisType.kY, 2);
////		setShooter(-m_joy.getY());
////	}
//}