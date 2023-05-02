function setup() {
    const canvasWidth = 700;
    const canvasHeight = 700;

    const canvas = createCanvas(canvasWidth, canvasHeight);
    canvas.position((windowWidth - canvasWidth) / 2, (windowHeight - canvasHeight) / 2 + 50);
    gameMap = [];
    player = 1;
    game = GameFactory.GetGame('Connect-4', gameMap);
}

function draw() {
    
    game.Drawer(gameMap);

    let input = prompt("Enter your input at the form of ##-## (eg. a1-b3)");

    if (input) {
        let valid;
        [valid, gameMap] = game.Controller(gameMap, input, player);
        console.log(valid, gameMap);
        input = null;
        
        if (!valid)
            return;
        
        game.Drawer(gameMap);

        if (player == 1)
            player = 2;
        else
            player = 1;
    }
}
