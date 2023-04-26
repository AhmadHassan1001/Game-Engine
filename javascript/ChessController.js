const orderAlpha=(c)=>{

    return c.charCodeAt(0) - "a".charCodeAt(0);
}
const orderDigit=(c)=>{

    return c.charCodeAt(0) - "0".charCodeAt(0);
}
const move=(controller,pos1,pos2)=>{
    // TODO: Validate parameters
    // TODO: Validate action

    // Move
    console.log(`move  ${8-1-(orderDigit(pos1[1])-1)} ${orderAlpha(pos1[0])}`)
    controller.drawerMap[8-1-(orderDigit(pos2[1])-1)][orderAlpha(pos2[0])]=controller.drawerMap[8-1-(orderDigit(pos1[1])-1)][orderAlpha(pos1[0])];
    controller.drawerMap[8-1-(orderDigit(pos1[1])-1)][orderAlpha(pos1[0])]=null;
}

let actions={
    '{}-{}':move,
}

class ChessController extends Controller{
    
    constructor(){
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
