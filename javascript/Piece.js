class Piece {
    
    constructor(i, j, pieceWidth, pieceHeight,type) {
        this.pieceWidth = pieceWidth;
        this.pieceHeight = pieceHeight;
        this.x = pieceWidth * j;
        this.y = pieceHeight * i;
        this.labels=[]
        this.type=type
    }
}

class ChessPiece extends Piece {
    
    constructor(i, j,type) {
        super(i, j, 800 / 6, 267 / 2,type);
    }
}