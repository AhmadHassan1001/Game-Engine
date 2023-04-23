import scala.swing._
import scala.swing.event._
import java.awt.{Color, Graphics2D, BasicStroke}
import java.awt.geom._
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.FileInputStream
import java.io.File
import javax.swing.border.Border
import java.awt

object GameEngine {

  def main(args: Array[String]): Unit = {
    var input = "Connect4"

    //Chess Array
    var chessImageArrayW = Array((1,"RookWhite"),(2,"KnightWhite"),(3,"BishopWhite"),(4,"QueenWhite"),
                                 (5,"KingWhite"),(3,"BishopWhite"),(2,"KnightWhite"),(1,"RookWhite"))
    var chessImageArrayB = Array((7,"RookBlack"),(8,"KnightBlack"),(9,"BishopBlack"),(10,"QueenBlack"),
                                 (11,"KingBlack"),(9,"BishopBlack"),(8,"KnightBlack"),(7,"RookBlack"))

    var chessBoard = Array.ofDim[Tuple2[Int,String]](8,8)
    chessBoard(0) = chessImageArrayW
    chessBoard(1) = Array.fill[Tuple2[Int,String]](8)(6,"PawnWhite")
    chessBoard(6) = Array.fill[Tuple2[Int,String]](8)(12,"PawnBlack")
    chessBoard(7) = chessImageArrayB  

    
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
  

    val Chess_Controller = (input: String) => {
      if(input.size != 4) false

      var start:Tuple2[Char,Int] = (input(0),input(1).asDigit)
      var end  :Tuple2[Char,Int] = (input(2),input(3).asDigit)

      true
    }: Boolean

    val Connect4_Controller = (input: String) => {true}: Boolean

    val XO_Controller = (input: String) => {true}: Boolean

    val Checkers_Controller = (input: String) => {true}: Boolean

    val Queens_Controller = (input: String) => {true}: Boolean

    val Suduko_Controller = (input: String) => {true}: Boolean

    new MainFrame {
      title = "Game Engine"

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
                  case "Chess"    => abstractEngine(Color.GRAY, 8, 8, new Color(0xC2B280), Color.WHITE, "square",2,drawBoard,Chess_Controller)
                  case "8Queens"  => abstractEngine(Color.DARK_GRAY, 8, 8, new Color(0xC2B280), Color.WHITE, "square",2,drawBoard,Queens_Controller)
                  case "Connect4" => abstractEngine(Color.BLUE, 6, 8, Color.WHITE, Color.WHITE, "circle",1,drawBoard,Connect4_Controller)
                  case "XO"       => abstractEngine(Color.BLACK, 3, 3, Color.YELLOW, Color.YELLOW, "line",1,drawBoard,XO_Controller)
                  case "Suduko"   => abstractEngine(Color.LIGHT_GRAY, 9, 9, Color.BLACK, Color.WHITE, "line",1,drawBoard,Suduko_Controller)
                  case "Checkers" => abstractEngine(Color.DARK_GRAY, 8, 8, new Color(0xC2B280), Color.WHITE,"square",2,drawBoard,Checkers_Controller)
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
        bgColor: Color,rows: Int,cols: Int,color1: Color,color2: Color,shape: String,typ:Int,
        Drawer: (Color,Int,Int,Color,Color,String,Graphics2D) => Unit,
        Controller: (String) => Boolean
    ): Unit = {
      new MainFrame(null) {
        title = "Game Engine"
        class Canvas extends Component {
          preferredSize = new Dimension(700, 600)
          override def paint(g: Graphics2D): Unit = {

            Drawer(bgColor,rows,cols,color1,color2,shape,g);
            
            for {
              row <- 0 until 8
              col <- 0 until 8
            } {
              val x = (col * 57) + 75
              val y = (row * 57) + 75
              if(chessBoard(row)(col) != null) g.drawImage(readImage(chessBoard(row)(col)._2),x,y,50,50,null)
            }
          }       
          
          def readImage(img:String):BufferedImage = {
            val file = new File("src/main/resources/Chess/"+img+".png")
            val image = ImageIO.read(file)
            image
          }
        }  

        //GUI
        contents = new BoxPanel(Orientation.Vertical) {

          var canvas = new Canvas
          contents += canvas
          contents += Swing.Glue
          contents += new FlowPanel{
  
            contents += new BoxPanel(Orientation.NoOrientation){
              maximumSize = new Dimension(670,20)
              preferredSize = new Dimension(670,20)
              background = new Color(0xb1e9fe)

              var str = ""
              if(typ == 1)str = "Position"
              else str = "Start Position"

              contents += Swing.HStrut(10)          
              contents += new Label(str){font = new Font("Segoe Print", 1, 16)}
              contents += Swing.HStrut(27)
              if(typ == 2)contents += new Label("end Position"){font = new Font("Segoe Print", 1, 16)}
            }

            contents += new BoxPanel(Orientation.NoOrientation){
              maximumSize = new Dimension(670,20)
              preferredSize = new Dimension(670,20)
              background = new Color(0xb1e9fe)

              var inputField1 = new TextField("")
              var inputField2 = new TextField("")
              //Adjust Size
              inputField1.maximumSize = new Dimension(100,30)
              inputField2.maximumSize = new Dimension(100,30)
              //Adjust Font
              inputField1.font = new Font("Arial", 0, 15)
              inputField2.font = new Font("Arial", 0, 15)
             
              contents += Swing.HStrut(15)
              contents += inputField1
              contents += Swing.HStrut(35)
              if(typ == 2)contents += inputField2
              else contents += Swing.HStrut(100)

              contents += Swing.HStrut(320)
              contents += Button("Do Action!!") { 
                  var s1 = inputField1.text
                  var s2 = inputField2.text
                  var input = s1+s2
                  println(input) 

                  if(Controller(input)){

                    chessBoard(s2(0)-'a')(s2(1).asDigit-1) = chessBoard(s1(0)-'a')(s1(1).asDigit-1)
                    chessBoard(s1(0)-'a')(s1(1).asDigit-1) = null
                    canvas.repaint()
                  }                 
              }
            }
            background = new Color(0xb1e9fe)
            maximumSize = new Dimension(700,80)
            preferredSize = new Dimension(700,80)
            border = Swing.TitledBorder(Swing.EtchedBorder(Swing.Lowered), "Input")
          } 
        }
        bounds = new Rectangle(700, 700)
        centerOnScreen()
        resizable = false
        visible = true
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


      
    for{
      i <- 0 until 8
      j <- 0 until 8
    }{
      print(chessBoard(i)(j))
      if(j == 7)println()
    }

  //val file = new File("src/main/resources/Chess/BishopBlack.png")
  // val image = ImageIO.read(file)

*/