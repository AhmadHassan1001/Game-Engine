class ConnectFour extends Game {
    
    constructor(map, rows, columns) {
        super(map, rows, columns);

        for (let i = 0; i < rows; i++)
            for (let j = 0; j < columns; j++)
                map[i][j] = new ConnectFourPiece('rgba(255, 255, 255, 255)');
    }

    Drawer(map) {

        const canvasHeight = 700;
        const canvasWidth = 700;
        const offset = 60;
        const rows = 6;
        const columns = 7;
        const tileWidth = (canvasWidth - offset) / columns;
        const tileHeight = (canvasHeight - offset) / rows;

        background(255);

        fill(0, 0, 255, 255);
        rect(offset / 2, offset / 2, canvasWidth - offset, canvasHeight - offset);

        for (let i = 0; i <= columns; i++) {
            let c = char(unchar('a') + i - 1);
            textSize(30);
            fill(0, 0, 0, 255);
            text(c, i * tileWidth - 20, 25);
            text(c, i * tileWidth - 20, canvasHeight - 5);
        }

        for (let i = 0; i < rows; i++) {
            for (let j = 0; j < columns; j++) {
                strokeWeight(3);
                fill(map[i][j].colour);
                console.log(map[i][j].colour);
                circle(j * tileWidth + tileWidth / 2 + offset / 2, i * tileHeight + tileHeight / 2 + offset / 2, tileWidth - 5);
            }
        }
    }

    Controller(map, input, player) {

        if (input.length != 1 || !isAlpha(input) || orderAlpha(input) > map[0].length - 1 || orderAlpha(input) < 0)
            return [false, map];
        
        let columnIndex = orderAlpha(input);

        let i = 0;
        while (i < map.length && map[i][columnIndex].colour == 'rgba(255, 255, 255, 255)')
            i++;

        if (i == 0)
            return [false, map];

        if (player == 1)
            map[i - 1][columnIndex].colour = 'rgba(170, 45, 45, 255)';
        else
            map[i - 1][columnIndex].colour = 'rgba(245, 220, 35, 255)';

        return [true, map];
    }
}
