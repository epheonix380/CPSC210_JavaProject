package ui;

import java.util.Scanner;

// Represents a point where the user can decide whether to use the gui or the text based UI
public class CmdGuiSwitch {

    private Scanner input;
    private String answer;

    // MODIFIES: This
    // EFFECTS: Creates a new text based or graphics based UI dependent on user input
    public CmdGuiSwitch() {
        while (true) {
            System.out.println("CMD or GUI?");
            input = new Scanner(System.in);
            answer = input.nextLine();
            if (answer.toLowerCase().equals("cmd") || answer.toLowerCase().equals("gui")) {
                break;
            }
        }
        if (answer.toLowerCase().equals("gui")) {
            new Frame();
        } else {
            new StartApp();
        }
    }
}
