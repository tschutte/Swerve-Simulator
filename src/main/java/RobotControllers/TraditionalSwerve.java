package RobotControllers;

import interfaces.JoysticksInterface;
import interfaces.RobotController;
import interfaces.RobotInterface;
import interfaces.SwerveWheelInterface;

import java.util.List;

public class TraditionalSwerve implements RobotController {
    @Override // this tells Java that the `loop` method implements the `loop` method specified in `RobotController`
    public void loop(JoysticksInterface joysticks, RobotInterface robot) {
        double leftVelocity = joysticks.getLeftStick().y * 5.0; // get the velocity of the left half of the robot
        double rightVelocity = joysticks.getRightStick().y * 5.0; // get the velocity of the right half of the robot

        double angle = Math.atan2(joysticks.getLeftStick().y, joysticks.getLeftStick().x) - (Math.PI/2);
        System.out.println("radians: " + angle + " degrees: " + Math.toDegrees(angle));

        double magnitude = Math.sqrt(Math.pow(joysticks.getLeftStick().x, 2) + Math.pow(joysticks.getLeftStick().y, 2));

        //System.out.println("degrees: " + Math.toDegrees(angle) + " magnitude: " + magnitude);
        // this is a list of the swerve modules that are on the robot
        List<SwerveWheelInterface> drivetrain = robot.getDrivetrain();

        for(int i = 0; i < drivetrain.size(); i++) { // loop through each swerve module
            SwerveWheelInterface wheel = drivetrain.get(i); // the swerve module we are looking at

            wheel.setWheelAngle(angle); // make sure the wheels are always facing forward (none of this funny swerve business)

            wheel.setWheelVelocity((magnitude * 5.0));
        }
    }
}
