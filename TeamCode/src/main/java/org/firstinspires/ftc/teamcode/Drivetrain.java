package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class Drivetrain {
    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;

    IMU imu;
    YawPitchRollAngles angles;

    OpMode opmode;

    double heading;
    double roll;
    double pitch;
    double integralSum = 0;
    double lastError = 0;
    double Kp = 1;
    double Ki = 0;
    double Kd = 0;
    double targetAngle = 0;

    ElapsedTime timer;

    public Drivetrain(OpMode op) {
        opmode = op;

        motor1 = opmode.hardwareMap.dcMotor.get("q1");
        motor2 = opmode.hardwareMap.dcMotor.get("q2");
        motor3 = opmode.hardwareMap.dcMotor.get("q3");
        motor4 = opmode.hardwareMap.dcMotor.get("q4");

        timer = new ElapsedTime();

        SetupIMU();

    }

    public void StraferChassis(double theta, double power) {
        double turn = IMUTurning();
        opmode.telemetry.addData("turn", turn);
        turn = 0;
        double sin = Math.sin(theta - Math.PI / 4);
        double cos = Math.cos(theta - Math.PI / 4);

        double m = Math.max(Math.abs(sin), Math.abs(cos));

        double leftFront = power * (cos / m) + turn;
        double rightFront = power * (sin / m) - turn;
        double leftBack = power * (sin / m) + turn;
        double rightBack = power * (cos / m) - turn;

        if ((power + Math.abs(turn)) > 1) {
            leftFront /= power + turn;
            rightFront /= power + turn;
            leftBack /= power + turn;
            rightBack /= power + turn;
        }

        motor1.setPower(rightFront * 0.5);
        motor2.setPower(-leftFront * 0.5);
        motor3.setPower(-leftBack * 0.5);
        motor4.setPower(rightBack * 0.5);

//        telemetry.addData("theta", theta);
//        telemetry.addData("power", power);
//        telemetry.addData("turn", turn);

    }

    public void SetupIMU() {
        imu = opmode.hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
                )
        );
        imu.initialize(parameters);
    }

    public double IMUTurning() {
//      Current rotation of the robot
        angles = imu.getRobotYawPitchRollAngles();
        heading = angles.getYaw() * (Math.PI / 180);
        opmode.telemetry.addData("Heading in degrees", heading * (180 / Math.PI));
//      Input on the gamepad's right joystick
        double x = opmode.gamepad1.right_stick_x;
        double y = opmode.gamepad1.right_stick_y;
        double right_stick_angle = Math.atan2(y, x);
        if (Math.sqrt(x*x + y*y) > 0.5){
            targetAngle = right_stick_angle;
        }
        opmode.telemetry.addData("targetAngle", targetAngle);
        opmode.telemetry.addData("heading", heading);

        double turn = PID(right_stick_angle, heading);
        return turn;
    }

    public double PID(double reference, double state) {
        double error = angleWrap(reference - state);
        opmode.telemetry.addData("error", error);
//        telemetry.addData("Error:",error);
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / timer.seconds();
        lastError = error;

        timer.reset();

        double turn = (error * Kp) + (integralSum * Ki) + (lastError * Kd);
        return turn;
    }

    public double angleWrap(double radians) {
        while (radians > Math.PI) {
            radians -= 2 * Math.PI;
        }
        while (radians < -Math.PI) {
            radians += 2 * Math.PI;
        }
        return radians;
    }
}
