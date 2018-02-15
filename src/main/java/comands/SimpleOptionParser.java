package comands;

import java.util.Arrays;

public class SimpleOptionParser implements IConsoleParser {

    @Override
    public ICommand parseCommand(String[] args) {
        if(args.length <= 0)
            return new ErrorCommand("Empty command");

        switch (args[0]){
            case "Q": return parseQ(args);
            case "B": return parseB(args);
            case "C";
            case "L";
            case "R";
            other:
                return  new ErrorCommand("Unknown command " + args[0]);
        }
    }

    private ICommand parseQ(String[] args){
        if(args.length>1)
            return new ErrorCommand("Unknown options after Q command " + Arrays.copyOfRange(args, 1, args.length).toString());
        return new QuitCommand();
    }

    private ICommand parseB(String[] args){
        if(args.length>3)
            return new ErrorCommand("Unknown options after Q command " + Arrays.copyOfRange(args, 3, args.length).toString());
        return new FillAreaCommand();
    }

    private ICommand parseQ(String[] args){
        if(args.length>1)
            return new ErrorCommand("Unknown options after Q command " + Arrays.copyOfRange(args, 1, args.length).toString());
        return new QuitCommand();
    }

    private ICommand parseQ(String[] args){
        if(args.length>1)
            return new ErrorCommand("Unknown options after Q command " + Arrays.copyOfRange(args, 1, args.length).toString());
        return new QuitCommand();
    }

    private ICommand parseQ(String[] args){
        if(args.length>1)
            return new ErrorCommand("Unknown options after Q command " + Arrays.copyOfRange(args, 1, args.length).toString());
        return new QuitCommand();
    }
}
