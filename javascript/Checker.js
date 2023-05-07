/**
 * 
 * Rules:
 * each piece can move diagonally forward initially
 * if piece reached the opposite last row it becomes king and can move diagonally up or down(TODO)
 * piece move is only one square apart
 * piece can jump the opponent which removes the oponent checker
 * you can make many jump in one move (TODO)
 * 
 */
class Checker extends AbstractGameEngine{
    constructor(map, rows, columns) {

        super(map, rows, columns);
        this.spriteSheet = loadImage('/javascript/assets/checker.png');

    }
    Drawer(map) {
        super.Drawer();

        const canvasHeight = 700;
        const canvasWidth = 700;
        const offset = 60;
        const rows = 8;
        const columns = 8;
        const primaryColour = color(92, 64, 51, 255);
        const secondaryColour = color(196, 164, 132, 255);
        const tileWidth = (canvasWidth - offset) / columns;
        const tileHeight = (canvasHeight - offset) / rows;
        // white pieces
        map[0][0]=new CheckerPiece('white');
        map[0][2]=new CheckerPiece('white');
        map[0][4]=new CheckerPiece('white');
        map[0][6]=new CheckerPiece('white');
        map[1][1]=new CheckerPiece('white');
        map[1][3]=new CheckerPiece('white');
        map[1][5]=new CheckerPiece('white');
        map[1][7]=new CheckerPiece('white');

        // black pieces
        map[6][0]=new CheckerPiece('black');
        map[6][2]=new CheckerPiece('black');
        map[6][4]=new CheckerPiece('black');
        map[6][6]=new CheckerPiece('black');
        map[7][1]=new CheckerPiece('black');
        map[7][3]=new CheckerPiece('black');
        map[7][5]=new CheckerPiece('black');
        map[7][7]=new CheckerPiece('black');

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

        
        return [true, map];
    }

}