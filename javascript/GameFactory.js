class GameFactory {

    static GetGame(game) {

        let controller, drawer;
        switch (game) {
            // case 'Tic-Tac-Toe': controller = new ConnectFourController(); drawer = new ConnectFourDrawer(controller.drawerMap); break;
            case 'Connect-4'  : controller = new ConnectFourController(); drawer = new ConnectFourDrawer(controller.drawerMap); break;
            // case 'Checkers'   : controller = new ConnectFourController(); drawer = new ConnectFourDrawer(controller.drawerMap); break;
            case 'Chess'      : controller = new ChessController();       drawer = new ChessDrawer(controller.drawerMap);       break;
            case 'Sudoku'     : controller = new SudokuController();      drawer = new SudokuDrawer(controller.drawerMap);      break;
            // case '8-Queens'   : controller = new ConnectFourController(); drawer = new ConnectFourDrawer(controller.drawerMap); break;
        }

        return [controller, drawer];
    }
}