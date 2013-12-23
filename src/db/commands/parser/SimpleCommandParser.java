package db.commands.parser;

import db.commands.CommandType;
import db.commands.impl.*;

public class SimpleCommandParser implements ICommandParser {
    public static final String SEPARATOR = " ";

    @Override
    public Command getCommand(String rawCommand) {
        rawCommand = rawCommand.trim();

        CommandType command;
        String[] args = new String[0];
        if (rawCommand.contains(SEPARATOR)) {
            Integer spacePos = rawCommand.indexOf(SEPARATOR);
            String type = rawCommand.substring(0, spacePos);
            command = CommandType.getCommandFromType(type);
            String arguments = rawCommand. substring(spacePos + 1);
            args = arguments.trim().split(" ");
        } else {
            command = CommandType.getCommandFromType(rawCommand);
        }

        if (command != null) {
            switch (command) {
                case END:
                    return new EndCommand();
                case BEGIN:
                    return new BeginCommand();
                case COMMIT:
                    return new CommitCommand();
                case ROLLBACK:
                    return new RollbackCommand();
                case SET:
                    if (args.length == 2) {
                        return new SetCommand(args[0], args[1]);
                    }
                    return new InvalidCommand("Invalid number of arguments");
                case GET:
                    if (args.length == 1) {
                        return new GetCommand(args[0]);
                    }
                    return new InvalidCommand("Invalid number of arguments");
                case NUMEQUALTO:
                    if (args.length == 1) {
                        return new NumEqualToCommand(args[0]);
                    }
                    return new InvalidCommand("Invalid number of arguments");
                case UNSET:
                    if (args.length == 1) {
                        return new UnsetCommand(args[0]);
                    }
                    return new InvalidCommand("Invalid number of arguments");
                default:
                    break;
            }
        }
        return new InvalidCommand("The inserted command does not exist!");
    }
}
