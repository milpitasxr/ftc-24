package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Horizontal {
    ElapsedTime intakeTimer;
    String intakeAction;
    OpMode opmode;
    Servo rightExtender;
    Servo leftExtender;
    Servo rightRotator;
    Servo leftRotator;
    DcMotor intake;

    public Horizontal(OpMode op) {
        opmode = op;
        intakeTimer = new ElapsedTime();
        intakeAction = "RETRACT";

        rightExtender = opmode.hardwareMap.get(Servo.class, "right");
        leftExtender = opmode.hardwareMap.get(Servo.class, "right");
        rightRotator = opmode.hardwareMap.get(Servo.class, "right");
        leftRotator = opmode.hardwareMap.get(Servo.class, "right");
        intake = opmode.hardwareMap.get(DcMotor.class, "right");

    }

    public void loop() {
        changeHAction();
        //scoring actions
        if (intakeAction == "RETRACT") {
            retract();
        }
        if (intakeAction == "HALF") {
            half();
        }
        if (intakeAction == "EXTEND") {
            extend();
        } else if (intakeAction == "ZERO") {
            zero();
        }

    }

    private void retract() {
        rightExtender.setPosition(0);
        leftExtender.setPosition(0);

        rightRotator.setPosition(0);
        rightRotator.setPosition(0);

        intake.setPower(0);

    }

    private void half() {
        rightExtender.setPosition(0.5);
        leftExtender.setPosition(0.5);

        rightRotator.setPosition(1);
        rightRotator.setPosition(1);
        intake.setPower(0.8);

    }

    private void extend() {
        rightExtender.setPosition(1);
        leftExtender.setPosition(1);

        rightRotator.setPosition(1);
        rightRotator.setPosition(1);

        intake.setPower(0.8);

    }

    private void zero() {
        rightExtender.setPosition(0);
        leftExtender.setPosition(0);

        rightRotator.setPosition(1);
        rightRotator.setPosition(1);

        intake.setPower(0.8);

    }

    public void changeHAction() {
        if (gamepad1.a && gamepad1.dpad_right && intakeAction != "RETRACT") {
            intakeAction = "RETRACT";
            intakeTimer.reset();
        } else if (gamepad1.a && gamepad1.dpad_left && intakeAction != "EXTEND") {
            intakeAction = "EXTEND";
            intakeTimer.reset();
        } else if (gamepad1.a && gamepad1.dpad_up && intakeAction != "HALF") {
            intakeAction = "HALF";
            intakeTimer.reset();
        } else if (gamepad1.a && gamepad1.dpad_down && intakeAction != "ZERO") {
            intakeAction = "ZERO";
            intakeTimer.reset();
        }
    }

}
