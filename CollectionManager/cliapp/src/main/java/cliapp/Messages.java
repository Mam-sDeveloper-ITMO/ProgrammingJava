package cliapp;

import java.util.ResourceBundle;

/**
 * That class provides handy static access for project text resources
 */
public class Messages {
    /**
     * cliapp.commands.collection ElementRequiredCommand
     */
    public static class ElementCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
                .getBundle("cliapp.commands.collection.element");

        public static final String ID = BUNDLE.getString("Id");
        public static final String ID_DESCR = BUNDLE.getString("IdDescr");
        public static final String ID_NOT_POSITIVE = BUNDLE.getString("IdNotPositive");
        public static final String ID_NOT_EXISTS = BUNDLE.getString("IdNotExists");
        public static final String NAME = BUNDLE.getString("Name");
        public static final String NAME_DESCR = BUNDLE.getString("NameDescr");
        public static final String COORDINATE_X = BUNDLE.getString("CoordinateX");
        public static final String COORDINATE_X_DESCR = BUNDLE.getString("CoordinateXDescr");
        public static final String COORDINATE_X_VALIDATION_ERROR = BUNDLE.getString("CoordinateXValidationError");
        public static final String COORDINATE_Y = BUNDLE.getString("CoordinateY");
        public static final String COORDINATE_Y_DESCR = BUNDLE.getString("CoordinateYDescr");
        public static final String COORDINATE_Y_VALIDATION_ERROR = BUNDLE.getString("CoordinateYValidationError");
        public static final String REAL_HERO = BUNDLE.getString("RealHero");
        public static final String REAL_HERO_DESCR = BUNDLE.getString("RealHeroDescr");
        public static final String HAS_TOOTHPICK = BUNDLE.getString("HasToothpick");
        public static final String HAS_TOOTHPICK_DESCR = BUNDLE.getString("HasToothpickDescr");
        public static final String IMPACT_SPEED = BUNDLE.getString("ImpactSpeed");
        public static final String IMPACT_SPEED_DESCR = BUNDLE.getString("ImpactSpeedDescr");
        public static final String SOUNDTRACK_NAME = BUNDLE.getString("SoundtrackName");
        public static final String SOUNDTRACK_NAME_DESCR = BUNDLE.getString("SoundtrackNameDescr");
        public static final String MINUTES_OF_WAITING = BUNDLE.getString("MinutesOfWaiting");
        public static final String MINUTES_OF_WAITING_DESCR = BUNDLE.getString("MinutesOfWaitingDescr");
        public static final String MOOD = BUNDLE.getString("Mood");
        public static final String MOOD_DESCR = BUNDLE.getString("MoodDescr");
        public static final String MOOD_BY_NUMBER_NOT_EXISTS = BUNDLE.getString("MoodByNumberNotExists");
        public static final String MOOD_BY_NAME_NOT_EXISTS = BUNDLE.getString("MoodByNameNotExists");
        public static final String MOOD_VALIDATION_ERROR = BUNDLE.getString("MoodValidationError");
        public static final String CAR_NAME = BUNDLE.getString("CarName");
        public static final String CAR_NAME_DESCR = BUNDLE.getString("CarNameDescr");
    }
}
