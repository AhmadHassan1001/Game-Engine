import java.awt.{Color, Graphics}
import javax.swing.{JFrame, JPanel}

object BoardDrawer extends App {
  def drawBoard(drawMethod: (Graphics, Int, Int, Int, Int, Color, Color) => Unit, bgColor: Color, rows: Int, cols: Int, color1: Color, color2: Color, shape: String): Unit = {
    val frame = new JFrame("Board Drawer")
    val panel = new JPanel {
      override def paintComponent(g: Graphics): Unit = {
        g.setColor(bgColor)
        g.fillRect(0, 0, getWidth, getHeight)
        val tileSize = Math.min(getWidth / cols, getHeight / rows)
        for {
          row <- 0 until rows
          col <- 0 until cols
        } {
          val x = col * tileSize
          val y = row * tileSize
          val tileColor = if ((row + col) % 2 == 0) color1 else color2
          g.setColor(tileColor)
          shape match {
            case "line" => g.drawLine(x, y, x + tileSize, y + tileSize)
            case "square" => g.fillRect(x, y, tileSize, tileSize)
            case "circle" => g.fillOval(x, y, tileSize, tileSize)
            case _ => throw new IllegalArgumentException(s"Unsupported shape: $shape")
          }
        }
      }
    }
    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) dosn't work somehow
    frame.getContentPane.add(panel)
    frame.pack()
    frame.setVisible(true)
    frame.setSize(600, 600)
    drawMethod(panel.getGraphics, 0, 0, panel.getWidth, panel.getHeight, color1, color2)
  }

  def drawTwoColorBoard(g: Graphics, x: Int, y: Int, width: Int, height: Int, color1: Color, color2: Color): Unit = {
    g.setColor(color1)
    g.fillRect(x, y, width / 2, height)
    g.setColor(color2)
    g.fillRect(x + width / 2, y, width / 2, height)
  }

  def drawOneColorBoard(g: Graphics, x: Int, y: Int, width: Int, height: Int, color1: Color, color2: Color): Unit = {
    g.setColor(color1)
    g.fillRect(x, y, width, height)
  }

  drawBoard(drawTwoColorBoard, Color.CYAN, 8, 8, Color.WHITE, Color.BLACK, "square")
}
