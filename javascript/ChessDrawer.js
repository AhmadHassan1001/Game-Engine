

class ChessDrawer extends Drawer {

    constructor() {
        super(
            8, 8,                                               // rows, columns
            color(255, 255, 255, 255), color(60, 60, 60, 255),  // primary and secondary colours
            1,                                                  // stroke weight
            loadImage('/javascript/assets/chess_pieces.png'),   // sprite sheet
            800 / 6, 267 / 2                                    // piece width and height
        );

        for (let j = 0; j < this.columns; j++) {
            this.pieces[1][j] = new ChessPiece(1, 5);
            this.pieces[this.rows - 2][j] = new ChessPiece(0, 5);
        }

        // rook
        this.pieces[0][0] = new ChessPiece(1, 4);
        this.pieces[this.rows - 1][0] = new ChessPiece(0, 4);

        // knight
        this.pieces[0][1] = new ChessPiece(1, 3);
        this.pieces[this.rows - 1][1] = new ChessPiece(0, 3);

        // bishop
        this.pieces[0][2] = new ChessPiece(1, 2);
        this.pieces[this.rows - 1][2] = new ChessPiece(0, 2);

        // queen
        this.pieces[0][3] = new ChessPiece(1, 1);
        this.pieces[this.rows - 1][3] = new ChessPiece(0, 1);

        // king
        this.pieces[0][4] = new ChessPiece(1, 0);
        this.pieces[this.rows - 1][4] = new ChessPiece(0, 0);

        // bishop
        this.pieces[0][5] = new ChessPiece(1, 2);
        this.pieces[this.rows - 1][5] = new ChessPiece(0, 2);

        // knight
        this.pieces[0][6] = new ChessPiece(1, 3);
        this.pieces[this.rows - 1][6] = new ChessPiece(0, 3);
        
        // rook
        this.pieces[0][7] = new ChessPiece(1, 4);
        this.pieces[this.rows - 1][7] = new ChessPiece(0, 4);

    }
}
