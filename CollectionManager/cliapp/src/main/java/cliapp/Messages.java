package cliapp;

import java.util.ResourceBundle;

/**
 * That class provides handy static access for project text resources
 */
public class Messages {
    /**
     * cliapp.cliclient
     */
    public static class CLIClient {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.cliclient.cliclient");

        public static final String COMMAND_NOT_FOUND_ERROR =
            BUNDLE.getString("CommandNotFoundError");

        public static final String INLINE_PARAMS_COUNT_ERROR =
            BUNDLE.getString("InlineParamsCountError");

        public static final String ASK_REQUIREMENT = BUNDLE.getString("AskRequirement");

        public static final String ASK_DEFAULT_REQUIREMENT =
            BUNDLE.getString("AskDefaultRequirement");

        public static final String ASK_REQUIREMENT_ATTEMPTS_ERROR =
            BUNDLE.getString("AskRequirementAttemptsError");

        public static final String ASK_REQUIREMENT_WITH_ATTEMPTS =
            BUNDLE.getString("AskRequirementWithAttempts");
    }

    /**
     * cliapp.commands.collection.requirements
     */
    public static class ElementRequirements {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.element");

        public static final String ID = BUNDLE.getString("Id");

        public static final String ID_DESCR = BUNDLE.getString("IdDescr");

        public static final String ID_NOT_POSITIVE = BUNDLE.getString("IdNotPositive");

        public static final String ID_NOT_EXISTS = BUNDLE.getString("IdNotExists");

        public static final String ID_EXISTS = BUNDLE.getString("IdExists");

        public static final String NAME = BUNDLE.getString("Name");

        public static final String NAME_DESCR = BUNDLE.getString("NameDescr");

        public static final String COORDINATE_X = BUNDLE.getString("CoordinateX");

        public static final String COORDINATE_X_DESCR = BUNDLE.getString("CoordinateXDescr");

        public static final String COORDINATE_Y = BUNDLE.getString("CoordinateY");

        public static final String COORDINATE_Y_DESCR = BUNDLE.getString("CoordinateYDescr");

        public static final String REAL_HERO = BUNDLE.getString("RealHero");

        public static final String REAL_HERO_DESCR = BUNDLE.getString("RealHeroDescr");

        public static final String HAS_TOOTHPICK = BUNDLE.getString("HasToothpick");

        public static final String HAS_TOOTHPICK_DESCR = BUNDLE.getString("HasToothpickDescr");

        public static final String IMPACT_SPEED = BUNDLE.getString("ImpactSpeed");

        public static final String IMPACT_SPEED_DESCR = BUNDLE.getString("ImpactSpeedDescr");

        public static final String SOUNDTRACK_NAME = BUNDLE.getString("SoundtrackName");

        public static final String SOUNDTRACK_NAME_DESCR = BUNDLE.getString("SoundtrackNameDescr");

        public static final String MINUTES_OF_WAITING = BUNDLE.getString("MinutesOfWaiting");

        public static final String MINUTES_OF_WAITING_DESCR =
            BUNDLE.getString("MinutesOfWaitingDescr");

        public static final String MOOD = BUNDLE.getString("Mood");

        public static final String MOOD_DESCR = BUNDLE.getString("MoodDescr");

        public static final String MOODS_TITLE = BUNDLE.getString("MoodsTitle");

        public static final String MOOD_BY_NUMBER_NOT_EXISTS =
            BUNDLE.getString("MoodByNumberNotExists");

        public static final String MOOD_BY_NAME_NOT_EXISTS =
            BUNDLE.getString("MoodByNameNotExists");

        public static final String MOOD_VALIDATION_ERROR = BUNDLE.getString("MoodValidationError");

        public static final String CAR_NAME = BUNDLE.getString("CarName");

        public static final String CAR_NAME_DESCR = BUNDLE.getString("CarNameDescr");
    }

    /**
     * cliapp.commands.collection InfoCommand
     */
    public static class InfoCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("InfoName");

        public static final String DESCRIPTION = BUNDLE.getString("InfoDescription");

        public static final String SUCCESS = BUNDLE.getString("InfoSuccess");

        public static final String TYPE = BUNDLE.getString("InfoType");

        public static final String INIT_TIME = BUNDLE.getString("InfoInitTime");

        public static final String ELEMENTS_COUNT = BUNDLE.getString("InfoElementsCount");
    }

    /**
     * cliapp.commands.collection ShowCommand
     */
    public static class ShowCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("ShowName");

        public static final String DESCRIPTION = BUNDLE.getString("ShowDescription");

        public static final String LIST = BUNDLE.getString("ShowList");

        public static final String EMPTY = BUNDLE.getString("ShowEmpty");
    }

    /**
     * cliapp.commands.collection AddElementCommand
     */
    public static class AddElementCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("AddElementName");

        public static final String DESCRIPTION = BUNDLE.getString("AddElementDescription");

        public static final String SUCCESS = BUNDLE.getString("AddElementSuccess");
    }

    /**
     * cliapp.commands.collection UpdateElementCommand
     */
    public static class UpdateElementCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("UpdateName");

        public static final String DESCRIPTION = BUNDLE.getString("UpdateDescription");

        public static final String SUCCESS = BUNDLE.getString("UpdateSuccess");
    }

    /**
     * cliapp.commands.collection RemoveByIdCommand
     */
    public static class RemoveByIdCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("RemoveByIdName");

        public static final String DESCRIPTION = BUNDLE.getString("RemoveByIdDescription");

        public static final String SUCCESS = BUNDLE.getString("RemoveByIdSuccess");
    }

    /**
     * cliapp.commands.collection ClearCommand
     */
    public static class ClearCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("ClearName");

        public static final String DESCRIPTION = BUNDLE.getString("ClearDescription");

        public static final String SUCCESS = BUNDLE.getString("ClearSuccess");

        public static final String APPROVE = BUNDLE.getString("ClearApprove");

        public static final String APPROVE_DESCRIPTION =
            BUNDLE.getString("ClearApproveDescription");
    }

    /**
     * cliapp.commands.collection SaveCommand
     */
    public static class SaveCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("SaveName");

        public static final String DESCRIPTION = BUNDLE.getString("SaveDescription");

        public static final String SUCCESS = BUNDLE.getString("SaveSuccess");
    }

    /**
     * cliapp.commands.collection RemoveFirstCommand
     */
    public static class RemoveFirstCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("RemoveFirstName");

        public static final String DESCRIPTION = BUNDLE.getString("RemoveFirstDescription");

        public static final String SUCCESS = BUNDLE.getString("RemoveFirstSuccess");
    }

    /**
     * cliapp.commands.collection RemoveLastCommand
     */
    public static class RemoveLastCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("RemoveLastName");

        public static final String DESCRIPTION = BUNDLE.getString("RemoveLastDescription");

        public static final String SUCCESS = BUNDLE.getString("RemoveLastSuccess");
    }

    /**
     * cliapp.commands.collection RandomCommand
     */
    public static class RandomCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("RandomName");

        public static final String DESCRIPTION = BUNDLE.getString("RandomDescription");

        public static final String TITLE = BUNDLE.getString("RandomTitle");

        private static final ResourceBundle RANDOM_BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.random");

        public static final String[] NAMES = RANDOM_BUNDLE.getString("Names").split(",");

        public static final String[] SOUNDTRACKS =
            RANDOM_BUNDLE.getString("Soundtracks").split(",");

        public static final String[] CARS = RANDOM_BUNDLE.getString("Cars").split(",");
    }

    /**
     * cliapp.commands.collection HeadCommand
     */
    public static class HeadCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("HeadName");

        public static final String DESCRIPTION = BUNDLE.getString("HeadDescription");

        public static final String SUCCESS = BUNDLE.getString("HeadSuccess");
    }

    /**
     * cliapp.commands.collection AverageOfImpactSpeedCommand
     */
    public static class AverageOfImpactSpeedCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("AverageOfImpactSpeedName");

        public static final String DESCRIPTION = BUNDLE
            .getString("AverageOfImpactSpeedDescription");

        public static final String SUCCESS = BUNDLE.getString("AverageOfImpactSpeedSuccess");
    }

    /**
     * cliapp.commands.collection FilterBySoundtrackNameCommand
     */
    public static class FilterBySoundtrackNameCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("FilterBySoundtrackNameName");

        public static final String DESCRIPTION =
            BUNDLE.getString("FilterBySoundtrackNameDescription");

        public static final String SUCCESS = BUNDLE.getString("FilterBySoundtrackNameSuccess");
    }

    /**
     * cliapp.commands.collection PrintSortedCommand
     */
    public static class PrintSortedCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.collection.commands");

        public static final String NAME = BUNDLE.getString("PrintSortedName");

        public static final String DESCRIPTION = BUNDLE.getString("PrintSortedDescription");

        public static final String SUCCESS = BUNDLE.getString("PrintSortedSuccess");

        public static final String ORDER_REQUIREMENT_NAME =
            BUNDLE.getString("OrderRequirementName");

        public static final String ORDER_REQUIREMENT_DESCRIPTION =
            BUNDLE.getString("OrderRequirementDescription");

        public static final String ORDER_REQUIREMENT_ERROR =
            BUNDLE.getString("OrderRequirementError");
    }

    /**
     * cliapp.commands.cli HelpCommand
     */
    public static class HelpCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.cli.commands");

        public static final String NAME = BUNDLE.getString("HelpName");

        public static final String DESCRIPTION = BUNDLE.getString("HelpDescription");

        public static final String TITLE = BUNDLE.getString("HelpTitle");

        public static final String HELP_INLINE_PARAMS = BUNDLE.getString("HelpInlineParams");
    }

    /**
     * cliapp.commands.cli ExitCommand
     */
    public static class ExitCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.cli.commands");

        public static final String NAME = BUNDLE.getString("ExitName");

        public static final String DESCRIPTION = BUNDLE.getString("ExitDescription");

        public static final String GOODBYE = BUNDLE.getString("ExitGoodbye");
    }

    /**
     * cliapp.commands.cli HistoryCommand
     */
    public static class HistoryCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.cli.commands");

        public static final String NAME = BUNDLE.getString("HistoryName");

        public static final String DESCRIPTION = BUNDLE.getString("HistoryDescription");

        public static final String TITLE = BUNDLE.getString("HistoryTitle");

        public static final String EMPTY = BUNDLE.getString("HistoryEmpty");
    }

    /**
     * cliapp.commands.cli SetFuzzyCommand
     */
    public static class SetFuzzyCommand {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.commands.cli.commands");

        public static final String NAME = BUNDLE.getString("SetFuzzyName");

        public static final String DESCRIPTION = BUNDLE.getString("SetFuzzyDescription");

        public static final String FUZZY_ON = BUNDLE.getString("SetFuzzyOn");

        public static final String FUZZY_OFF = BUNDLE.getString("SetFuzzyOff");

        public static final String FUZZY_MODE_REQUIREMENT_NAME =
            BUNDLE.getString("FuzzyModeRequirementName");

        public static final String FUZZY_MODE_REQUIREMENT_DESCRIPTION =
            BUNDLE.getString("FuzzyModeRequirementDescription");
    }

    /**
     * Neko !
     */
    public static class Cats {
        private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("cliapp.cats.cats");

        public static final String CAT1 = BUNDLE.getString("Cat1");

        public static final String CAT2 = BUNDLE.getString("Cat2");

        public static final String CAT3 = BUNDLE.getString("Cat3");
    }
}
