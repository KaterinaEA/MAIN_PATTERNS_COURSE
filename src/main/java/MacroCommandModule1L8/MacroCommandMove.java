package MacroCommandModule1L8;

import exceptionModule1L3.ICommand;

import java.util.List;

public class MacroCommandMove implements ICommand{

    List<ICommand> commands;

    public MacroCommandMove(List<ICommand> commands) {

        this.commands = commands;

    }


    @Override
    public void execute() {

        for (ICommand command : commands) {

            try {
                try {
                    command.execute();
                } catch (Exception e) {
                    throw new CommandException("MacroCommandMove exception!");
                }
            } catch (Exception e) {

                throw new RuntimeException(e.getMessage());

            }

        }
    }
}
