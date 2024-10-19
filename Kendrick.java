// import main FTC package
package org.firstinspires.ftc.teamcode;

// imports from FTC package
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.bosch.BHI260IMU;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp

public class Kendrick extends OpMode{
    
    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;
    
    // initialize motor references
    @Override    
    public void init() {
        motor1 = hardwareMap.dcMotor.get("Q1");
        motor2 = hardwareMap.dcMotor.get("Q2");
        motor3 = hardwareMap.dcMotor.get("Q3");
        motor4 = hardwareMap.dcMotor.get("Q4");

        telemetry.addData("Code","Initialized");
    }
    
    // detect gamepad input and move the bot
    @Override
    public void loop(){
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        // call movement function
        StraferChassis(Math.atan2(y, x), Math.sqrt((x*x)+(y*y)), gamepad1.right_stick_x);
    }
    
    // function to set motor powers according to gamepad input
    public void StraferChassis(double theta, double power, double turn){
        
        double sin = Math.sin(theta - Math.PI/4);
        double cos = Math.cos(theta - Math.PI/4);
        
        double m = Math.max(Math.abs(sin), Math.abs(cos));
        
        double leftFront = power * (cos/m) + turn;
        double rightFront = power * (sin/m) - turn;
        double leftBack = power * (sin/m) + turn;
        double rightBack = power * (cos/m) - turn;

        if((power + Math.abs(turn)) > 1){
            leftFront /= power + turn;
            rightFront /= power + turn;
            leftBack /= power + turn;
            rightBack /= power + turn;
        }
        
        motor1.setPower(rightFront);
        motor2.setPower(-leftFront);
        motor3.setPower(-leftBack);
        motor4.setPower(rightBack);
        
        // log gamepad inputs to console
        telemetry.addData("theta", theta);
        telemetry.addData("power", power);
        telemetry.addData("turn", turn);
        
    }
}