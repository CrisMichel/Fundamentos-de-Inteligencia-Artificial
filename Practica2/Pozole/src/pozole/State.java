package pozole;

import java.util.ArrayList;

/**
 *
 * @author 
 */
public class State 
{
    int[][] board; 
    int posI;
    int posJ;
    char movement='n';
    
    public State(String sts)
    {
        int n=0;
        this.board = new int[4][4];
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++)
            {   
                char c=sts.charAt(n);
                if(c == 'A') board[i][j]=10;
                if(c == 'B') board[i][j]=11;
                if(c == 'C') board[i][j]=12;
                if(c == 'D') board[i][j]=13;
                if(c == 'E') board[i][j]=14;
                if(c == 'F') board[i][j]=15;
                else
                    board[i][j]=Character.getNumericValue(c);
                
                if(board[i][j]==0)
                {
                    posI = i;
                    posJ = j;
                }
                n++;
                System.out.print(board[i][j]);
            }
        }
        System.out.println("");
    }
    
    public State (int[][] board, char movement)
    {
        this.movement = movement;
        this.board = new int[4][4];
        for(int i=0;i<4;i++)
            for(int j=0;j<4;j++)
            {
                this.board[i][j]=board[i][j];
                if(board[i][j]==0)
                {
                    posI = i;
                    posJ = j;
                }
            }
    }
    
    public void show()
    {
        switch(movement)
        {
            case 'u' -> System.out.println("Move UP");
            case 'd' -> System.out.println("Move DOWN");
            case 'l' -> System.out.println("Move LEFT");
            case 'r' -> System.out.println("Move RIGHT");
            case 'n' -> System.out.println("START");
        }
        System.out.println("+--+--+--+--+");
        for(int i=0;i<4;i++)
        {   System.out.print("|");
            for(int j=0;j<4;j++)
            {
                if(board[i][j]>9)
                    System.out.print(board[i][j]+"|");
                else
                    System.out.print(board[i][j]+" |");
            }
            System.out.println("\n+--+--+--+--+");
        }
    }
    
    public void swap(int i,int j)
    {
        int aux=board[i][j];
        board[i][j]=0;
        board[posI][posJ]=aux;
        posI=i;
        posJ=j;
    }
    
    
    // Función sucesor
    public ArrayList<State> nextStates() {
        ArrayList<State> next = new ArrayList<>();
        int[][] newBoard = new int[4][4];

        // Clone board
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newBoard[i][j] = this.board[i][j];
                if (board[i][j] == 0) {
                    posI = i;
                    posJ = j;
                }
            }
        }

        // Move up
        if (posI > 0) {
            int newI = posI - 1;
            State newState = new State(newBoard, 'u');
            newState.swap(newI, posJ);
            next.add(newState);
        }

        // Move down
        if (posI < 3) {
            int newI = posI + 1;
            State newState = new State(newBoard, 'd');
            newState.swap(newI, posJ);
            next.add(newState);
        }

        // Move left
        if (posJ > 0) {
            int newJ = posJ - 1;
            State newState = new State(newBoard, 'l');
            newState.swap(posI, newJ);
            next.add(newState);
        }

        // Move right
        if (posJ < 3) {
            int newJ = posJ + 1;
            State newState = new State(newBoard, 'r');
            newState.swap(posI, newJ);
            next.add(newState);
        }

        return next;
    }

    
    public int[][] getBoard()
    {
        return board;
    }
    
    // Test objetivo
    public boolean goalFunction(State goal) {
        boolean success = true;
        int[][] goalBoard = goal.getBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (goalBoard[i][j] != board[i][j]) {
                    success = false;
                    break;
                }
            }
            if (!success) {
                break;
            }
        }
        return success;
    }
    
    public int getI()
    {
        return posI;
    }
    
    public int getJ()
    {
        return posJ;
    }
    
    public char getMovement()
    {
        return movement;
    }
    
    public boolean isEqual(State s) {
        boolean isEqual = true;
        int[][] sBoard = s.getBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (sBoard[i][j] != board[i][j]) {
                    isEqual = false;
                    break;
                }
            }
            if (!isEqual) {
                break;
            }
        }
        return isEqual;
    }
}
