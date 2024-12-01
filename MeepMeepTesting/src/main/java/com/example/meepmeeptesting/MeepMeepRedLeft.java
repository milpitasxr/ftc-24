package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepRedLeft {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(0, -72+12, Math.toRadians(90)))
                        .lineTo(new Vector2d(0,-33))

                        //.splineTo(new Vector2d(-10, -36), Math.toRadians(90))
                        .lineTo(new Vector2d(-0.4, -34))
                        .splineToConstantHeading(new Vector2d(-48, -40), Math.toRadians(90))
                        //.lineTo(new Vector2d(-48,-40))
                        .lineToLinearHeading(new Pose2d(-52,-52, Math.toRadians(45)))

                        .lineToLinearHeading(new Pose2d(-58,-40, Math.toRadians(90)))
                        .lineToLinearHeading(new Pose2d(-52,-52, Math.toRadians(45)))


                        .lineToLinearHeading(new Pose2d(-58,-40, Math.toRadians(130)))
                        .lineToLinearHeading(new Pose2d(-52,-52, Math.toRadians(45)))

                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
