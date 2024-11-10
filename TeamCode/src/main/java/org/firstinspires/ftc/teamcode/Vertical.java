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
    Servo rotate;
    DcMotor leftLimit;
    DcMotor rightLift;
    ElapsedTime vTimer;
    String vAction;

    public Vertical(OpMode op) {
        opmode = op;
        vTimer = new ElapsedTime();
        vAction = "RETRACT";

        armLeft =  opmode.hardwareMap.get(Servo.class, "al");
        armRight =  opmode.hardwareMap.get(Servo.class, "ar");
        verticalLeft =  opmode.hardwareMap.get(Servo.class, "vl");
        verticalRight =   opmode.hardwareMap.get(Servo.class, "vr");
        claw =  opmode.hardwareMap.get(Servo.class, "claw");
        rotate =  opmode.hardwareMap.get(Servo.class, "rotate");

        //map this properly:
        rightLift = opmode.hardwareMap.get(DcMotor.class, "vMotor1");
        leftLimit = opmode.hardwareMap.get(DcMotor.class, "rightLift");

        rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLift.setDirection(DcMotor.Direction.FORWARD);
    }

    public void loop(){
        changeVAction();
        if(vAction=="RETRACT"){
            retract();
        }
        if(vAction=="UPPER"){
            upper();
        }
        if(vAction=="LOWER"){
            lower();
        }
        if(vAction=="DOWN"){
            down();
        }
        if(vAction=="TOP"){
            top();
        }
    }

    private void retract(){
        rightLift.setPower(-0.2);
        rightLift.setTargetPosition(0);
        opmode.telemetry.addData("ticks", rightLift.getCurrentPosition());
    }

    private void upper(){
        rightLift.setPower(0.2);
        rightLift.setTargetPosition((int) TICKS_PER_REVOLUTION);
        opmode.telemetry.addData("ticks", rightLift.getCurrentPosition());
    }

    private void lower(){
        // WIP
    }

    private void down(){
        // WIP
    }

    private void top(){
        // WIP
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
