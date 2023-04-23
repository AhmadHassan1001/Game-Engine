class Drawer {

    constructor(
        rows, columns,
        primaryColour, secondaryColour,
        strokeWeight,
        spriteSheet,
        pieceWidth, pieceHeight
    ) {
        this.rows = rows;
        this.columns = columns;
        this.primaryColour = primaryColour;
        this.secondaryColour = secondaryColour;
        this.strokeWeight = strokeWeight;
        this.spriteSheet = spriteSheet;
        this.pieceWidth = pieceWidth;
        this.pieceHeight = pieceHeight;

        this.pieces = [];
        for (let i = 0; i < this.rows; i++)
            this.pieces.push([]);
    
        for (let i = 0; i < this.rows; i++)
            for (let j = 0; j < this.columns; j++)
                this.pieces[i].push(null);
    }

    DrawBoard() {
        const tileWidth = canvasWidth / this.columns;
        const tileHeight = canvasHeight / this.rows;

        strokeWeight(this.strokeWeight);

        for (let i = 0; i < this.rows; i++) {
            for (let j = 0; j < this.columns; j++) {
                if ((i + j) % 2 == 0)
                    fill(this.primaryColour);
                else
                    fill(this.secondaryColour);
                rect(j * tileWidth, i * tileHeight, tileWidth, tileHeight);
            }
        }
    }

    DrawPieces() {
        const tileWidth = canvasWidth / this.columns;
        const tileHeight = canvasHeight / this.rows;

        this.pieces.forEach((row, i) => {
            row.forEach((element, j) => {
                if (element != null) {
                    image(this.spriteSheet, j * tileWidth, i * tileHeight, tileWidth, tileHeight, element.x, element.y, element.pieceWidth, element.pieceHeight);
                }
            });
        });
    }
}