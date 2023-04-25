class Piece {
    
    constructor(i, j, pieceWidth, pieceHeight,type) {
        this.pieceWidth = pieceWidth;
        this.pieceHeight = pieceHeight;
        this.x = pieceWidth * j;
        this.y = pieceHeight * i;
        this.labels=[]
        this.type=type
    }
}

class ChessPiece extends Piece {
    
    constructor(i, j,type) {
        super(i, j, 800 / 6, 267 / 2,type);
    }
}

class Pawn extends ChessPiece{
    constructor(i,j) {
        super(i, j,"pawn");
    }
    
    validateMove(pos1,pos2,target){
        let validpawn = false;
        //front cell is empty
        validpawn ||= orderDigit(pos2[1]) == (orderDigit(pos1[1]) + ((this.labels.includes('white')) ? 1 : -1)) && Math.abs(orderAlpha(pos2[0]) - orderAlpha(pos1[0])) == 0 && target == null;
        //corner cells has enemy
        validpawn ||= orderDigit(pos2[1]) == (orderDigit(pos1[1]) + ((this.labels.includes('white')) ? 1 : -1)) && Math.abs(orderAlpha(pos2[0]) - orderAlpha(pos1[0])) == 1 && target != null && (target.labels.includes('black') && this.labels.includes('white') || target.labels.includes('white') && this.labels.includes('black'));
        return validpawn;
    }
}
class Knight extends ChessPiece{
    constructor(i,j) {
        super(i, j,"knight");
    }
    
    validateMove(pos1,pos2,target){
        let validknight = true;
        //cell is end of L
        validknight &&= (abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) == 2 && abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) == 1) || (abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) == 1 && abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) == 2);

        //cell is empty or has enemy
        validknight &&= (target == null) || (target.labels.includes('black') && origin.labels.includes('white')) || (target.labels.includes('white') && origin.labels.includes('black'));
        return validknight;
    }
}

class King extends ChessPiece{
    constructor(i,j) {
        super(i, j,"king");
    }
    
    validateMove(pos1,pos2,target){
        let validking = true;
        validking = true;
        //cell is near
        let ecludian_distance = abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) + abs(orderDigit(pos1[1]) - orderDigit(pos2[1]));
        validking &&= (ecludian_distance >= 1) && abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) <= 1 && abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) <= 1;
        console.log(validking)
        //cell is empty or has enemy
        validking &&= (target == null) || (target.labels.includes('black') && origin.labels.includes('white')) || (target.labels.includes('white') && origin.labels.includes('black'));
        return validking;
    }
}
class Rook extends ChessPiece{
    constructor(i,j) {
        super(i, j,"rook");
    }
    
    validateMove(pos1,pos2,target){
        let validrook = true;
        validrook = true;
        //way is vertiacl or horizontal
        validrook &&= (abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) >= 1 && abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) == 0) || (abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) == 0 && abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) >= 1);
        console.log('validrook', validrook)
        if (!validrook) return;
        //way is clear
        if (abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) >= 1) {//horizontal
            let operator = -abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) / (orderAlpha(pos1[0]) - orderAlpha(pos2[0]));
            console.log(operator);
            for (let i = orderAlpha(pos1[0]) + operator; i != orderAlpha(pos2[0]); i += operator) {
                validrook &&= controller.drawerMap[8 - 1 - (orderDigit(pos1[1]) - 1)][i] == null;
            }

        } else {//vertical
            let operator = -abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) / ((orderDigit(pos1[1]) - orderDigit(pos2[1])));
            for (let i = orderDigit(pos1[1]) + operator; i != orderDigit(pos2[1]); i += operator) {
                validrook &&= controller.drawerMap[8 - 1 - (i - 1)][orderAlpha(pos1[0])] == null;
            }
        }
        //cell is empty or has enemy
        validrook &&= (target == null) || (target.labels.includes('black') && origin.labels.includes('white')) || (target.labels.includes('white') && origin.labels.includes('black'));
        return validrook;
    }
}

class Bishop extends ChessPiece{
    constructor(i,j) {
        super(i, j,"bishop");
    }
    
    validateMove(pos1,pos2,target){
        let validbishop = true;
        validbishop = true;
        //way is 45 or 135
        validbishop &&= abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) >= 1 && abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) == abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0]));
        console.log('validbishop', validbishop)
        if (!validbishop) return;
        //way is clear
        let operatori = -abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) / (orderAlpha(pos1[0]) - orderAlpha(pos2[0]));
        let operatorj = -abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) / ((orderDigit(pos1[1]) - orderDigit(pos2[1])));
        console.log(orderAlpha(pos1[0]), orderDigit(pos1[1]));
        console.log(operatori, operatorj)
        for (let i = orderAlpha(pos1[0]) + operatori, j = orderDigit(pos1[1]) + operatorj; i != orderAlpha(pos2[0]); i += operatori, j += operatorj) {

            validbishop &&= controller.drawerMap[8 - 1 - (j - 1)][i] == null;
            console.log(i, j);
            console.log(validbishop);
        }


        //cell is empty or has enemy
        validbishop &&= (target == null) || (target.labels.includes('black') && origin.labels.includes('white')) || (target.labels.includes('white') && origin.labels.includes('black'));
        return validbishop;
    }
}
class Queen extends ChessPiece{
    constructor(i,j) {
        super(i, j,"queen");
    }
    
    validateMove(pos1,pos2,target){
        
        let validqueen1 = true;
        //way is 45 or 135
        validqueen1 &&= abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) >= 1 && abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) == abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0]));
        console.log('validqueen1', validqueen1)
        if (validqueen1) {
            //way is clear
            let operatori = -abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) / (orderAlpha(pos1[0]) - orderAlpha(pos2[0]));
            let operatorj = -abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) / ((orderDigit(pos1[1]) - orderDigit(pos2[1])));
            console.log(orderAlpha(pos1[0]), orderDigit(pos1[1]));
            console.log(operatori, operatorj)
            for (let i = orderAlpha(pos1[0]) + operatori, j = orderDigit(pos1[1]) + operatorj; i != orderAlpha(pos2[0]); i += operatori, j += operatorj) {

                validqueen1 &&= controller.drawerMap[8 - 1 - (j - 1)][i] == null;
                console.log(i, j);
                console.log(validqueen1);
            }

        }


        //cell is empty or has enemy
        validqueen1 &&= (target == null) || (target.labels.includes('black') && origin.labels.includes('white')) || (target.labels.includes('white') && origin.labels.includes('black'));

        let validqueen2 = true;
        //way is vertiacl or horizontal
        validqueen2 &&= (abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) >= 1 && abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) == 0) || (abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) == 0 && abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) >= 1);
        console.log('validqueen2', validqueen2)
        if (validqueen2) {
            //way is clear
            if (abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) >= 1) {//horizontal
                let operator = -abs(orderAlpha(pos1[0]) - orderAlpha(pos2[0])) / (orderAlpha(pos1[0]) - orderAlpha(pos2[0]));
                console.log(operator);
                for (let i = orderAlpha(pos1[0]) + operator; i != orderAlpha(pos2[0]); i += operator) {
                    validqueen2 &&= controller.drawerMap[8 - 1 - (orderDigit(pos1[1]) - 1)][i] == null;
                }

            } else {//vertical
                let operator = -abs(orderDigit(pos1[1]) - orderDigit(pos2[1])) / ((orderDigit(pos1[1]) - orderDigit(pos2[1])));
                for (let i = orderDigit(pos1[1]) + operator; i != orderDigit(pos2[1]); i += operator) {
                    validqueen2 &&= controller.drawerMap[8 - 1 - (i - 1)][orderAlpha(pos1[0])] == null;
                }
            }
        }

        //cell is empty or has enemy
        validqueen2 &&= (target == null) || (target.labels.includes('black') && origin.labels.includes('white')) || (target.labels.includes('white') && origin.labels.includes('black'));
        return validqueen1 ||validqueen2;
    }
}
