package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Vertical {
    ElapsedTime bucketTimer;
    String bucketAction;

    public Vertical() {
        bucketTimer = new ElapsedTime();
        bucketAction = "RETRACT";
    }
    public void changeVAction() {
        if (gamepad1.b && gamepad1.dpad_right && bucketAction != "RETRACT") {
            bucketAction = "RETRACT";
            bucketTimer.reset();
        } else if (gamepad1.b && gamepad1.dpad_left && bucketAction != "LOWER") {
            bucketAction = "LOWER";
            bucketTimer.reset();
        } else if (gamepad1.b && gamepad1.dpad_up && bucketAction != "UPPER") {
            bucketAction = "UPPER";
            bucketTimer.reset();
        } else if (gamepad1.b && gamepad1.dpad_down && bucketAction != "DOWN") {
            bucketAction = "DOWN";
            bucketTimer.reset();
        } else if (gamepad1.y && gamepad1.dpad_up && bucketAction != "TOP") {
            bucketAction = "TOP";
            bucketTimer.reset();
        } else if (gamepad1.y && gamepad1.dpad_left && bucketAction != "MID") {
            bucketAction = "MID";
            bucketTimer.reset();
        } else if (gamepad1.y && gamepad1.dpad_down && bucketAction != "PICK") {
            bucketAction = "PICK";
            bucketTimer.reset();
        } else if (gamepad1.y && gamepad1.dpad_right && bucketAction != "PLACE") {
            bucketAction = "PLACE";
            bucketTimer.reset();
        }
    }
}
