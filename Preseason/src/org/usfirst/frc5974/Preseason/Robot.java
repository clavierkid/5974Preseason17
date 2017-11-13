
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
	
	// Stolen code from last year, reformatting if possible?
	//xBox mapping of controllers
	double AxisControlLeftX;
	double AxisControlLeftY;
	double AxisControlRightX;
	double AxisControlRightY;
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
	//The IMU/10 degrees of freedom
	double HeadingX;
	double HeadingY;
	double HeadingZ;
	double AccelX;
	double AccelY;
	double AccelZ;
	double RateX;
	double RateY;
	double RateZ;
	double Altitude;
	double Pitch;
	double Yaw;
	double Roll;
	double Angle;
	double AngleX;
	double AngleY;
	double AngleZ;
	double distZ;
	double Temp;

	int processStep; //a step of a program
	boolean toggleDriveMode = true; 
	boolean toggleAscenderMode = true;
	boolean fastMode = false;
	double driveSpeed;
	double velX = 0;
	double velZ = 0;
	double velY = 0;
	double distX;
	double distY;
	double startPosition;
	boolean XPressed = false;
	boolean JoyRightToggle = false;
	
	void updateAll(){
		updateController();
		updateSensors();
		updateMotors();
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
	
	public void UpdateMotors() {
		motorLeft.set((-1 * driveSpeed) * AxisControlLeftY);
		motorOtherLeft.set((-1 * driveSpeed) * AxisControlLeftY);
		motorRight.set(driveSpeed * AxisControlRightY);
		motorOtherRight.set(driveSpeed * AxisControlRightY);
	}
	
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
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
