package co.kaioru.nautilus.core.command;

import co.kaioru.nautilus.core.user.User;

import java.util.LinkedList;

public class CommandBuilder extends co.kaioru.retort.util.builder.CommandBuilder<CommandBuilder, Command, ICommandExecutable> {

	public CommandBuilder(String name) {
		super(name);
	}

	@Override
	public Command build(ICommandExecutable commandExecutable) {
		return new Command() {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getDesc() {
				return desc;
			}

			@Override
			public void execute(LinkedList<String> args, User user) throws Exception {
				commandExecutable.execute(args, user);
			}

		};
	}

}
