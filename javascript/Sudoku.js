class Sudoku extends Game {

    constructor(map, rows, columns) {
        super(map, rows, columns);
    }

    Drawer(map) {

        super.Drawer();
        
        const canvasHeight = 700;
        const canvasWidth = 700;
        const offset = 60;
        const rows = 9;
        const columns = 9;
        const primaryColour = color(255, 255, 255, 255);
        const secondaryColour = color(255, 255, 255, 255);
        const tileWidth = (canvasWidth - offset) / columns;
        const tileHeight = (canvasHeight - offset) / rows;
        
        this.SquareBoardDrawer(rows, columns, primaryColour, secondaryColour, tileWidth, tileHeight, offset);
        
        stroke(0, 0, 0, 255);
        strokeWeight(10);
        line(0, (canvasHeight - offset) / 3 - 5 + offset / 2, canvasWidth, (canvasHeight - offset) / 3 - 5 + offset / 2);
        line(0, (canvasHeight - offset) / 3 * 2 - 5 + offset / 2, canvasWidth, (canvasHeight - offset) / 3 * 2 - 5 + offset / 2);
        line((canvasWidth - offset) / 3 - 5 + offset / 2, 0, (canvasWidth - offset) / 3 - 5 + offset / 2, canvasHeight);
        line((canvasHeight - offset) / 3 * 2 - 5 + offset / 2, 0, (canvasHeight - offset) / 3 * 2 - 5 + offset / 2, canvasHeight);
        
        this.DrawText(rows, columns, tileWidth, tileHeight, canvasWidth, canvasHeight);
        this.PieceDrawer(map, tileWidth, tileHeight, offset);
    }

    PieceDrawer(map, tileWidth, tileHeight, offset) {

        strokeWeight(5);

        for (let i = 0; i < map.length; i++) {
            for (let j = 0; j < map[i].length; j++) {
                if (map[i][j] != null) {
                    textSize(30);
                    textStyle('bold');
                    fill(255, 255, 255, 255);
                    text(map[i][j], j * tileWidth + offset / 2 + 25, i * tileHeight + 50 + offset / 2);
                }
            }
        }
    }

    Controller(map, input, player) {

        const rows = map.length;
        const columns = map[0].length;

        
        input = input.replaceAll(' ', '');
        input = input.toLowerCase();

        if (input.length != 4)
            return [false, map];

        let inputList = input.split('-');
        let tileLetter = inputList[0][0];
        let tileNumber = inputList[0][1];
        let playedNumber = inputList[1];

        console.log(tileNumber);


        // validating input
        if (
            !isAlpha(tileLetter) || orderAlpha(tileLetter) > columns - 1 ||
            !isDigit(tileNumber) || orderDigit(tileNumber) > rows || orderDigit(tileNumber) < 1 ||
            playedNumber.length != 1 || !isDigit(playedNumber) || orderDigit(playedNumber) > 9 || orderDigit(playedNumber) < 1
        )
            return [false, map];
        
        let rowIndex =  9 - orderDigit(tileNumber);
        let columnIndex = orderAlpha(tileLetter);
        
        // if (this.originalBoard[rowIndex][columnIndex] != null)
            // return [false, map];

        map[rowIndex][columnIndex] = playedNumber;
        return [true, map];
    }
}
