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

    // var scaledImage = ImageIO.read(new FileInputStream("/src/main/scala/Assets/Chess/BishopBlack.png"));

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

        listenTo(mouse.clicks)
        listenTo(keys)

        reactions += {
          case MouseClicked(_, p, _, _, _) =>
            println(p)
            requestFocus()
          case KeyPressed(_, c, _, _) => c match {
            
            case Key.W =>{
              j+=1;
              if(j == 6)j=0;
              repaint()
            }

            case Key.S =>{
              j-=1;
              if(j < 0)j=5;
              repaint()
            }

            case Key.Enter =>{
              input = Games(j);

              val reqBoard = input match {
                  case "Chess" | "8Queens" | "Checkers" => chess_Drawer
                  case "Connect4"                      => connect4_Drawer
                  case "XO"                            => XO_Drawer
                  case "Suduko"                        => Suduko_Drawer
              }

              val reqController = input match {
                  case "Chess"    => Chess_Controller
                  case "8Queens"   => Queens_Controller
                  case "Connect4" => Connect4_Controller
                  case "XO"       => XO_Controller
                  case "Suduko"   => Suduko_Controller
                  case "Checkers" => Checkers_Controller
              }

              abstractEngine(reqBoard,reqController)
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
        Drawer: (Graphics2D) => Unit,
        Controller: (String) => Unit
    ): Unit = {
      new MainFrame(null) {
        title = "GUI Program"

        contents = new BoxPanel(Orientation.Vertical) {
          override def paint(g: Graphics2D): Unit = {
            Drawer(g);
            // if(scaledImage == null) println("no")
            // else g.drawImage(scaledImage,100,100,null)
          }
          // repaint()
        }

        this.bounds_=(new Rectangle(700, 650))
        this.background_=(new Color(0, 0, 0))
        this.centerOnScreen()
        this.visible = true
      }
    }
  }
}

// contents = new BoxPanel(Orientation.Vertical) {
//   listenTo(mouse.clicks)
//   listenTo(keys)
//   reactions += {
//     case MouseClicked(_, p, _, _, _) => println(p)
//           requestFocus()
//     case KeyTyped(_, c, _, _) => println(s"Key typed: $c")
//           requestFocus()
//   }

//   // override def paint(g: Graphics2D): Unit = {

//   // }
// }
