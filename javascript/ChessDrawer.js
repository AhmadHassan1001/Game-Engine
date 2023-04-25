

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
            this.pieces[1][j] = new Pawn(1, 5);
            this.pieces[1][j].labels.push('black');
            this.pieces[this.rows - 2][j] = new Pawn(0, 5);
            this.pieces[this.rows - 2][j].labels.push('white');
        }

        // rook black
        this.pieces[0][0] = new Rook(1, 4);
        this.pieces[0][0].labels.push('black');

        //rook white
        this.pieces[this.rows - 1][0] = new Rook(0, 4);
        this.pieces[this.rows - 1][0].labels.push('white');


        // knight
        this.pieces[0][1] = new Knight(1, 3);
        this.pieces[0][1].labels.push('black');
        this.pieces[this.rows - 1][1] = new Knight(0, 3);
        this.pieces[this.rows - 1][1].labels.push('white');

        // bishop
        this.pieces[0][2] = new Bishop(1, 2);
        this.pieces[0][2].labels.push('black');
        this.pieces[this.rows - 1][2] = new Bishop(0, 2);
        this.pieces[this.rows - 1][2].labels.push('white');

        // queen
        this.pieces[0][3] = new Queen(1, 1);
        this.pieces[0][3].labels.push('black');
        this.pieces[this.rows - 1][3] = new Queen(0, 1);
        this.pieces[this.rows - 1][3].labels.push('white');

        // king
        this.pieces[0][4] = new King(1, 0);
        this.pieces[0][4].labels.push('black');
        this.pieces[this.rows - 1][4] = new King(0, 0);
        this.pieces[this.rows - 1][4].labels.push('white');

        // bishop
        this.pieces[0][5] = new Bishop(1, 2);
        this.pieces[0][5].labels.push('black');
        this.pieces[this.rows - 1][5] = new Bishop(0, 2);
        this.pieces[this.rows - 1][5].labels.push('white');

        // knight
        this.pieces[0][6] = new Knight  (1, 3);
        this.pieces[0][6].labels.push('black');
        this.pieces[this.rows - 1][6] = new Knight  (0, 3);
        this.pieces[this.rows - 1][6].labels.push('white');

        // rook
        this.pieces[0][7] = new Rook(1, 4);
        this.pieces[0][7].labels.push('black');
        this.pieces[this.rows - 1][7] = new Rook(0, 4);
        this.pieces[this.rows - 1][7].labels.push('white');

    }
}
