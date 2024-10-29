package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Horizontal {
    ElapsedTime intakeTimer;
    String intakeAction;

    public Horizontal() {
        intakeTimer = new ElapsedTime();
        intakeAction = "RETRACT";
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
