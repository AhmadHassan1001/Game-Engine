import scala.swing._
import scala.swing.event._
import java.awt.{Color, Graphics2D, BasicStroke}
import java.awt.geom._
import java.awt.Canvas
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.FileInputStream

object GuiProgramSix {

  def main(args: Array[String]): Unit = {
    var input = "Connect4"

    //var scaledImage = ImageIO.read(new FileInputStream("./Assets/Chess/BishopBlack.png"));

    var drawBoard = (bgColor: Color,rows: Int,cols: Int,color1: Color,color2: Color,shape: String,g: Graphics2D) => {

      var width = 700;
      var height = 600;

      g.setColor(Color.WHITE)
      g.fillRect(0, 0, width, height)

      val tileSize = Math.min((width - 150) / cols, (height - 150) / rows)
      g.setColor(bgColor)
      g.fillRect(50, 50, tileSize * cols + 50, tileSize * rows + 50)

      g.setStroke(new BasicStroke(height / 100))
      val font = new Font("Arial", 0, 16)
      g.setFont(font)

      for {
        row <- 0 until rows
        col <- 0 until cols
      } {
        val x = (col * tileSize) + 75
        val y = (row * tileSize) + 75
        val tileColor = if ((row + col) % 2 == 0) color1 else color2
        g.setColor(tileColor)

        shape match {
          case "line" => {
            if (row != rows - 1 && col != cols - 1) {
              // changes color every 3 steps for soduko
              if (((row + 1) * (col + 1)) % 3 == 0) g.setColor(color1)
              else g.setColor(color2)
              g.drawLine(
                x + tileSize,
                75,
                x + tileSize,
                75 + (tileSize * (rows))
              )
              g.drawLine(
                75,
                y + tileSize,
                75 + (tileSize * (cols)),
                y + tileSize
              )
            }
          }
          case "square" => g.fillRect(x, y, tileSize, tileSize)
          case "circle" => g.fillOval(x, y, tileSize, tileSize)
          case _ => throw new IllegalArgumentException(s"Unsupported shape: $shape")
        }

        val aChar = ('a' + row).toChar
        val aString = s"$aChar"
        val oneString = s"${col + 1}"
        g.setColor(Color.BLACK)
        g.drawString(aString, 25, y + tileSize / 2)
        g.drawString(oneString, x + tileSize / 2, 25)
        g.drawString(aString, tileSize * cols + 125, y + tileSize / 2)
        g.drawString(oneString, x + tileSize / 2, tileSize * rows + 125)
      }
    }
  

    val Chess_Controller = (input: String) => {}

    val Connect4_Controller = (input: String) => {}

    val XO_Controller = (input: String) => {}

    val Checkers_Controller = (input: String) => {}

    val Queens_Controller = (input: String) => {}

    val Suduko_Controller = (input: String) => {}

    new MainFrame {
      title = "GUI Program"

      val Games: List[String] = List("Chess", "Connect4", "XO", "Checkers", "Suduko", "8Queens")

      contents = new BoxPanel(Orientation.Vertical) {

        var j = 0;
        override def paint(g: Graphics2D): Unit = {
          var myFont = new Font("Courier New", 1, 20);
          g.setFont(myFont)

          g.setBackground(new Color(0xE5E1E6))
          g.clearRect(0,0,700,650);
          g.setColor(new Color(200,100,250))

          var i = 0;
          Games.foreach(x => {
            g.drawString(x, 300, 300 + 50 * i)
            i += 1
          })
          
          g.drawString(">", 270, 300 + 50 * j)
        }

        listenTo(keys)
        listenTo(mouse.clicks)

        reactions += {
          case MouseClicked(_, p, _, _, _) =>
            println(p)
            requestFocus()
          case KeyPressed(_, c, _, _) => c match {
            
            case Key.S =>{
              j+=1;
              if(j == 6)j=0;
              repaint()
            }

            case Key.W =>{
              j-=1;
              if(j < 0)j=5;
              repaint()
            }

            case Key.Enter =>{
              input = Games(j);
              
              input match {
                  case "Chess"    => abstractEngine(Color.DARK_GRAY, 8, 8, Color.BLACK, Color.WHITE, "square",drawBoard,Chess_Controller)
                  case "8Queens"  => abstractEngine(Color.DARK_GRAY, 8, 8, Color.BLACK, Color.WHITE, "square",drawBoard,Queens_Controller)
                  case "Connect4" => abstractEngine(Color.BLUE, 6, 8, Color.WHITE, Color.WHITE, "circle",drawBoard,Connect4_Controller)
                  case "XO"       => abstractEngine(Color.BLACK, 3, 3, Color.YELLOW, Color.YELLOW, "line",drawBoard,XO_Controller)
                  case "Suduko"   => abstractEngine(Color.LIGHT_GRAY, 9, 9, Color.BLACK, Color.WHITE, "line",drawBoard,Suduko_Controller)
                  case "Checkers" => abstractEngine(Color.DARK_GRAY, 8, 8, Color.BLACK, Color.WHITE, "square",drawBoard,Checkers_Controller)
              }

              dispose()
            }
            requestFocus()
          }
        }
      }

      size = new Dimension(700, 650)
      background = new Color(0xf2d16b)
      centerOnScreen
      visible = true
    }

    def abstractEngine(
        bgColor: Color,rows: Int,cols: Int,color1: Color,color2: Color,shape: String,
        Drawer: (Color,Int,Int,Color,Color,String,Graphics2D) => Unit,
        Controller: (String) => Unit
    ): Unit = {
      new MainFrame(null) {
        title = "Game Engine"

        contents = new BoxPanel(Orientation.Vertical) {
          override def paint(g: Graphics2D): Unit = {
            Drawer(bgColor,rows,cols,color1,color2,shape,g);
          }
        }

        this.bounds_=(new Rectangle(700, 700))
        this.background_=(new Color(0, 0, 0))
        this.centerOnScreen()
        this.resizable = false
        this.visible = true
      }
    }
  }
}



/*
contents = new BoxPanel(Orientation.Vertical) {
  listenTo(mouse.clicks)
  listenTo(keys)
  reactions += {
    case MouseClicked(_, p, _, _, _) => println(p)
          requestFocus()
    case KeyTyped(_, c, _, _) => println(s"Key typed: $c")
          requestFocus()
  }

  override def paint(g: Graphics2D): Unit = {

  }
}

val chess_Drawer = (g: Graphics2D) => {
      g.setColor(new Color(0xffffff))
      g.fillRect(120, 80, 440, 440)
      g.setColor(new Color(100, 100, 100))
      for {
        i <- 0 to 7
        j <- 0 to 7
      } {

        if ((i + j) % 2 == 0) g.setColor(new Color(0xeeeed2))
        else g.setColor(new Color(0, 0, 0))

        g.fillRect(140 + i * 50, 100 + j * 50, 50, 50)
      }
    }

    val connect4_Drawer = (g: Graphics2D) => {
      g.setColor(new Color(0x06038d))
      g.fillRoundRect(150, 90, 400, 440, 10, 10)
      g.setColor(new Color(255, 255, 255))
      for {
        i <- 0 to 5
        j <- 0 to 6
      } {
        g.fillOval(185 + i * 55, 120 + j * 55, 50, 50)
      }
    }

    val XO_Drawer = (g: Graphics2D) => {
      g.setColor(new Color(139, 211, 230))
      g.fillRoundRect(45, 45, 600, 520, 10, 10)
      g.setColor(new Color(206, 162, 0))
      g.setStroke(new BasicStroke(10))

      g.drawLine(246, 50, 246, 560)
      g.drawLine(442, 50, 442, 560)
      g.drawLine(50, 220, 640, 220)
      g.drawLine(50, 390, 640, 390)
    }

    /*to be implemented*/
    val Suduko_Drawer = (g: Graphics2D) => {
      g.setColor(new Color(139, 211, 230))
      g.fillRoundRect(45, 45, 600, 520, 10, 10)
      g.setColor(new Color(206, 162, 0))
      g.setStroke(new BasicStroke(10))

      g.drawLine(246, 50, 246, 560)
      g.drawLine(442, 50, 442, 560)
      g.drawLine(50, 220, 640, 220)
      g.drawLine(50, 390, 640, 390)
    }

  var textField = new TextField()
  textField.background = Color.white
  textField.font = new Font("Arial",1,16)
  textField.maximumSize = new Dimension(600,60)
  textField.minimumSize = new Dimension(600,60)
  textField.preferredSize = new Dimension(600,60)
  textField.xLayoutAlignment_=(0f)

  var button = new Button("input")
  button.xLayoutAlignment_=(600f)

  contents += Swing.VStrut(600) 
  contents += textField
  contents += button

*/