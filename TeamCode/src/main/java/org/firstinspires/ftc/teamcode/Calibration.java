
package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

@Autonomous
public class Calibration extends LinearOpMode {
    DcMotor frontRight, frontLeft, backRight, backLeft;
    DcMotor leftEncoder, rightEncoder, middleEncoder;
    BNO055IMU imu;

    ElapsedTime timer = new ElapsedTime();

    static final double calibrationSpeed = 0.5;
    static final double TICKS_PER_REV = 8192;
    static final double WHEEL_DIAMETER = 32/25.4; // Wheel diameter is 32mms, so we have to convert to inches.
    static final double GEAR_RATIO = 1;
    static final double TICKS_PER_INCH = WHEEL_DIAMETER * Math.PI * GEAR_RATIO/TICKS_PER_REV;

    File sideWheelSeperationFile = AppUtil.getInstance().getSettingsFile("sideWheelsSeperation");
    File middleTickOffsetFile = AppUtil.getInstance().getSettingsFile("middleTickOffset");

    @Override
    public void runOpMode(){
        // Initialize  all the motors
        frontLeft = hardwareMap.dcMotor.get("q1");
        frontRight = hardwareMap.dcMotor.get("q4");
        backLeft = hardwareMap.dcMotor.get("q2");
        backRight = hardwareMap.dcMotor.get("q3");

        // Initialize all the Dead Wheels/ Encoders
        leftEncoder = hardwareMap.dcMotor.get("ol");
        rightEncoder = hardwareMap.dcMotor.get("or");
        middleEncoder = hardwareMap.dcMotor.get("om");

        //
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        resetOdometryEncoders();

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        imu.initialize(parameters);

        telemetry.addData("Status", "READY TO GO BAYBEE");
        telemetry.update();

        waitForStart();

        // Might have to change what angle we use based on how REV HUB is mounted.
        while(imu.getAngularOrientation().firstAngle < 90 && opModeIsActive()){
            frontRight.setPower(-calibrationSpeed);
            backRight.setPower(-calibrationSpeed);
            frontLeft.setPower(calibrationSpeed);
            backLeft.setPower(calibrationSpeed);

            if (imu.getAngularOrientation().firstAngle < 60){
                frontRight.setPower(-calibrationSpeed);
                backRight.setPower(-calibrationSpeed);
                frontLeft.setPower(calibrationSpeed);
                backLeft.setPower(calibrationSpeed);
            }
            else {
                frontRight.setPower(-calibrationSpeed / 2);
                backRight.setPower(-calibrationSpeed / 2);
                frontLeft.setPower(calibrationSpeed / 2);
                backLeft.setPower(calibrationSpeed / 2);
            }

        }
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);

        timer.reset();
        while(timer.seconds() < 1 && opModeIsActive()){
            telemetry.addData("Status", "Waiting for reset");
            telemetry.update();
        }

        double angle = imu.getAngularOrientation().firstAngle;
        double encoderDifference = Math.abs(Math.abs(leftEncoder.getCurrentPosition()) - Math.abs(rightEncoder.getCurrentPosition()));
        double sideEncoderTickOffset = encoderDifference / angle;
        double sideWheelSeperation = (180 * sideEncoderTickOffset) / (TICKS_PER_INCH * Math.PI);
        double middleEncoderTickOffset = middleEncoder.getCurrentPosition() / Math.toRadians(imu.getAngularOrientation().firstAngle);

        ReadWriteFile.writeFile(sideWheelSeperationFile, String.valueOf(sideWheelSeperation));
        ReadWriteFile.writeFile(middleTickOffsetFile, String.valueOf(middleEncoderTickOffset));


    }

    void resetOdometryEncoders(){
        leftEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        middleEncoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}