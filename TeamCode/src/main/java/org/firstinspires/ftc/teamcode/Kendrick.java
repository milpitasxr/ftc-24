// Import main FTC package
package org.firstinspires.ftc.teamcode;

// Imports from FTC package
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp

public class Kendrick extends OpMode{

    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;

    IMU imu;
    YawPitchRollAngles angles;
    String intakeAction = "RETRACT";
    String bucketAction = "DOWN";

    double heading;
    double roll;
    double pitch;

    double integralSum = 0;
    double lastError = 0;
    double Kp = 1;
    double Ki = 0;
    double Kf = 0;
    ElapsedTime timer;
    ElapsedTime intakeTimer;
    ElapsedTime bucketTimer;

    // private BNO055IMU imu;

    @Override
    public void init() {
        // Initialize motor references
        motor1 = hardwareMap.dcMotor.get("Q1");
        motor2 = hardwareMap.dcMotor.get("Q2");
        motor3 = hardwareMap.dcMotor.get("Q3");
        motor4 = hardwareMap.dcMotor.get("Q4");

        // Initialize the Inertial Measurement Unit (IMU)
        SetupIMU();

        // Create timers for different actions
        timer = new ElapsedTime();
        intakeTimer = new ElapsedTime();
        bucketTimer = new ElapsedTime();
        telemetry.addData("IMU Status: ", "Ready");
    }

    // Main loop
    @Override
    public void loop(){
        // Detect gamepad inputs and call movement function
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        StraferChassis(Math.atan2(y, x), Math.sqrt((x*x)+(y*y)), gamepad1.right_stick_x);

        // Detect changes using the IMU
        UpdateIMU();

        // Perform certain actions (if the correct gamepad buttons are pressed) and log the time since they were last performed
        changeHAction();
        telemetry.addData("Intake Timer", intakeTimer.time());
        changeVAction();
        telemetry.addData("Bucket Timer", bucketTimer.time());

        // Call PID function with right joycon inputs
        PID(Math.atan2(gamepad1.right_stick_y, gamepad1.right_stick_x),heading);
        //telemetry.addData(PID(0, heading));
    }

    // Function to initialize the IMU
    public void SetupIMU(){
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        );
        imu.initialize(parameters);
    }

    // Function to update data from the IMU
    public void UpdateIMU(){
        angles = imu.getRobotYawPitchRollAngles();

        heading = angles.getYaw() * (Math.PI/180);
        roll = angles.getRoll();
        pitch = angles.getPitch();

        telemetry.addData("Heading: ", heading);
        telemetry.addData("Roll: ", roll);
        telemetry.addData("Pitch: ", pitch);
    }

    // Function to adjust motor powers according to the user inputs (strafing movement)
    public void StraferChassis(double theta, double power, double turn){

        double sin = Math.sin(theta - Math.PI/4);
        double cos = Math.cos(theta - Math.PI/4);

        double m = Math.max(Math.abs(sin), Math.abs(cos));

        double leftFront = power * (cos/m) + turn;
        double rightFront = power * (sin/m) - turn;
        double leftBack = power * (sin/m) + turn;
        double rightBack = power * (cos/m) - turn;

        if((power + Math.abs(turn)) > 1){
            leftFront /= power + turn;
            rightFront /= power + turn;
            leftBack /= power + turn;
            rightBack /= power + turn;
        }

        motor1.setPower(rightFront * 0.5);
        motor2.setPower(-leftFront * 0.5);
        motor3.setPower(-leftBack * 0.5);
        motor4.setPower(rightBack * 0.5);

        telemetry.addData("theta", theta);
        telemetry.addData("power", power);
        telemetry.addData("turn", turn);

    }

    // Function for PID turning
    public double PID(double reference, double state){
        double error = angleWrap(reference - state);
        telemetry.addData("Error:",error);
        integralSum += error * timer.seconds();
        double derivative = (error- lastError) / timer.seconds();
        lastError = error;

        timer.reset();

        double turn = (error * Kp) + (derivative * Kp) + (integralSum * Ki) + (reference * Kf);
        return turn;
    }

    // Function to calculate angle wrapping
    public double angleWrap(double radians){
        while (radians > Math.PI){
            radians -= 2 * Math.PI;
        }
        while (radians < -Math.PI){
            radians += 2 * Math.PI;
        }
        return radians;
    }

    // Function for changing the horizontal action
    public void changeHAction(){
        telemetry.addData("INTAKE STATUS", intakeAction);
        if (gamepad1.a && gamepad1.dpad_right && intakeAction != "RETRACT"){
            intakeAction = "RETRACT";
            intakeTimer.reset();
        }
        else if (gamepad1.a && gamepad1.dpad_left && intakeAction != "EXTEND"){
            intakeAction = "EXTEND";
            intakeTimer.reset();
        }
        else if (gamepad1.a && gamepad1.dpad_up && intakeAction != "HALF"){
            intakeAction = "HALF";
            intakeTimer.reset();
        }
        else if (gamepad1.a && gamepad1.dpad_down && intakeAction != "ZERO"){
            intakeAction = "ZERO";
            intakeTimer.reset();
        }
    }

    // Function for changing the vertical action
    public void changeVAction(){
        telemetry.addData("BUCKET STATUS", bucketAction);
        if (gamepad1.b && gamepad1.dpad_right && bucketAction != "RETRACT"){
            bucketAction = "RETRACT";
            bucketTimer.reset();
        }
        else if (gamepad1.b && gamepad1.dpad_left && bucketAction != "LOWER"){
            bucketAction = "LOWER";
            bucketTimer.reset();
        }
        else if (gamepad1.b && gamepad1.dpad_up && bucketAction != "UPPER"){
            bucketAction = "UPPER";
            bucketTimer.reset();
        }
        else if (gamepad1.b && gamepad1.dpad_down && bucketAction != "DOWN"){
            bucketAction = "DOWN";
            bucketTimer.reset();
        }
        else if (gamepad1.y && gamepad1.dpad_up && bucketAction != "TOP"){
            bucketAction = "TOP";
            bucketTimer.reset();
        }
        else if (gamepad1.y && gamepad1.dpad_left && bucketAction != "MID"){
            bucketAction = "MID";
            bucketTimer.reset();
        }
        else if (gamepad1.y && gamepad1.dpad_down && bucketAction != "PICK"){
            bucketAction = "PICK";
            bucketTimer.reset();
        }
        else if (gamepad1.y && gamepad1.dpad_right && bucketAction != "PLACE"){
            bucketAction = "PLACE";
            bucketTimer.reset();
        }
    }
}