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

    Servo rightExtender1;
    Servo rightExtender2;
    Servo leftExtender1;
    Servo leftExtender2;
    Servo leftIntake;
    Servo rightIntake;
    DcMotor intake;

    public Horizontal(OpMode op) {
        opmode = op;
        intakeTimer = new ElapsedTime();
        intakeAction = "RETRACT";

        rightExtender1 = opmode.hardwareMap.get(Servo.class, "rh1");
        rightExtender2 = opmode.hardwareMap.get(Servo.class, "rh2");
        leftExtender1 = opmode.hardwareMap.get(Servo.class, "lh1");
        leftExtender2 = opmode.hardwareMap.get(Servo.class, "lh2");
        leftIntake = opmode.hardwareMap.get(Servo.class, "li");
        rightIntake = opmode.hardwareMap.get(Servo.class, "ri");
        intake = opmode.hardwareMap.get(DcMotor.class, "intake");

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
        rightIntake.setPosition(0.73);
        //leftIntake.setPosition(0.5);

        intake.setPower(0);

        leftExtender1.setPosition(0);
        leftExtender2.setPosition(1);

        rightExtender1.setPosition(1-0.733);
        rightExtender2.setPosition(0.733);

    }

    private void half() {


    }

    private void extend() {
        leftExtender1.setPosition(1);
        leftExtender2.setPosition(0);

        rightExtender1.setPosition(1);
        rightExtender2.setPosition(0);

        if (intakeTimer.milliseconds() >= 10){
            rightIntake.setPosition(0.9);
            intake.setPower(0.9);
        }
    }

    private void zero() {
//        WIP
        leftExtender1.setPosition(0);
        leftExtender2.setPosition(1);

        rightExtender1.setPosition(1);
        rightExtender2.setPosition(0);

        rightIntake.setPosition(0.9);
        intake.setPower(0.9);

    }

    public void changeHAction() {
        // Bring all the way back
        if (opmode.gamepad1.a && opmode.gamepad1.dpad_right && intakeAction != "RETRACT") {
            intakeAction = "RETRACT";
            intakeTimer.reset();

            // Extend fully
        } else if (opmode.gamepad1.a && opmode.gamepad1.dpad_left && intakeAction != "EXTEND") {
            intakeAction = "EXTEND";
            intakeTimer.reset();

            // Extend halfway
        } else if (opmode.gamepad1.a && opmode.gamepad1.dpad_up && intakeAction != "HALF") {
            intakeAction = "HALF";
            intakeTimer.reset();

            // Tilt down to pick up sample
        } else if (opmode.gamepad1.a && opmode.gamepad1.dpad_down && intakeAction != "ZERO") {
            intakeAction = "ZERO";
            intakeTimer.reset();
        }
    }

}
