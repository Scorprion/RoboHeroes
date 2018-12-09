package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "AutonomousB_D_3", group = "Auto")
@Disabled
public class AutonomousB_D_3 extends LinearOpMode {

    HardwareMapInit robot  = new HardwareMapInit();   // Use a Pushbot's hardware
    public boolean colorFound = false;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        // Turn on light through the program
        if (robot.Color_Sensor instanceof SwitchableLight) {
            ((SwitchableLight) robot.Color_Sensor).enableLight(true);
        }

        waitForStart();
        movement();
    }

    public void cs() {
        //Making sure the motors go backward at 0.3 speed
        robot.Left.setPower(0);
        robot.Right.setPower(0);

        robot.Left.setPower(-0.2);
        robot.Right.setPower(-0.2);

        //added second pause also for debugging
        sleep(250);
        while(!colorFound) {
            float[] hsvValues = new float[3];
            final float values[] = hsvValues;
            robot.Color_Sensor = hardwareMap.get(NormalizedColorSensor.class, "ColorSensor");
            NormalizedRGBA colors = robot.Color_Sensor.getNormalizedColors();
            Color.colorToHSV(colors.toColor(), hsvValues);
            telemetry.addLine()
                    .addData("H", "%.3f", hsvValues[0])
                    .addData("S", "%.3f", hsvValues[1])
                    .addData("V", "%.3f", hsvValues[2]);
            telemetry.addLine()
                    .addData("a", "%.3f", colors.alpha)
                    .addData("r", "%.3f", colors.red)
                    .addData("g", "%.3f", colors.green)
                    .addData("b", "%.3f", colors.blue);
            int color = colors.toColor();
            telemetry.addLine("raw Android color: ")
                    .addData("a", "%02x", Color.alpha(color))
                    .addData("r", "%02x", Color.red(color))
                    .addData("g", "%02x", Color.green(color))
                    .addData("b", "%02x", Color.blue(color));
            float max = Math.max(Math.max(Math.max(colors.red, colors.green), colors.blue), colors.alpha);
            colors.red /= max;
            colors.green /= max;
            colors.blue /= max;
            color = colors.toColor();
            telemetry.addLine("normalized color:  ")
                    .addData("a", Color.alpha(color))
                    .addData("r", Color.red(color))
                    .addData("g", Color.green(color))
                    .addData("b", Color.blue(color));
            telemetry.update();

            // Detects a change in the color and then stops robot after the red or blue values
            // reach a certain threshold. After that, it drops our team marker
            if(Color.blue(color) >= 125 || Color.red(color) >= 140) {
                robot.Left.setPower(0);
                robot.Right.setPower(0);
                //turn 180 degrees
                //turn(-0.3, 4);
                //Drop the team marker, wait a second, then reset the dropper
                sleep(1000);
                //Set the boolean "colorFound" to true to stop the repeating while loop
                colorFound = true;
            }
        }
    }

    public void movement() {
        forward(-0.4, 1);
        sleep(1000);
        forward(0.4, 0.35);
        sleep(1000);
        robot.Left.setPower(0);
        robot.Right.setPower(0);
        sleep(1000);
        turn(0.4, 1.225);
        sleep(1000);
        //turn(-0.4, 1.45);
        forward(-0.4, 1.825);
        sleep(1000);
        turn(0.45, 0.45);
        cs();
        forward(0.4, 1);
        robot.Left.setPower(0);
        robot.Right.setPower(0.5);
        sleep(1500);
        forward(-0.4, 2);

        //stop all motion
        stopMotion();
    }

    public void forward(double speed, double seconds) {
        double time = seconds * 1000;

        //Move at the speed "speed" and pause for "seconds" amount of time before stopping
        robot.Left.setPower(speed);
        robot.Right.setPower(speed);
        sleep((long)time);
        robot.Left.setPower(0);
        robot.Right.setPower(0);
    }

    public void turn(double speed, double seconds) {
        double time = seconds * 1000;

        //turn at speed "speed" and pause for "seconds" amount of time before stopping
        robot.Left.setPower(-speed);
        robot.Right.setPower(speed);
        sleep((long)time);
        robot.Left.setPower(0);
        robot.Right.setPower(0);
    }

    public void stopMotion() {
        robot.Left.setPower(0);
        robot.Right.setPower(0);
    }
}
