import OXOExceptions.*;

class OXOController
{
    OXOModel gameModel;
    int currentPlayerIndex;
    final int MAX_COMMAND_LENGTH = 2;
    final int MAX_BOARD_DIMENSION = 9;

    public OXOController(OXOModel model)
    {
        gameModel = model;
        currentPlayerIndex = 0;
        gameModel.setCurrentPlayer(gameModel.getPlayerByNumber(currentPlayerIndex));
        gameModel.setWinner(null);
    }

    public void handleIncomingCommand(String command) throws OXOMoveException
    {
        checkIncomingCommand(command);
        userInteraction(command);
        if (checkForWinner() == false && isBoardFull() == true) {
            gameModel.setGameDrawn();
        }
    }

    void checkIncomingCommand(String command) throws OXOMoveException {
        checkCommandLength(command);
        checkCharacter(command);
        checkCellBound(command);
        checkCellAvailability(command);
    }

    void userInteraction(String command) {
        int row = getRowIndex(command.charAt(0));
        int col = getColIndex(command.charAt(1));
        gameModel.setCellOwner(row,col,gameModel.getPlayerByNumber(currentPlayerIndex));
        setCurrentPlayerIndex();
        gameModel.setCurrentPlayer(gameModel.getPlayerByNumber(currentPlayerIndex));
    }

    boolean checkForWinner() {
        if (checkWinInAllRows() == true) {
            return true;
        }
        if (checkWinInAllCols() == true) {
            return true;
        }
        if (checkWinInAllDiagonals() == true) {
            return true;
        }
        return false;
    }

    boolean isBoardFull() {
        for (int row = 0; row < gameModel.getNumberOfRows(); row++) {
            for (int col = 0; col < gameModel.getNumberOfColumns(); col++) {
                if (gameModel.getCellOwner(row,col) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean checkWinInAllRows() {
        for (int i = 0; i < gameModel.getNumberOfRows(); i++) {
            if(winByRow(i) == true) {
                return true;
            }
        }
        return false;
    }

    boolean checkWinInAllCols() {
        for (int i = 0; i < gameModel.getNumberOfColumns(); i++) {
            if(winByCol(i) == true) {
                return true;
            }
        }
        return false;
    }

    boolean checkWinInAllDiagonals() {
        int rowOffset = gameModel.getNumberOfRows() - gameModel.getWinThreshold();
        int colOffset = gameModel.getNumberOfColumns() - gameModel.getWinThreshold();
        for (int row = 0; row < rowOffset + 1; row++) {
            for (int col = 0; col < colOffset + 1; col++) {
                if (winSubTopLeftToBottomRight(row,col) == true) {
                    gameModel.setWinner(gameModel.getCellOwner(row,col));
                    return true;
                }
                if (winSubBottomLeftToTopRight(row + gameModel.getWinThreshold() - 1,col) == true) {
                    gameModel.setWinner(gameModel.getCellOwner(row + gameModel.getWinThreshold() - 1,col));
                    return true;
                }
            }
        }
        return false;
    }

    boolean winByRow(int row) {
        int offset = gameModel.getNumberOfColumns() - gameModel.getWinThreshold();
        for (int i = 0; i < offset + 1; i++) {
            if (winSubRow(row,i) == true) {
                gameModel.setWinner(gameModel.getCellOwner(row,i));
                return true;
            }
        }
        return false;
    }

    boolean winByCol(int col) {
        int offset = gameModel.getNumberOfRows() - gameModel.getWinThreshold();
        for (int i = 0; i < offset + 1; i++) {
            if (winSubCol(i,col) == true) {
                gameModel.setWinner(gameModel.getCellOwner(i,col));
                return true;
            }
        }
        return false;
    }

    boolean winSubRow(int row, int col) {
        if (gameModel.getWinThreshold() == 1 && gameModel.getCellOwner(row,col) == null) {
            return false;
        }
        for (int i = 0; i < gameModel.getWinThreshold() - 1; i++) {
            if (compareTwoCells(row, i + col, row, i + col + 1) == false) {
                return false;
            }
        }
        return true;
    }

    boolean winSubCol(int row, int col) {
        if (gameModel.getWinThreshold() == 1 && gameModel.getCellOwner(row,col) == null) {
            return false;
        }
        for (int i = 0; i < gameModel.getWinThreshold() - 1; i++) {
            if (compareTwoCells(row + i, col, row + i + 1, col) == false) {
                return false;
            }
        }
        return true;
    }

    boolean winSubTopLeftToBottomRight(int row, int col) {
        if (gameModel.getWinThreshold() == 1 && gameModel.getCellOwner(row,col) == null) {
            return false;
        }
        for (int i = 0; i < gameModel.getWinThreshold() - 1; i++) {
            if (compareTwoCells(row + i, col + i, row + i + 1, col + i + 1) == false) {
                return false;
            }
        }
        return true;
    }

    boolean winSubBottomLeftToTopRight(int row, int col) {
        if (gameModel.getWinThreshold() == 1 && gameModel.getCellOwner(row,col) == null) {
            return false;
        }
        for (int i = 0; i < gameModel.getWinThreshold() - 1; i++) {
            if (compareTwoCells(row - i, col + i, row - i - 1, col + i + 1) == false) {
                return false;
            }
        }
        return true;
    }

    boolean compareTwoCells(int row1, int col1, int row2, int col2) {
        if (gameModel.getCellOwner(row1,col1) == gameModel.getCellOwner(row2,col2)
                && gameModel.getCellOwner(row1,col1) != null) {
            return true;
        }
        return false;
    }

    void checkCommandLength(String command) throws InvalidIdentifierLengthException {
        if (command.length() != MAX_COMMAND_LENGTH) {
            throw new InvalidIdentifierLengthException(command.length());
        }
    }

    void checkCharacter(String command) throws InvalidIdentifierCharacterException {
        int row = getRowIndex(command.charAt(0));
        int col = getColIndex(command.charAt(1));
        if (row < 0 || row > (MAX_BOARD_DIMENSION - 1)) {
            throw new InvalidIdentifierCharacterException(command.charAt(0),RowOrColumn.ROW);
        }
        if (col < 0 || col > (MAX_BOARD_DIMENSION - 1)) {
            throw new InvalidIdentifierCharacterException(command.charAt(1),RowOrColumn.COLUMN);
        }
    }

    void checkCellBound(String command) throws OutsideCellRangeException {
        int row = getRowIndex(command.charAt(0));
        int col = getColIndex(command.charAt(1));
        if (row >= gameModel.getNumberOfRows()) {
            throw new OutsideCellRangeException(gameModel.getNumberOfRows(),
                    gameModel.getNumberOfColumns(),
                    row,RowOrColumn.ROW);
        }
        if (col >= gameModel.getNumberOfColumns()) {
            throw new OutsideCellRangeException(gameModel.getNumberOfRows(),
                    gameModel.getNumberOfColumns(),
                    col,RowOrColumn.COLUMN);
        }
    }

    void checkCellAvailability(String command) throws CellAlreadyTakenException {
        int row = getRowIndex(command.charAt(0));
        int col = getColIndex(command.charAt(1));
        if (gameModel.getCellOwner(row,col) != null) {
            throw new CellAlreadyTakenException(row,col);
        }
    }

    int getRowIndex(char c) {
        return Character.toLowerCase(c) - 'a';
    }

    int getColIndex(char c) {
        return c - '0' - 1;
    }

    void setCurrentPlayerIndex() {
        currentPlayerIndex++;
        currentPlayerIndex %= gameModel.getNumberOfPlayers();
    }

    int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
/*
    public void printCells() {
        gameModel.printCells();
    }

 */
}
