class ConnectFourDrawer extends Drawer {

    constructor(pieces) {
        super(
            pieces,
            6, 7,
            null, null,
            3
        );
    }

    DrawBoard() {
        const tileWidth = (canvasWidth - this.offset) / this.columns;

        background(255);

        fill(0, 0, 255, 255);
        rect(this.offset / 2, this.offset / 2, canvasWidth - this.offset, canvasHeight - this.offset);

        for (let i = 0; i <= this.columns; i++) {
            let c = char(unchar('a') + i - 1);
            textSize(30);
            fill(0, 0, 0, 255);
            text(c, i * tileWidth - 20, 25);
            text(c, i * tileWidth - 20, canvasHeight - 5);
        }
    }

    DrawPieces() {
        const tileWidth = (canvasWidth - this.offset) / this.columns;
        const tileHeight = (canvasHeight - this.offset) / this.rows;

        this.pieces.forEach((row, i) => {
            row.forEach((element, j) => {
                if (element != null) {
                    strokeWeight(this.strokeWeight);
                    fill(element.colour);
                    circle(j * tileWidth + tileWidth / 2 + this.offset / 2, i * tileHeight + tileHeight / 2 + this.offset / 2, tileWidth - 5);
                }
            });
        });
    }
}

class ConnectFourController extends Controller {
    constructor() {
        super(null, 6, 7);

        for (let i = 0; i < this.rows; i++)
            for (let j = 0; j < this.columns; j++)
                this.drawerMap[i][j] = new ConnectFourPiece();
    }

    run(action) {

        this.player = (this.player % 2) + 1;

        if (action.length != 1 || !isAlpha(action) || orderAlpha(action) > this.columns - 1 || orderAlpha(action) < 0)
            return false;
        
        let columnIndex = orderAlpha(action);

        let i = 0;
        while (i < this.rows && this.drawerMap[i][columnIndex].colour == 'rgba(255, 255, 255, 255)')
            i++;

        if (this.player == 1)
            this.drawerMap[i - 1][columnIndex].colour = 'rgba(170, 45, 45, 255)';
        else
            this.drawerMap[i - 1][columnIndex].colour = 'rgba(245, 220, 35, 255)';
    }
}
