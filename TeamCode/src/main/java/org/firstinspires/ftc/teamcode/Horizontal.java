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
        intake = opmode.hardwareMap.dcMotor.get("intake");

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
//        WIP

    }

    private void half() {
//        WIP

    }

    private void extend() {
//        WIP

    }

    private void zero() {
//        WIP

    }

    public void changeHAction() {
        // Bring all the way back
        if (gamepad1.a && gamepad1.dpad_right && intakeAction != "RETRACT") {
            intakeAction = "RETRACT";
            intakeTimer.reset();

            // Extend fully
        } else if (gamepad1.a && gamepad1.dpad_left && intakeAction != "EXTEND") {
            intakeAction = "EXTEND";
            intakeTimer.reset();

            // Extend halfway
        } else if (gamepad1.a && gamepad1.dpad_up && intakeAction != "HALF") {
            intakeAction = "HALF";
            intakeTimer.reset();

            // Tilt down to pick up sample
        } else if (gamepad1.a && gamepad1.dpad_down && intakeAction != "ZERO") {
            intakeAction = "ZERO";
            intakeTimer.reset();
        }
    }

}
