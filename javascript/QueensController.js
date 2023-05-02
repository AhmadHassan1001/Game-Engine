
const move_queen = (drawerMap, pos) => {
    let valid = true;
    // Validate parameters

    // validate first parameter
    valid &&= (pos.length == 2 && isAlpha(pos[0]) && isDigit(pos[1]) && orderAlpha(pos[0]) <= orderAlpha('h') && orderDigit(pos[1]) <= orderDigit('8') && orderDigit(pos[1]) >= orderDigit('1'));


    // Validate action
    // validate there is object to move
    let origin = drawerMap[8 - 1 - (orderDigit(pos[1]) - 1)][orderAlpha(pos[0])];
    valid &&= origin == null;

    // validate the 4 directions H,V,45,135

    //  validate Horizontal and vertical
    for (let i = 0; i < 8; i++) {
        valid &&= drawerMap[8 - 1 - (orderDigit(pos[1]) - 1)][i] == null;
        valid &&= drawerMap[i][orderAlpha(pos[0])] == null;
    }

    //  validate 45 and 135
    let cof1 = Math.min(8 - 1 - (orderDigit(pos[1]) - 1), orderAlpha(pos[0]));
    let cof2 = Math.min((orderDigit(pos[1]) - 1), orderAlpha(pos[0]));

    for (let i = 0; i < 8; i++) {
        if (8 - 1 - (orderDigit(pos[1]) - 1) - cof1 + i < 8 && orderAlpha(pos[0]) - cof1 + i < 8)
            valid &&= drawerMap[8 - 1 - (orderDigit(pos[1]) - 1) - cof1 + i][orderAlpha(pos[0]) - cof1 + i] == null;

        if (8 - 1 - (orderDigit(pos[1]) - 1) + cof2 - i > 0 && orderAlpha(pos[0]) - cof2 + i < 8)
            valid &&= drawerMap[8 - 1 - (orderDigit(pos[1]) - 1) + cof2 - i][orderAlpha(pos[0]) - cof2 + i] == null;
    }



    if (!valid) return;
    // Move
    console.log('move')
    drawerMap[8 - 1 - (orderDigit(pos[1]) - 1)][orderAlpha(pos[0])] = new Queen(1, 1);
}

actions = {
    '{}': move_queen,
}

class QueensController extends Controller {

    constructor(drawerMap) {
        super(drawerMap, actions);
    }
}
