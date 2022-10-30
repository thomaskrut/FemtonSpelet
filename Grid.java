import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class Grid implements ActionListener {
    JFrame frame = new JFrame();
    JPanel mainPanel = new JPanel(); //Ändrar namn till mainPanel för att lättare ha koll på vad koden referar till.
    JButton[][] buttonArray;
    final int rows = 4;  //Horizontal
    final int columns = 4;//Vertikal

    // int[] oneDimensionalArray = generateOneDimensionalArray(); //Generar en 1d array med elementen 0-15.
    List<Integer> listOfNumbers = generateListOfNumbers();
    int[][] gameBoard = generateBoardArray(listOfNumbers); //Skapar 'spelbrädan'. todo:Klarar vi oss med att bara Jbutton arrayen? Ska denna raderas?

    public Grid() {

        showGrid();
    }

    public void showGrid() {

        mainPanel.setLayout(new GridLayout(4, 4));
        mainPanel.setBackground(Color.black);
        buttonArray = generateButtonArray(); //Skapar buttonarray och tilldelar textvärde 0-15.
        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

    }


    // används inte i denna variant
    /* public int[] generateOneDimensionalArray() {  //Skapar en array 0-15. Används för att skapa gameBoard och buttonArray.
        int[] generatedArray = new int[16];
        for (int i = 0; i < 16; i++) {
            generatedArray[i] = i;
        }
        return generatedArray;
    } */

    public List<Integer> generateListOfNumbers() {
        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            listOfNumbers.add(i);
        }
        Collections.shuffle(listOfNumbers);
        return listOfNumbers;

    }


    public int[][] generateBoardArray(List<Integer> listOfNumbers) {
        int[][] gameBoardArray = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                gameBoardArray[i][j] = listOfNumbers.get((i * columns) + j);
        }
        return gameBoardArray;
    }

    public JButton[][] generateButtonArray() {
        JButton[][] buttonArray = new JButton[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttonArray[i][j] = new JButton();
                buttonArray[i][j].addActionListener(this);              //Lägger till action listener på knappen när den skapas.
                buttonArray[i][j].setText(String.valueOf(gameBoard[i][j]));
                if (gameBoard[i][j] == 0) {
                    buttonArray[i][j].setVisible(false);
                }
                mainPanel.add(buttonArray[i][j]);
            }
        }

        return buttonArray;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if(s.isBlank()){
            s="0";
        }
        int selectedTile =Integer.parseInt(s);

        System.out.println(s);
        System.out.println(selectedTile);
        //används i testsyfte då jag inte kan skapa en testmap. Skriver ut button text.
        //Kanske gå att använda mot int array "gameBoard" för att ha koll vart varje tile är lättare?


    }
}
