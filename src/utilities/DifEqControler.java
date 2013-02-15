/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 * @author RaiderPC
 */
public class DifEqControler {
//	boolean freeze = false;
//	double lastSpeed = 0;
//	double e = 2.71828;
//	double timeInFuture;
//	double lastOutput = 0;
//	public DifEqControler(double timeInFuture_){
//		timeInFuture = timeInFuture_;
//	}
//
//	public void freeze(){
//		freeze = true;
//	}
//
//	public void unfreeze(){
//		freeze = false;
//	}
//
//	private double avg(double a, double b){
//		return (a+ b )/ 2.0;
//	}
//
//	public double getOutput(double newlyCalculatedSpeed, double time,double targetSpeed){
//		if(lastOutput < 0.001){
//			lastOutput = DriverStation.getInstance().getBatteryVoltage() * targetSpeed / (12.0*6200); 
//		}
//		if(!freeze){
//			double c1 = (newlyCalculatedSpeed - avg(newlyCalculatedSpeed, lastSpeed)) / (Math.pow(e,(-0.004212*(time / 2.0)/(0.00185)))  -  1);
//			double futureSpeed = c1 * Math.(e,(0.004212*(time+(timeInFuture))/0.00185)) - c1 + avg(newlyCalculatedSpeed, lastSpeed);
//			lastOutput = lastOutput * targetSpeed / futureSpeed;
//		}
//		lastSpeed = newlyCalculatedSpeed;
//		return lastOutput;
//	}
}
