import java.awt.{Color, Font, Graphics, Graphics2D, BasicStroke}
import javax.swing.{JFrame, JPanel}
import scala.swing.event.WindowClosed
import javax.swing.WindowConstants

object BoardDrawer extends App {
  def drawBoard(
      bgColor: Color,
      rows: Int,
      cols: Int,
      color1: Color,
      color2: Color,
      shape: String
  ): Unit = {
    val frame = new JFrame("Board Drawer")
    val panel = new JPanel {
      override def paintComponent(g: Graphics): Unit = {
        g.setColor(Color.WHITE)
        g.fillRect(0, 0, getWidth, getHeight)
        val tileSize =
          Math.min((getWidth - 150) / cols, (getHeight - 150) / rows)
        g.setColor(bgColor)
        g.fillRect(50, 50, tileSize * cols + 50, tileSize * rows + 50)
        val g2d = g.asInstanceOf[Graphics2D]
        g2d.setStroke(new BasicStroke(getHeight / 100))
        val font = new Font("Arial", Font.PLAIN, 16)
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
            case _ =>
              throw new IllegalArgumentException(s"Unsupported shape: $shape")
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
    }
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    frame.getContentPane.add(panel)
    frame.pack()
    frame.setVisible(true)
    frame.setSize(512, 512)
  }

  // XO
  drawBoard(Color.BLACK, 3, 3, Color.YELLOW, Color.YELLOW, "line")
  // chess & others
  drawBoard(Color.DARK_GRAY, 8, 8, Color.BLACK, Color.WHITE, "square")
  // connect4
  drawBoard(Color.BLUE, 6, 8, Color.WHITE, Color.WHITE, "circle")
  // sudoko
  drawBoard(Color.LIGHT_GRAY, 9, 9, Color.BLACK, Color.WHITE, "line")

}
