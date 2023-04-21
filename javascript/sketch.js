const canvasWidth = 700;
const canvasHeight = 700;

function setup() {
  const canvas = createCanvas(canvasWidth, canvasHeight);
  canvas.position((windowWidth - canvasWidth) / 2, (windowHeight - canvasHeight) / 2);
  controller=new ChessController();
}

function draw() {
  controller.drawer.DrawBoard();
  controller.drawer.DrawPieces();

  // let input = document.getElementById('input');

}

function update(){
  controller.run(document.getElementById("inputField").value);
  document.getElementById("inputField").value="";
}