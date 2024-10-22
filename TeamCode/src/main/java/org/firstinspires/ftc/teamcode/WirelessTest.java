// Use this file to test the wireless debugging/send code over directly to control hub

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp

public class WirelessTest extends OpMode {
    // define i to increment and show up on the screen of the phone
    int i;

    @Override
    public void init(){
        i = 0;
        // see if the code works
        telemetry.addData("Bot", "Initialized");
    }

    @Override
    public void loop(){
        // increment and update the value of i
        i++;
        telemetry.addData("Count of I", i);
    }
}
