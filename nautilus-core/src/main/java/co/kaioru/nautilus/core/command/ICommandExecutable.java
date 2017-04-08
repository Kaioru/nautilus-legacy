package co.kaioru.nautilus.core.command;

import co.kaioru.nautilus.core.user.User;

import java.util.LinkedList;

@FunctionalInterface
public interface ICommandExecutable extends co.kaioru.retort.command.ICommandExecutable {

	default void execute(LinkedList<String> args) throws Exception {
		return;
	}

	void execute(LinkedList<String> args, User user) throws Exception;

}
