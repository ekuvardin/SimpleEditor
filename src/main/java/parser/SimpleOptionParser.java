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
            x = parseCoordinate(args[1]);
            y = parseCoordinate(args[2]);
            colour = parseColour(args[3]);
        }
        catch(ParseException e){
            return new ErrorCommand(e.getMessage());
        }

        return new FillAreaCommand(x, y, colour);
    }

    private ICommand parseBN(String[] args) {
        if (args.length > 5)
            return new ErrorCommand("Unknown options after BN command " + Arrays.toString(Arrays.copyOfRange(args, 4, args.length)));

        if (args.length < 5)
            return new ErrorCommand("Not enough args for BN command");

        int x, y, threadCount;
        char colour;
        try {
            x = parseCoordinate(args[1]);
            y = parseCoordinate(args[2]);
            colour = parseColour(args[3]);
            threadCount= parseThreadCount(args[4]);
        } catch(ParseException e){
            return new ErrorCommand(e.getMessage());
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
            w = parseCoordinate(args[1]);
            h = parseCoordinate(args[2]);
        } catch (ParseException e) {
            return new ErrorCommand(e.getMessage());
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
            w = parseCoordinate(args[1]);
            h = parseCoordinate(args[2]);
        } catch (ParseException e) {
            return new ErrorCommand(e.getMessage());
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
            x1 = parseCoordinate(args[1]);
            y1 = parseCoordinate(args[2]);
            x2 = parseCoordinate(args[3]);
            y2 = parseCoordinate(args[4]);
        } catch (ParseException e) {
            return new ErrorCommand(e.getMessage());
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
            x1 = parseCoordinate(args[1]);
            y1 = parseCoordinate(args[2]);
            x2 = parseCoordinate(args[3]);
            y2 = parseCoordinate(args[4]);
        } catch (ParseException e) {
            return new ErrorCommand(e.getMessage());
        }

        return new CreateRectangleCommand(x1, y1, x2, y2);
    }

    private static class ParseException extends Exception{

        ParseException(String message){
            super(message);
        }
    }

    private int parseCoordinate(String param) throws ParseException {
        return parseCoordinate(param,"X,Y coordinates" );
    }


    private int parseThreadCount(String param) throws ParseException {
        return parseCoordinate(param,"Thread count" );
    }

    private int parseCoordinate(String param, String prefix) throws ParseException {
        int coord;
        try {
            coord = Integer.parseInt(param);
        } catch (NumberFormatException e) {
            throw  new ParseException(String.format("%s should be integer", prefix));
        }
        if (coord <= 0) {
            throw new ParseException(String.format("%s should be positive", prefix));
        }
        return coord;
    }

    private char parseColour(String param) throws ParseException {
        if (param.length() > 1 ||param.charAt(0) < 97 && param.charAt(0) > 122) {
            throw new ParseException(String.format("Unknown colour %s, colour must be in range 'a', ... ,'z'", param.charAt(0)));
        }
        return param.charAt(0);
    }
}
