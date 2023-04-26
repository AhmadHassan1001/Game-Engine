

class ChessDrawer extends Drawer {

    constructor(pieces) {
        super(
            pieces,
            8, 8,                                               // rows, columns
            color(255, 255, 255, 255), color(60, 60, 60, 255),  // primary and secondary colours
            1,                                                  // stroke weight
            loadImage('/javascript/assets/chess_pieces.png'),   // sprite sheet
            800 / 6, 267 / 2                                    // piece width and height
        );

        

    }
}
