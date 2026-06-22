package org.firstinspires.ftc.teamcode.global;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;
import com.seattlesolvers.solverslib.hardware.servos.ServoExGroup;




/*
* NAMING CONVENTION
*
* variables start lowercase, each new word starts uppercase
*
* MotorGroup objects are singular
*
* "intake" inside an object name is always replaced by "in"
* "outtake" -> "out"
* "differential" -> "diff"
* */

public class Robot extends com.seattlesolvers.solverslib.command.Robot{

    //singleton
    private static final Robot instance = new Robot();
    public static Robot getInstance() {
        return instance;
    }


    //drive motors
    public MotorEx RFdrive; //right front
    public MotorEx RBdrive; //right back
    public MotorEx LFdrive; //just follow naming convention
    public MotorEx LBdrive;


    //actuator motors
    public MotorEx inMotor; //intake motor
    public MotorGroup outMotor; // outtake motor group, keep singular for simplicity


    //actuator servos
    public ServoEx outClawServo;
    public ServoEx inClawServo;
    public ServoEx angleOutClawServo;
    public ServoEx orientationInClawServo;
    public ServoEx diffRightServo;
    public ServoEx diffLeftServo;
    public ServoEx parallelIntakeServo;
    public ServoEx outExtensionServo;
    public ServoExGroup outServos;


    //Sensors
    public VoltageSensor voltageSensor;



    //Timers
    private ElapsedTime voltageTimer;
    private double cachedVoltage;
    public static double VOLTAGE_SENSOR_POLLING_RATE = 5; // Hertz




    public void init(HardwareMap hwMap) {
        RFdrive = new MotorEx(hwMap, "RF").setCachingTolerance(0.01);
        LFdrive = new MotorEx(hwMap, "LF").setCachingTolerance(0.01);
        LBdrive = new MotorEx(hwMap, "LB").setCachingTolerance(0.01);
        RBdrive = new MotorEx(hwMap, "RB").setCachingTolerance(0.01);

        RBdrive.setRunMode(Motor.RunMode.RawPower);
        RFdrive.setRunMode(Motor.RunMode.RawPower);
        LBdrive.setRunMode(Motor.RunMode.RawPower);
        LFdrive.setRunMode(Motor.RunMode.RawPower);

        RBdrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        RFdrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        LBdrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        LFdrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        outMotor = new MotorGroup(
                new MotorEx(hwMap, "leftOut")
                        .setCachingTolerance(0.01)
                        .setInverted(true), //change if necessary
                new MotorEx(hwMap, "rightOut")
                        .setCachingTolerance(0.01)
        );
        outMotor.setRunMode(Motor.RunMode.PositionControl); //P controller
        outMotor.setPositionTolerance(12); //tolerance in encoder ticks
        outMotor.setPositionCoefficient(0.05); //Kp for the P controller
        outMotor.setDistancePerPulse(0.015); // self-explanatory, please change

        inMotor.setRunMode(Motor.RunMode.PositionControl);
        inMotor.setPositionTolerance(12); //tolerance in encoder ticks
        inMotor.setPositionCoefficient(0.05); //Kp for the P controller
        inMotor.setDistancePerPulse(0.015); // self-explanatory, please change

        outServos = new ServoExGroup(
                new ServoEx(hwMap, "rightOutServo").setInverted(true),
                new ServoEx(hwMap, "leftOutServo")
        );


        outClawServo = new ServoEx(hwMap, "outClawServo").setCachingTolerance(0.001);
        inClawServo = new ServoEx(hwMap, "inClawServo").setCachingTolerance(0.001);
        angleOutClawServo = new ServoEx(hwMap, "inClawServo").setCachingTolerance(0.001);
        orientationInClawServo = new ServoEx(hwMap, "orientationInClawServo").setCachingTolerance(0.001);
        diffRightServo = new ServoEx(hwMap, "diffRightServo").setCachingTolerance(0.001);
        diffLeftServo = new ServoEx(hwMap, "diffLeftServo").setCachingTolerance(0.001);
        parallelIntakeServo = new ServoEx(hwMap, "parallelIntakeServo").setCachingTolerance(0.001);
        outExtensionServo = new ServoEx(hwMap, "outExtensionServo").setCachingTolerance(0.001);

        voltageSensor = hwMap.voltageSensor.iterator().next();

        //also add all subsystems
    }


    public double getVoltage() {
//        return 12;
        // this is chopped for loop times
        if (voltageTimer == null) {
            cachedVoltage = voltageSensor.getVoltage();
            voltageTimer = new ElapsedTime();
            voltageTimer.reset();
        } else if (voltageTimer.milliseconds() > (1.0 / VOLTAGE_SENSOR_POLLING_RATE) * 1000) {
            cachedVoltage = voltageSensor.getVoltage();
        }
        if (Double.isNaN(cachedVoltage) || cachedVoltage <= 0) {
            cachedVoltage = 12;
        }
        return cachedVoltage;
    }
}
