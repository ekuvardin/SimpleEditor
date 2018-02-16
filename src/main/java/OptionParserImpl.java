package comands;


import joptsimple.*;

import java.util.List;

public class OptionParserImpl implements comands.IConsoleParser {

    private OptionParser parser;
    private OptionSpec<Integer> canvas;
    private OptionSpec<Integer> line;
    private OptionSpec<Integer> rectangle;
    private OptionSpec<String> fill;
    private OptionSpecBuilder quit;

    public OptionParserImpl(){
        this.parser = new OptionParser("Q");
        OptionSpecBuilder canvasBuilder = this.parser.accepts("C", "Create canvas");

        OptionSpecBuilder lineBuilder = this.parser.accepts("L", "Create new line");

        OptionSpecBuilder rectangleBuilder = this.parser.accepts("R", "Create new rectangle");

        OptionSpecBuilder fillBuilder = this.parser.accepts("B", "Fill area with specified colour");

        OptionSpecBuilder quitBuilder = this.parser.accepts("Q", "Quit program");

        this.canvas = canvasBuilder.requiredUnless("B", "L", "R", "Q")
                .withRequiredArg().ofType(Integer.class).describedAs("C").withValuesSeparatedBy(" ");

        this.line= lineBuilder.requiredUnless("C", "B", "R", "Q")
                .withRequiredArg().ofType(Integer.class).describedAs("L").withValuesSeparatedBy(" ");

        this.rectangle = rectangleBuilder.requiredUnless("C", "L", "B", "Q")
                .withRequiredArg().ofType(Integer.class).describedAs("R").withValuesSeparatedBy(" ");;

        this.fill = fillBuilder.requiredUnless("C", "L", "R", "Q")
                .withRequiredArg().describedAs("B").withValuesSeparatedBy(" ");

        this.quit = quitBuilder.requiredUnless("C", "L", "R", "B");
    }

    @Override
    public ICommand parseCommand(String[] args) {
        return new ErrorCommand("");
        /*OptionSet set;
        try {
            set = parser.parse(args);
        } catch (OptionException e) {
            return new ErrorCommand(e);
        }

        /*List<?> nonOptions = set.nonOptionArguments();
        if(nonOptions!=null && !nonOptions.isEmpty()){
            return new ErrorCommand("Not recognized options " + nonOptions.toString());
        }
        if(set.has(canvas))
            return new CreateCanvasCommand();
        else if(set.has(line))
            return new CreateLineCommand();
        else if(set.has(rectangle))
            return new CreateRectangleCommand();
        else if(set.has(fill))
            return new FillAreaCommand();
        else if(set.has(quit))
            return new QuitCommand();
        else
            return new ErrorCommand(new RuntimeException("Unknown command. "));*/
    }
}
