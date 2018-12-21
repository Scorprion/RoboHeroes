package Atlas.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

import Atlas.Autonomous.Init.HardwareAtlas;

@TeleOp(name= "AtlasTeleOp", group= "Pushbot")
public class AtlasTeleOp extends OpMode {
    double ShoulderSpeed = 0;
    double ElbowSpeed = 0;
    double turnspeed = 0;
    double speed = 0;
    double RElbowSpeed = 0;
    double LElbowSpeed = 0;
    
    //Set the speed of the motors when the Left or Right sticks are not idle

    HardwareAtlas robot = new HardwareAtlas();
    
    public void init() {
        robot.init(hardwareMap);
        /*Winch1 = hardwareMap.dcMotor.get("Winch1");
        Winch2 = hardwareMap.dcMotor.get("Winch2");*/



        //DriveL.setDirection(DcMotor.Direction.REVERSE);
        robot.LShoulder.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void loop() {
        ShoulderSpeed = 0.65;
        RElbowSpeed = gamepad2.right_stick_y * 0.5;
        LElbowSpeed = gamepad2.left_stick_y * 0.5;
        turnspeed = gamepad1.right_stick_x;
        speed = gamepad1.left_stick_y;
        telemetry.addData("The speed for both motors", speed);
        telemetry.addData("The speed for both motors in turning", turnspeed);

        /*
        -----------------------------
        |                           |
        |                           |
        |         Gamepad 2         |
        |                           |
        |                           |
        -----------------------------
         */
        //The Shoulders
        if (gamepad2.a) {
            robot.LShoulder.setPower(ShoulderSpeed);
            robot.RShoulder.setPower(ShoulderSpeed);
        } else {
            ShoulderSpeed = 0;
        }

        if (gamepad2.b) {
            robot.LShoulder.setPower(-ShoulderSpeed);
            robot.RShoulder.setPower(-ShoulderSpeed);
        } else {
            ShoulderSpeed = 0;
        }


        //The LElbow
        if (gamepad2.left_stick_y > 0.1 || gamepad2.left_stick_y < -0.1) {
            robot.LElbow.setPower(LElbowSpeed);
        } else {
            ElbowSpeed = 0;
        }

        if (gamepad2.right_stick_y > 0.1 || gamepad2.right_stick_y < -0.1) {
            robot.RElbow.setPower(RElbowSpeed);
        }

        //The LClamp
        if (gamepad2.left_trigger > 0.1 || gamepad2.left_trigger < -0.1) {
            robot.LClamp.setPosition(1);
            try {
                //Wait 250 milliseconds before stopping the movement of the clamp
                TimeUnit.MILLISECONDS.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            robot.LClamp.setPosition(0.5);
        }
        if (gamepad2.left_bumper) {
            robot.LClamp.setPosition(0);
        }

        //The RClamp
        if (gamepad2.right_trigger > 0.1 || gamepad2.right_trigger < -0.1) {
            robot.RClamp.setPosition(1);
            try {
                //Wait 250 milliseconds before stopping the movement of the clamp
                TimeUnit.MILLISECONDS.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            robot.RClamp.setPosition(0.5);
        }
        if (gamepad2.right_bumper) {
            robot.RClamp.setPosition(0);
        }



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
            robot.Left.setPower(turnspeed);
            robot.Right.setPower(turnspeed);
        } else {
            robot.Left.setPower(0);
            robot.Right.setPower(0);
        }

        //Moving
        if (gamepad1.left_stick_y >= 0.1 || gamepad1.left_stick_y <= -0.1) {
            robot.Left.setPower(-speed);
            robot.Right.setPower(speed);
        } else {
            robot.Left.setPower(0);
            robot.Right.setPower(0);
        }

        //Tilting and resetting back our marker servo platform
        if(gamepad1.a) {
            robot.Marker.setPosition(1.0);
        }
        if(gamepad1.b) {
            robot.Marker.setPosition(0);
        }
        //The winch
        /*if (gamepad2.robot.Left_bumper) {
            Winch1.setPower(1);
            Winch2.setPower(1);
        } else {
            Winch1.setPower(0);
            Winch2.setPower(0);


        }*/

    }
}
