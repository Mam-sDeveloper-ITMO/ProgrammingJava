package cliapp;

import java.util.ResourceBundle;

/**
 * This class holds all the text resources used in the application, organized
 * into different nested classes for
 * easier access.
 */
public class TextResources {
    public static class App {
        private static final ResourceBundle BUNDLE = ResourceBundle
                .getBundle("cliapp.app");

        public static final String CANNOT_CREATE_MANAGER = BUNDLE.getString("CannotInitManager");

        public static final String INCORRECT_ARGS = BUNDLE.getString("IncorrectArgs");
    }

    public static class CatsResources {
        private static final ResourceBundle BUNDLE = ResourceBundle
                .getBundle("cliapp.cats");

        public static final String CAT1 = BUNDLE.getString("Cat1");

        public static final String CAT2 = BUNDLE.getString("Cat2");

        public static final String CAT3 = BUNDLE.getString("Cat3");
    }

    public static class CLIClientResources {
        private static final ResourceBundle BUNDLE = ResourceBundle
                .getBundle("cliapp.cliclient.cliclient");

        public static final String COMMAND_NOT_FOUND_ERROR = BUNDLE.getString("CommandNotFoundError");

        public static final String INLINE_PARAMS_ERROR = BUNDLE.getString("InlineParamsError");

        public static final String ASK_REQUIREMENT = BUNDLE.getString("AskRequirement");

        public static final String ASK_DEFAULT_REQUIREMENT = BUNDLE.getString("AskDefaultRequirement");

        public static final String ASK_REQUIREMENT_ATTEMPTS_ERROR = BUNDLE.getString("AskRequirementAttemptsError");

        public static final String ASK_REQUIREMENT_WITH_ATTEMPTS = BUNDLE.getString("AskRequirementWithAttempts");
    }

    public static class Commands {

        public static class Cli {

            public static class HelpCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.cli.commands");

                public static final String NAME = BUNDLE.getString("HelpCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("HelpCommand.Description");

                public static final String COMMANDS_TITLE = BUNDLE.getString("HelpCommand.Title");

                public static final String COMMANDS_INLINE_PARAMS = BUNDLE.getString("HelpCommand.InlineParams");
            }

            public static class ExitCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.cli.commands");

                public static final String NAME = BUNDLE.getString("ExitCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("ExitCommand.Description");

                public static final String GOODBYE = BUNDLE.getString("ExitCommand.Goodbye");
            }

            public static class HistoryCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.cli.commands");

                public static final String NAME = BUNDLE.getString("HistoryCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("HistoryCommand.Description");

                public static final String HISTORY_TITLE = BUNDLE.getString("HistoryCommand.Title");

                public static final String HISTORY_EMPTY = BUNDLE.getString("HistoryCommand.Empty");
            }

            public static class SetFuzzyCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.cli.commands");

                public static final String NAME = BUNDLE.getString("SetFuzzyCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("SetFuzzyCommand.Description");

                public static final String FUZZY_ON = BUNDLE.getString("SetFuzzyCommand.On");

                public static final String FUZZY_OFF = BUNDLE.getString("SetFuzzyCommand.Off");

                public static class FuzzyModeRequirementResources {
                    public static final String NAME = BUNDLE.getString("SetFuzzyCommand.FuzzyModeRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE
                            .getString("SetFuzzyCommand.FuzzyModeRequirement.Description");
                }
            }

            public static class ExecuteCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.cli.commands");

                public static final String NAME = BUNDLE.getString("ExecuteCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("ExecuteCommand.Description");

                public static final String EXECUTING_COMMAND = BUNDLE.getString("ExecuteCommand.Title");

                public static final String LINE_ERROR = BUNDLE.getString("ExecuteCommand.LineError");

                public static final String NOT_ENOUGH_DYNAMIC_PARAMS = BUNDLE
                        .getString("ExecuteCommand.NotEnoughDynamicParams");

                public static final String INCORRECT_LINE_FORMAT = BUNDLE
                        .getString("ExecuteCommand.IncorrectLineFormat");

                public static final String MAX_CALL_COUNT_EXCEED = BUNDLE
                        .getString("ExecuteCommand.MaxCallCountExceed");

                public static class ScriptFileRequirementResources {
                    public static final String NAME = BUNDLE.getString("ExecuteCommand.ScriptFileRequirement.Name");

                    public static final String FILE_ERROR = BUNDLE
                            .getString("ExecuteCommand.ScriptFileRequirement.FileError");

                    public static final String DESCRIPTION = BUNDLE
                            .getString("ExecuteCommand.ScriptFileRequirement.Description");

                    public static final String INCORRECT_EXTENSION = BUNDLE
                            .getString("ExecuteCommand.ScriptFileRequirement.IncorrectExtension");

                }
            }
        }

        public static class Collection {
            public static class RequirementsResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.requirements");

                public static class IdRequirement {
                    public static final String NAME = BUNDLE.getString("IdRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE.getString("IdRequirement.Description");

                    public static final String NOT_POSITIVE = BUNDLE.getString("IdRequirement.NotPositive");

                    public static final String NOT_EXISTS = BUNDLE.getString("IdRequirement.NotExists");

                    public static final String EXISTS = BUNDLE.getString("IdRequirement.Exists");
                }

                public static class NameRequirement {
                    public static final String NAME = BUNDLE.getString("NameRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE.getString("NameRequirement.Description");
                }

                public static class CoordinateXRequirement {
                    public static final String NAME = BUNDLE.getString("CoordinateXRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE.getString("CoordinateXRequirement.Description");

                    public static final String VALIDATION_ERROR = BUNDLE
                            .getString("CoordinateXRequirement.ValidationError");
                }

                public static class CoordinateYRequirement {
                    public static final String NAME = BUNDLE.getString("CoordinateYRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE.getString("CoordinateYRequirement.Description");

                    public static final String VALIDATION_ERROR = BUNDLE
                            .getString("CoordinateYRequirement.ValidationError");
                }

                public static class RealHeroRequirement {
                    public static final String NAME = BUNDLE.getString("RealHeroRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE.getString("RealHeroRequirement.Description");
                }

                public static class HasToothpickRequirement {
                    public static final String NAME = BUNDLE.getString("HasToothpickRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE.getString("HasToothpickRequirement.Description");
                }

                public static class ImpactSpeedRequirement {
                    public static final String NAME = BUNDLE.getString("ImpactSpeedRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE.getString("ImpactSpeedRequirement.Description");
                }

                public static class SoundtrackRequirement {
                    public static final String NAME = BUNDLE.getString("SoundtrackRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE.getString("SoundtrackRequirement.Description");
                }

                public static class MinutesOfWaitingRequirement {
                    public static final String NAME = BUNDLE.getString("MinutesOfWaitingRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE
                            .getString("MinutesOfWaitingRequirement.Description");
                }

                public static class MoodRequirement {
                    public static final String NAME = BUNDLE.getString("MoodRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE.getString("MoodRequirement.Description");

                    public static final String TITLE = BUNDLE.getString("MoodRequirement.Title");

                    public static final String BY_NUMBER_NOT_EXISTS = BUNDLE
                            .getString("MoodRequirement.ByNumberNotExists");

                    public static final String BY_NAME_NOT_EXISTS = BUNDLE
                            .getString("MoodRequirement.ByNameNotExists");

                    public static final String VALIDATION_ERROR = BUNDLE
                            .getString("MoodRequirement.ValidationError");
                }

                public static class CarRequirement {
                    public static final String NAME = BUNDLE.getString("CarRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE.getString("CarRequirement.Description");
                }
            }

            public static class InfoCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("InfoCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("InfoCommand.Description");

                public static final String TITLE = BUNDLE.getString("InfoCommand.Title");

                public static final String TYPE = BUNDLE.getString("InfoCommand.Type");

                public static final String INIT_TIME = BUNDLE.getString("InfoCommand.InitTime");

                public static final String ELEMENTS_COUNT = BUNDLE.getString("InfoCommand.ElementsCount");
            }

            public static class ShowCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("ShowCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("ShowCommand.Description");

                public static final String TITLE = BUNDLE.getString("ShowCommand.Title");

                public static final String EMPTY = BUNDLE.getString("ShowCommand.Empty");
            }

            public static class AddElementCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("AddElementCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("AddElementCommand.Description");

                public static final String SUCCESS = BUNDLE.getString("AddElementCommand.Success");
            }

            public static class UpdateElementCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("UpdateElementCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("UpdateElementCommand.Description");

                public static final String SUCCESS = BUNDLE.getString("UpdateElementCommand.Success");
            }

            public static class RemoveByIdCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("RemoveByIdCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("RemoveByIdCommand.Description");

                public static final String SUCCESS = BUNDLE.getString("RemoveByIdCommand.Success");
            }

            public static class ClearCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("ClearCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("ClearCommand.Description");

                public static final String SUCCESS = BUNDLE.getString("ClearCommand.Success");

                public static class ApproveRequirement {
                    private static final ResourceBundle BUNDLE = ResourceBundle
                            .getBundle("cliapp.commands.collection.commands");

                    public static final String NAME = BUNDLE.getString("ClearCommand.ApproveRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE
                            .getString("ClearCommand.ApproveRequirement.Description");
                }
            }

            public static class SaveCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("SaveCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("SaveCommand.Description");

                public static final String SUCCESS = BUNDLE.getString("SaveCommand.Success");

                public static final String SAVE_ERROR = BUNDLE.getString("SaveCommand.SaveError");

                public static final String FILE_NOT_FOUND = BUNDLE.getString("SaveCommand.FileNotFound");

                public static final String ASK_FILE_PATH = BUNDLE.getString("SaveCommand.AskFilePath");

                public static class FileNameRequirement {
                    private static final ResourceBundle BUNDLE = ResourceBundle
                            .getBundle("cliapp.commands.collection.commands");

                    public static final String NAME = BUNDLE
                            .getString("SaveCommand.FileNameRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE
                            .getString("SaveCommand.FileNameRequirement.Description");
                }
            }

            public static class RemoveFirstCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("RemoveFirstCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("RemoveFirstCommand.Description");

                public static final String SUCCESS = BUNDLE.getString("RemoveFirstCommand.Success");
            }

            public static class RemoveLastCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("RemoveLastCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("RemoveLastCommand.Description");

                public static final String SUCCESS = BUNDLE.getString("RemoveLastCommand.Success");
            }

            public static class HeadCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("HeadCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("HeadCommand.Description");

                public static final String TITLE = BUNDLE.getString("HeadCommand.Title");
            }

            public static class TailCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("TailCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("TailCommand.Description");

                public static final String TITLE = BUNDLE.getString("TailCommand.Title");
            }

            public static class AverageOfImpactSpeedCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("AverageOfImpactSpeedCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("AverageOfImpactSpeedCommand.Description");

                public static final String TITLE = BUNDLE.getString("AverageOfImpactSpeedCommand.Title");
            }

            public static class FilterByImpactSpeedCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("FilterByImpactSpeedCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("FilterByImpactSpeedCommand.Description");

                public static final String TITLE = BUNDLE.getString("FilterByImpactSpeedCommand.Title");

                public static final String EMPTY = BUNDLE.getString("FilterByImpactSpeedCommand.Empty");
            }

            public static class PrintSortedCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("PrintSortedCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("PrintSortedCommand.Description");

                public static final String EMPTY = BUNDLE.getString("PrintSortedCommand.Empty");

                public static final String TITLE = BUNDLE.getString("PrintSortedCommand.Title");

                public static class SortOrderRequirement {
                    private static final ResourceBundle BUNDLE = ResourceBundle
                            .getBundle("cliapp.commands.collection.commands");

                    public static final String NAME = BUNDLE
                            .getString("PrintSortedCommand.SortOrderRequirement.Name");

                    public static final String DESCRIPTION = BUNDLE
                            .getString("PrintSortedCommand.SortOrderRequirement.Description");

                    public static final String ILLEGAL_ORDER = BUNDLE
                            .getString("PrintSortedCommand.SortOrderRequirement.IllegalOrder");
                }
            }

            public static class RandomCommandResources {
                private static final ResourceBundle BUNDLE = ResourceBundle
                        .getBundle("cliapp.commands.collection.commands");

                public static final String NAME = BUNDLE.getString("RandomCommand.Name");

                public static final String DESCRIPTION = BUNDLE.getString("RandomCommand.Description");

                public static final String TITLE = BUNDLE.getString("RandomCommand.Title");

                public static class RandomValues {
                    public static final String[] NAMES = BUNDLE.getString("RandomCommand.RandomValues.Names")
                            .split(",");

                    public static final String[] SOUNDTRACKS = BUNDLE
                            .getString("RandomCommand.RandomValues.Soundtracks").split(",");

                    public static final String[] CARS = BUNDLE.getString("RandomCommand.RandomValues.Cars").split(",");
                }
            }
        }
    }
}
