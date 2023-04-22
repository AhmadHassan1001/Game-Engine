

class ChessDrawer extends Drawer {

    constructor() {
        super(
            8, 8,                                               // rows, columns
            color(255, 255, 255, 255), color(60, 60, 60, 255),  // primary and secondary colours
            1,                                                  // stroke weight
            loadImage('/javascript/assets/chess_pieces.png'),    // sprite sheet
            800 / 6, 267 / 2                                    // piece width and height
        );

        for (let j = 0; j < this.columns; j++) {
            this.pieces[1][j] = new ChessPiece(1, 5,'pawn');
            this.pieces[1][j].labels.push('black');
            this.pieces[this.rows - 2][j] = new ChessPiece(0, 5,'pawn');
            this.pieces[this.rows - 2][j].labels.push('white');
        }

        // rook black
        this.pieces[0][0] = new ChessPiece(1, 4,'rock');
        this.pieces[0][0].labels.push('black');

        //rook white
        this.pieces[this.rows - 1][0] = new ChessPiece(0, 4,'rock');
        this.pieces[this.rows - 1][0].labels.push('white');


        // knight
        this.pieces[0][1] = new ChessPiece(1, 3,'knight');
        this.pieces[0][1].labels.push('black');
        this.pieces[this.rows - 1][1] = new ChessPiece(0, 3,'knight');
        this.pieces[this.rows - 1][1].labels.push('white');

        // bishop
        this.pieces[0][2] = new ChessPiece(1, 2,'bishop');
        this.pieces[0][2].labels.push('black');
        this.pieces[this.rows - 1][2] = new ChessPiece(0, 2,'bishop');
        this.pieces[this.rows - 1][2].labels.push('white');

        // queen
        this.pieces[0][3] = new ChessPiece(1, 1,'queen');
        this.pieces[0][3].labels.push('black');
        this.pieces[this.rows - 1][3] = new ChessPiece(0, 1,'queen');
        this.pieces[this.rows - 1][3].labels.push('white');

        // king
        this.pieces[0][4] = new ChessPiece(1, 0,'king');
        this.pieces[0][4].labels.push('black');
        this.pieces[this.rows - 1][4] = new ChessPiece(0, 0,'king');
        this.pieces[this.rows - 1][4].labels.push('white');

        // bishop
        this.pieces[0][5] = new ChessPiece(1, 2,'bishop');
        this.pieces[0][5].labels.push('black');
        this.pieces[this.rows - 1][5] = new ChessPiece(0, 2,'bishop');
        this.pieces[this.rows - 1][5].labels.push('white');

        // knight
        this.pieces[0][6] = new ChessPiece(1, 3,'knight');
        this.pieces[0][6].labels.push('black');
        this.pieces[this.rows - 1][6] = new ChessPiece(0, 3,'knight');
        this.pieces[this.rows - 1][6].labels.push('white');

        // rook
        this.pieces[0][7] = new ChessPiece(1, 4,'rook');
        this.pieces[0][7].labels.push('black');
        this.pieces[this.rows - 1][7] = new ChessPiece(0, 4,'rook');
        this.pieces[this.rows - 1][7].labels.push('white');

    }
}
