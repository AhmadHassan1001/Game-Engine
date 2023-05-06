import scala.swing._
import scala.swing.event._
import scala.collection.mutable._
import java.awt.{Color, Graphics2D, BasicStroke}
import java.awt.geom._
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.FileInputStream
import java.io.File
import javax.swing.border.Border
import javax.swing.SwingConstants
import java.awt
import javax.swing.JFrame
import scala.util.Random

object GameEngine {


  def AbstractDrawer (bgColor: Color,rows: Int,cols: Int,color1: Color,color2: Color,shape: String,g: Graphics2D) = {

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
        case "line" => 
          if (row != rows - 1 && col != cols - 1) 
          {
            // changes color every 3 steps for soduko
            if (((row + 1) * (col + 1)) % 3 == 0) g.setColor(color1)
            else g.setColor(color2)

            g.drawLine(x + tileSize,75,x + tileSize,75 + (tileSize * (rows)))
            g.drawLine(75,y + tileSize,75 + (tileSize * (cols)),y + tileSize)
          }
        
        case "square" => g.fillRect(x, y, tileSize, tileSize)
        case "circle" => g.fillOval(x, y, tileSize, tileSize)
        case _ => throw new IllegalArgumentException(s"Unsupported shape: $shape")
      }

      val aChar = ('a' + col).toChar
      val letterString = s"$aChar"
      val numberString = s"${rows - row }"
      g.setColor(Color.BLACK)
      g.drawString(numberString, 25, (y + tileSize / 2))
      g.drawString(numberString, (tileSize * cols + 125), (y + tileSize / 2))
      g.drawString(letterString, (x + tileSize / 2), 25)
      g.drawString(letterString, (x + tileSize / 2), (tileSize * rows + 125))
    }
  }

  def readImage(img:String):BufferedImage = {
    val file = new File("src/main/resources/"+img+".png")
    val image = ImageIO.read(file)
    image
  }

  def abstractEngine[T](
    gameName:String,
    Drawer: (Seq[T]) => MainFrame,
    Controller: (Seq[T],String,Int) => Boolean,
    state:Seq[T]
  ): Unit = 
  {
    new MainFrame(null) {
      title = gameName

      //GUI
      contents = new BoxPanel(Orientation.Vertical) {

        contents += new FlowPanel{

          //text panel
          contents += new BoxPanel(Orientation.NoOrientation){
            maximumSize = new Dimension(670,20)
            preferredSize = new Dimension(670,20)
            background = new Color(0xb1e9fe)

            var str = gameName match{
              case "Chess"|"8Queens"|"Checkers" => "Start Position"
              case "XO" | "Connect4" |"Suduko" => "Position"
            }

            contents += Swing.HStrut(20)
            val firstLabel = new Label(str){font = new Font("Arial", 1, 12)}
            firstLabel.maximumSize = new Dimension(100,20)
            firstLabel.horizontalAlignment = Alignment.Left
            contents += firstLabel
            contents += Swing.HStrut(20)

            gameName match {
              case "Chess"|"Checkers" => {
                contents += new Label("end Position"){font = new Font("Arial", 1, 12)}
              }
              case "Suduko" => {
                contents += new Label("value"){font = new Font("Arial", 1, 12)}
              }
              case "8Queens"|"XO" | "Connect4" =>{}
            }

          }
          
          //input panel
          contents += new BoxPanel(Orientation.NoOrientation){
            maximumSize = new Dimension(670,20)
            preferredSize = new Dimension(670,20)
            background = new Color(0xb1e9fe)

            var txt = gameName match{
              case "Chess"|"8Queens"|"Checkers" => "Black's  Turn"
              case "XO" | "Connect4" => "Red Player's Turn"
              case "Suduko" =>""
            }

            var inputField1 = new TextField("")
            var inputField2 = new TextField("")
            var turnlabel = new Label(txt)
            //Adjust Size
            inputField1.maximumSize = new Dimension(100,30)
            inputField2.maximumSize = new Dimension(100,30)
            //Adjust Font
            inputField1.font = new Font("Arial", 0, 15)
            inputField2.font = new Font("Arial", 0, 15)
            //label adj
            turnlabel.maximumSize = new Dimension(200,20)
            turnlabel.font = new Font("Arial", 1, 15)
            turnlabel.foreground = new Color(0x013220)
            
            contents += Swing.HStrut(20)
            contents += inputField1
            contents += Swing.HStrut(20)
            
            gameName match {
              case "Chess"|"Suduko" => {
                contents += inputField2
              }
              case "8Queens"|"Checkers"|"XO"|"Connect4" =>{
                contents += Swing.HStrut(100)
              }
            }

            contents += Swing.HStrut(50)
            contents += turnlabel
            contents += Swing.HStrut(100)

            var turn = 0
            //input handeling
            var oldframe:MainFrame = Drawer(state)
            contents += Button("Do Action!!") { 
                var s1 = inputField1.text
                var s2 = inputField2.text
                var input = s1+s2
                println(input) 
                // sudukoBoard(Math.abs(s1(1).asDigit-rows))(s1(0)-'a')=s2.toInt
                // canvas.repaint()

                if(Controller(state,input,turn)){
                  turnlabel.foreground = new Color(0x013220)

                  turn+=1
                  turn = turn%2  

                  if(turn%2 == 0)
                  {
                    turnlabel.text = gameName match{
                      case "Chess"|"8Queens"|"Checkers" => "Black's  Turn"
                      case "XO" | "Connect4" => "Red Player's Turn"
                      case "Suduko" => ""
                    }
                  } 
                  else 
                  {
                    turnlabel.text = gameName match{
                      case "Chess"|"8Queens"|"Checkers" => "White's  Turn"
                      case "XO" | "Connect4" => "Yellow Player's Turn"
                      case "Suduko" => ""
                    }
                  }
                  //Reset Input Fields After each Move
                  inputField1.text =""
                  inputField2.text =""
                  
                  if(oldframe != null)oldframe.dispose()

                  oldframe = Drawer(state);
                }
                else{
                  turnlabel.foreground = Color.RED
                  turnlabel.text = "Not valid"
                }       
            }
          }

          background = new Color(0xb1e9fe)
          maximumSize = new Dimension(700,80)
          preferredSize = new Dimension(700,80)
          border = Swing.TitledBorder(Swing.EtchedBorder(Swing.Lowered), "Input")
        } 
      }
      
      bounds = new Rectangle(700, 120)
      centerOnScreen()
      resizable = false
      visible = true
    }
  }

  //main
  def main(args: Array[String]): Unit = {

    //Chess Drawer
    val Chess_Drawer = (Board:Seq[Array[Tuple2[Int,String]]]) =>{
      var rows = 8
      var cols = 8

      var frame = new MainFrame (){

        class Canvas extends Component{
          override def paint(g: Graphics2D): Unit = {
            AbstractDrawer(Color.GRAY, rows, cols, new Color(0xC2B280), Color.WHITE, "square",g)

            for {
              row <- 0 until rows
              col <- 0 until cols
              } {
              val x = (col * 57) + 75
              val y = (row * 57) + 75
              if(Board(row)(col) != null) g.drawImage(readImage("Chess/"+Board(row)(col)._2),x,y,50,50,null)
            }
          }  
        } 

        contents = new Canvas
        bounds = new Rectangle(700, 700)
        centerOnScreen()
        resizable = false
        visible = true    
      } 

      frame
    }

    //Connect4 Drawer
    val Connect4_Drawer = (Board:Seq[ListBuffer[Int]]) => {
      var rows = 6
      var cols = 7

      var frame = new MainFrame (){

        class Canvas extends Component{
          override def paint(g: Graphics2D): Unit = {
           AbstractDrawer(Color.BLUE, rows, cols, Color.WHITE, Color.WHITE, "circle",g)

            for(i <- 0 until Board.size){
              var pos = i
              var list = Board(i)
              var circleSize = 76
              var x = (pos * 75) + 75
          
              for(j <- 0 until list.size){
                var y = 450 - (75 * j)
                list(j) match {
                  case 0 => g.drawImage(readImage("Connect4/Red"),x,y,circleSize,circleSize,null)
                  case 1 => g.drawImage(readImage("Connect4/Yellow"),x,y,circleSize,circleSize,null)
                }
              } 
            }
          }  
        } 

        contents = new Canvas
        bounds = new Rectangle(700, 700)
        centerOnScreen()
        resizable = false
        visible = true    
      } 

      frame
    }

    //XO Drawer
    val XO_Drawer = (XOArray:Seq[Array[Int]]) => {
      var rows = 3
      var cols = 3

      var frame = new MainFrame (){

        class Canvas extends Component{
          override def paint(g: Graphics2D): Unit = {
            AbstractDrawer(Color.BLACK, rows, cols, Color.YELLOW, Color.YELLOW, "line",g)
            
            g.setFont(new Font("Arial",1,100))
            
            for {
              row <- 0 until rows
              col <- 0 until cols
              } {
              val x = (col * 150) + 110
              val y = (row * 160) + 170

              XOArray(row)(col) match {
                case 0 =>  g.setColor(Color.RED);g.drawString("X",x,y)
                case 1 =>  g.setColor(Color.BLUE);g.drawString("O",x,y)
                case _ => ""
              }
            }
          }  
        } 

        contents = new Canvas
        bounds = new Rectangle(700, 700)
        centerOnScreen()
        resizable = false
        visible = true    
      } 

      frame
    }

    //Checkers Drawer
    val Checkers_Drawer = (Board:Seq[Array[Int]]) => {

      var rows = 8
      var cols = 8

      var frame = new MainFrame (){

        class Canvas extends Component{
          override def paint(g: Graphics2D): Unit = {
            AbstractDrawer(Color.DARK_GRAY, rows, cols, Color.WHITE, new Color(0xC2B280),"square",g)

            for {
              row <- 0 until rows
              col <- 0 until cols
              } {
              val x = (col * 57) + 75
              val y = (row * 57) + 75
              if(Board(row)(col) == 1) g.drawImage(readImage("Checkers/1"),x,y,50,50,null)
              if(Board(row)(col) == 0) g.drawImage(readImage("Checkers/0"),x,y,50,50,null)
            }
          }  
        } 

        contents = new Canvas
        bounds = new Rectangle(700, 700)
        centerOnScreen()
        resizable = false
        visible = true    
      } 
      frame
    }

    //8 Queens Drawer
    val Queens_Drawer = (g: Graphics2D) => {
      var rows = 8
      var cols = 8
      var frame = new MainFrame (){

        class Canvas extends Component{
          override def paint(g: Graphics2D): Unit = {
            AbstractDrawer(Color.DARK_GRAY, rows, cols, new Color(0xC2B280), Color.WHITE, "square",g)

            /*to be continued*/
          }  
        } 

        contents = new Canvas
        bounds = new Rectangle(700, 700)
        centerOnScreen()
        resizable = false
        visible = true    
      }
      
      frame
    }

    //Suduko Drawer
    val Suduko_Drawer = (sudukoBoard:Seq[Array[Int]]) =>{
      var rows = 9
      var cols = 9

      var frame = new MainFrame (){

        class Canvas extends Component{
          override def paint(g: Graphics2D): Unit = {
            AbstractDrawer(Color.LIGHT_GRAY, rows, cols, Color.BLACK, Color.WHITE, "line",g)            
            val font = new Font("Arial", 0, 30)
            g.setFont(font)
            for {
              row <- 0 until rows
              col <- 0 until cols
            } {
              val x = (col * 50) + 75 +16
              val y = (row * 50) + 75 +34
              if(sudukoBoard(row)(col) != 0){
                if(sudukoBoard(row)(col) > 9){
                  g.setColor(Color.BLUE)
                  g.drawString((sudukoBoard(row)(col)/10).toString,x,y)
                }
                else {
                  g.setColor(Color.BLACK)
                  g.drawString(sudukoBoard(row)(col).toString,x,y)
                }
              }
          }  
        } 
      }

        contents = new Canvas
        bounds = new Rectangle(700, 700)
        centerOnScreen()
        resizable = false
        visible = true    
      } 

      frame
    }

    /*Check if a given coordinates is in the board or out*/
    def InBoard(input: String,rows:Int,cols:Int):Boolean = {

      if(input.size != 4)false
      
      var start:Tuple2[Int,Int] = (input(0)-'a'+1,input(1).asDigit)
      if(start._1 <= 0 || start._1 > cols )false
      if(start._2 <= 0 || start._2 > rows )false

      if(input.size > 2) {
        var end  :Tuple2[Int,Int] = (input(2)-'a'+1,input(3).asDigit)
        if(end._1 <= 0 || end._1 > cols )false
        if(end._2 <= 0 || end._2 > rows )false
      }

      true
    }

    /* checks for 1 input at a time eg "a1" only*/
    def InBoardo(input: String,rows:Int,cols:Int):Boolean = {
      
      var checker:Tuple2[Int,Int] = (input(0)-'a'+1,input(1).asDigit)
      if(checker._1 <= 0 || checker._1 > cols )false
      if(checker._2 <= 0 || checker._2 > rows )false

      true
    }

    val Chess_Controller = (chessBoard:Seq[Array[Tuple2[Int,String]]],input: String,turn:Int /*0->Black,1->white*/) => {

      var rows = 8
      var cols = 8
      //Check if Start and end Position are in Board*/
      var ret = true
      
      if(!InBoard(input,rows,cols))ret = false

      /*tuples containing position of peice in the Chess array (row,column)*/
      var start:Tuple2[Int,Int] = (Math.abs(input(1).asDigit-rows),input(0)-'a')
      var end  :Tuple2[Int,Int] = (Math.abs(input(3).asDigit-rows),input(2)-'a')
      
      /*if start peice is null then return false*/
      if(chessBoard(start._1)(start._2) == null)ret = false
      
      /*Check Validty of Start Position*/
      if(chessBoard(start._1)(start._2) != null)
      {
        /*if peice at start position is black and it's white turn then return false*/
        if(chessBoard(start._1)(start._2)._1 > 6 && turn == 1)ret = false

        /*if peice at start position is white and it's Black white turn then return false*/
        if(chessBoard(start._1)(start._2)._1 <= 6 && turn == 0)ret = false
      }

      /*Check Validty of end Position*/
      if(chessBoard(end._1)(end._2) != null)
      {
        /*if peice at end position is Black and it's Black turn then return false*/
        if(chessBoard(end._1)(end._2)._1 > 6 && turn == 0)ret = false

        /*if peice at end position is White and it's white turn then return false*/
        if(chessBoard(end._1)(end._2)._1 <= 6 && turn == 1)ret = false
      }      

      var peiceType = 14
      if(chessBoard(start._1)(start._2) != null){
        //Get the type of starting Peice
        peiceType = chessBoard(start._1)(start._2)._1
      }
  
      var deltaX = end._2 - start._2 
      var deltaY = end._1 - start._1
      var dirX = 0
      var dirY = 0

      //Get the direction Vector's X
      if(deltaX > 0)dirX = 1
      else if(deltaX < 0)dirX = -1

      //Get the direction Vector's Y
      if(deltaY > 0)dirY = 1
      else if(deltaY < 0)dirY = -1

      def rookMove():Boolean={

        var accept = true
        //if both Change in X and Change in Y are not zero then this move is Invalid
        if(Math.abs(deltaY) > 0 && Math.abs(deltaX) > 0)accept = false

        var startY = start._1
        var startX = start._2
        
        
        //go in the direction of the Move
        while((startY != end._1 || startX != end._2) && accept){
          startY += dirY
          startX +=dirX
          
          //if there is any peice in the pass then this move is InValid So return false
          if((chessBoard(startY)(startX) != null) && (startY != end._1 || startX != end._2)) 
            accept = false         
        }

        accept
      }

      def knightMove():Boolean={

        /*Valid Moves for knight are only those whose cahnge in one of its dimensions is 2 
          and the other is 1, otherwise this Move is In valid*/
        if((Math.abs(deltaY) == 2 && Math.abs(deltaX) == 1) || 
          (Math.abs(deltaY) == 1 && Math.abs(deltaX) == 2))
          true
        else
          false 
      }

      def bishopMove():Boolean={
        
        var accept = true
        /*Valid Moves for Bishop are only Moves Where absolute Change in x equals absolute Change in Y*/
        if(Math.abs(deltaY) == Math.abs(deltaX))
        {
          var startY = start._1
          var startX = start._2
        
          //go in the direction of the Move
          while(startY != end._1 || startX != end._2){
            startY += dirY
            startX +=dirX
            
            //if there is any peice in the pass then this move is InValid So return false
            if((chessBoard(startY)(startX) != null) && (startY != end._1 || startX != end._2)) 
              accept = false         
          }
        }
        else
          accept = false

        accept
      }

      def queenMove():Boolean={

        var accept = true
        /*only the none acceptable Move is the Move when change in x and Y are not equal and none of them are zeros*/
        if(Math.abs(deltaY) > 0 && Math.abs(deltaX) > 0 && Math.abs(deltaY) != Math.abs(deltaX))accept = false
        
        var startY = start._1
        var startX = start._2
        
        //go in the direction of the Move
        while((startY != end._1 || startX != end._2) && accept)
        {
          startY +=dirY
          startX +=dirX
            
          //if there is any peice in the pass then this move is InValid So return false
          if((chessBoard(startY)(startX) != null) && (startY != end._1 || startX != end._2)) 
              accept = false         
        }

        accept
      }

      def kingMove():Boolean={
        var accept = true

        if(Math.abs(deltaY)<2 && Math.abs(deltaX)<2){
          var startY = start._1+deltaY
          var startX = start._2+deltaX

          for{
            i <- List(0,-1,1)
            j <- List(0,-1,1)
          }{
            if(i>=0 && i<8 && j>=0 && j<8 && (i != 0 || j != 0)){
              
              if(chessBoard(startY+i)(startX+j)._1 <= 6 && turn == 0)accept = false
              if(chessBoard(startY+i)(startX+j)._1 > 6 && turn == 1)accept = false
              
            }
          }
        }
        else{
          accept = false
        }

        accept
      }

      def pawnMove():Boolean={

        var accept = false
        var startY = start._1
        var startX = start._2

        if(turn == 0){
          //Black pawn moves up by 1
          if(deltaY == -1 && deltaX == 0 && chessBoard(startY-1)(startX) == null)accept = true 
          //Black pawn moves diagonally left by 1
          if(deltaY == -1 && deltaX == -1 && chessBoard(startY-1)(startX-1) != null)accept = true
          //Black pawn moves diagonally right by 1
          if(deltaY == -1 && deltaX == 1 && chessBoard(startY-1)(startX+1) != null)accept = true
          //Black pawn moves up by 2 at first move
          if(deltaY == -2 && startY == 6 && deltaX == 0 && chessBoard(startY-2)(startX) == null && chessBoard(startY-1)(startX) == null)accept = true
        }
        else{
          //White pawn moves up by 1
          if(deltaY == 1 && deltaX == 0 && chessBoard(startY+1)(startX) == null)accept = true
          //White pawn moves diagonally left by 1
          if(deltaY == 1 && deltaX == -1 && chessBoard(startY+1)(startX-1) != null)accept = true
          //White pawn moves diagonally right by 1
          if(deltaY == 1 && deltaX == 1 && chessBoard(startY+1)(startX+1) != null)accept = true
          //White pawn moves up by 2 at first move
          if(deltaY == 2 && startY == 1 && deltaX == 0 && chessBoard(startY+2)(startX) == null && chessBoard(startY+1)(startX) == null)accept = true
        }
      
        accept
      }

      var valid = peiceType match{
        case 1|7  => rookMove
        case 2|8  => knightMove
        case 3|9  => bishopMove
        case 4|10 => queenMove
        case 5|11 => kingMove
        case 6|12 => pawnMove
        case _ => false
      }
      
      if(ret){  
        if(valid){
          chessBoard(Math.abs(end._1))(end._2) = chessBoard(start._1)(start._2)
          chessBoard(start._1)(start._2) = null
          true
        }
        else false
      }
      else{
        false
      }

    }: Boolean

    //Connect4 Controller
    val Connect4_Controller = (connect4Array:Seq[ListBuffer[Int]],input: String,Turn:Int) => {
      var rows = 6
      var cols = 7

      var loc = input(0) - 'a'
      var ret = false
      if(loc >= 0 && loc <= 6)ret = true
      
      if(connect4Array(loc).size < 6 && ret)connect4Array(loc)+=Turn
      else ret = false

      ret
    }: Boolean

    //Xo Controller
    val XO_Controller = (XOArray:Seq[Array[Int]],input: String,Turn:Int) => {
      var ret = true
      var rows = 3
      var cols = 3
      if(!InBoardo(input,rows,cols))ret = false

      var pos:Tuple2[Int,Int] = (Math.abs(input(1).asDigit-rows),input(0)-'a')
      if(XOArray(pos._1)(pos._2) == -1)XOArray(pos._1)(pos._2) = Turn
      else ret = false

      ret
    }: Boolean

    //Suduko Controller
    val Suduko_Controller = (sudukoBoard:Seq[Array[Int]],input: String,Turn:Int) => {
      var rows = 9
      var cols = 9
      val col = Character.getNumericValue(input.charAt(0)) - 10
      println(col)
      val row = 9 - (Character.digit(input.charAt(1), 10))
      println(row)
      var value = Character.digit(input.charAt(2), 10)
      println(value)
      if(!InBoardo(input.substring(0, input.length - 1),rows,cols))false
      if(sudukoBoard(row)(col) > 9) false
      else {sudukoBoard(row)(col) = value
        true
      }
      
    }: Boolean

    //Checkers Controller
    val Checkers_Controller = (checkersBoard:Seq[Array[Int]],input: String,turn:Int) => {true}: Boolean

    //Queens Controller
    val Queens_Controller = (input: String,Turn:Int) => {true}: Boolean

    //start menu
    new MainFrame {
      title = "Game Engine"

      var input = "Connect4" //dummy
      
      val Games: List[String] = List("Chess", "Connect4", "XO", "Checkers", "Suduko", "8Queens")

      val gamesCnt = Games.size

      object SudokuGenerator {
        def generateBoard(emptySpaces: Int = Random.between(60, 70)): Array[Array[Int]] = {
          val board = Array.ofDim[Int](9, 9)
          for (i <- 0 until 9; j <- 0 until 9) {
            board(i)(j) = 0
          }
          fillBoard(board, 0, 0)
          randomlyRemoveNumbers(board, emptySpaces)

          for (i <- 0 until 9) {
            for (j <- 0 until 9) {
              board(i)(j) *= 10
            }
          }

          board
        }

        private def fillBoard(board: Array[Array[Int]], row: Int, col: Int): Boolean = {
          if (col == 9) {
            return fillBoard(board, row + 1, 0)
          }
          if (row == 9) {
            return true
          }
          val nums = randomPermutation()
          for (num <- nums) {
            if (isValid(board, row, col, num)) {
              board(row)(col) = num
              if (fillBoard(board, row, col + 1)) {
                return true
              }
              board(row)(col) = 0
            }
          }
          false
        }

        private def isValid(board: Array[Array[Int]], row: Int, col: Int, num: Int): Boolean = {
          val boxRowStart = 3 * (row / 3)
          val boxColStart = 3 * (col / 3)
          for (i <- 0 until 9) {
            if (board(row)(i) == num || board(i)(col) == num || board(boxRowStart + i / 3)(boxColStart + i % 3) == num) {
              return false
            }
          }
          true
        }

        private def randomlyRemoveNumbers(board: Array[Array[Int]], emptySpaces: Int): Unit = {
          var count = 0
          while (count < emptySpaces) {
            val row = Random.nextInt(9)
            val col = Random.nextInt(9)
            if (board(row)(col) != 0) {
              board(row)(col) = 0
              count += 1
            }
          }
        }

        private def randomPermutation(): Array[Int] = {
          val nums = Array.range(1, 10)
          for (i <- nums.length - 1 to 1 by -1) {
            val j = Random.nextInt(i + 1)
            val tmp = nums(i)
            nums(i) = nums(j)
            nums(j) = tmp
          }
          nums
        }
      }

      val sudukoBoard = SudokuGenerator.generateBoard(/*add number here for emptyspaces or leave empty for random*/)

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

      //Connect4 Array
      var connect4Array:ListBuffer[ListBuffer[Int]] = ListBuffer(ListBuffer(),ListBuffer(),ListBuffer(),ListBuffer(),
                                                                 ListBuffer(),ListBuffer(),ListBuffer())   


      //XO Array
      var XoArray:Array[Array[Int]] = Array(
        Array(-1,-1,-1),
        Array(-1,-1,-1),
        Array(-1,-1,-1)
      )

      var checkersBoard:Array[Array[Int]] = Array(
        Array(-1,1,-1,1,-1,1,-1,1),
        Array(1,-1,1,-1,1,-1,1,-1),
        Array(-1,1,-1,1,-1,1,-1,1),
        Array(-1,-1,-1,-1,-1,-1,-1,-1),
        Array(-1,-1,-1,-1,-1,-1,-1,-1),
        Array(0,-1,0,-1,0,-1,0,-1),
        Array(-1,0,-1,0,-1,0,-1,0),
        Array(0,-1,0,-1,0,-1,0,-1)
      )                           


      //drawing the menu
      contents = new BoxPanel(Orientation.Vertical) {
        var j = 0; //to move Selector arrow

        def readImage(img:String):BufferedImage = {
          val file = new File("src/main/resources/MainMenu/"+img+".jpg")
          val image = ImageIO.read(file)
          image
        }
        override def paint(g: Graphics2D): Unit = {
          var myFont = new Font("Arial", 1, 16);
          g.setFont(myFont)
          g.setBackground(new Color(0xffffff))
          g.clearRect(0,0,700,600)
          g.setColor(new Color(0x000000))
          g.drawImage(readImage(Games(j)),20,20,660,300,null) //660*300 image

          var i = 0;
          Games.foreach(x => {
            g.drawString(x, 300, 340 + 35 * i)
            i += 1
          })
          
          g.drawString(">", 270, 340 + 35 * j)
        }
        //movment
        listenTo(keys)
        listenTo(mouse.clicks)

        reactions += {
          case MouseClicked(_, p, _, _, _) =>
            println(p)
            requestFocus()
          case KeyPressed(_, c, _, _) => c match {
            
            case Key.S =>{
              j+=1;
              if(j == gamesCnt)j=0;
              repaint()
            }

            case Key.W =>{
              j-=1;
              if(j < 0)j=gamesCnt-1;
              repaint()
            }

            case Key.Enter =>{
              input = Games(j);
              
              input match {
                case "Chess"    => abstractEngine(input,Chess_Drawer,Chess_Controller,chessBoard)
                //case "8Queens"  => abstractEngine(input,8,8,Queens_Drawer,Queens_Controller)
                case "Connect4" => abstractEngine(input,Connect4_Drawer,Connect4_Controller,connect4Array)
                case "XO"       => abstractEngine(input,XO_Drawer,XO_Controller,XoArray)
                case "Suduko"   => abstractEngine(input,Suduko_Drawer,Suduko_Controller,sudukoBoard)
                case "Checkers" => abstractEngine(input,Checkers_Drawer,Checkers_Controller,checkersBoard)
              }

              dispose()
            }
            requestFocus()
          }
        }
      }

      background = new Color(0xf2d16b)
      visible = true
      size = new Dimension(716, 600) //not 700 to make place for the border error pixels
      resizable = false
      centerOnScreen
    }
  }
}


/*For Debug*/
// println("OK")
// println(Math.abs(s2(1).asDigit-rows),s2(0)-'a')
// println(Math.abs(s1(1).asDigit-rows),s1(0)-'a')