import javax.swing.*;
import java.awt.*;


public class Grid extends JFrame {
    JFrame frame = new JFrame();
    JPanel mainPanel = new JPanel(); //Ändrar namn till mainPanel för att lättare ha koll på vad koden referar till.
    JButton[][] buttonArray;
    final int rows = 4;  //Horizontal
    final int columns = 4;//Vertikal

    int[] oneDimensionalArray = generateOneDimensionalArray(); //Generar en 1d array med elementen 0-15.

    public Grid() {
        int[][] gameBoard = generateBoardArray(oneDimensionalArray); //Skapar 'spelbrädan'. todo:Klarar vi oss med att bara Jbutton arrayen? Ska denna raderas?
        showGrid();
    }


    public void showGrid() {

        mainPanel.setLayout(new GridLayout(4, 4));
        buttonArray = generateButtonArray(oneDimensionalArray); //Skapar buttonarray och tilldelar textvärde 0-15.
        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

    }


    public int[] generateOneDimensionalArray() {  //Skapar en array 0-15. Används för att skapa gameBoard och buttonArray.
        int[] generatedArray = new int[16];
        for (int i = 0; i < 16; i++) {
            generatedArray[i] = i;
        }
        return generatedArray;
    }


    public int[][] generateBoardArray(int[] oneDimensionalArray) {
        int[][] gameBoardArray = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                gameBoardArray[i][j] = oneDimensionalArray[(i * columns) + j];
        }
        return gameBoardArray;
    }

    public JButton[][] generateButtonArray(int[] oneDimensionalArray) {
        JButton[][] buttonArray = new JButton[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttonArray[i][j] = new JButton();
                mainPanel.add(buttonArray[i][j]);
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                    buttonArray[i][j].setText(String.valueOf(oneDimensionalArray[(i * columns) + j]));

            }
        }
        buttonArray[0][0].setText(""); // 0 0 blir blank istället för 0.
        return buttonArray;
    }




}




