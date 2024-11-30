// Import main FTC package
package org.firstinspires.ftc.teamcode;

// Imports from FTC package

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp
public class Kendrick extends OpMode {

    Drivetrain dt;
    Horizontal h;
    Vertical v;

    @Override
    public void init() {

        dt = new Drivetrain(this);
        v = new Vertical(this);
        h = new Horizontal(this);
    }

    // Main loop
    @Override
    public void loop() {
        // Detect gamepad inputs and call movement function

        dt.StraferChassis();

        v.updateAction();
        v.loop();

        h.updateAction();
        h.loop();

    }


}