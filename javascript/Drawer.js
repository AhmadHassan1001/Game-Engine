class Drawer {

    constructor(
        pieces,
        rows, columns,
        primaryColour, secondaryColour,
        strokeWeight,
        spriteSheet,
        pieceWidth, pieceHeight
    ) {
        this.pieces = pieces;
        this.rows = rows;
        this.columns = columns;
        this.primaryColour = primaryColour;
        this.secondaryColour = secondaryColour;
        this.strokeWeight = strokeWeight;
        this.spriteSheet = spriteSheet;
        this.pieceWidth = pieceWidth;
        this.pieceHeight = pieceHeight;
        this.offset = 60;
    }

    DrawBoard() {
        const tileWidth = (canvasWidth - this.offset) / this.columns;
        const tileHeight = (canvasHeight - this.offset) / this.rows;

        background(255);

        strokeWeight(this.strokeWeight);

        for (let i = 0; i < this.rows; i++) {
            for (let j = 0; j < this.columns; j++) {
                if ((i + j) % 2 == 0)
                    fill(this.primaryColour);
                else
                    fill(this.secondaryColour);
                rect(j * tileWidth + this.offset / 2, i * tileHeight + this.offset / 2, tileWidth, tileHeight);
            }
        }

        for (let i = 0; i <= this.columns; i++) {
            let c = char(unchar('a') + i - 1);
            textSize(30);
            fill(0, 0, 0, 255);
            text(c, i * tileWidth - 20, 25);
            text(c, i * tileWidth - 20, canvasHeight - 5);
        }

        for (let i = 0; i <= this.rows; i++) {
            textSize(30);
            fill(0, 0, 0, 255);
            text(i, 5, i * tileHeight);
            text(i, canvasWidth - 25, i * tileHeight);
        }
    }

    DrawPieces() {
        const tileWidth = (canvasWidth - this.offset) / this.columns;
        const tileHeight = (canvasHeight - this.offset) / this.rows;

        this.pieces.forEach((row, i) => {
            row.forEach((element, j) => {
                if (element != null) {
                    image(this.spriteSheet, j * tileWidth + this.offset / 2, i * tileHeight + this.offset / 2, tileWidth, tileHeight, element.x, element.y, element.pieceWidth, element.pieceHeight);
                }
            });
        });
    }
}