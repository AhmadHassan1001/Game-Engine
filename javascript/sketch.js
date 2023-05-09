function setup() {

    // main menu
    createCanvas(700, 700).position(innerWidth / 2 - 350, innerHeight / 2 - 300);
    background(50);

    let games = ['8-Queens', 'Checkers', 'Chess', 'Connect-4', 'Sudoku', 'Tic-Tac-Toe'];
    for (let i = 0; i < games.length; i++) {
        textAlign('center');
        textSize(48);
        fill(255, 255, 255, 255);
        rect(0, (i + 1) * 700 / games.length, 700, 2);
        text((i + 1) + '- ' + games[i], 350, (i + 1) * 700 / games.length - 50);
    }

    let index;
    setTimeout(() => {
        let interval = setInterval(() => {
            index = prompt('Enter the game number');
            if (index >= 1 && index <= 6) {
                clearInterval(interval);
                const engine = GameFactory.GetGame(games[index - 1]);
            }
        }, 16);
    }, 100);
    
}
