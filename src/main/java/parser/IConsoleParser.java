package parser;

import comands.ICommand;

/*
   Define parse command interface
 */
public interface IConsoleParser {

    /**
     * @param args arguments to parse
     * @return new command
     */
    ICommand parseCommand(String[] args);
}
