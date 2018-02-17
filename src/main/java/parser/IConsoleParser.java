package parser;

import comands.ICommand;

public interface IConsoleParser {
    ICommand parseCommand(String[] args);
}
