
const move=(controller,pos1,pos2)=>{
    valid=true;
    // Validate parameters

    // validate first parameter
    valid&&=(pos1.length==2&&isAlpha(pos1[0])&&isDigit(pos1[1])&&orderAlpha(pos1[0])<=orderAlpha('h')&&orderDigit(pos1[1])<=orderDigit('8')&&orderDigit(pos1[1])>=orderDigit('1'));
    // validate second parameter
    valid&&=(pos2.length==2&&isAlpha(pos2[0])&&isDigit(pos2[1])&&orderAlpha(pos2[0])<=orderAlpha('h')&&orderDigit(pos2[1])<=orderDigit('8')&&orderDigit(pos2[1])>=orderDigit('1'));


    // Validate action
    // validate there is object to move
    let origin=controller.drawerMap[8-1-(orderDigit(pos1[1])-1)][orderAlpha(pos1[0])];
    let target=controller.drawerMap[8-1-(orderDigit(pos2[1])-1)][orderAlpha(pos2[0])];
    valid&&=origin!=null;

    // for each piece validate action

    if(origin.type=='pawn'){
        validpawn=false;
        //front cell is empty
        validpawn||=orderDigit(pos2[1])==(orderDigit(pos1[1])+((origin.labels.includes('white'))?1:-1))&&Math.abs(orderAlpha(pos2[0])-orderAlpha(pos1[0]))==1&&target==null;
        //corner cells has enemy
        validpawn||=orderDigit(pos2[1])==(orderDigit(pos1[1])+((origin.labels.includes('white'))?1:-1))&&Math.abs(orderAlpha(pos2[0])-orderAlpha(pos1[0]))==1&&target!=null&&(target.labels.includes('black')&&origin.labels.includes('white')||target.labels.includes('white')&&origin.labels.includes('black'));
        valid&&=validpawn;
        console.log(Math.abs(orderAlpha(pos2[0])-orderAlpha(pos1[0]))==1);
    }

    if(!valid)return;
    // Move
    console.log(`move  ${8-1-(orderDigit(pos1[1])-1)} ${orderAlpha(pos1[0])}`)
    controller.drawerMap[8-1-(orderDigit(pos2[1])-1)][orderAlpha(pos2[0])]=controller.drawerMap[8-1-(orderDigit(pos1[1])-1)][orderAlpha(pos1[0])];
    controller.drawerMap[8-1-(orderDigit(pos1[1])-1)][orderAlpha(pos1[0])]=null;
}

let actions={
    '{}-{}':move,
}

class ChessController extends Controller{
    
    constructor(drawerMap){
        super(drawerMap,actions);
    }
}
