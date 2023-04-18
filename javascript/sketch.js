const canvasWidth = 900;
const canvasHeight = 900;

function setup() {
  const canvas = createCanvas(canvasWidth, canvasHeight);
  canvas.position((windowWidth - canvasWidth) / 2, (windowHeight - canvasHeight) / 2);

  drawer = new ChessDrawer();
}

function draw() {
  drawer.DrawBoard();
  drawer.DrawPieces();

  let input = document.getElementById('input');
}
