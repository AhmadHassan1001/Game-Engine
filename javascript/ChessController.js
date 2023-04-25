
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

    constructor(drawerMap) {
        super(drawerMap, actions);
    }
}
