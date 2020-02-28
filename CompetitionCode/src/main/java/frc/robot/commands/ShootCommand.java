/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Storage;

/**
 * COMPETITION READY
 */
public class ShootCommand extends CommandBase {

  private Shooter shooter;
  private Storage storage;
  private double targetRPM;

  private double startTime;

  /**
   * Creates a new ShootCommand.
   */
  public ShootCommand(Shooter shooter, Storage storage, int targetRPM) {
    this.shooter = shooter;
    this.storage = storage;
    this.targetRPM = targetRPM;
    addRequirements(shooter, storage);

    this.startTime = System.currentTimeMillis();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.setSpeedWithRPM(targetRPM); // Sets target RPM (must be called each frame to update)

    // Output stats
    SmartDashboard.putNumber("Target RPM", targetRPM);
    SmartDashboard.putNumber("Current RPM", shooter.getMotorSpeed());

    // After 3 seconds, begin shooting.
    // This is because generally it takes 3 seconds to be safe to shoot.
    if (System.currentTimeMillis() - startTime > Constants.SHOOTER_REV_TIME) {
      storage.setFeedSpeed();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    storage.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
