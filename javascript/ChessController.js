
const move = (drawerMap, pos1, pos2) => {
    let valid = true;
    // Validate parameters

    // validate first parameter
    valid &&= (pos1.length == 2 && isAlpha(pos1[0]) && isDigit(pos1[1]) && orderAlpha(pos1[0]) <= orderAlpha('h') && orderDigit(pos1[1]) <= orderDigit('8') && orderDigit(pos1[1]) >= orderDigit('1'));
    // validate second parameter
    valid &&= (pos2.length == 2 && isAlpha(pos2[0]) && isDigit(pos2[1]) && orderAlpha(pos2[0]) <= orderAlpha('h') && orderDigit(pos2[1]) <= orderDigit('8') && orderDigit(pos2[1]) >= orderDigit('1'));


    // Validate action
    // validate there is object to move
    let origin = drawerMap[8 - 1 - (orderDigit(pos1[1]) - 1)][orderAlpha(pos1[0])];
    let target = drawerMap[8 - 1 - (orderDigit(pos2[1]) - 1)][orderAlpha(pos2[0])];
    valid &&= origin != null;

    // for each piece validate action

    valid &&= origin.validateMove(pos1,pos2,target);

    if (!valid) return;
    // Move
    console.log(`move  ${8 - 1 - (orderDigit(pos1[1]) - 1)} ${orderAlpha(pos1[0])}`)
    drawerMap[8 - 1 - (orderDigit(pos2[1]) - 1)][orderAlpha(pos2[0])] = drawerMap[8 - 1 - (orderDigit(pos1[1]) - 1)][orderAlpha(pos1[0])];
    drawerMap[8 - 1 - (orderDigit(pos1[1]) - 1)][orderAlpha(pos1[0])] = null;
}

let actions = {
    '{}-{}': move,
}

class ChessController extends Controller {
    
    constructor() {
        super(actions, 8, 8);

        for (let j = 0; j < this.columns; j++) {
            this.drawerMap[1][j] = new ChessPiece(1, 5);
            this.drawerMap[this.rows - 2][j] = new ChessPiece(0, 5);
        }

        // rook
        this.drawerMap[0][0] = new ChessPiece(1, 4);
        this.drawerMap[this.rows - 1][0] = new ChessPiece(0, 4);

        // knight
        this.drawerMap[0][1] = new ChessPiece(1, 3);
        this.drawerMap[this.rows - 1][1] = new ChessPiece(0, 3);

        // bishop
        this.drawerMap[0][2] = new ChessPiece(1, 2);
        this.drawerMap[this.rows - 1][2] = new ChessPiece(0, 2);

        // queen
        this.drawerMap[0][3] = new ChessPiece(1, 1);
        this.drawerMap[this.rows - 1][3] = new ChessPiece(0, 1);

        // king
        this.drawerMap[0][4] = new ChessPiece(1, 0);
        this.drawerMap[this.rows - 1][4] = new ChessPiece(0, 0);

        // bishop
        this.drawerMap[0][5] = new ChessPiece(1, 2);
        this.drawerMap[this.rows - 1][5] = new ChessPiece(0, 2);

        // knight
        this.drawerMap[0][6] = new ChessPiece(1, 3);
        this.drawerMap[this.rows - 1][6] = new ChessPiece(0, 3);
        
        // rook
        this.drawerMap[0][7] = new ChessPiece(1, 4);
        this.drawerMap[this.rows - 1][7] = new ChessPiece(0, 4);
    }
}
