package cliapp.commands.cli;

import static commands.requirements.validators.common.StringValidators.notEmptyValidator;

import java.io.Serializable;
import java.util.Map;

import adapter.Adapter;
import adapter.exceptions.ReceiveResponseFailed;
import adapter.exceptions.SendRequestFailed;
import adapter.exceptions.SocketInitFailed;
import auth.AuthToken;
import cliapp.TextsManager;
import cliapp.cliclient.CLIClient;
import commands.OutputChannel;
import commands.exceptions.ExecutionError;
import commands.requirements.Requirement;
import commands.requirements.RequirementsPipeline;
import commands.requirements.exceptions.RequirementAskError;
import commands.requirements.exceptions.ValidationError;
import commands.requirements.validators.Validator;
import server.responses.Response;
import textlocale.text.TextSupplier;

/**
 * Ask user to sign in or sign up and send request to auth service
 */
public class LoginCommand extends CLICommand {
    static TextSupplier ts = TextsManager.getTexts().getPackage("commands.cli")::getText;

    /**
     * Adapter for auth service
     */
    private final Adapter authAdapter;

    /**
     * USed to return the token to the main app
     */
    private final AuthToken[] tokenContainer;

    /**
     * Validator for mode
     */
    private static final Validator<String, String> modeValidator = (mode) -> {
        if (mode.equals("sign in") || mode.equals("sign up")) {
            return mode;
        } else {
            throw new ValidationError(mode, ts.t("LoginCommand.ModeRequirement.Error"));
        }
    };

    /**
     * Requirement for mode
     */
    private static final Requirement<String, String> modeRequirement = new Requirement<>(
            ts.t("LoginCommand.ModeRequirement.Name"),
            ts.t("LoginCommand.ModeRequirement.Description"),
            modeValidator);

    /**
     * Requirement for login
     */
    private static final Requirement<String, String> loginRequirement = new Requirement<>(
            ts.t("LoginCommand.LoginRequirement.Name"),
            ts.t("LoginCommand.LoginRequirement.Description"),
            notEmptyValidator);

    /**
     * Requirement for password
     */
    private static final Requirement<String, String> passwordRequirement = new Requirement<>(
            ts.t("LoginCommand.PasswordRequirement.Name"),
            ts.t("LoginCommand.PasswordRequirement.Description"),
            notEmptyValidator);

    /**
     * Constructor for LoginCommand
     *
     * @param client the instance of {@link CLIClient} class
     */
    public LoginCommand(CLIClient client, Adapter authAdapter, AuthToken[] tokenContainer) {
        super(ts.t("LoginCommand.Name"), ts.t("LoginCommand.Description"), client);
        this.authAdapter = authAdapter;
        this.tokenContainer = tokenContainer;
    }

    /**
     * Try to login user 3 times
     *
     * @param pipeline an instance of {@link RequirementsPipeline} class
     * @param output   an instance of {@link OutputChannel} class
     * @throws ExecutionError if there is an error executing the command
     */
    @Override
    public void execute(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        String mode;
        try {
            mode = pipeline.askRequirement(modeRequirement);
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }
        for (int i = 0; i < 3; i++) {
            try {
                if (mode.equals("sign in")) {
                    tokenContainer[0] = signIn(pipeline, output);
                } else {
                    tokenContainer[0] = signUp(pipeline, output);
                }
                return;
            } catch (ExecutionError e) {
                output.putString(e.getMessage());
                output.putString(ts.t("LoginCommand.Messages.TryAgain"));
            }
        }
        throw new ExecutionError(ts.t("LoginCommand.Messages.LoginFailed"));
    }

    /**
     * Sign in user
     *
     * @return auth token
     * @throws ExecutionError if there is an error executing the command
     */
    private AuthToken signIn(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        String login;
        String password;
        try {
            login = pipeline.askRequirement(loginRequirement);
            password = pipeline.askRequirement(passwordRequirement);
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }

        Map<String, Serializable> data = Map.of("login", login, "password", password);
        Response response;
        try {
            response = authAdapter.triggerServer("auth.login", data);
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed e) {
            throw new ExecutionError(TextsManager.getTexts().getText("app.ConnectLater"));
        }

        if (response.getCode() == 200) {
            System.out.println(ts.t("LoginCommand.Messages.LoginSuccess"));
            return (AuthToken) response.getData().get("token");
        } else if (response.getCode() == 902) {
            throw new ExecutionError(ts.t("LoginCommand.Messages.LoginFailed"));
        } else {
            throw new ExecutionError(ts.t("LoginCommand.Messages.UnknownError"));
        }
    }

    /**
     * Sign up user
     *
     * @return auth token
     * @throws ExecutionError if there is an error executing the command
     */
    private AuthToken signUp(RequirementsPipeline pipeline, OutputChannel output) throws ExecutionError {
        String login;
        String password;
        try {
            login = pipeline.askRequirement(loginRequirement);
            password = pipeline.askRequirement(passwordRequirement);
        } catch (RequirementAskError e) {
            throw new ExecutionError(e.getMessage());
        }
        Map<String, Serializable> data = Map.of("login", login, "password", password);
        Response response;
        try {
            response = authAdapter.triggerServer("auth.register", data);
        } catch (SocketInitFailed | SendRequestFailed | ReceiveResponseFailed e) {
            throw new ExecutionError(e.getMessage());
        }

        if (response.getCode() == 200) {
            System.out.println(ts.t("LoginCommand.Messages.RegisterSuccess"));
            return (AuthToken) response.getData().get("token");
        } else if (response.getCode() == 901) {
            throw new ExecutionError(ts.t("LoginCommand.Messages.LoginExists"));
        } else {
            throw new ExecutionError(ts.t("LoginCommand.Messages.UnknownError"));
        }
    }

}
