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
    List<Integer> listOfNumbers = generateFixedListOfNumbersForTesting();
    int[][] gameBoard = generateBoardArray(listOfNumbers); //Skapar 'spelbrädan'. todo:Klarar vi oss med att bara Jbutton arrayen? Ska denna raderas?

    public Grid() {

        showGrid();
    }

    public void showGrid() {

        mainPanel.setLayout(new GridLayout(rows, columns));
        //mainPanel.setBackground(Color.black);
        buttonArray = generateButtonArray(); //Skapar buttonarray och tilldelar textvärde 0-15.
        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public List<Integer> generateListOfNumbers() {
        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < columns * rows; i++) {
            listOfNumbers.add(i);
        }
        Collections.shuffle(listOfNumbers);
        return listOfNumbers;
    }

    public List<Integer> generateFixedListOfNumbers() {
        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < columns * rows; i++) {
            listOfNumbers.add(i);
        }
        Collections.swap(listOfNumbers, 0,1);
        Collections.swap(listOfNumbers, 5,1);

        return listOfNumbers;
    }

    public List<Integer> generateFixedListOfNumbersForTesting() {
        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 1; i < columns * rows; i++) {
            listOfNumbers.add(i);
        }

        listOfNumbers.add(0);

        Collections.swap(listOfNumbers, 14,15);

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
                buttonArray[i][j].setFocusable(false);

                if (gameBoard[i][j] == 0) {
                    buttonArray[i][j].setVisible(false);
                }
                mainPanel.add(buttonArray[i][j]);
            }
        }

        return buttonArray;
    }


    //ritar upp alla knappar
    public void updateButtonsDisplay() {

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttonArray[i][j].setText(String.valueOf(gameBoard[i][j]));
                if (gameBoard[i][j] == 0) {
                    buttonArray[i][j].setVisible(false);
                } else {
                    buttonArray[i][j].setVisible(true);
                }
            }
        }
    }

    public boolean updateButtonArray(JButton buttonPressed) {
        int number = Integer.parseInt(buttonPressed.getText());
        int x = 0;
        int y = 0;

        //hittar x och y för den siffra man klickat på
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameBoard[i][j] == number) {
                    x = i;
                    y = j;
                }
            }
        }

        // kollar knappen till vänster om den man klickat på
        if (x > 0) {
            if (gameBoard[x - 1][y] == 0) {
                gameBoard[x - 1][y] = number;
                gameBoard[x][y] = 0;
                return true;
            }
        }

        //kollar knappen till höger om den man klickat på
        if (x < columns - 1) {
            if (gameBoard[x + 1][y] == 0) {
                gameBoard[x + 1][y] = number;
                gameBoard[x][y] = 0;
                return true;
            }
        }

        //kollar knappen ovanför den man klickat på
        if (y > 0) {
            if (gameBoard[x][y - 1] == 0) {
                gameBoard[x][y - 1] = number;
                gameBoard[x][y] = 0;
                return true;
            }
        }

        //kollar knappen nedanför den man klickat på
        if (y < rows - 1) {
            if (gameBoard[x][y + 1] == 0) {
                gameBoard[x][y + 1] = number;
                gameBoard[x][y] = 0;
                return true;
            }
        }

        //returnerar false om man tryckte på en knapp som
        //inte hade en tom knapp bredvid
        return false;
    }

    public boolean checkForWinningPosition() {

        int counter = 1;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(gameBoard[i][j] == counter) {
                    counter++;
                }
            }
        }

        System.out.println(counter);

        if (counter == rows * columns) {
            return true;
        }
        else {
            return false;
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        System.out.println(buttonPressed.getText());

        if (updateButtonArray(buttonPressed)) {

            updateButtonsDisplay();

            if(checkForWinningPosition()) {

                int userChoice = JOptionPane.showConfirmDialog(null, "Grattis, du löste pusslet! Antal förflyttningar: " + " . Vill du starta ett nytt spel?");

                System.out.println(userChoice);

                if (userChoice == 0) {
                    gameBoard = generateBoardArray(generateListOfNumbers()); //TODO Anropa metod istället som startar nytt spel?
                    updateButtonsDisplay();
                }
            }
        }

    }
}


