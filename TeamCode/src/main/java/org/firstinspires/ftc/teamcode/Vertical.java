package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Vertical {

    double TICKS_PER_REVOLUTION = 751.8;

    OpMode opmode;

    Servo armLeft;
    Servo armRight;
    Servo claw;
    Servo clawrotate;
    DcMotor leftLift;
    DcMotor rightLift;
    ElapsedTime vTimer;
    String vAction;
    boolean clawStatus;

    public Vertical(OpMode op) {
        opmode = op;
        vTimer = new ElapsedTime();
        vAction = "RETRACT";
        clawStatus = false;

        armLeft = opmode.hardwareMap.get(Servo.class, "a1");
        armRight = opmode.hardwareMap.get(Servo.class, "a2");
        leftLift = opmode.hardwareMap.dcMotor.get("lift1");
        rightLift = opmode.hardwareMap.dcMotor.get("lift2");
        clawrotate = opmode.hardwareMap.get(Servo.class, "c1");
        claw = opmode.hardwareMap.get(Servo.class, "c2");

        leftLift.setDirection(DcMotor.Direction.FORWARD);
        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void loop() {
        changeVAction();
        opmode.telemetry.addData("status", vAction);

        // LIFT ACTIONS
        if (vAction == "RETRACT") {
            retract();
        }
        if (vAction == "UPPER") {
            upperBucket();
        }
        if (vAction == "LOWER") {
            lowerBucket();
        }

        //SPECIMEN ACTIONS
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

    // Move lift to a desired position with desired speed
    public void moveToPosition(DcMotor lift, double position, double speed) {
        lift.setTargetPosition((int) (TICKS_PER_REVOLUTION * position));
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(speed);
    }

    private void retract(){
        claw.setPosition(0.35);

        if (vTimer.milliseconds() >= 5){
            armLeft.setPosition(0.98);
            armRight.setPosition(0.02);
        }

        if (vTimer.milliseconds() >= 15){
            clawrotate.setPosition(0.5);
        }

        if (vTimer.milliseconds() >= 20){
            moveToPosition(leftLift, 0, 0.9);
        }
    }

    private void upperBucket(){
        if (clawStatus == false){
            claw.setPosition(1);
        }
        else {
            claw.setPosition(0.3);
        }

        if (vTimer.milliseconds() >= 50){

            moveToPosition(leftLift, 6, 0.9);
        }

        if (vTimer.milliseconds() >= 80){
            armLeft.setPosition(0.3);
            armRight.setPosition(0.7);
            clawrotate.setPosition(0.25);
            clawStatus = true;
        }

        if (opmode.gamepad1.b && opmode.gamepad1.left_bumper){
            claw.setPosition(0.1);
        }
    }

    private void lowerBucket() {
        claw.setPosition(1);

        if (vTimer.milliseconds() >= 50){
            moveToPosition(leftLift, 2, 0.9);
        }

        if (vTimer.milliseconds() >= 80){
            armLeft.setPosition(0.3);
            armRight.setPosition(0.7);
            clawrotate.setPosition(0.25);
        }

        if (opmode.gamepad1.b && opmode.gamepad1.left_bumper){
            claw.setPosition(0.1);
        }
    }

    private void top() {
        claw.setPosition(1);

        if (vTimer.milliseconds() >= 40){
            armLeft.setPosition(0.8);
            armRight.setPosition(0.2);
            clawrotate.setPosition(0.25);
        }
        // place specimen
    }

    private void down() {
        claw.setPosition(1);

        if (vTimer.milliseconds() >= 40){
            armLeft.setPosition(0.8);
            armRight.setPosition(0.2);
            clawrotate.setPosition(0.25);
        }
        // place specimen
    }

    private void pickSpecimen() {
        armRight.setPosition(1);
        claw.setPosition(0.3);
    }

    private void release(){

    }

    public void changeVAction() {
        // LIFT ACTIONS
        if (opmode.gamepad1.b && opmode.gamepad1.dpad_right && vAction != "RETRACT") {
            vAction = "RETRACT";
            vTimer.reset();
        } else if (opmode.gamepad1.b && opmode.gamepad1.dpad_left && vAction != "LOWER") {
            vAction = "LOWER";
            vTimer.reset();
        } else if (opmode.gamepad1.b && opmode.gamepad1.dpad_up && vAction != "UPPER") {
            vAction = "UPPER";
            vTimer.reset();
        }

        // SPECIMEN ACTIONS
        else if (opmode.gamepad1.y && opmode.gamepad1.dpad_up && vAction != "TOP") {
            vAction = "TOP";
            vTimer.reset();
        } else if (opmode.gamepad1.y && opmode.gamepad1.dpad_left && vAction != "DOWN") {
            vAction = "DOWN";
            vTimer.reset();
        } else if (opmode.gamepad1.y && opmode.gamepad1.dpad_down && vAction != "PICK") {
            vAction = "PICK";
            vTimer.reset();
        } else if (opmode.gamepad1.y && opmode.gamepad1.dpad_right && vAction != "PLACE") {
            vAction = "PLACE";
            vTimer.reset();
        }
    }
}
