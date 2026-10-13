package org.firstinspires.ftc.teamcode.global.custommath;

import static org.firstinspires.ftc.teamcode.global.Constants.gravitationalAcceleration;
import org.firstinspires.ftc.teamcode.global.custommath.Vector3d;

import org.jetbrains.annotations.ApiStatus;

public class MathFunctions {

    /**
     * Finds leg of triangle given hypotenuse and the other leg
     */
    public static double findLeg(double hypot, double leg) {
        if (leg > hypot || hypot <= 0 || leg <= 0) {
            return Double.NaN;
        }

        return Math.sqrt(Math.pow(hypot, 2) - Math.pow(leg, 2));
    }

    /**
     * Finds hypotenuse of triangle given both legs
     */
    public static double findHypotenuse(double leg1, double leg2) {
        return Math.hypot(leg1, leg2);
    }


    /*
    * Finds the required initial velocity as a
    * Vector3d for a given apex and start point
    */
    @ApiStatus.Experimental
    public static Vector3d computeInitialShotVelocity(Vector3d start, Vector3d apex){
        if(start.getZ() >= apex.getZ()){
            return new Vector3d(Double.NaN, Double.NaN, Double.NaN);
        }
        Vector3d velocity;
        double apexTime = Math.sqrt(apex.substract(start).dotProduct(gravitationalAcceleration) * (-2.0) / gravitationalAcceleration.dotProduct(gravitationalAcceleration));
        velocity = apex.substract(start).scalarMultiplication(1/apexTime).substract(gravitationalAcceleration.scalarMultiplication(0.5).scalarMultiplication(apexTime));
        return velocity;
    }
    // maybe do linearly approximate the deviation from this based on
    // distance and add that as a factor to the shooting motor speed.
    // When writing the equation for the motor speed and hood angle
    // to ball velocity, use a similar method to account for the fact
    // that the ball is not in ideal contact
}
