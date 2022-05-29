import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import edu.kzoo.grid.Grid;
import edu.kzoo.grid.display.CheckeredBackgroundDisplay;
import edu.kzoo.grid.gui.EnabledDisabledStates;
import edu.kzoo.grid.gui.SteppedGridAppFrame;
import edu.kzoo.util.Debug;

/**
 * Chess 110:<br>
 *
 * A <code>Chess110GUI</code> object represents a window with control
 * buttons and a display for a Chess 110 project.
 * Most of the functionality is provided in the ActiveGridFrame
 * superclass.
 *
 * @author David Shen
 * @version 5/24/21
 **/
public class ChessieGUI extends SteppedGridAppFrame {
    // Specify button attributes.
    private static final boolean DISPLAY_AFTER_RESTART = true;
    private static final boolean DISPLAY_AFTER_EACH_STEP = true;
    private static final boolean DISPLAY_AFTER_EACH_STOP = true;

    private static final String usualTitle = "Chessie";
    private static final Color playingBgColor = new Color(150, 150, 150);
    private static final Color whiteWonBgColor = new Color(200, 200, 200);
    private static final Color blackWonBgColor = new Color(100, 100, 100);
    private static final Color lightSquare = new Color(240, 217, 181);
    private static final Color darkSquare = new Color(181, 136, 99);

    private static final String printMoves = "Print Potential Moves to Console";
    private static final String dontPrintMoves = " Stop Printing Potential Moves  ";
    private JButton printMovesToggleButton;
    private boolean printMovesToggle;

    /**
     * Constructs a new ChessieGUI object.
     */
    public ChessieGUI(ChessieController controller) {
        super(controller, DISPLAY_AFTER_EACH_STEP);
        includeStepOnceButton();
        includeStepNTimesButton();
        includeRunButton();
        includeStopButton(DISPLAY_AFTER_EACH_STOP);
        includeSetResetButton("Restart", EnabledDisabledStates.NEEDS_GRID_AND_APP_WAITING, DISPLAY_AFTER_RESTART);
        includeSpeedSlider();
        includeControlComponent(makeDebugNodebugDropdown(), 1);

        controller.setGUI(this);
        constructWindowContents(usualTitle, playingBgColor, 400, 400, 10);

        controller.init();
        showGrid();
    }

    /**
     * Creates a panel with a toggle button that lets the user choose
     * whether or not the program should print a log of the individual
     * moves to the console.
     * 
     * @return a panel containing the buttons for running the application
     **/
    protected JComponent makeDebugNodebugDropdown() {
        printMovesToggleButton = new JButton(printMoves);
        printMovesToggle = true;
        printMovesToggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handlePrintMovesToggleButton();
            }
        });

        printMovesToggleButton.setEnabled(true);

        return printMovesToggleButton;
    }

    /** Toggles between printing and not printing moves to the console. **/
    protected void handlePrintMovesToggleButton() {
        if (printMovesToggle) {
            Debug.turnOn();
            printMovesToggleButton.setText(dontPrintMoves);
        } else {
            Debug.turnOff();
            printMovesToggleButton.setText(printMoves);
        }
        printMovesToggle = !printMovesToggle;
    }

    // Redefines setGrid to also reset the window title and
    // background color of the grid.
    @Override
    public void setGrid(Grid grid) {
        super.setGrid(grid);
        setTitle(usualTitle);
        getDisplay().setBackgroundDisplay(new CheckeredBackgroundDisplay(getDisplay(), lightSquare, darkSquare));
    }

    /**
     * Notifies the user that White won.
     * Alters the background color of the grid and prints a message to the
     * console.
     **/
    public void notifyUserWhiteWon() {
        System.out.println("Game Over -- White wins!");
        setTitle("White won!");
        getDisplay().setBackgroundColor(whiteWonBgColor);
    }

    /**
     * Notifies the user that Black won.
     * Alters the background color of the grid and prints a message to the
     * console.
     **/
    public void notifyUserBlackWon() {
        System.out.println("Game Over -- Black wins!");
        setTitle("Black won!");
        getDisplay().setBackgroundColor(blackWonBgColor);
    }

    /**
     * Notifies the user that Black won.
     * Alters the background color of the grid and prints a message to the
     * console.
     **/
    public void notifyUserDraw() {
        System.out.println("Game Over -- Draw");
        setTitle("Draw");
    }
}
