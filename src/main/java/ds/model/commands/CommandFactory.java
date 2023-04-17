package ds.model.commands;

public class CommandFactory {
    public static Command getCommand(CommandEnum commandEnum) {
        Command result = null;
        switch (commandEnum) {
            case LOOK_AROUND -> result = new LookAroundCommand();
            case MOVE_BACTERIA -> result = new MoveBacteriaCommand();
            case NEUTRALIZE_POISON -> result = new NeutralizePoisonCommand();
            case INCREASE_VELOCITY -> result = new IncreaseVelocityCommand();
        }
        return result;
    }
}
