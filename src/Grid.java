import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class Grid implements ActionListener {

    JFrame frame = new JFrame();
    JPanel gamePanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton[][] buttonArray;
    JButton newGame = new JButton("Nytt spel");
    JButton cheatButton = new JButton("Fuskknappen");
    int turnCounter = 0;
    int rows = 4;
    int columns = 4;
    List<Integer> listOfNumbers;
    int[][] gameBoard;
    Stopwatch timer = new Stopwatch();


    public Grid(boolean testing) {

        if (!testing) {
            initGame();
        }

    }

    public int getTurnCounter() {
        return turnCounter;
    }

    public void initGame() {

        frame.setTitle("Femtonspelet");
        frame.setLayout(new BorderLayout());
        gamePanel.setLayout(new GridLayout(rows, columns));
        frame.add(gamePanel);
        frame.add(buttonPanel, BorderLayout.NORTH);
        buttonPanel.add(newGame);
        buttonPanel.add(cheatButton);
        newGame.addActionListener(this);
        cheatButton.addActionListener(this);

        listOfNumbers = generateListOfNumbers(false);
        gameBoard = generateBoardArray(listOfNumbers);
        buttonArray = generateButtonArray(gameBoard);

        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        timer.start();
    }

    public List<Integer> generateListOfNumbers(boolean fixed) {

        if (!fixed) {
            List<Integer> listOfNumbers = new ArrayList<>();
            for (int i = 1; i < columns * rows; i++) {
                listOfNumbers.add(i);
            }
            Collections.shuffle(listOfNumbers);
            listOfNumbers = makeSolvable(listOfNumbers);
            return listOfNumbers;

        } else {

            List<Integer> listOfNumbers = new ArrayList<>();
            for (int i = 0; i < columns * rows; i++) {
                listOfNumbers.add(i);
            }
            Collections.swap(listOfNumbers, 0, 1);
            Collections.swap(listOfNumbers, 5, 1);

            return listOfNumbers;
        }
    }

    // Returnerar en array av integer som motsvarar knapparnas
    // v??rden.
    public int[][] generateBoardArray(List<Integer> listOfNumbers) {
        int[][] gameBoardArray = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                gameBoardArray[i][j] = listOfNumbers.get((i * columns) + j);
        }
        return gameBoardArray;
    }

    // Returnerar en array av JButtons vars utseende, text och
    // actionListener s??tts i en for-loop. Knapp med texten "0"
    // blir osynlig.
    public JButton[][] generateButtonArray(int[][] gameBoard) {
        JButton[][] buttonArray = new JButton[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttonArray[i][j] = new JButton();
                buttonArray[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttonArray[i][j].addActionListener(this);
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


    // Ritar upp alla knappar. Anropas d?? knappar bytt
    // text efter att anv??ndaren klickat, samt vid
    // ny spelomg??ng.
    public void updateButtonsDisplay() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buttonArray[i][j].setText(String.valueOf(gameBoard[i][j]));
                buttonArray[i][j].setVisible(gameBoard[i][j] != 0);
            }
        }
    }

    // Metoden tar emot en JButton, l??ser av texten p?? den, hittar den
    // i grid-arrayen och kollar sedan om n??gon intilliggande knapp
    // ??r "tom", i s?? fall byter knapparna text och metoden returnerar
    // true, annars returnerar metoden false.
    public boolean updateButtonArray(JButton buttonPressed) {
        int number = Integer.parseInt(buttonPressed.getText());
        int x = 0;
        int y = 0;

        //hittar x och y f??r den siffra man klickat p??
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gameBoard[i][j] == number) {
                    x = i;
                    y = j;
                }
            }
        }

        // kollar knappen till v??nster om den man klickat p??
        if (x > 0) {
            if (gameBoard[x - 1][y] == 0) {
                gameBoard[x - 1][y] = number;
                gameBoard[x][y] = 0;
                return true;
            }
        }

        //kollar knappen till h??ger om den man klickat p??
        if (x < columns - 1) {
            if (gameBoard[x + 1][y] == 0) {
                gameBoard[x + 1][y] = number;
                gameBoard[x][y] = 0;
                return true;
            }
        }

        //kollar knappen ovanf??r den man klickat p??
        if (y > 0) {
            if (gameBoard[x][y - 1] == 0) {
                gameBoard[x][y - 1] = number;
                gameBoard[x][y] = 0;
                return true;
            }
        }

        //kollar knappen nedanf??r den man klickat p??
        if (y < rows - 1) {
            if (gameBoard[x][y + 1] == 0) {
                gameBoard[x][y + 1] = number;
                gameBoard[x][y] = 0;
                return true;
            }
        }

        //returnerar false om man tryckte p?? en knapp som
        //inte hade en tom knapp bredvid
        return false;
    }

    // Metoden returnerar true om siffrorna ligger i nummerordning
    // och om den tomma rutan ??r f??rst eller sist.
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

    // Metoden tar emot en lista och r??knar antalet "inversions", dvs
    // par av siffror d??r en efterliggande siffran ??r h??gra ??n den
    // som st??r f??rst. Om antalet inversion ??r udda m??ste den tomma rutan
    // ligga p?? en j??mn rad nerifr??n r??knat; om antalet inversions ??r j??mnt
    // m??ste den tomma rutan ligga p?? en udda rad nerifr??n r??knat. Metoden
    // stoppar in den tomma rutan (0:an) p?? r??tt rad och returnerar listan.
    // Koden ??r v??r egen, men logiken har vi hittat p?? denna websida:
    // https://www.geeksforgeeks.org/check-instance-15-puzzle-solvable/
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

        if (numberOfInversions % 2 == 0) { // j??mnt antal inversions
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

        return list;
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        if (buttonPressed.equals(newGame) || buttonPressed.equals(cheatButton)) {

            if (buttonPressed.equals(newGame)) {

                listOfNumbers = generateListOfNumbers(false);

            } else if (buttonPressed.equals(cheatButton)) {

                listOfNumbers = generateListOfNumbers(true);

            }

            turnCounter = 0;
            timer.reset();
            gameBoard = generateBoardArray(listOfNumbers);
            updateButtonsDisplay();

        } else {

            if (updateButtonArray(buttonPressed)) {
                updateButtonsDisplay();
                turnCounter++;
                if (checkForWinningPosition()) {

                    timer.stop();

                    String winMessage = "Grattis, du l??ste pusslet!\n\nAntal f??rflyttningar: " + getTurnCounter() + "\nTid: " + timer.getStringTime() + "\n\n";
                    String[] choices = {"Nytt spel", "Avsluta"};

                    int userChoice = JOptionPane.showOptionDialog(null, winMessage, "Grattis!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choices, choices[0]);

                    if (userChoice == 0) {
                        newGame.doClick();

                    } else {
                        System.exit(0);
                    }
                }

            }


        }
    }
}


