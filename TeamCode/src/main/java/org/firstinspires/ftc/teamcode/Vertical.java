package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Vertical {

    Servo armLeft;
    Servo armRight;
    Servo verticalLeft;
    Servo verticalRight;
    Servo claw;
    Servo rotate;

    DcMotor vMotor1;
    DcMotor vMotor2;
    ElapsedTime vTimer;
    String vAction;

    public Vertical() {
        vTimer = new ElapsedTime();
        vAction = "RETRACT";

        armLeft = hardwareMap.get(Servo.class, "al");
        armRight = hardwareMap.get(Servo.class, "ar");
        verticalLeft = hardwareMap.get(Servo.class, "vl");
        verticalRight = hardwareMap.get(Servo.class, "vr");
        claw = hardwareMap.get(Servo.class, "claw");
        rotate = hardwareMap.get(Servo.class, "rotate");

        //map this properly:
        //vMotor1 = hardwareMap.get(DcMotor.class, "vMotor1");
        //vMotor2 = hardwareMap.get(DcMotor.class, "vMotor2");


    }

    public void Update() {
        changeVAction();
        if (vAction == "RETRACT") {
            retract();
        }
    }

    private void retract() {
//      WIP: Function to retract the vertical arm
    }

    public void changeVAction() {
        if (gamepad1.b && gamepad1.dpad_right && vAction != "RETRACT") {
            vAction = "RETRACT";
            vTimer.reset();
        } else if (gamepad1.b && gamepad1.dpad_left && vAction != "LOWER") {
            vAction = "LOWER";
            vTimer.reset();
        } else if (gamepad1.b && gamepad1.dpad_up && vAction != "UPPER") {
            vAction = "UPPER";
            vTimer.reset();
        } else if (gamepad1.b && gamepad1.dpad_down && vAction != "DOWN") {
            vAction = "DOWN";
            vTimer.reset();
        } else if (gamepad1.y && gamepad1.dpad_up && vAction != "TOP") {
            vAction = "TOP";
            vTimer.reset();
        } else if (gamepad1.y && gamepad1.dpad_left && vAction != "MID") {
            vAction = "MID";
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