class SudokuDrawer extends Drawer {

    constructor() {
        super(
            9, 9,
            color(255, 255, 255, 255), color(255, 255, 255, 255),
            1
        );

        this.pieces[0][0] = 2;
        this.pieces[0][1] = 9;
        this.pieces[0][2] = 1;
        this.pieces[0][3] = 3;
        this.pieces[0][4] = 4;
    }

    DrawBoard() {
        super.DrawBoard();
        
        stroke(0, 0, 0, 255);
        strokeWeight(10);
        line(0, canvasHeight / 3 - 5, canvasWidth, canvasHeight / 3);
        line(0, canvasHeight / 3 * 2 - 5, canvasWidth, canvasHeight / 3 * 2);
        line(canvasWidth / 3, 0, canvasWidth / 3 - 5, canvasHeight);
        line(canvasWidth / 3 * 2, 0, canvasWidth / 3 * 2 - 5, canvasHeight);
    }

    DrawPieces() {
        const tileWidth = canvasWidth / this.columns;
        const tileHeight = canvasHeight / this.rows;

        this.pieces.forEach((row, i) => {
            row.forEach((element, j) => {
                if (element != null) {
                    textSize(40);
                    textStyle('bold');
                    text(element, j * tileWidth + 25, i * tileHeight + 50);
                }
            });
        });
    }
}