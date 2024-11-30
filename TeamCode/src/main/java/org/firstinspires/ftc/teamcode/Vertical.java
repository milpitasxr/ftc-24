package org.firstinspires.ftc.teamcode;

//import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
@Config
public class Vertical {

    double TICKS_PER_REVOLUTION = 751.8;

    OpMode opmode;

    Servo armLeft;
    Servo armRight;
    Servo claw;
    Servo clawrotate;
    DcMotor leftLift;
    DcMotor rightLift;

    ElapsedTime timer;
    String action;

    public static double RELEASE_CLAW = 0;
    public static double TIGHT_CLAW = 1;
    public static double LOOSE_CLAW = 0.5;

    public static double UPPER_LIFT = 6;
    public static double LOWER_LIFT = 2;
    public static double SCORE_CLAW_JOINT = 0.5;
    public static double SCORE_ARM = 0.7;

    public static double RETRACT_ARM = 0.02;//from reference of right arm
    public static double RETRACT_CLAW_JOINT = 0;

    public static double SPECIMEN_ARM = 0.2;
    public static double SPECIMEN_CLAW_JOINT = 0.5;
    public static double TOP_RUNG_LIFT = 4;
    public static double LOW_RUNG_LIFT = 1;

    public static double HOOK_ARM = 0.15;
    public static double HOOK_CLAW_JOINT = 0.5;

    public Vertical(OpMode op) {
        opmode = op;
        timer = new ElapsedTime();
        action = "RETRACT";

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

        // LIFT ACTIONS
        if (action == "retract") {
            retract();
        }
        if (action == "scoreUpperBucket") {
            scoreBucket(true);
        }
        if (action == "scoreLowerBucket") {
            scoreBucket(false);
        }

        //SPECIMEN ACTIONS
        if (action == "placeTopRung") {
            placeSpecimen(true);
        }
        if (action == "placeLowRung") {
            placeSpecimen(false);
        }
        if (action == "pickSpecimen") {
            pickSpecimen();
        }
        if (action == "hookSpecimen") {
            hookSpecimen();
        }
    }

    // Move lift to a desired position with desired speed
    public void moveToPosition(DcMotor lift, double position, double speed) {
        lift.setTargetPosition((int) (TICKS_PER_REVOLUTION * position));
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(speed);
    }

    private void retract(){
        if(timer.milliseconds() <= 5){
            claw.setPosition(RELEASE_CLAW);//open claw
        }

        else if (timer.milliseconds() <=10){
            claw.setPosition(RELEASE_CLAW);//open claw

            armLeft.setPosition(1-RETRACT_ARM);//lower arm
            armRight.setPosition(RETRACT_ARM);
            clawrotate.setPosition(RETRACT_CLAW_JOINT);//rotate into position
        }
        else{
            claw.setPosition(RELEASE_CLAW);//open claw
            armLeft.setPosition(1-RETRACT_ARM);//lower arm
            armRight.setPosition(RETRACT_ARM);
            clawrotate.setPosition(RETRACT_CLAW_JOINT);//rotate into position

            moveToPosition(leftLift, 0, 0.9);
            moveToPosition(rightLift, 0, 0.9);
        }
    }

    private void scoreBucket(boolean upper){
        double liftTarget = LOWER_LIFT;
        if(upper){liftTarget = UPPER_LIFT;}

        if(timer.milliseconds() <=50){
            claw.setPosition(LOOSE_CLAW);//this should be loose grip

        }

        else if (timer.milliseconds() <=80){
            //start moving up
            claw.setPosition(LOOSE_CLAW);//this should be loose grip
            moveToPosition(leftLift, liftTarget, 0.5);
            moveToPosition(rightLift, liftTarget, 0.5);

        }

        else{
            moveToPosition(leftLift, liftTarget, 0.9);
            moveToPosition(rightLift, liftTarget, 0.9);

            claw.setPosition(TIGHT_CLAW);//this should now be hard stiff grip on the sampel
            clawrotate.setPosition(SCORE_CLAW_JOINT); //this should keep the claw in the correct angle to score into the bucket
            armLeft.setPosition(1-SCORE_ARM);
            armRight.setPosition(SCORE_ARM);

        }
    }

    private void placeSpecimen(boolean top) {

        if (timer.milliseconds() <= 40){
            claw.setPosition(TIGHT_CLAW);//grip the specimen

        }else{
            claw.setPosition(TIGHT_CLAW);//grip the specimen
            armLeft.setPosition(1-SPECIMEN_ARM);//move arm and claw to correct position
            armRight.setPosition(SPECIMEN_ARM);
            clawrotate.setPosition(SCORE_CLAW_JOINT);

            if(top){
                moveToPosition(leftLift, TOP_RUNG_LIFT, 0.9);
                moveToPosition(rightLift, LOW_RUNG_LIFT, 0.9);
            }else{
                moveToPosition(leftLift, TOP_RUNG_LIFT, 0.9);
                moveToPosition(rightLift, LOW_RUNG_LIFT, 0.9);
            }

        }
    }

    private void pickSpecimen() {
        if(timer.milliseconds()<=400){
            claw.setPosition(RELEASE_CLAW);//now we open the claw
            armRight.setPosition(0.9);
            armLeft.setPosition(0.1);
            //we need to first use both servos so we have enough torque adn power to actually rotate it 270 degrees from retract
        }else{
            armRight.setPosition(1);//now we set the arm fully down
            claw.setPosition(RELEASE_CLAW);//now we open the claw
        }
    }

    private void hookSpecimen(){
        if(timer.milliseconds()<=30) {
            armLeft.setPosition(1-HOOK_ARM);//lowers the arm a bit to hook the specimen
            armRight.setPosition(HOOK_ARM);//0.15
            clawrotate.setPosition(HOOK_CLAW_JOINT);//0.5
            claw.setPosition(TIGHT_CLAW);
        }

        if(timer.milliseconds() <=80){
            armLeft.setPosition(1-HOOK_ARM);//lowers the arm a bit to hook the specimen
            armRight.setPosition(HOOK_ARM);
            clawrotate.setPosition(HOOK_CLAW_JOINT);
            claw.setPosition(RELEASE_CLAW);//releases the claw so that we can later go back to retract
        }else if(timer.milliseconds()<=90){

            armLeft.setPosition(1-RETRACT_ARM);//lower arm and rotate claw into position
            armRight.setPosition(RETRACT_ARM);
            clawrotate.setPosition(RETRACT_CLAW_JOINT);
            claw.setPosition(RELEASE_CLAW);
        }
    }

    public void changeAction(String newAction){
        if(newAction!=action){
            action = newAction;
            timer.reset();
        }

    }

    public void updateAction() {
        // LIFT ACTIONS
        if (opmode.gamepad1.b && opmode.gamepad1.dpad_right) {
            changeAction("retract");
        } else if (opmode.gamepad1.b && opmode.gamepad1.dpad_left) {
            changeAction("retract");

        } else if (opmode.gamepad1.b && opmode.gamepad1.dpad_up) {
            changeAction("retract");

        }

        // SPECIMEN ACTIONS
        else if (opmode.gamepad1.y && opmode.gamepad1.dpad_up) {
            changeAction("retract");

        } else if (opmode.gamepad1.y && opmode.gamepad1.dpad_left) {
            changeAction("retract");

        } else if (opmode.gamepad1.y && opmode.gamepad1.dpad_down) {
            changeAction("retract");

        } else if (opmode.gamepad1.y && opmode.gamepad1.dpad_right) {
            changeAction("retract");

        }
    }
}