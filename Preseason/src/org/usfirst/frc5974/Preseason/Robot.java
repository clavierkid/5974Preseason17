
// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc5974.Preseason;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc5974.Preseason.commands.*;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.analog.adis16448.frc.ADIS16448_IMU; //navigation
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Joystick masterRemote;
	Timer Time = new Timer();
	CameraServer camera;
	RobotDrive robotdrive;
	Spark lBack = new Spark(1);
	Spark lFront = new Spark(3);
	Spark rBack = new Spark(2);
	Spark rFront = new Spark(0);
	Spark clamp = new Spark(4);
	
	// Stolen code from last year, reformatting if possible?
	//xBox mapping of controllers
	double AxisControlLeftX = 0;
	double AxisControlLeftY = 0;
	double AxisControlRightX = 0;
	double AxisControlRightY = 0;
	double TriggerLeft;
	double TriggerRight;
	boolean ButtonA;
	boolean APressed;
	boolean ButtonB;
	boolean ButtonX;
	boolean ButtonY;
	boolean ButtonStart;
	boolean ButtonBack;
	boolean BumperLeft;
	boolean BumperRight;
	boolean JoyButtonLeft;
	boolean JoyButtonRight;
	boolean dPadd;
	boolean dPadu;
	boolean dPadl;
	boolean dPadr;
	//The IMU/10 degrees of freedom
	
	/*
	 * double HeadingX;
	 * double HeadingY;
	 * double HeadingZ;
	 * 
	 * Meant to measure the distance to magnetic north. With a gyroscope, apparently.
	 * 
	 * This is entirely useless, why code it in the first place? -JavaGreenhorn.
	 */
	
	/*
	 * double AccelX; 	Gyroscope value for X-Axis Rotation
	 * double AccelY; 	Gyroscope value for Y-Axis Rotation
	 * double AccelZ; 	Gyroscope value for Z-Axis Rotation
	 * 
	 * Meant to gauge acceleration, with a gyroscope we don't have.
	 */
	
	/*
	 * double RateX;
	 * double RateY;
	 * double RateZ;
	 * 
	 * Measures the R.P.M. of each axis, with a gyroscope we don't have.
	 */
	
	/*
	 * double Altitude; 
	 * double Temp;
	 * 
	 * Meant to measure motor temp, roborio specific. Might not be needed.
	 */
	
	/*
	 *double Pitch; 	Gyroscope value for X-Axis Rotation
	 *double Yaw;		Gyroscope value for Y-Axis Rotation
	 *double Roll;		Gyroscope value for Z-Axis Rotation
	 *
	 * We don't have a gyroscope. If we install one, Aiming/stabilization, perhaps?
	 */
	
	//double Angle; *Completely Unused.*
	
	/*
	 * double AngleX;
	 * double AngleY;
	 * double AngleZ;
	 * 
	 * Meant to measure the rotation value of the gyroscope, which we don't have.
	 */
	
	//double distZ; *Distance measurement, meant to be used without tele-op.*
	

	int processStep; //a step of a program
	boolean toggleDriveMode = true; 
	boolean toggleAscenderMode = true;
	double driveSpeed = .5;
	double velX = 0;
	double velZ = 0;
	double velY = 0;
	double distX;
	double distY;
	double startPosition;
	boolean XPressed = false;
	boolean JoyRightToggle = false;
	
	public void updateAll(){
		
		updateController();
		//updateSensors();
	}
	
	public void updateController(){
		//Got button values from control panel, mostly guessed on axes from RoboPong code
		updateAxis();
		updateTrigger();
		updateButton();
		updateBumper();
		updateJoy();
		deadZones();
	}
	
	public void deadZones() { //The Axis are too accurate and thus need to be cut off
		if(Math.abs(AxisControlLeftY) <= 0.1) {
			AxisControlLeftY = 0;
		}
		if (Math.abs(AxisControlRightY) <= 0.1) {
			AxisControlRightY = 0;
		}
		if(Math.abs(AxisControlLeftX) <= 0.1) {
			AxisControlLeftX = 0;
		}
		if(Math.abs(AxisControlRightX) <= 0.1) {
			AxisControlRightX = 0;
		}
		if(Math.abs(TriggerLeft) <= 0.1) {
			TriggerLeft = 0;
		}
		if(Math.abs(TriggerRight) <= 0.1) {
			TriggerRight = 0;
		}
	}
	
	public void updateTrigger(){ //Updates the Axis on the triggers
		TriggerLeft = masterRemote.getRawAxis(2);
		TriggerRight = masterRemote.getRawAxis(3);
	}
	
	public void updateButton(){ //Updates button values
		ButtonA = masterRemote.getRawButton(1);
		ButtonB = masterRemote.getRawButton(2);
		ButtonX = masterRemote.getRawButton(3);
		ButtonY = masterRemote.getRawButton(4);
		ButtonStart = masterRemote.getRawButton(8);
		ButtonBack = masterRemote.getRawButton(7);
		dPadd = masterRemote.getRawButton(13);
		dPadu = masterRemote.getRawButton(12);
		dPadl = masterRemote.getRawButton(14);
		dPadr = masterRemote.getRawButton(15);
		
	}
	
	public void updateBumper(){ //Updates the Bumper values
		BumperLeft = masterRemote.getRawButton(5);
		BumperRight = masterRemote.getRawButton(6);
	}
	
	public void updateJoy(){ //Updates the joystick buttons
		JoyButtonLeft = masterRemote.getRawButton(9);
		JoyButtonRight = masterRemote.getRawButton(10);
	}
	
	public void updateAxis(){ //Updates the axes on the joysticks
		AxisControlLeftY = masterRemote.getRawAxis(1);
		AxisControlRightY = masterRemote.getRawAxis(5);
		AxisControlLeftX = masterRemote.getRawAxis(0);
		AxisControlRightX = masterRemote.getRawAxis(4);
	}
	
	/*public void updateAccel(){ //Updates the values for Acceleration
		AccelX = Navigation.getAccelX();
		AccelY = Navigation.getAccelY();
		AccelZ = Navigation.getAccelZ();
		SmartDashboard.putNumber("Accel X", AccelX);
		SmartDashboard.putNumber("Accel Y", AccelY);
		SmartDashboard.putNumber("Accel Z", AccelZ);
	}
	
	public void updateRate(){ //Updates the rate at which one of these values are moving
		RateX = Navigation.getRateX();
		RateY = Navigation.getRateY();
		RateZ = Navigation.getRateZ();
		SmartDashboard.putNumber("Rate X", RateX);
		SmartDashboard.putNumber("Rate Y", RateY);
		SmartDashboard.putNumber("Rate Z", RateZ);
	}
	
	public void updateRotations(){ //Updates the Yaw, Pitch, and Roll values
		Pitch = Math.floor(Navigation.getPitch());
		Yaw = Math.floor(Navigation.getYaw());
		Roll = Math.floor(Navigation.getRoll());
		SmartDashboard.putNumber("Pitch", Pitch);
		SmartDashboard.putNumber("Yaw", Yaw);
		SmartDashboard.putNumber("Roll", Roll);
	}
	
	public void updateAngles(){ //Gets the angle of the gyroscope
		AngleX = Navigation.getAngleX();
		AngleY = Navigation.getAngleY();
		AngleZ = Navigation.getAngleZ();
		SmartDashboard.putNumber("Angle X", AngleX);
		SmartDashboard.putNumber("Angle Y", AngleY);
		SmartDashboard.putNumber("Angle Z", AngleZ);
	}
	
	public void updateHeading(){ //Updates the "How far away are we from magnetic north?" values
		HeadingX = Navigation.getMagX();
		HeadingY = Navigation.getMagY();
		HeadingZ = Navigation.getMagZ();
		SmartDashboard.putNumber("Heading X", HeadingX);
		SmartDashboard.putNumber("Heading Y", HeadingY);
		SmartDashboard.putNumber("Heading Z", HeadingZ);
	}
	
	public void updateSensors(){ //Updates the sensor values
		updateHeading();
		updateAccel();
		updateRate();
		updateRotations();
		updateAngles();
		updateWeather();
	}*/
	
	
    Command autonomousCommand;

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
		masterRemote = new Joystick(0);
        oi = new OI();

        // instantiate the command used for the autonomous period
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        autonomousCommand = new AutonomousCommand();

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        
        updateAll();
        
        masterRemote.setRumble(Joystick.RumbleType.kRightRumble, 0.5);
    	masterRemote.setRumble(Joystick.RumbleType.kLeftRumble, 0.5);
    	Timer.delay(1);
		masterRemote.setRumble(Joystick.RumbleType.kRightRumble, 0);
		masterRemote.setRumble(Joystick.RumbleType.kLeftRumble, 0);
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	updateAll();
    	//Drive speed switcher
    	
    	if (dPadl){
    		masterRemote.setRumble(Joystick.RumbleType.kRightRumble, 0.5);
        	masterRemote.setRumble(Joystick.RumbleType.kLeftRumble, 0.5);
        	Timer.delay(1);
    		masterRemote.setRumble(Joystick.RumbleType.kRightRumble, 0);
    		masterRemote.setRumble(Joystick.RumbleType.kLeftRumble, 0);
    		driveSpeed = 1;
    	}
    	if (dPadr){
    		driveSpeed = .5;
    	}
    	
    	//drive left, but it's inverted so multiply by -1
    	lFront.set((driveSpeed) * (AxisControlLeftY - AxisControlLeftX));
		lBack.set((driveSpeed) * (AxisControlLeftY - AxisControlLeftX));
		rFront.set((-1 * driveSpeed) * (AxisControlRightY + AxisControlRightX));
		rBack.set((-1 * driveSpeed) * (AxisControlRightY + AxisControlRightX));
		
    	lFront.set(AxisControlLeftY);
    	
    	clamp.set(TriggerLeft);
    	
    	
        Scheduler.getInstance().run();
        
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
