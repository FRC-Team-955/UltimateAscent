///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package autonomous;
//
//import edu.wpi.first.wpilibj.Timer;
//import utilities.Robot;
//import utilities.Vars;
//
///**
// *
// * @author RaiderPC
// */
//public class OldStyleAuto {
//	
//	private Robot m_bot;
//	private Timer m_tmAuto = new Timer();
//	private int m_iShootSpeed = 1;
//	private double m_iFeedTime = 3;
//	private boolean m_bFirstTime = true;
//	
//	public OldStyleAuto(Robot bot)
//	{
//		m_bot = bot;
//	}
//	
//	public void run()
//	{
//		if(m_bFirstTime)
//		{
//			m_tmAuto.start();
//			m_bot.setShooter(m_iShootSpeed);
//			m_bot.setDriveSpeed(.25, .25);
//			m_bFirstTime = false;
//		}
//		
//		if(m_tmAuto.get() > 2)
//			m_bot.setDriveSpeed(0, 0);
//
//		if(m_tmAuto.get() < 2)
//			m_bot.setFeeder(false);
//		
//		else if(m_tmAuto.get() > 2 && m_tmAuto.get() < 4)
//			m_bot.setFeeder(true);
//		
//		else if(m_tmAuto.get() > 4 && m_tmAuto.get() < 6)
//			m_bot.setFeeder(false);
//		
//		else if(m_tmAuto.get() > 6 && m_tmAuto.get() < 8)
//			m_bot.setFeeder(true);
//		
//		else if(m_tmAuto.get() > 8 && m_tmAuto.get() < 10)
//			m_bot.setFeeder(false);
//		
//		else if(m_tmAuto.get() > 10 && m_tmAuto.get() < 12)
//			m_bot.setFeeder(true);
//		
//		else 
//		{
//			m_bot.setFeeder(false);
//			m_bot.setShooter(0);
//		}
//	}
//	
////	public void run()
////	{
////		if(m_bFirstTime)
////		{
////			m_tmAuto.start();
////			m_bot.setShooter(m_iShootSpeed);
////			m_bFirstTime = false;
////		}
////		
////		if(m_tmAuto.get() < m_iFeedTime * 1)
////			m_bot.setFeeder(false);
////		
////		else if(m_tmAuto.get() > m_iFeedTime * 1 && m_tmAuto.get() < m_iFeedTime * 2)
////			m_bot.setFeeder(true);
////		
////		else if(m_tmAuto.get() > m_iFeedTime * 2 && m_tmAuto.get() < m_iFeedTime * 3)
////			m_bot.setFeeder(false);
////		
////		else if(m_tmAuto.get() > m_iFeedTime * 3 && m_tmAuto.get)
////	}
//	
//}
