class SudokuDrawer extends Drawer {

    constructor(pieces) {
        super(
            pieces,
            9, 9,
            color(255, 255, 255, 255), color(255, 255, 255, 255),
            1
        );

        this.originalBoard = JSON.parse(JSON.stringify(this.pieces)); // deep copy of this.drawerMap
    }

    DrawBoard() {
        super.DrawBoard();
        
        stroke(0, 0, 0, 255);
        strokeWeight(10);
        line(0, (canvasHeight - this.offset) / 3 - 5 + this.offset / 2, canvasWidth, (canvasHeight - this.offset) / 3 - 5 + this.offset / 2);
        line(0, (canvasHeight - this.offset) / 3 * 2 - 5 + this.offset / 2, canvasWidth, (canvasHeight - this.offset) / 3 * 2 - 5 + this.offset / 2);
        line((canvasWidth - this.offset) / 3 - 5 + this.offset / 2, 0, (canvasWidth - this.offset) / 3 - 5 + this.offset / 2, canvasHeight);
        line((canvasHeight - this.offset) / 3 * 2 - 5 + this.offset / 2, 0, (canvasHeight - this.offset) / 3 * 2 - 5 + this.offset / 2, canvasHeight);
    }

    DrawPieces() {
        const tileWidth = (canvasWidth - this.offset) / this.columns;
        const tileHeight = (canvasHeight - this.offset) / this.rows;

        this.pieces.forEach((row, i) => {
            row.forEach((element, j) => {
                if (element != null) {
                    textSize(30);
                    textStyle('bold');
                    if (this.originalBoard[i][j] != null)
                        fill(0, 0, 255, 255);
                    else
                        fill(255, 255, 255, 255);
                    text(element, j * tileWidth + this.offset / 2 + 25, i * tileHeight + 50 + this.offset / 2);
                }
            });
        });
    }
}

class SudokuController extends Controller {

    constructor() {
        super(null, 9, 9);

        this.drawerMap[0][0] = 2;
        this.drawerMap[0][1] = 9;
        this.drawerMap[0][2] = 1;
        this.drawerMap[0][3] = 3;
        this.drawerMap[0][4] = 4;

        this.originalBoard = JSON.parse(JSON.stringify(this.drawerMap)); // deep copy of this.drawerMap
    }

    run(action) {
        action = action.replaceAll(' ', '');
        action = action.toLowerCase();
        let actionList = action.split('-');
        
        let tileLetter = actionList[0][0];
        let tileNumber = actionList[0][1];
        let playedNumber = actionList[1];

        // validating input
        if (
            !isAlpha(tileLetter) || orderAlpha(tileLetter) > this.drawerMap[0].length - 1 ||
            !isDigit(tileNumber) || orderDigit(tileNumber) > this.drawerMap.length - 1 || orderDigit(tileNumber) < 1 ||
            playedNumber.length != 1 || !isDigit(playedNumber) || orderDigit(playedNumber) > 9 || orderDigit(playedNumber) < 1
        )
            return false;
        
        let rowIndex = orderDigit(tileNumber) - 1;
        let columnIndex = orderAlpha(tileLetter);
        
        if (this.originalBoard[rowIndex][columnIndex] != null)
            return false;

        this.drawerMap[rowIndex][columnIndex] = playedNumber;
        return true;
    }
}

const isAlpha=(c)=>{
    return c.charCodeAt(0) >= "a".charCodeAt(0) &&c.charCodeAt(0) <= "z".charCodeAt(0);
}
const isDigit=(c)=>{
    return c.charCodeAt(0) >= "0".charCodeAt(0) &&c.charCodeAt(0) <= "9".charCodeAt(0);
}