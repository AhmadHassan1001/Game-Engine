class TicTacToe extends AbstractGameEngine {

    constructor(map, rows, columns) {
        super(map, rows, columns);
    }

    Drawer(map) {
        super.Drawer();
        const canvasHeight = 700;
        const canvasWidth = 700;
        const offset = 60;
        
        const rows = 3;
        const columns = 3;
        const primaryColour = color(255, 255, 255, 255);
        const secondaryColour = color(255, 255, 255, 255);
        const tileWidth = (canvasWidth - offset) / columns;
        const tileHeight = (canvasHeight - offset) / rows;
        this.SquareBoardDrawer(rows, columns, primaryColour, secondaryColour, tileWidth, tileHeight, offset);
        this.DrawText(rows, columns, tileWidth, tileHeight, canvasWidth, canvasHeight, offset);
        this.PieceDrawer(map, tileWidth, tileHeight, offset);
    }

    PieceDrawer(map, tileWidth, tileHeight, offset) {
        for (let i = 0; i < map.length; i++) {
            for (let j = 0; j < map[i].length; j++) {
                if (map[i][j] != null) {
                    textSize(150);
                    textAlign('center', 'center');
                    text(map[i][j], j * tileWidth + tileWidth / 2 + offset / 2, i * tileHeight + tileHeight / 2 + offset / 2 + 15);
                }
            }
        }
    }

    Controller(map, input, player) {

        const rows = map.length;
        const columns = map[0].length;
        
        input = input.replaceAll(' ', '');
        input = input.toLowerCase();

        if (input.length != 2)
            return [false, map];

        let tileLetter = input[0];
        let tileNumber = input[1];

        // validating input
        if (
            !isAlpha(tileLetter) || orderAlpha(tileLetter) > columns - 1 ||
            !isDigit(tileNumber) || orderDigit(tileNumber) > rows || orderDigit(tileNumber) < 1
        )
            return [false, map];
        
        let rowIndex =  rows - orderDigit(tileNumber);
        let columnIndex = orderAlpha(tileLetter);

        if (map[rowIndex][columnIndex] != null)
            return [false, map];

        if (player == 1)
            map[rowIndex][columnIndex] = 'X';
        else
            map[rowIndex][columnIndex] = 'O'
        return [true, map];
    }
}