// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  //Define Objects
  private DifferentialDrive m_robotDrive;
  private Joystick m_DriveStick;
  private WPI_TalonSRX FRDrive;
  private WPI_TalonSRX FLDrive;
  private WPI_TalonSRX BRDrive;
  private WPI_TalonSRX BLDrive;

  private Joystick m_SpecialsStick;
  private WPI_TalonSRX intake;
  private WPI_TalonSRX clamp;
  private WPI_TalonSRX boomAngle;
  private WPI_TalonSRX boomSlide;

  private Timer m_timer;

  @Override
  public void robotInit() {
    //Drive Definitions
    FRDrive = new WPI_TalonSRX(10);
    FLDrive = new WPI_TalonSRX(11);
    BRDrive = new WPI_TalonSRX(12);
    BLDrive = new WPI_TalonSRX(13);

    m_DriveStick = new Joystick(0);

    BRDrive.follow(FRDrive);
    BLDrive.follow(FLDrive);

    m_robotDrive = new DifferentialDrive(FLDrive, FRDrive);


    //Specials definitions
    m_SpecialsStick = new Joystick(1);

    intake = new WPI_TalonSRX(21);
    clamp = new WPI_TalonSRX(21);
    boomAngle = new WPI_TalonSRX(21);
    boomSlide = new WPI_TalonSRX(21);

  }


  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer = new Timer();
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 5.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }


  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    m_robotDrive.arcadeDrive(-m_DriveStick.getY(), -m_DriveStick.getZ());


    if(m_SpecialsStick.getRawButton(1)){
      intake.set(0.5);
    }else if(m_SpecialsStick.getRawButton(2)){
      intake.set(-0.5);
    }else{
      intake.set(0);
    }

    double slidePower;

    if(Math.abs(m_SpecialsStick.getY()) < 0.2){
      slidePower = 0;
    }else{
      slidePower = m_SpecialsStick.getY()*0.5;
    }
    boomSlide.set(slidePower);

  }
}
