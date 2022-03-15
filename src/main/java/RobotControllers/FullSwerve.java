package RobotControllers;

import interfaces.JoysticksInterface;
import interfaces.RobotController;
import interfaces.RobotInterface;
import interfaces.SwerveWheelInterface;

import java.awt.geom.Point2D;
import java.util.List;

public class FullSwerve implements RobotController {
    private Double[] getTranslation(double x, double y) {
        double angle = Math.atan2(y, x) - (Math.PI/2);
        double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));

        Double[] result = new Double[2];
        result[0] = angle;
        result[1] = magnitude;

        return result;
    }

    private Double[] getRotation(SwerveWheelInterface wheel, double z) {
        Point2D.Double wheelPosition = wheel.getPosition();

        double angle;
        if(z > 0) {
            angle = Math.atan2(-wheelPosition.y, -wheelPosition.x);
        } else {
            angle = Math.atan2(wheelPosition.y, wheelPosition.x);
        }

        double magnitude = Math.abs(z);

        Double[] result = new Double[2];

        result[0] = angle;
        result[1] = magnitude;
        return result;
    }

    private Double[] getMovement(SwerveWheelInterface wheel, double x, double y, double z) {
        Double[] translation = getTranslation(x, y);
        Double[] rotation = getRotation(wheel, z);

        //https://www.omnicalculator.com/math/vector-addition#vector-addition-formula
        double translation_x = translation[1] * Math.cos(translation[0]);
        double translation_y = translation[1] * Math.sin(translation[0]);

        double rotation_x = rotation[1] * Math.cos(rotation[0]);
        double rotation_y = rotation[1] * Math.sin(rotation[0]);

        double result_x = translation_x + rotation_x;
        double result_y = translation_y + rotation_y;

        double magnitude = Math.sqrt(Math.pow(result_x, 2) + Math.pow(result_y, 2));

        double angle;
        if (Math.abs(magnitude) > 0) {
            //angle = Math.acos(result_x / magnitude);
            angle = Math.atan2(result_y, result_x);
        } else {
            angle = 0;
        }

        Double result[] = new Double[2];
        result[0] = angle;
        result[1] = magnitude;

        return result;
    }

    @Override // this tells Java that the `loop` method implements the `loop` method specified in `RobotController`
    public void loop(JoysticksInterface joysticks, RobotInterface robot) {
        // this is a list of the swerve modules that are on the robot
        List<SwerveWheelInterface> drivetrain = robot.getDrivetrain();

        for(int i = 0; i < drivetrain.size(); i++) { // loop through each swerve module
            SwerveWheelInterface wheel = drivetrain.get(i); // the swerve module we aaaaaare looking at

            Double[] movement = getMovement(wheel, joysticks.getLeftStick().x, joysticks.getLeftStick().y, joysticks.getRightStick().x);

            wheel.setWheelAngle(movement[0]); // make sure the wheels are always facing forward (none of this funny swerve business)

            wheel.setWheelVelocity((movement[1] * 5.0));
        }
    }
}
