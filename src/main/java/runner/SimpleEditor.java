package runner;

import canvas.Viewer.ConsoleViewer;
import comands.*;
import parser.IConsoleParser;
import parser.SimpleOptionParser;

import java.util.Scanner;

public class SimpleEditor {

    public static void main(String[] args) {
        IConsoleParser parser = new SimpleOptionParser();
        ConsoleViewer view = new ConsoleViewer();
        SimpleEditor w = new SimpleEditor();
        view.printMessage("Welcome to simple drawing program!");
        try (Scanner scanner = new Scanner(System.in)) {
            ICommand command;
            do {
                command = parser.parseCommand((scanner.nextLine()).split("\\s+"));
                command.execute(view);
            } while (!(command.quitCommand()));
        }
    }
}
