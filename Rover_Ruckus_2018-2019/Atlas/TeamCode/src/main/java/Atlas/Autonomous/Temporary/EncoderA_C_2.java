package Atlas.Autonomous.Temporary;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

import Atlas.Autonomous.Init.AggregatedClass;
import Atlas.Autonomous.Init.HardwareAtlas;

@Autonomous(name = "EnocderA_C_2", group = "Auto")
public class EncoderA_C_2 extends AggregatedClass {
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();
        movement();
    }


    public void movement() {
        //Landing
        robot.angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        robot.Latching.setPower(0.8);
        robot.Winch.setPower(-1);
        sleep(2000);
        encoderDrives(0.5, 1, -1);
        sleep(250);
        robot.Latching.setPower(-1);
        sleep(500);
        encoderDrives(0.5, -1, 1);
        sleep(250);
        robot.Latching.setPower(0);
        robot.Winch.setPower(0);
        stopMotors();

        //Enocders
        encoderDrives(0.7, 38, 38, 5);

        //ColorSensor
        markerAC2();
    }
}
