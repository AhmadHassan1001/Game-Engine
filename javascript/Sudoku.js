class Sudoku extends AbstractGameEngine {

    constructor(map, rows, columns) {
        super(map, rows, columns);
        // TODO: generate board
        this.#fillValues(map);
        
        this.originalBoard = JSON.parse(JSON.stringify(map));
    }

    Drawer(map) {

        super.Drawer();
        
        const canvasHeight = 700;
        const canvasWidth = 700;
        const offset = 60;
        const rows = 9;
        const columns = 9;
        const primaryColour = color(255, 255, 255, 255);
        const secondaryColour = color(255, 255, 255, 255);
        const tileWidth = (canvasWidth - offset) / columns;
        const tileHeight = (canvasHeight - offset) / rows;
        
        this.SquareBoardDrawer(rows, columns, primaryColour, secondaryColour, tileWidth, tileHeight, offset);
        
        stroke(0, 0, 0, 255);
        strokeWeight(10);
        line(0, (canvasHeight - offset) / 3 - 5 + offset / 2, canvasWidth, (canvasHeight - offset) / 3 - 5 + offset / 2);
        line(0, (canvasHeight - offset) / 3 * 2 - 5 + offset / 2, canvasWidth, (canvasHeight - offset) / 3 * 2 - 5 + offset / 2);
        line((canvasWidth - offset) / 3 - 5 + offset / 2, 0, (canvasWidth - offset) / 3 - 5 + offset / 2, canvasHeight);
        line((canvasHeight - offset) / 3 * 2 - 5 + offset / 2, 0, (canvasHeight - offset) / 3 * 2 - 5 + offset / 2, canvasHeight);
        
        this.DrawText(rows, columns, tileWidth, tileHeight, canvasWidth, canvasHeight, offset);
        this.PieceDrawer(map, tileWidth, tileHeight, offset);
    }

    PieceDrawer(map, tileWidth, tileHeight, offset) {

        strokeWeight(5);

        for (let i = 0; i < map.length; i++) {
            for (let j = 0; j < map[i].length; j++) {
                if (map[i][j] != null) {
                    textSize(30);
                    textStyle('bold');
                    if (this.originalBoard[i][j] != null) {
                        stroke(255, 255, 255, 255);
                        fill(0, 0, 255, 255);
                    }   
                    else
                        fill(0, 0, 0, 255);
                    text(map[i][j], j * tileWidth + offset / 2 + 25, i * tileHeight + 50 + offset / 2);
                }
            }
        }
    }

    Controller(map, input, player) {

        const rows = map.length;
        const columns = map[0].length;

        
        input = input.replaceAll(' ', '');
        input = input.toLowerCase();

        if (input.length != 4)
            return [false, map];

        let inputList = input.split('-');
        let tileLetter = inputList[0][0];
        let tileNumber = inputList[0][1];
        let playedNumber = inputList[1];

        // validating input
        if (
            !isAlpha(tileLetter) || orderAlpha(tileLetter) > columns - 1 ||
            !isDigit(tileNumber) || orderDigit(tileNumber) > rows || orderDigit(tileNumber) < 1 ||
            playedNumber.length != 1 || !isDigit(playedNumber) || orderDigit(playedNumber) > 9 || orderDigit(playedNumber) < 1
        )
            return [false, map];
        
        let rowIndex =  rows - orderDigit(tileNumber);
        let columnIndex = orderAlpha(tileLetter);
        
        if (this.originalBoard[rowIndex][columnIndex] != null)
            return [false, map];

        map[rowIndex][columnIndex] = playedNumber;
        return [true, map];
    }

    // Sudoku Generator
    #fillValues(map) {
        // Fill the diagonal of SRN x SRN matrices
        this.#fillDiagonal(map);
 
        // Fill remaining blocks
        this.#fillRemaining(0, 3, map);
 
        // Remove Randomly K digits to make game
        this.#removeKDigits(map);
    }
 
    // Fill the diagonal SRN number of SRN x SRN matrices
    #fillDiagonal(map) {
        for (let i = 0; i < 9; i += 3) {
            // for diagonal box, start coordinates->i==j
            this.#fillBox(i, i, map);
        }
    }
 
    // Returns false if given 3 x 3 block contains num.
    #unUsedInBox(rowStart, colStart, num, map) {
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                if (map[rowStart + i][colStart + j] === num) {
                    return false;
                }
            }
        }
        return true;
    }
 
    // Fill a 3 x 3 matrix.
    #fillBox(row, col, map) {
        let num = 0;
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                while (true) {
                    num = this.#randomGenerator(9);
                    if (this.#unUsedInBox(row, col, num, map)) {
                        break;
                    }
                }
                map[row + i][col + j] = num;
            }
        }
    }
 
    // Random generator
    #randomGenerator(num) {
        return Math.floor(Math.random() * num + 1);
    }
 
    // Check if safe to put in cell
    #checkIfSafe(i, j, num, map) {
        return (
            this.#unUsedInRow(i, num, map) &&
            this.#unUsedInCol(j, num, map) &&
            this.#unUsedInBox(i - (i % 3), j - (j % 3), num, map)
        );
    }
 
    // check in the row for existence
    #unUsedInRow(i, num, map) {
        for (let j = 0; j < 9; j++) {
            if (map[i][j] === num) {
                return false;
            }
        }
        return true;
    }
 
    // check in the row for existence
    #unUsedInCol(j, num, map) {
        for (let i = 0; i < 9; i++) {
            if (map[i][j] === num) {
                return false;
            }
        }
        return true;
    }
 
    // A recursive function to fill remaining matrix
    #fillRemaining(i, j, map) {
        // Check if we have reached the end of the matrix
        if (i === 9 - 1 && j === 9) {
            return true;
        }
 
        // Move to the next row if we have reached the end of the current row
        if (j === 9) {
            i += 1;
            j = 0;
        }
 
 
        // Skip cells that are already filled
        if (map[i][j] !== 0) {
            return this.#fillRemaining(i, j + 1, map);
        }
 
        // Try filling the current cell with a valid value
        for (let num = 1; num <= 9; num++) {
            if (this.#checkIfSafe(i, j, num, map)) {
                map[i][j] = num;
                if (this.#fillRemaining(i, j + 1, map)) {
                    return true;
                }
                map[i][j] = 0;
            }
        }
 
        // No valid value was found, so backtrack
        return false;
    }
 
    // Remove the K no. of digits to complete game
    #removeKDigits(map) {
        let count = 81 - this.#randomGenerator(60);
 
        while (count !== 0) {
            // extract coordinates i and j
            let i = Math.floor(Math.random() * 9);
            let j = Math.floor(Math.random() * 9);
            if (map[i][j] !== 0) {
                count--;
                map[i][j] = null;
            }
        }
 
        return;
    }
}
