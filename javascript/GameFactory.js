class GameFactory {

    static GetGame(game, map) {

        let rows, columns;
        switch (game) {
            // case 'Tic-Tac-Toe': controller = new ConnectFourController(); drawer = new ConnectFourDrawer(controller.drawerMap); break;
            // case 'Connect-4'  : controller = new ConnectFourController(); drawer = new ConnectFourDrawer(controller.drawerMap); break;
            // case 'Checkers'   : controller = new ConnectFourController(); drawer = new ConnectFourDrawer(controller.drawerMap); break;
            // case 'Chess'      : return new Chess();
            // case 'Sudoku'     : return new Sudoku();
            // case '8-Queens'   : controller = new ConnectFourController(); drawer = new ConnectFourDrawer(controller.drawerMap); break;


            case 'Chess': {
                rows = 8;
                columns = 8;
                return new Chess(map, rows, columns);
            }

            case 'Connect-4': {
                rows = 6;
                columns = 7;
                return new ConnectFour(map, rows, columns);
            }

            case 'Sudoku': {
                rows = 9;
                columns = 9;
                return new Sudoku(map, rows, columns);
            }

            case 'Queens': {
                rows = 8;
                columns = 8;
                return new Queens(map, rows, columns);
            }
        }
    }
}