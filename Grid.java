import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Grid extends JFrame implements ActionListener {
    JFrame frame = new JFrame();
    JPanel mainPanel = new JPanel(); //Ändrar namn till mainPanel för att lättare ha koll på vad koden referar till.
    JButton[][] buttonArray;
    final int rows = 4;  //Horizontal
    final int columns = 4;//Vertikal
    JLabel[][] labelArray;


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
        JLabel[][] labelArray = generateJLabelArray(oneDimensionalArray);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttonArray[i][j] = new JButton((i + "," + j));
                buttonArray[i][j].setName((i + "," + j));
                buttonArray[i][j].setHideActionText(true);
                buttonArray[i][j].add(labelArray[i][j]);
                buttonArray[i][j].addActionListener(this);              //Lägger till action listener på knappen när den skapas.

                mainPanel.add(buttonArray[i][j]);
            }
        }

        return buttonArray;
    }

    public JLabel[][] generateJLabelArray(int[] oneDimensionalArray) {
        JLabel[][] labelArray = new JLabel[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                labelArray[i][j] = new JLabel();
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                labelArray[i][j].setText(String.valueOf(oneDimensionalArray[(i * columns) + j]));
            }
        }

        labelArray[0][0].setText(""); // 0 0 blir blank istället för 0.
        return labelArray;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.isBlank()) {
            s = "0";
        }
//        int selectedTile = Integer.parseInt(s);

        System.out.println(s);
//        System.out.println(selectedTile);

        //används i testsyfte då jag inte kan skapa en testmap. Skriver ut button text.
        //Kanske gå att använda mot int array "gameBoard" för att ha koll vart varje tile är lättare?


    }
}
