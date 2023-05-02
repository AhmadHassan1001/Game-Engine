class Game {

    constructor(map, rows, columns) {
        
        for (let i = 0; i < rows; i++)
            map.push([]);
    
        for (let i = 0; i < rows; i++)
            for (let j = 0; j < columns; j++)
                map[i].push(null);
    }

    Controller(map, input, player) {}

    Drawer(map) {

        const canvasWidth = 700;
        const canvasHeight = 700;

        let canvas = createCanvas(canvasWidth, canvasHeight);
        canvas.position((windowWidth - canvasWidth) / 2, (windowHeight - canvasHeight) / 2 + 50);

        background(255);
    }

    DrawText(rows, columns, tileWidth, tileHeight, canvasWidth, canvasHeight) {

        strokeWeight(1);

        for (let i = 0; i <= columns; i++) {
            let c = char(unchar('a') + i - 1);
            textSize(30);
            fill(0, 0, 0, 255);
            text(c, i * tileWidth - 20, 25);
            text(c, i * tileWidth - 20, canvasHeight - 5);
        }

        for (let i = 0; i <= rows; i++) {
            textSize(30);
            fill(0, 0, 0, 255);
            text(rows - i + 1, 5, i * tileHeight);
            text(rows - i + 1, canvasWidth - 25, i * tileHeight);
        }
    }

    SquareBoardDrawer(rows, columns, primaryColour, secondaryColour, tileWidth, tileHeight, offset) {

        strokeWeight(1);
        
        for (let i = 0; i < rows; i++) {
            for (let j = 0; j < columns; j++) {
                if ((i + j) % 2 == 0)
                    fill(primaryColour);
                else
                    fill(secondaryColour);
                rect(j * tileWidth + offset / 2, i * tileHeight + offset / 2, tileWidth, tileHeight);
            }
        }
    }
}
