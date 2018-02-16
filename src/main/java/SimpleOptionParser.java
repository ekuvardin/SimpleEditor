package comands;

import java.util.Arrays;

public class SimpleOptionParser implements comands.IConsoleParser {

    @Override
    public ICommand parseCommand(String[] args) {
        if(args.length <= 0)
            return new ErrorCommand("Empty command");

        switch (args[0]){
            case "Q": return parseQ(args);
            case "B": return parseB(args);
            case "C": return parseC(args);
            case "L": return parseL(args);
            case "R": return parseR(args);
        }

        return new ErrorCommand("Unknown command " + args[0]);
    }

    private ICommand parseQ(String[] args){
        if(args.length>1)
            return new ErrorCommand("Unknown options after Q command " + Arrays.toString(Arrays.copyOfRange(args, 1, args.length)));
        return new QuitCommand();
    }

    private ICommand parseB(String[] args){
        if(args.length>4)
            return new ErrorCommand("Unknown options after B command " + Arrays.toString(Arrays.copyOfRange(args, 4, args.length)));

        if(args.length<4)
            return new ErrorCommand("Not enough args for B command");

        int x,y;
        char colour;
        try{
            x = Integer.parseInt(args[1]);
            y = Integer.parseInt(args[2]);
        } catch (NumberFormatException e){
            return new ErrorCommand("X,Y coordinates should be integer");
        }

        if(x<=0 || y<=0){
            return new ErrorCommand("X,Y coordinates should be positive");
        }

        colour = args[3].charAt(0);


        return new FillAreaCommand(x,y,colour);
    }

    private ICommand parseC(String[] args){
        if(args.length>3)
            return new ErrorCommand("Unknown options after C command " + Arrays.toString(Arrays.copyOfRange(args, 3, args.length)));

        if(args.length<3)
            return new ErrorCommand("Not enough args for C command");

        int w,h;
        try{
            w = Integer.parseInt(args[1]);
            h = Integer.parseInt(args[2]);
        } catch (NumberFormatException e){
            return new ErrorCommand("w,h coordinates should be integer");
        }

        if(w<=0 || h<=0){
            return new ErrorCommand("X,Y coordinates should be positive");
        }

        return new CreateCanvasCommand(w,h);
    }

    private ICommand parseL(String[] args){
        if(args.length>5)
            return new ErrorCommand("Unknown options after L command " + Arrays.toString(Arrays.copyOfRange(args, 5, args.length)));

        if(args.length<5)
            return new ErrorCommand("Not enough args for L command");

        int x1,y1,x2,y2;
        try{
            x1 = Integer.parseInt(args[1]);
            y1 = Integer.parseInt(args[2]);
            x2 = Integer.parseInt(args[3]);
            y2 = Integer.parseInt(args[4]);
        } catch (NumberFormatException e){
            return new ErrorCommand("X,Y coordinates should be integer");
        }

        return new CreateLineCommand(x1,y1,x2,y2);
    }

    private ICommand parseR(String[] args){
        if(args.length>5)
            return new ErrorCommand("Unknown options after R command " + Arrays.toString(Arrays.copyOfRange(args, 5, args.length)));

        if(args.length<5)
            return new ErrorCommand("Not enough args for R command");

        int x1,y1,x2,y2;
        try{
            x1 = Integer.parseInt(args[1]);
            y1 = Integer.parseInt(args[2]);
            x2 = Integer.parseInt(args[3]);
            y2 = Integer.parseInt(args[4]);
        } catch (NumberFormatException e){
            return new ErrorCommand("X,Y coordinates should be integer");
        }

        return new CreateRectangleCommand(x1,y1,x2,y2);
    }
}