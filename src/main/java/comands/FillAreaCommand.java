package comands;

public class FillAreaCommand implements ICommand {

    private int x;
    private int y;
    private char colour;

    public FillAreaCommand(int x, int y, char colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    @Override
    public void execute() {

    }
}
