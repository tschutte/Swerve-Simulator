package RobotControllers;

import interfaces.JoysticksInterface;
import interfaces.RobotController;
import interfaces.RobotInterface;
import interfaces.SwerveWheelInterface;

import java.awt.geom.Point2D;
import java.util.List;

public class SpinDrive implements RobotController{
    @Override // this tells Java that the `loop` method implements the `loop` method specified in `RobotController`
    public void loop(JoysticksInterface joysticks, RobotInterface robot) {
        double leftVelocity = joysticks.getLeftStick().y * 5.0; // get the velocity of the left half of the robot
        double rightVelocity = joysticks.getRightStick().y * 5.0; // get the velocity of the right half of the robot

        //double angle = Math.atan2(-joysticks.getLeftStick().x, joysticks.getLeftStick().y);
        //System.out.println("radians: " + angle + " degrees: " + Math.toDegrees(angle));

        //double magnitude = Math.sqrt(Math.pow(joysticks.getLeftStick().x, 2) + Math.pow(joysticks.getLeftStick().y, 2));
        double magnitude = Math.abs(joysticks.getLeftStick().x);

        //System.out.println("degrees: " + Math.toDegrees(angle) + " magnitude: " + magnitude);
        // this is a list of the swerve modules that are on the robot
        List<SwerveWheelInterface> drivetrain = robot.getDrivetrain();

        for(int i = 0; i < drivetrain.size(); i++) { // loop through each swerve module
            SwerveWheelInterface wheel = drivetrain.get(i); // the swerve module we aaaaaare looking at

            Point2D.Double wheelPosition = wheel.getPosition();
            //System.out.println("wheel: " + i + " x: " + wheelPosition.x + " y: " + wheelPosition.y);

            double angle;
            if(joysticks.getLeftStick().x > 0) {
                angle = Math.atan2(-wheelPosition.y, -wheelPosition.x);
            } else {
                angle = Math.atan2(wheelPosition.y, wheelPosition.x);
            }



            System.out.println("wheel: " + i + " radians: " + angle + " degrees: " + Math.toDegrees(angle));

            wheel.setWheelAngle(angle); // make sure the wheels are always facing forward (none of this funny swerve business)

            wheel.setWheelVelocity((magnitude * 5.0));
        }
    }
}
