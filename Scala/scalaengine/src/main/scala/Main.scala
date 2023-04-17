import scala.swing._
import scala.swing.event._
import java.awt.{Color,Graphics2D,BasicStroke}
import java.awt.geom._
import java.awt.Canvas

object GuiProgramSix {

  def main(args: Array[String]): Unit = {
    val input = "Connect4"    

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
      g.setColor(new Color(0x06038D))
      g.fillRoundRect(150, 90, 400, 440,10,10)
      g.setColor(new Color(255,255,255))
      for {
        i <- 0 to 5
        j <- 0 to 6
      } {
        g.fillOval(185 + i * 55, 120 + j * 55, 50, 50)
      }
    }

    val XO_Drawer = (g: Graphics2D) => {
      g.setColor(new Color(139,211,230))
      g.fillRoundRect(45, 45, 600, 520,10,10)
      g.setColor(new Color(206,162,0))
      g.setStroke(new BasicStroke(10))
      
      g.drawLine(246,50,246,560)
      g.drawLine(442,50,442,560)
      g.drawLine(50,220,640,220)
      g.drawLine(50,390,640,390)
    }

    new MainFrame(null) {
      title = "GUI Program"

      val reqBoard = input match {
        case "Chess"|"Queens"|"Checkers" => chess_Drawer
        case "Connect4" => connect4_Drawer
        case "XO" => XO_Drawer
      }

      contents = new BoxPanel(Orientation.Vertical){
        override def paint(g: Graphics2D): Unit = {
          reqBoard(g);
        }
      }

      this.bounds_=(new Rectangle(700, 650))
      this.background_=(new Color(0, 0, 0))
      this.centerOnScreen()
      this.visible = true
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