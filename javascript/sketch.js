const canvasWidth = 700;
const canvasHeight = 700;

function setup() {
  const canvas = createCanvas(canvasWidth, canvasHeight);
  canvas.position((windowWidth - canvasWidth) / 2, (windowHeight - canvasHeight) / 2);
  controller = new SudokuController();
  drawer = new SudokuDrawer(controller.drawerMap);
}

function draw() {
  drawer.DrawBoard();
  drawer.DrawPieces();

  // let input = document.getElementById('input');

}

function update(){
  controller.run(document.getElementById("inputField").value);
  document.getElementById("inputField").value="";
}