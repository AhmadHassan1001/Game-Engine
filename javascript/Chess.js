class Chess extends Game {
    
    constructor(map, rows, columns) {

        super(map, rows, columns);

        for (let j = 0; j < columns; j++) {
            map[1][j] = new Pawn(1, 5, 'black');
            map[rows - 2][j] = new Pawn(0, 5, 'white');
        }

        // rook
        map[0][0] = new Rook(1, 4, 'black');
        map[rows - 1][0] = new Rook(0, 4, 'white');

        // knight
        map[0][1] = new Knight(1, 3, 'black');
        map[rows - 1][1] = new Knight(0, 3, 'white');

        // bishop
        map[0][2] = new Bishop(1, 2, 'black');
        map[rows - 1][2] = new Bishop(0, 2, 'white');

        // queen
        map[0][3] = new Queen(1, 1, 'black');
        map[rows - 1][3] = new Queen(0, 1, 'white');

        // king
        map[0][4] = new King(1, 0, 'black');
        map[rows - 1][4] = new King(0, 0, 'white');

        // bishop
        map[0][5] = new Bishop(1, 2, 'black');
        map[rows - 1][5] = new Bishop(0, 2, 'white');

        // knight
        map[0][6] = new Knight(1, 3, 'black');
        map[rows - 1][6] = new Knight(0, 3, 'white');
        
        // rook
        map[0][7] = new Rook(1, 4, 'black');
        map[rows - 1][7] = new Rook(0, 4, 'white');

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
        
        input = input.replaceAll(' ', '');
        input = input.toLowerCase();

        let inputList = input.split('-');
        let pos1 = inputList[0];
        let pos2 = inputList[1];

        if (input.length != 5)
            return[false, map];

        let valid = true;
        

        // validate first parameter
        valid &&= (pos1.length == 2 && isAlpha(pos1[0]) && isDigit(pos1[1]) && orderAlpha(pos1[0]) <= orderAlpha('h') && orderDigit(pos1[1]) <= orderDigit('8') && orderDigit(pos1[1]) >= orderDigit('1'));

        // validate second parameter
        valid &&= (pos2.length == 2 && isAlpha(pos2[0]) && isDigit(pos2[1]) && orderAlpha(pos2[0]) <= orderAlpha('h') && orderDigit(pos2[1]) <= orderDigit('8') && orderDigit(pos2[1]) >= orderDigit('1'));


        // Validate action

        // validate there is object to move
        let origin = map[8 - 1 - (orderDigit(pos1[1]) - 1)][orderAlpha(pos1[0])];
        let target = map[8 - 1 - (orderDigit(pos2[1]) - 1)][orderAlpha(pos2[0])];
        
        valid &&= origin != null;
        
        valid &&= (origin.colour=="black"&&player==1)||(origin.colour=="white"&&player==2)

        // for each piece validate action
        if (origin)
            valid &&= origin.validateMove(pos1,pos2,target);
        
        if (!valid)
            return [false, map];

        // Move
        console.log(`move  ${8 - 1 - (orderDigit(pos1[1]) - 1)} ${orderAlpha(pos1[0])}`)
        map[8 - 1 - (orderDigit(pos2[1]) - 1)][orderAlpha(pos2[0])] = map[8 - 1 - (orderDigit(pos1[1]) - 1)][orderAlpha(pos1[0])];
        map[8 - 1 - (orderDigit(pos1[1]) - 1)][orderAlpha(pos1[0])] = null;

        return [true, map];
    }
}
