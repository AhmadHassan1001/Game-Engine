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
    controller.drawer.pieces[8-1-(orderDigit(pos2[1])-1)][orderAlpha(pos2[0])]=controller.drawer.pieces[8-1-(orderDigit(pos1[1])-1)][orderAlpha(pos1[0])];
    controller.drawer.pieces[8-1-(orderDigit(pos1[1])-1)][orderAlpha(pos1[0])]=null;
}

let actions={
    '{}-{}':move,
}

class ChessController extends Controller{
    
    constructor(){
        super(actions);
        
        this.drawer= new ChessDrawer();
    }
}
