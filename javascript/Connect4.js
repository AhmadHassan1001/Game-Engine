class Connect4Drawer extends Drawer {

    constructor() {
        super(
            6, 7,
            null, null,
            3
        );

        for (let i = 0; i < this.rows; i++)
            for (let j = 0; j < this.columns; j++)
                this.pieces[i][j] = new Connect4Piece();
    }

    DrawBoard() {
        background('rgba(0, 0, 255, 255)');
    }

    DrawPieces() {
        const tileWidth = canvasWidth / this.columns;
        const tileHeight = canvasHeight / this.rows;

        this.pieces.forEach((row, i) => {
            row.forEach((element, j) => {
                if (element != null) {
                    strokeWeight(this.strokeWeight);
                    fill(element.colour);
                    circle(j * tileWidth + tileWidth / 2, i * tileHeight + tileHeight / 2, tileWidth - 5);
                }
            });
        });
    }
}