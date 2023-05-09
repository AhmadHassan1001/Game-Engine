class AbstractGameEngine {

    constructor(map, rows, columns) {
        
        for (let i = 0; i < rows; i++)
            map.push([]);
    
        for (let i = 0; i < rows; i++)
            for (let j = 0; j < columns; j++)
                map[i].push(null);

        setTimeout(() => {
            let player = 1;
            this.Drawer(map);

            // main loop
            setInterval(() => {
                let input = prompt("Enter your input");

                if (input) {
                    let valid;
                    [valid, map] = this.Controller(map, input, player);
                    console.log(valid, map);
                    input = null;
                    
                    if (!valid)
                        return;
                    
                    this.Drawer(map);

                    if (player == 1)
                        player = 2;
                    else
                        player = 1;
                }
            }, 1000);
        }, 100);
    }

    Controller(map, input, player) {}

    Drawer(map) {

        const canvasWidth = 700;
        const canvasHeight = 700;

        let canvas = createCanvas(canvasWidth, canvasHeight);
        canvas.position((windowWidth - canvasWidth) / 2, (windowHeight - canvasHeight) / 2 + 50);

        background(255);
    }

    DrawText(rows, columns, tileWidth, tileHeight, canvasWidth, canvasHeight, offset) {

        strokeWeight(1);

        for (let i = 1; i <= columns; i++) {
            let c = char(unchar('a') + i - 1);
            textSize(30);
            fill(0, 0, 0, 255);
            textAlign('center', 'alphabetic');
            text(c, (i - 1) * tileWidth + offset / 2 + tileWidth / 2, 25);
            text(c, (i - 1) * tileWidth + offset / 2 + tileWidth / 2, canvasHeight - 5);
        }

        for (let i = 1; i <= rows; i++) {
            textSize(30);
            textAlign('left', 'alphabetic');
            fill(0, 0, 0, 255);
            text(rows - i + 1, 5, (i - 1) * tileHeight + tileHeight / 2 + offset / 2 + 10);
            text(rows - i + 1, canvasWidth - 25, (i - 1) * tileHeight + tileHeight / 2 + offset / 2 + 10);
        }
    }

    PieceDrawer(map, tileWidth, tileHeight, offset) {}

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
