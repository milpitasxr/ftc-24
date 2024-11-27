package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;

public class GlobalCoordinateSystem implements Runnable{


    DcMotor leftEncoder, rightEncoder, middleEncoder;

    boolean isRunning = true;

    double leftEncoderValue, rightEncoderValue, middleEncoderValue;
    double changeInOrientation;
    double OLDLeftEncoderPosition, OLDRightEncoderPosition, OLDMiddleEncoderPosition;

    double globalX,globalY,robotOrientation;

    double encoderWheelDistance;
    double middleEncoderTickOffset;
    int sleepTime;

    File sideWheelsIsSeparationFile = AppUtil.getInstance().getSettingsFile("sideWheelsSeparationFile.txt");
    File middleTickOffsetFile = AppUtil.getInstance().getSettingsFile("middleEncoderTickOffset.txt");

    public GlobalCoordinateSystem(DcMotor leftEncoder, DcMotor rightEncoder, DcMotor middleEncoder, double TICKS_PER_INCH, int threadsSleepDelay){
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        this.middleEncoder = middleEncoder;
        sleepTime = threadsSleepDelay;

        encoderWheelDistance = Double.parseDouble(ReadWriteFile.readFile(sideWheelsIsSeparationFile).trim()) * TICKS_PER_INCH;
        middleEncoderTickOffset = Double.parseDouble(ReadWriteFile.readFile(middleTickOffsetFile).trim());

    }

    public void positionUpdate(){
        double leftEncoderPosition = leftEncoder.getCurrentPosition();
        double rightEncoderPosition = rightEncoder.getCurrentPosition();

        double leftChange = leftEncoderPosition - OLDLeftEncoderPosition;
        double rightChange = rightEncoderPosition - OLDRightEncoderPosition;

        changeInOrientation = (leftChange - rightChange) / encoderWheelDistance;
        robotOrientation += changeInOrientation;

        double middleEncoderPosition = middleEncoder.getCurrentPosition();
        double rawHorizontalChange = middleEncoderPosition - OLDMiddleEncoderPosition;
        double horizontalChange = rawHorizontalChange - (changeInOrientation * middleEncoderTickOffset);

        double sides = (leftChange + rightChange) / 2;
        double frontBack = horizontalChange;

        globalX = sides * Math.sin(robotOrientation) + frontBack * Math.cos(robotOrientation);
        globalY = sides * Math.cos(robotOrientation) - frontBack * Math.sin(robotOrientation);

        OLDLeftEncoderPosition = leftEncoderPosition;
        OLDRightEncoderPosition = rightEncoderPosition;
        OLDMiddleEncoderPosition = middleEncoderPosition;

    }

    public double returnXCoordinate(){ return globalX; }

    public double returnYCoordinate(){ return globalY; }

    public double returnOrientation(){ return Math.toDegrees(robotOrientation) % 360; }

    public void stop(){ isRunning = false; }



    @Override
    public void run() {
        while (isRunning){
            positionUpdate();
        }
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
