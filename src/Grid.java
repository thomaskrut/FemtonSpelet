import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class Grid implements ActionListener {
    JFrame frame = new JFrame();
    JPanel gamePanel = new JPanel(); //Ändrar namn till gridPanel för att lättare ha koll på vad koden referar till.
    JPanel buttonPanel = new JPanel();
    JButton[][] buttonArray;
    JButton newGame = new JButton("Nytt spel");
    JButton cheatButton = new JButton("Fuskknappen");
    int turnCounter = 0;
    int rows = 4; //Horizontal
    int columns = 4; //Vertikal
    List<Integer> listOfNumbers = generateListOfNumbers();
    int[][] gameBoard = generateBoardArray(listOfNumbers); //Skapar 'spelbrädan'.
    Stopwatch timer=new Stopwatch();



    public Grid(boolean testing) {

        if (!testing) {
            showGrid();
        }

    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void showGrid() {
        frame.setTitle("Femtonspelet");
        frame.setLayout(new BorderLayout());
        gamePanel.setLayout(new GridLayout(rows, columns));
        buttonArray = generateButtonArray(); //Skapar buttonarray och tilldelar textvärde 0-15.
        frame.add(gamePanel);
        frame.add(buttonPanel, BorderLayout.NORTH);
        buttonPanel.add(newGame);
        buttonPanel.add(cheatButton);
        newGame.addActionListener(this);
        cheatButton.addActionListener(this);
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        timer.start();
    }

    public List<Integer> generateListOfNumbers() {
        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 1; i < columns * rows; i++) {
            listOfNumbers.add(i);
        }
        Collections.shuffle(listOfNumbers);
        listOfNumbers = makeSolvable(listOfNumbers);
        return listOfNumbers;
    }

    public List<Integer> generateFixedListOfNumbers() {
        List<Integer> listOfNumbers = new ArrayList<>();
        for (int i = 0; i < columns * rows; i++) {
            listOfNumbers.add(i);
        }
        Collections.swap(listOfNumbers, 0, 1);
        Collections.swap(listOfNumbers, 5, 1);

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
                buttonArray[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                //buttonArray[i][j].setBackground(new Color(200, 200, 200));
                buttonArray[i][j].addActionListener(this);              //Lägger till action listener på knappen när den skapas.
                buttonArray[i][j].setText(String.valueOf(gameBoard[i][j]));
                buttonArray[i][j].setFocusable(false);

                if (gameBoard[i][j] == 0) {
                    buttonArray[i][j].setVisible(false);
                }
                gamePanel.add(buttonArray[i][j]);
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
                if (gameBoard[i][j] == counter) {
                    counter++;
                }
            }
        }

        if (counter == rows * columns && (gameBoard[0][0] == 0 || gameBoard[rows - 1][columns - 1] == 0)) {
            return true;
        } else {
            return false;
        }

    }

    public List<Integer> makeSolvable(List<Integer> list) {

        int numberOfInversions = 0;
        Random rand = new Random();
        int rowToPutZero = rand.nextInt(rows);
        int columnToPutZero = rand.nextInt(columns);
        int indexOfZeroInList;

        for (int i = 0; i < list.size(); i++) {

            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) > list.get(j)) {
                    numberOfInversions++;
                }
            }

        }

        if (numberOfInversions % 2 == 0) { // jämnt antal inversions
            while (rowToPutZero % 2 == 0) {
                rowToPutZero = rand.nextInt(rows);
            }
        }
        if (numberOfInversions % 2 != 0) { // udda antal inversions
            while (rowToPutZero % 2 != 0) {
                rowToPutZero = rand.nextInt(rows);
            }
        }

        indexOfZeroInList = columnToPutZero + (rowToPutZero * rows);
        list.add(indexOfZeroInList, 0);

        System.out.println(numberOfInversions);
        System.out.println(rowToPutZero);

        return list;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();
        if (buttonPressed.equals(newGame) || buttonPressed.equals(cheatButton)) {
            if (buttonPressed.equals(newGame)) {
                List<Integer> listOfNumbers = generateListOfNumbers();
                gameBoard = generateBoardArray(listOfNumbers);
                updateButtonsDisplay();
                turnCounter = 0;
                timer.reset();


            } else if (buttonPressed.equals(cheatButton)) {
                timer.reset();
                List<Integer> listOfNumbers = generateFixedListOfNumbers();
                gameBoard = generateBoardArray(listOfNumbers);
                updateButtonsDisplay();
            }

        } else {
            if (updateButtonArray(buttonPressed)) {
                // System.out.println(buttonPressed.getText());
                updateButtonsDisplay();
                turnCounter++;
                if (checkForWinningPosition()) {
                    timer.stop();
                    String winMessage = "Grattis, du löste pusslet!\n\nAntal förflyttningar: " + getTurnCounter() + "\nTid: "+ timer.getTimeString()+"\n\n";
                    String[] choices = {"Nytt spel", "Avsluta"};

                    int userChoice = JOptionPane.showOptionDialog(null, winMessage, "Grattis!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choices, choices[0]);

                    if (userChoice == 0) {
                        newGame.doClick();

                    } else {
                        System.exit(0);
                    }
                }
                // System.out.println(turnCounter);
            }


        }
    }
}


