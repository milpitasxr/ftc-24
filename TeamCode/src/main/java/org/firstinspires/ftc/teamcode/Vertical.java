package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


public class Vertical {

    double TICKS_PER_REVOLUTION = 751.8;

    OpMode opmode;

    Servo armLeft;
    Servo armRight;
    Servo verticalLeft;
    Servo verticalRight;
    Servo claw;
    Servo clawrotate;
    DcMotor leftLimit;
    DcMotor rightLift;
    ElapsedTime vTimer;
    String vAction;

    public Vertical(OpMode op) {
        opmode = op;
        vTimer = new ElapsedTime();
        vAction = "RETRACT";

        armLeft = opmode.hardwareMap.get(Servo.class, "al");
        armRight = opmode.hardwareMap.get(Servo.class, "ar");
        verticalLeft = opmode.hardwareMap.get(Servo.class, "vl");
        verticalRight = opmode.hardwareMap.get(Servo.class, "vr");
        claw = opmode.hardwareMap.get(Servo.class, "claw");
        clawrotate = opmode.hardwareMap.get(Servo.class, "rotate");

        //map this properly:
//        rightLift = opmode.hardwareMap.get(DcMotor.class, "vMotor1");
//        leftLimit = opmode.hardwareMap.get(DcMotor.class, "rightLift");

        rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLift.setDirection(DcMotor.Direction.FORWARD);
    }

    public void loop() {
        changeVAction();
        //NORMAL ACTIONS
        if (vAction == "RETRACT") {
            retract();
        }
        if (vAction == "UPPER") {
            upperBucket();
        }
        if (vAction == "LOWER") {
            lowerBucket();
        }
        //SPECIMEN
        if (vAction == "TOP") {
            top();
        }
        if (vAction == "DOWN") {
            down();
        }
        if (vAction == "PICK") {
            pickSpecimen();
        }
        if (vAction == "PLACE") {
            // WIP
        }
    }

    private void retract() {
        claw.setPosition(1);

        rightLift.setPower(-0.2);
        rightLift.setTargetPosition(0);
    }

    private void upperBucket() {
        claw.setPosition(0);

        if (vTimer.milliseconds() >= 10) {
            rightLift.setPower(0.2);
            rightLift.setTargetPosition((int) TICKS_PER_REVOLUTION * 8);
        }
        if (vTimer.milliseconds() >= 100) {
            armLeft.setPosition(1);
            armLeft.setPosition(1);
        } else {
            armLeft.setPosition(1);
            armLeft.setPosition(1);
        }
    }

    private void lowerBucket() {
        // WIP

        claw.setPosition(0);

        if (vTimer.milliseconds() >= 10) {
            rightLift.setPower(-0.2);
            rightLift.setTargetPosition((int) TICKS_PER_REVOLUTION * 4);
        }
        if (vTimer.milliseconds() >= 100) {
            armLeft.setPosition(1);
            armLeft.setPosition(1);
        } else {
            armLeft.setPosition(1);
            armLeft.setPosition(1);
        }
    }

    private void top() {
        // WIP
        armLeft.setPosition(0.5);
        armRight.setPosition(0.5);
        clawrotate.setPosition(0.5);
    }

    private void down() {
        armLeft.setPosition(0.1);
        armRight.setPosition(0.1);
        clawrotate.setPosition(0.5);
    }

    private void pickSpecimen() {
        // WIP
        armLeft.setPosition(1);
        armRight.setPosition(1);
        claw.setPosition(1);
    }

    public void changeVAction() {
        //NORMAL ACTIONS
        if (gamepad1.b && gamepad1.dpad_right && vAction != "RETRACT") {
            vAction = "RETRACT";
            vTimer.reset();
        } else if (gamepad1.b && gamepad1.dpad_left && vAction != "LOWER") {
            vAction = "LOWER";
            vTimer.reset();
        } else if (gamepad1.b && gamepad1.dpad_up && vAction != "UPPER") {
            vAction = "UPPER";
            vTimer.reset();
        }

        //SPECIMEN ACTIONS
        else if (gamepad1.y && gamepad1.dpad_up && vAction != "TOP") {
            vAction = "TOP";
            vTimer.reset();
        } else if (gamepad1.y && gamepad1.dpad_left && vAction != "DOWN") {
            vAction = "DOWN";
            vTimer.reset();
        } else if (gamepad1.y && gamepad1.dpad_down && vAction != "PICK") {
            vAction = "PICK";
            vTimer.reset();
        } else if (gamepad1.y && gamepad1.dpad_right && vAction != "PLACE") {
            vAction = "PLACE";
            vTimer.reset();
        }
    }
}
