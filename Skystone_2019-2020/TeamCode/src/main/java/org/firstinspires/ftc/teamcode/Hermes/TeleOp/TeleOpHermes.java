package org.firstinspires.ftc.teamcode.Hermes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name= "TeleOp Hermes", group= "Pushbot")
public class TeleOpHermes extends OpMode {
    //Making the slower arm and elbow toggle (driver 2)
    private ElapsedTime openClaw = new ElapsedTime();
    private boolean switchedS = false;
    private boolean usedRecently = false;
    private double controlSpeedE = 1, controlSpeedS = 1;

    //Making the slower robot toggle (driver 1)
    private ElapsedTime rmove = new ElapsedTime();

    private boolean robotCSpeed = false; // the booleaor the robot's speed to be able to slow it down
    private boolean robotUsedRecent = false;
    private double robotControlSpeed = 1;

    private double turnspeed = 0;
    private double strafespeed = 0;
    private double speed = 0;

    DcMotor BackLeft, BackRight, FrontLeft, FrontRight;

    public void init() {
        FrontRight = hardwareMap.get(DcMotor.class, "FrontRight");
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FrontLeft = hardwareMap.get(DcMotor.class, "FrontLeft");
        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //DriveL.setDirection(DcMotor.Direction.REVERSE);

        BackRight = hardwareMap.get(DcMotor.class, "BackRight");
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BackLeft = hardwareMap.get(DcMotor.class, "BackLeft");
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        turnspeed = gamepad1.right_stick_x * robotControlSpeed;
        strafespeed = gamepad1.left_stick_x * robotControlSpeed;
        speed = gamepad1.left_stick_y * -robotControlSpeed;

        /*
        -----------------------------
        |                           |
        |                           |
        |         Gamepad 1         |
        |                           |
        |                           |
        -----------------------------
         */
        //Turning
        if (gamepad1.right_stick_x >= 0.1 || gamepad1.right_stick_x <= -0.1) {
                FrontRight.setPower(-turnspeed);
                BackRight.setPower(-turnspeed);
                FrontLeft.setPower(turnspeed);
                BackLeft.setPower(turnspeed);
        }

        //Strafing
        FrontRight.setPower(speed -strafespeed);
        BackRight.setPower(speed + strafespeed);
        FrontLeft.setPower(speed + strafespeed);
        BackLeft.setPower(speed -strafespeed);

        telemetry.addData("Speeds: ", "%.5f, %.5f, %.5f", (speed), (strafespeed), (turnspeed));
        telemetry.update();
    }

}