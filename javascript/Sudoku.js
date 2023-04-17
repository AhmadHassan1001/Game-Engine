class SudokuDrawer extends Drawer {

    constructor() {
        super(9, 9, color(255, 255, 255, 255), color(255, 255, 255, 255), 1);
    }

    DrawBoard() {
        super.DrawBoard();
        
        stroke(0, 0, 0, 255);
        strokeWeight(10);
        line(0, canvasHeight / 3, canvasWidth, canvasHeight / 3);
        line(0, canvasHeight / 3 * 2, canvasWidth, canvasHeight / 3 * 2);
        line(canvasWidth / 3, 0, canvasWidth / 3, canvasHeight);
        line(canvasWidth / 3 * 2, 0, canvasWidth / 3 * 2, canvasHeight);
    }
}