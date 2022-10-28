import javax.swing.*;
import java.awt.*;


public class Grid extends JFrame {

    private Integer[][] grid = new Integer[4][4]; //Vill inte ta bort din rad.


    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton[][] button;
    JLabel[][] buttonlabel;
    final int rows = 4;  //Horizontal
    final int columns = 4;//Vertikal
    int[][] gameBoard;


    public Grid() {
        gameBoard = new int[rows][columns];  //Skapar 'spelbrädan'.
        showGrid();
    }


    public void showGrid() {

        panel.setLayout(new GridLayout(4, 4));
        button = new JButton[rows][columns];
        buttonlabel = new JLabel[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                button[i][j] = new JButton();
                String labelString = i + "." + j; //labelString till knappens array-position
                buttonlabel[i][j] = new JLabel();
                buttonlabel[i][j].setText(labelString);
                button[i][j].add(buttonlabel[i][j]);
                panel.add(button[i][j]);
            }
        }
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

    }


}
