


class Queens extends AbstractGameEngine {
    constructor(map, rows, columns) {

        super(map, rows, columns);
        this.spriteSheet = loadImage('/javascript/assets/chess_pieces.png');

    }

    Drawer(map) {
        super.Drawer();

        const canvasHeight = 700;
        const canvasWidth = 700;
        const offset = 60;
        const rows = 8;
        const columns = 8;
        const primaryColour = color(255, 255, 255, 255);
        const secondaryColour = color(60, 60, 60, 255);
        const tileWidth = (canvasWidth - offset) / columns;
        const tileHeight = (canvasHeight - offset) / rows;

        this.SquareBoardDrawer(rows, columns, primaryColour, secondaryColour, tileWidth, tileHeight, offset);
        this.DrawText(rows, columns, tileWidth, tileHeight, canvasWidth, canvasHeight);
        this.PieceDrawer(map, tileWidth, tileHeight, offset);
    }

    PieceDrawer(map, tileWidth, tileHeight, offset) {

        const pieceWidth = 800 / 6;
        const pieceHeight = 267 / 2;


        for (let i = 0; i < map.length; i++) {
            for (let j = 0; j < map[i].length; j++) {
                if (map[i][j] != null) {
                    image(this.spriteSheet, j * tileWidth + offset / 2, i * tileHeight + offset / 2, tileWidth, tileHeight, map[i][j].x, map[i][j].y, pieceWidth, pieceHeight);
                }
            }
        }
    }

    Controller(map, input, player) {
        let pos = input.toLowerCase();
        let valid = true;
        // Validate parameters

        // validate first parameter
        valid &&= (pos.length == 2 && isAlpha(pos[0]) && isDigit(pos[1]) && orderAlpha(pos[0]) <= orderAlpha('h') && orderDigit(pos[1]) <= orderDigit('8') && orderDigit(pos[1]) >= orderDigit('1'));


        // Validate action
        // validate there is object to move
        let origin = map[8 - 1 - (orderDigit(pos[1]) - 1)][orderAlpha(pos[0])];
        if(origin!=null){///TO BE TESTED
            map[8 - 1 - (orderDigit(pos[1]) - 1)][orderAlpha(pos[0])]=null;
            return [true, map];
        }
        valid &&= origin == null;

        // validate the 4 directions H,V,45,135

        //  validate Horizontal and vertical
        for (let i = 0; i < 8; i++) {
            valid &&= map[8 - 1 - (orderDigit(pos[1]) - 1)][i] == null;
            valid &&= map[i][orderAlpha(pos[0])] == null;
        }

        //  validate 45 and 135
        let cof1 = Math.min(8 - 1 - (orderDigit(pos[1]) - 1), orderAlpha(pos[0]));
        let cof2 = Math.min((orderDigit(pos[1]) - 1), orderAlpha(pos[0]));

        for (let i = 0; i < 8; i++) {
            if (8 - 1 - (orderDigit(pos[1]) - 1) - cof1 + i < 8 && orderAlpha(pos[0]) - cof1 + i < 8)
                valid &&= map[8 - 1 - (orderDigit(pos[1]) - 1) - cof1 + i][orderAlpha(pos[0]) - cof1 + i] == null;

            if (8 - 1 - (orderDigit(pos[1]) - 1) + cof2 - i > 0 && orderAlpha(pos[0]) - cof2 + i < 8)
                valid &&= map[8 - 1 - (orderDigit(pos[1]) - 1) + cof2 - i][orderAlpha(pos[0]) - cof2 + i] == null;
        }

        // console.log("Not valid");


        if (!valid) return [false, map];
        // console.log("Valid");
        // Move
        // console.log('move')
        map[8 - 1 - (orderDigit(pos[1]) - 1)][orderAlpha(pos[0])] = new Queen(1, 1);
        // console.log('map',map);
        return [true, map];

    }
}

