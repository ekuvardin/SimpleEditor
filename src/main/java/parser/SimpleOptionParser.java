package parser;

import comands.*;

import java.util.Arrays;

public class SimpleOptionParser implements IConsoleParser {

    @Override
    public ICommand parseCommand(String[] args) {
        if (args.length <= 0)
            return new ErrorCommand("Empty command");

        switch (args[0]) {
            case "Q":
                return parseQ(args);
            case "B":
                return parseB(args);
            case "C":
                return parseC(args);
            case "L":
                return parseL(args);
            case "R":
                return parseR(args);
            case "CN":
                return parseCN(args);
            case "BN":
                return parseBN(args);
        }

        return new ErrorCommand("Unknown command " + args[0]);
    }

    private ICommand parseQ(String[] args) {
        if (args.length > 1)
            return new ErrorCommand("Unknown options after Q command " + Arrays.toString(Arrays.copyOfRange(args, 1, args.length)));
        return new QuitCommand();
    }

    private ICommand parseB(String[] args) {
        if (args.length > 4)
            return new ErrorCommand("Unknown options after B command " + Arrays.toString(Arrays.copyOfRange(args, 4, args.length)));

        if (args.length < 4)
            return new ErrorCommand("Not enough args for B command");

        int x, y;
        char colour;
        try {
            x = Integer.parseInt(args[1]);
            y = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            return new ErrorCommand("X,Y coordinates should be integer");
        }

        if (x <= 0 || y <= 0) {
            return new ErrorCommand("X,Y coordinates should be positive");
        }

        if (args[3].length() > 1 || args[3].charAt(0) < 97 && args[3].charAt(0) > 122) {
            return new ErrorCommand(String.format("Unknow colour %s, colour must be in range 'a', ... ,'z'", args[3]));
        }
        colour = args[3].charAt(0);


        return new FillAreaCommand(x, y, colour);
    }

    private ICommand parseBN(String[] args) {
        if (args.length > 5)
            return new ErrorCommand("Unknown options after BN command " + Arrays.toString(Arrays.copyOfRange(args, 4, args.length)));

        if (args.length < 5)
            return new ErrorCommand("Not enough args for BN command");

        int x, y;
        char colour;
        try {
            x = Integer.parseInt(args[1]);
            y = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            return new ErrorCommand("X,Y coordinates should be integer");
        }

        if (x <= 0 || y <= 0) {
            return new ErrorCommand("X,Y coordinates should be positive");
        }

        if (args[3].length() > 1 || args[3].charAt(0) < 97 && args[3].charAt(0) > 122) {
            return new ErrorCommand(String.format("Unknow colour %s, colour must be in range 'a', ... ,'z'", args[3]));
        }
        colour = args[3].charAt(0);

        int threadCount;
        try {
            threadCount = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            return new ErrorCommand("Thread count should be integer");
        }

        return new FillConcurrentAreaCommand(x, y, colour, threadCount);
    }

    private ICommand parseC(String[] args) {
        if (args.length > 3)
            return new ErrorCommand("Unknown options after C command " + Arrays.toString(Arrays.copyOfRange(args, 3, args.length)));

        if (args.length < 3)
            return new ErrorCommand("Not enough args for C command");

        int w, h;
        try {
            w = Integer.parseInt(args[1]);
            h = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            return new ErrorCommand("w,h coordinates should be integer");
        }

        if (w <= 0 || h <= 0) {
            return new ErrorCommand("X,Y coordinates should be positive");
        }

        return new CreateCanvasCommand(w, h);
    }

    private ICommand parseCN(String[] args) {
        if (args.length > 3)
            return new ErrorCommand("Unknown options after CN command " + Arrays.toString(Arrays.copyOfRange(args, 3, args.length)));

        if (args.length < 3)
            return new ErrorCommand("Not enough args for CN command");

        int w, h;
        try {
            w = Integer.parseInt(args[1]);
            h = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            return new ErrorCommand("w,h coordinates should be integer");
        }

        if (w <= 0 || h <= 0) {
            return new ErrorCommand("X,Y coordinates should be positive");
        }

        return new CreateConcurrentCanvasCommand(w, h);
    }

    private ICommand parseL(String[] args) {
        if (args.length > 5)
            return new ErrorCommand("Unknown options after L command " + Arrays.toString(Arrays.copyOfRange(args, 5, args.length)));

        if (args.length < 5)
            return new ErrorCommand("Not enough args for L command");

        int x1, y1, x2, y2;
        try {
            x1 = Integer.parseInt(args[1]);
            y1 = Integer.parseInt(args[2]);
            x2 = Integer.parseInt(args[3]);
            y2 = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            return new ErrorCommand("X,Y coordinates should be integer");
        }

        return new CreateLineCommand(x1, y1, x2, y2);
    }

    private ICommand parseR(String[] args) {
        if (args.length > 5)
            return new ErrorCommand("Unknown options after R command " + Arrays.toString(Arrays.copyOfRange(args, 5, args.length)));

        if (args.length < 5)
            return new ErrorCommand("Not enough args for R command");

        int x1, y1, x2, y2;
        try {
            x1 = Integer.parseInt(args[1]);
            y1 = Integer.parseInt(args[2]);
            x2 = Integer.parseInt(args[3]);
            y2 = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            return new ErrorCommand("X,Y coordinates should be integer");
        }

        return new CreateRectangleCommand(x1, y1, x2, y2);
    }
}
