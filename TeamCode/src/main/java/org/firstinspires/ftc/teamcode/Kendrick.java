// Import main FTC package
package org.firstinspires.ftc.teamcode;

// Imports from FTC package
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp
public class Kendrick extends OpMode{

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

        dt = new Drivetrain(this);
        v = new Vertical(this);
        h = new Horizontal(this);

        // Create timers for different actions
        timer = new ElapsedTime();
        intakeTimer = new ElapsedTime();
        bucketTimer = new ElapsedTime();
    }

    // Main loop
    @Override
    public void loop(){
        // Detect gamepad inputs and call movement function
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        dt.StraferChassis(Math.atan2(y, x), Math.sqrt((x*x)+(y*y)));
        v.loop();

        telemetry.addData("Intake Timer", intakeTimer.time());
        //v.changeVAction();
        telemetry.addData("Bucket Timer", bucketTimer.time());

    }


}