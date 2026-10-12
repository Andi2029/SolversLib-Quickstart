package org.firstinspires.ftc.teamcode.global.custommath;

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

}
