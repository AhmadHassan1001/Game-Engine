class GameFactory {

    static GetGame(game) {

        let rows, columns;
        switch (game) {
            
            case 'Chess':
                rows = 8;
                columns = 8;
                return new Chess([], rows, columns);

            case 'Connect-4':
                rows = 6;
                columns = 7;
                return new ConnectFour([], rows, columns);

            case 'Sudoku':
                rows = 9;
                columns = 9;
                return new Sudoku([], rows, columns);

            case 'Tic-Tac-Toe':
                rows = 3;
                columns = 3;
                return new TicTacToe([], rows, columns);

            case '8-Queens':
                rows = 8;
                columns = 8;
                return new Queens([], rows, columns);

            }
            case 'Checker': {
                rows = 8;
                columns = 8;
                return new Checker([], rows, columns);
            }
        }
    }
}