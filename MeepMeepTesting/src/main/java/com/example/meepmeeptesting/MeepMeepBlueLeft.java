package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepBlueLeft {

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(0, 72+12, Math.toRadians(270)))
                        .lineTo(new Vector2d(0,33))

                        //.splineTo(new Vector2d(-10, -36), Math.toRadians(90))
                        .lineTo(new Vector2d(-0.4, 34))
                        .splineToConstantHeading(new Vector2d(-48, 42), Math.toRadians(270))
                        .lineToLinearHeading(new Pose2d(-57,52, Math.toRadians(270)))

                        //.lineTo(new Vector2d(-48,-40))
                        //.lineToLinearHeading(new Pose2d(52,-47, Math.toRadians(90)))

                        .lineToLinearHeading(new Pose2d(-58,42, Math.toRadians(270)))
                        .lineToLinearHeading(new Pose2d(-57,52, Math.toRadians(270)))

                        //.lineToLinearHeading(new Pose2d(58,-47, Math.toRadians(90)))

                        .lineToLinearHeading(new Pose2d(-56,42, Math.toRadians(240)))
                        .lineToLinearHeading(new Pose2d(-56,52, Math.toRadians(270)))


                        //.lineToLinearHeading(new Pose2d(63,-42, Math.toRadians(90)))
                        //.lineToLinearHeading(new Pose2d(68,-47, Math.toRadians(90)))
//
//
//                        .lineToLinearHeading(new Pose2d(58,-30, Math.toRadians(130)))
//                        .lineToLinearHeading(new Pose2d(52,-48, Math.toRadians(270)))

                        .build());




//                        //.splineTo(new Vector2d(-10, -36), Math.toRadians(90))
//                        .lineTo(new Vector2d(-0.4, -34))
//                        .splineToConstantHeading(new Vector2d(-48, -40), Math.toRadians(90))
//                        //.lineTo(new Vector2d(-48,-40))
//                        .lineToLinearHeading(new Pose2d(-52,-52, Math.toRadians(45)))
//
//                        .lineToLinearHeading(new Pose2d(-58,-40, Math.toRadians(90)))
//                        .lineToLinearHeading(new Pose2d(-52,-52, Math.toRadians(45)))
//
//
//                        .lineToLinearHeading(new Pose2d(-58,-40, Math.toRadians(130)))
//                        .lineToLinearHeading(new Pose2d(-52,-52, Math.toRadians(45)))



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
