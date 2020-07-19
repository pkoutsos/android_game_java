package suza.project.crazyballs.state;

import suza.project.crazyballs.util.Util;

public class LevelConfig {

        public int good_ball_score = 10;
        public int bad_ball_score = -5;

        public int getNormalBallSpawnPeriod() {
                return normalBallSpawnPeriod;
        }

        public int getLifeBallSpawnPeriod() {
                return lifeBallSpawnPeriod;
        }

        public int getBadBallSpawnPeriod() {
                return badBallSpawnPeriod;
        }

        public int getStarSpawnPeriod() {
                return starSpawnPeriod;
        }

        public int getLifeSaverSpawnPeriod() {
                return lifeSaverSpawnPeriod;
        }

        private int normalBallSpawnPeriod = 2000; //ms
        private int lifeBallSpawnPeriod = 10000; // ms
        private int starSpawnPeriod = 20000; // ms
        private int lifeSaverSpawnPeriod = 20000; // ms
        private int badBallSpawnPeriod = 5000;  // ms

        private double multiplier;

        LevelConfig(double multiplier) {
                this.multiplier = multiplier;
                good_ball_score /= multiplier;
                randomizeNormalPeriod();
                randomizeBadPeriod();
                randomizeLifePeriod();
        }

        public static LevelConfig generate_hard() {
                return new LevelConfig(0.7);
        }

        public static LevelConfig generate_medium() {
                return new LevelConfig(1);
        }

        public static LevelConfig generate_easy() {
                return new LevelConfig(1.3);
        }

        public void randomizeNormalPeriod() {
                normalBallSpawnPeriod = (int) (Util.randomInteger(3, 5) * 1000 * multiplier);
        }

        public void randomizeBadPeriod() {
                badBallSpawnPeriod = (int) (Util.randomInteger(7, 9) * 1000 * multiplier);
        }

        public void randomizeLifePeriod() {
                lifeBallSpawnPeriod = (int) (Util.randomInteger(7, 12) * 1000 * multiplier);
        }

        public void randomizeStarPeriod() {
                starSpawnPeriod = (int) (Util.randomInteger(17, 22) * 1000 * multiplier);
        }

        public void randomizeLifeSaverPeriod() {
                lifeSaverSpawnPeriod = (int) (Util.randomInteger(17, 22) * 1000 * multiplier);
        }
}
