package org.firstinspires.ftc.teamcode.global;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.geometry.Pose2d;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;
import com.seattlesolvers.solverslib.hardware.servos.ServoExGroup;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;




/*
* NAMING CONVENTION
*
* objects & instances start lowercase, each new word starts uppercase
* classes names start uppercase, each new word starts with uppercase
*
* MotorGroup objects are singular
*
* "intake" inside an object name is ALWAYS replaced by "in"
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
    public MotorGroup inMotor; //intake motor


    //actuator servos
    public ServoEx servo;
    public ServoExGroup turretServos;
    public ServoEx rampServo;

    //Shooter
    public boolean readyToLaunch = true;
    public MotorGroup launchMotors;
    public Motor.Encoder launchEncoder;
    public ServoEx hoodServo;


    //Sensors
    public VoltageSensor voltageSensor;


    //Timers
    private ElapsedTime voltageTimer;
    private double cachedVoltage;
    public static double VOLTAGE_SENSOR_POLLING_RATE = 5; // Hertz

    //Localizer
    public GoBildaPinpointDriver pinpoint;
    //public IMU imu;

    //Systems
    public Camera camera;




    public void init(HardwareMap hwMap) {
        // Hardware
        voltageSensor = hwMap.voltageSensor.iterator().next();

        RFdrive = new MotorEx(hwMap, "RF").setCachingTolerance(0.01);
        LFdrive = new MotorEx(hwMap, "LF").setCachingTolerance(0.01);
        LBdrive = new MotorEx(hwMap, "LB").setCachingTolerance(0.01);
        RBdrive = new MotorEx(hwMap, "RB").setCachingTolerance(0.01);

        RFdrive.setRunMode(Motor.RunMode.RawPower);
        LFdrive.setRunMode(Motor.RunMode.RawPower);
        LBdrive.setRunMode(Motor.RunMode.RawPower);
        RBdrive.setRunMode(Motor.RunMode.RawPower);

        RBdrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        RFdrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        LBdrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        LFdrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        pinpoint = hwMap.get(GoBildaPinpointDriver.class, "pinpoint");//add


        hoodServo = new ServoEx(hwMap, "hoodServo").setCachingTolerance(0.001)
                .setInverted(true);
        rampServo = new ServoEx(hwMap, "rampServo").setCachingTolerance(0.001)
                .setInverted(false);

        inMotor.setRunMode(Motor.RunMode.PositionControl);
        inMotor.setPositionTolerance(12); //tolerance in encoder ticks
        inMotor.setPositionCoefficient(0.05); //Kp for the P controller
        inMotor.setDistancePerPulse(0.015); // self-explanatory, please change


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
