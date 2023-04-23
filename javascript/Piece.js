class Piece {
    
    constructor(i, j, pieceWidth, pieceHeight) {
        this.pieceWidth = pieceWidth;
        this.pieceHeight = pieceHeight;
        this.x = pieceWidth * j;
        this.y = pieceHeight * i;
    }
}

class ChessPiece extends Piece {
    
    constructor(i, j) {
        super(i, j, 800 / 6, 267 / 2);
    }
}

class Connect4Piece {
    
    constructor() {
        this.colour = 'rgba(255, 255, 255, 255)';
    }
}