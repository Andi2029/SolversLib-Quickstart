package org.firstinspires.ftc.teamcode.systems;

import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.global.Robot;

public class Intake extends SubsystemBase {


    int retractedPosition; //in ticks
    double extendDistance;

    private final Robot robot = Robot.getInstance();

    public enum IntakeState{
        EXTEND,
        RETRACT,
        GRAB,
        TRANSFER,
        STOP
    }

    public static IntakeState intakeState = IntakeState.EXTEND;

    public void update(){
        switch (intakeState){
            case STOP:
                robot.inMotor.setVelocity(0);
                break;

            case EXTEND:
                extendTo(extendDistance);
                break;

            case RETRACT:
                robot.inMotor.setTargetPosition(retractedPosition);
                break;

            case TRANSFER:

        }

    }

    public boolean isMoving(){
        return robot.inMotor.getCorrectedVelocity() > 0.01;
    }

    public void extendTo (double distance){
        robot.inMotor.setTargetDistance(distance);
    }

}
