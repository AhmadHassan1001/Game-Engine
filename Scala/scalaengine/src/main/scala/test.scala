import java.awt.{Color, Graphics, Graphics2D, BasicStroke}
import javax.swing.{JFrame, JPanel}
import scala.swing.event.WindowClosed
import javax.swing.WindowConstants

object BoardDrawer extends App {
  def drawBoard(bgColor: Color, rows: Int, cols: Int, color1: Color, color2: Color, shape: String): Unit = {
    val frame = new JFrame("Board Drawer")
    val panel = new JPanel {
      override def paintComponent(g: Graphics): Unit = {
        g.setColor(bgColor)
        g.fillRect(0, 0, getWidth, getHeight)
        val g2d = g.asInstanceOf[Graphics2D] // cast to Graphics2D
        g2d.setStroke(new BasicStroke(5)) // set line width to 5
        val tileSize = Math.min((getWidth-100) / cols, (getHeight-100) / rows)
        for {
          row <- 0 until rows
          col <- 0 until cols
        } {
          val x = ( col * tileSize ) +50
          val y = ( row * tileSize ) +50
          val tileColor = if ((row + col) % 2 == 0) color1 else color2
          g.setColor(tileColor)
          shape match {
            case "line" => { if (row != rows-1 && col != cols-1) {
                            g.drawLine(x+tileSize, 0, x+tileSize , rows * tileSize) 
                            g.drawLine(0, y+tileSize, cols*tileSize , y+tileSize)}}
            case "square" => g.fillRect(x, y, tileSize, tileSize)
            case "circle" => g.fillOval(x, y, tileSize, tileSize)
            case _ => throw new IllegalArgumentException(s"Unsupported shape: $shape")
          }
        }
      }
    }
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.getContentPane.add(panel)
    frame.pack()
    frame.setVisible(true)
    frame.setSize(600, 600)
  }

  drawBoard(Color.BLUE, 8, 8, Color.BLACK, Color.WHITE, "square")

}
