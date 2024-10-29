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
public class Kendrick extends OpMode {

    Drivetrain dt;
    Horizontal h;
    Vertical v;

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
        dt = new Drivetrain();
        v = new Vertical();
        h = new Horizontal();
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
    public void loop() {
        // Detect gamepad inputs and call movement function
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
//        dt.StraferChassis(Math.atan2(y, x), Math.sqrt((x*x)+(y*y)), gamepad1.right_stick_x);
        dt.StraferChassis(Math.atan2(y, x), Math.sqrt((x * x) + (y * y)));

        // Detect changes using the IMU
        UpdateIMU();

        // Perform certain actions (if the correct gamepad buttons are pressed) and log the time since they were last performed
        h.changeHAction();
        telemetry.addData("Intake Timer", intakeTimer.time());
        v.changeVAction();
        telemetry.addData("Bucket Timer", bucketTimer.time());

        // Call PID function with right joycon inputs
        //dt.PID(Math.atan2(gamepad1.right_stick_y, gamepad1.right_stick_x),heading);
        //telemetry.addData(PID(0, heading));
    }

    // Function to initialize the IMU
    public void SetupIMU() {
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
    public void UpdateIMU() {
        angles = imu.getRobotYawPitchRollAngles();

        heading = angles.getYaw() * (Math.PI / 180);
        roll = angles.getRoll();
        pitch = angles.getPitch();

        telemetry.addData("Heading: ", heading);
        telemetry.addData("Roll: ", roll);
        telemetry.addData("Pitch: ", pitch);
    }

}