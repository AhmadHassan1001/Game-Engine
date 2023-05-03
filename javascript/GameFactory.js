class GameFactory {

    static GetGame(game) {

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
                return new Chess([], rows, columns);
            }

            case 'Connect-4': {
                rows = 6;
                columns = 7;
                return new ConnectFour([], rows, columns);
            }

            case 'Sudoku': {
                rows = 9;
                columns = 9;
                return new Sudoku([], rows, columns);
            }
        }
    }
}