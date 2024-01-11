import java.util.*;

class OXOModel
{
    private ArrayList<ArrayList<OXOPlayer>> cells;
    private ArrayList<OXOPlayer> players;
    private OXOPlayer currentPlayer;
    private OXOPlayer winner;
    private boolean gameDrawn;
    private int winThreshold;

    //changed
    public OXOModel(int numberOfRows, int numberOfColumns, int winThresh)
    {
        gameDrawn = false;
        winThreshold = winThresh;
        cells = new ArrayList<>(numberOfRows);
        for (int i = 0; i < numberOfRows; i++) {
            cells.add(new ArrayList<OXOPlayer>(numberOfColumns));
        }
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                cells.get(i).add(null);
            }
        }
        players = new ArrayList<OXOPlayer>(2);
    }

    //changed
    public int getNumberOfPlayers()
    {
        return players.size();
    }

    //changed
    public void addPlayer(OXOPlayer player)
    {
        players.add(player);
    }

    //changed
    public OXOPlayer getPlayerByNumber(int number)
    {
        return players.get(number);
    }

    public OXOPlayer getWinner()
    {
        return winner;
    }

    public void setWinner(OXOPlayer player)
    {
        winner = player;
    }

    public OXOPlayer getCurrentPlayer()
    {
        return currentPlayer;
    }

    public void setCurrentPlayer(OXOPlayer player)
    {
        currentPlayer = player;
    }

    //changed
    public int getNumberOfRows()
    {
        return cells.size();
    }

    //changed
    public int getNumberOfColumns()
    {
        return cells.get(0).size();
    }

    //changed
    public OXOPlayer getCellOwner(int rowNumber, int colNumber)
    {
        return cells.get(rowNumber).get(colNumber);
    }

    //changed
    public void setCellOwner(int rowNumber, int colNumber, OXOPlayer player)
    {
        if (colNumber >= this.getNumberOfColumns()) {
            for (int i = 0; i < this.getNumberOfRows(); i++) {
                for (int j = cells.get(i).size(); j < colNumber + 1; j++) {
                    cells.get(i).add(null);
                }
            }
        }

        if (rowNumber >= this.getNumberOfRows()) {
            for (int i = cells.size(); i < rowNumber + 1; i++) {
                cells.add(new ArrayList<OXOPlayer>(this.getNumberOfColumns()));
                for (int j = 0; j < this.getNumberOfColumns(); j++) {
                    cells.get(i).add(null);
                }
            }
        }
        cells.get(rowNumber).set(colNumber,player);
    }

    public void setWinThreshold(int winThresh)
    {
        winThreshold = winThresh;
    }

    public int getWinThreshold()
    {
        return winThreshold;
    }

    public void setGameDrawn()
    {
        gameDrawn = true;
    }

    public boolean isGameDrawn()
    {
        return gameDrawn;
    }

/*
    public void printCells() {
        System.out.println(this.cells);
    }

 */

}
