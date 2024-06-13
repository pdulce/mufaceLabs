package com.mfc.backend.microdiplomas.api.command;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandHandler<R> {
    private final Map<Class<? extends Command>, Command> commandMap = new HashMap<>();
    public void registerCommand (Class<? extends Command> commandClass, Command command) {
        commandMap.put(commandClass, command);
    }
    public Object executeCommand(Class<? extends Command> commandClass) {
        Command command = commandMap.get(commandClass);
        if (command != null) {
            return command.execute();
        } else {
            throw new IllegalArgumentException("No command registered for " + commandClass.getName());
        }
    }
}
