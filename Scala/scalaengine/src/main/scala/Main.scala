import scala.swing._
import scala.swing.event._
import scala.util.control.Breaks._
import scala.collection.mutable._
import java.awt.{BasicStroke, Color, Graphics2D}
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File
import scala.util.Random
import scala.util.control.Breaks
import org.jpl7._
import scala.language.postfixOps

object GameEngine {

  def AbstractDrawer (bgColor: Color,rows: Int,cols: Int,color1: Color,color2: Color,shape: String,g: Graphics2D) = {


    val width = 700
    val height = 600

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

            g.drawLine(x + tileSize,75,x + tileSize,75 + (tileSize * rows))
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

  /*Check if a given coordinates is in the board or out*/
  def InBoard(input: String,rows:Int,cols:Int):Boolean = {

    if(input.size != 2)return false
    
    val Pos:(Int, Int) = (input(1).asDigit,input(0)-'a'+1)
    if(Pos._1 <= 0 || Pos._1 > rows )return false
    if(Pos._2 <= 0 || Pos._2 > cols )return false

    return true
  }

  def abstractEngine[T](
    gameName:String,
    Drawer: (Seq[Array[Int]]) => MainFrame,
    Controller: (Seq[Array[Int]],Array[String],Int) => Boolean,
    state:Seq[Array[Int]]
  ): Unit = 
  {

      def stringfy(arg: Seq[Array[Int]], N: Int): String = {

        var out: String = "[";
        for (i <- 0 to N - 1) {
          out += "["
          for (j <- 0 to N - 1) {
            if (arg(i)(j) == 0) {
              out += "_";
            } else {
              if (arg(i)(j) >= 10)
                out += (arg(i)(j) / 10).toString();
              else
                out += arg(i)(j).toString();
            }

            if (j != N - 1) {
              out += ",";
            }
          }
          out += "]"
          if (i != N - 1) {
            out += ",";
          }
        }
        out += "]"
        return out;
      }
      def toQArray(arg: Seq[Array[Int]], N: Int): String = {
        var out: String = "[";
        for (i <- 0 to N - 1) {
          breakable{
          for (j <- 0 to N - 1){
            if (arg(i)(j) != 0) {
              out += (j+1).toString();
              break
            } else if (j==N-1){
              out += "_";
            }

          }

          }
          if (i != N - 1) {
            out += ",";
          }
        }
        out += "]"
        System.out.println(out);
        return out;
      }

      def parseStringToArray(input: String): Seq[Array[Int]] = {
        val rowPattern = "\\[(.*?)\\]".r
        val numberPattern = "\\d+".r

        val rows = rowPattern.findAllIn(input).toArray
        val array2D = Array.ofDim[Int](rows.length, rows.length)

        for ((rowString, rowIndex) <- rows.zipWithIndex) {
          val numbers = numberPattern.findAllIn(rowString).toArray
          for ((numString, colIndex) <- numbers.zipWithIndex) {
            array2D(rowIndex)(colIndex) = numString.toInt
          }
        }

        array2D
      }
      def parseStringToQArray(input: String):Array[Int] = {
        var out=input.split("[^-\\d]+").tail;
        var outInt=out.map(x=>x.toInt);
        System.out.println(outInt);
        // val array2D = Array.ofDim[Int](rows.length, rows.length)

        // for ((rowString, rowIndex) <- rows.zipWithIndex) {
        //   val numbers = numberPattern.findAllIn(rowString).toArray
        //   for ((numString, colIndex) <- numbers.zipWithIndex) {
        //     array2D(rowIndex)(colIndex) = numString.toInt
        //   }
        // }

        outInt
      }
      def parseQArrayToState(input: Array[Int]): Seq[Array[Int]] = {
        
        val array2D = Array.ofDim[Int](input.length, input.length)

        for(i<-0 until input.length){
          for(j<-0 until input.length){
             if(j+1==input(i)){
              array2D(i)(j)=1;
             }else{
              array2D(i)(j)=0;

             }
          }
        
        }
        System.out.println("Solution");
        for(i<-0 until input.length){
          for(j<-0 until input.length){
             if(j+1==input(i)){
              array2D(i)(j)=1;
             }else{
              array2D(i)(j)=0;

             }
        System.out.print(array2D(i)(j));
        System.out.print(",");
          }
          System.out.println();
        }

        array2D
      }
      def copyInto(map1:Seq[Array[Int]],map2:Seq[Array[Int]])={
  for(i<-0 until map1.length){
          for(j<-0 until map1.length){
             map1(i)(j)=map2(i)(j);
          }
        }
}

      def prolSuduko(board: Seq[Array[Int]]): Seq[Array[Int]] = {
        val q1 = new Query("consult('C:/Users/acer/Documents/GitHub/Game-Engine/Scala/scalaengine/src/main/resources/Prolog/suduko.pl')")
        System.out.println("consult "+ (if(q1.hasSolution) "succeed" else "failed")) 
        val str = stringfy(board, 9)
        //def stringfy(arg: Seq[Array[Int]], N: Int): String = {}
        var qs =""
        
        val q = new Query( "Rows = " + str + ",sudoku(Rows),maplist(label, Rows),maplist(portray_clause, Rows).")
        qs = q.oneSolution().toString()          
             
        parseStringToArray(qs)
        //def parseStringToArray(input: String): Array[Array[Int]] = {}
      }

      def prolQueens(board: Seq[Array[Int]]): Seq[Array[Int]] = {
        val q1 = new Query("consult('C:/Users/acer/Documents/GitHub/Game-Engine/Scala/scalaengine/src/main/resources/Prolog/queens2.pl')")
        System.out.println("consult "+ (if(q1.hasSolution) "succeed" else "failed")) 
        val str = toQArray(board, 8)
        //def stringfy(arg: Seq[Array[Int]], N: Int): String = {}
        var qs =""
        System.out.println("State");
        for(i<-0 until board.length){
          for(j<-0 until board.length){
             
        System.out.print(board(i)(j));
        System.out.print(",");
          }
          System.out.println();
        }
        val q = new Query( "Rows = " + str + ",queens(Rows),label(Rows),maplist(portray_clause, Rows).")
        qs = q.oneSolution().toString()
        System.out.println(qs);   
        parseQArrayToState(parseStringToQArray(qs));    
        // board


        //def parseStringToArray(input: String): Array[Array[Int]] = {}
      }

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

            var str: String = gameName match{
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
              case "Chess"|"Checkers" => "Black's  Turn"
              case "XO" | "Connect4" => "Red Player's Turn"
              case "Suduko"|"8Queens" =>""
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
              case "Chess"|"Suduko"|"Checkers" => {
                contents += inputField2
              }
              case "8Queens"|"XO"|"Connect4" =>{
                contents += Swing.HStrut(100)
              }
            }

            contents += Swing.HStrut(50)
            contents += turnlabel

            gameName match {
              case "Chess"|"XO"|"Connect4"|"Checkers" => {
                contents += Swing.HStrut(100)
              }
              case "Suduko" =>{
                contents += Button("Solve"){

                  val solved = prolSuduko(state)
                  copyInto(state,solved);
                  if(oldframe != null)oldframe.dispose()
                  oldframe = Drawer(state);
                }
              }
              case "8Queens" =>{
                contents += Button("Solve"){

                  val solved = prolQueens(state)
                  copyInto(state,solved);
                  if(oldframe != null)oldframe.dispose()
                  oldframe = Drawer(state);
                }
              }
            }



            var turn = 0
            //input handeling
            var oldframe:MainFrame = Drawer(state)
            contents += Button("Do Action!!") { 
                var s1 = inputField1.text
                var s2 = inputField2.text
                var input:Array[String] = Array(s1,s2)
                println(input(0),input(1)) 

                if(Controller(state,input,turn)){
                  turnlabel.foreground = new Color(0x013220)

                  turn+=1
                  turn = turn%2  

                  if(turn%2 == 0)
                  {
                    turnlabel.text = gameName match{
                      case "Chess"|"Checkers" => "Black's  Turn"
                      case "XO" | "Connect4" => "Red Player's Turn"
                      case "Suduko"|"8Queens" => ""
                    }
                  } 
                  else 
                  {
                    turnlabel.text = gameName match{
                      case "Chess"|"Checkers" => "White's  Turn"
                      case "XO" | "Connect4" => "Yellow Player's Turn"
                      case "Suduko"|"8Queens" => ""
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

  def abstractEngine_no_solver[T](
    gameName:String,
    Drawer: (Seq[T]) => MainFrame,
    Controller: (Seq[T],Array[String],Int) => Boolean,
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

            var str: String = gameName match{
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
              case "Chess"|"Checkers" => "Black's  Turn"
              case "XO" | "Connect4" => "Red Player's Turn"
              case "Suduko"|"8Queens" =>""
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
              case "Chess"|"Suduko"|"Checkers" => {
                contents += inputField2
              }
              case "8Queens"|"XO"|"Connect4" =>{
                contents += Swing.HStrut(100)
              }
            }

            contents += Swing.HStrut(50)
            contents += turnlabel

            gameName match {
              case "Chess"|"XO"|"Connect4"|"Checkers" => {
                contents += Swing.HStrut(100)
              }
            }



            var turn = 0
            //input handeling
            var oldframe:MainFrame = Drawer(state)
            contents += Button("Do Action!!") { 
                var s1 = inputField1.text
                var s2 = inputField2.text
                var input:Array[String] = Array(s1,s2)
                println(input(0),input(1)) 

                if(Controller(state,input,turn)){
                  turnlabel.foreground = new Color(0x013220)

                  turn+=1
                  turn = turn%2  

                  if(turn%2 == 0)
                  {
                    turnlabel.text = gameName match{
                      case "Chess"|"Checkers" => "Black's  Turn"
                      case "XO" | "Connect4" => "Red Player's Turn"
                      case "Suduko"|"8Queens" => ""
                    }
                  } 
                  else 
                  {
                    turnlabel.text = gameName match{
                      case "Chess"|"Checkers" => "White's  Turn"
                      case "XO" | "Connect4" => "Yellow Player's Turn"
                      case "Suduko"|"8Queens" => ""
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
    val Chess_Drawer = (Board:Seq[Array[(Int, String)]]) =>{
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
                case _ =>
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
              if (Board(row)(col) != -1){
              val n = Board(row)(col).toString()
              g.drawImage(readImage("Checkers/"+n),x,y,50,50,null)
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

    //8 Queens Drawer
    val Queens_Drawer = (Board:Seq[Array[Int]]) => {
      var rows = 8
      var cols = 8
      var frame = new MainFrame (){

        class Canvas extends Component{
          override def paint(g: Graphics2D): Unit = {
            AbstractDrawer(Color.DARK_GRAY, rows, cols, new Color(0xC2B280), Color.WHITE, "square",g)

            for {
              row <- 0 until rows
              col <- 0 until cols
              } {
              val x = (col * 57) + 75
              val y = (row * 57) + 75
              if(Board(row)(col) == 1) g.drawImage(readImage("Queens/Queen"),x,y,50,50,null)
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

    val Chess_Controller = (chessBoard:Seq[Array[(Int, String)]], input:Array[String], turn:Int /*0->Black,1->white*/) => {

      var rows = 8
      var cols = 8
      var ret = true

      //Check if Start and end Position are in Board*/
      if(!InBoard(input(0),rows,cols) || !InBoard(input(1),rows,cols) )ret = false

      if(ret){
        /*tuples containing position of peice in the Chess array (row,column)*/
        var start:(Int, Int) = (Math.abs(input(0)(1).asDigit-rows),input(0)(0)-'a')
        var end  :(Int, Int) = (Math.abs(input(1)(1).asDigit-rows),input(1)(0)-'a')
        
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
        
        if(valid){
          chessBoard(Math.abs(end._1))(end._2) = chessBoard(start._1)(start._2)
          chessBoard(start._1)(start._2) = null
          
          ret = true
        }
        else
          ret = false
      }
      
      ret

    }: Boolean

    //Connect4 Controller
    val Connect4_Controller = (connect4Array:Seq[ListBuffer[Int]],input: Array[String],Turn:Int) => {
      var rows = 6
      var cols = 7
      var loc = 10
      var ret = false

      if(input(0).size != 0)loc = input(0)(0) - 'a'
      if(loc >= 0 && loc <= 6)ret = true
      
      if(ret && connect4Array(loc).size < 6)connect4Array(loc)+=Turn
      else ret = false

      ret
    }: Boolean

    //Xo Controller
    val XO_Controller = (XOArray:Seq[Array[Int]],input: Array[String],Turn:Int) => {
      var ret = true
      var rows = 3
      var cols = 3
      if(!InBoard(input(0),rows,cols))ret = false

      if(ret){
        var pos:(Int, Int) = (Math.abs(input(0)(1).asDigit-rows),input(0)(0)-'a')
        if(XOArray(pos._1)(pos._2) == -1)XOArray(pos._1)(pos._2) = Turn
        else ret = false
      }

      ret
    }: Boolean

    //Suduko Controller
    val Suduko_Controller = (sudukoBoard:Seq[Array[Int]],input: Array[String],Turn:Int) => {
      var rows = 9
      var cols = 9

      var ret = true;
      if(!InBoard(input(0),rows,cols) || input(1).size != 1)ret = false

      if(ret){
        val col = Character.getNumericValue(input(0).charAt(0)) - 10
        val row = 9 - (Character.digit(input(0).charAt(1), 10))
        var value = Character.digit(input(1).charAt(0), 10)

        if(sudukoBoard(row)(col) > 9)ret = false
        else sudukoBoard(row)(col) = value
      }

      ret
    }: Boolean

    //Checkers Controller
    val Checkers_Controller = (checkersBoard:Seq[Array[Int]],input: Array[String],turn:Int) => {
      var rows = 8
      var cols = 8
      var ret = true

      if(!InBoard(input(0),rows,cols) || !InBoard(input(1),rows,cols)) ret = false

      if(ret){
        /*tuples containing position of peice in the Checkers array (row,column)*/
        var start:(Int, Int) = (Math.abs(input(0)(1).asDigit-rows),input(0)(0)-'a')
        var end  :(Int, Int) = (Math.abs(input(1)(1).asDigit-rows),input(1)(0)-'a')

        //checks if the first peice is valid
        if(checkersBoard(start._1)(start._2) == -1) ret = false

        /*if peice at start position is black and it's white turn then return false*/
        if( checkersBoard(start._1)(start._2)%2 == 0  && turn == 0) ret = false

        /*if peice at start position is white and it's Black white turn then return false*/
        if( checkersBoard(start._1)(start._2)%2 == 1  && turn == 1) ret = false

        var deltaX = end._2 - start._2 
        var deltaY = end._1 - start._1
        println(deltaX)
        println(deltaY)
        //checks for diagonal move
        if (Math.abs(deltaY) != Math.abs(deltaX)) ret = false
        var step = Math.abs(deltaY)
        println(step)

        //eating and double jump
        if (step != 1){        
          //assuming we can jump over our own peices if it's only 1
          if (step == 2 && checkersBoard(start._1 + deltaY/2)(start._2 + deltaX/2) != -1){
            if ( (turn == 0 && checkersBoard(start._1 + deltaY/2)(start._2 + deltaX/2)%2 == 0) ||
            ( turn == 1 && checkersBoard(start._1 + deltaY/2)(start._2 + deltaX/2)%2 == 1)){
              checkersBoard(start._1 + deltaY/2)(start._2 + deltaX/2) = -1
              ret = true
            } 
            else ret = false
          }
          //can't jump multiple times on his own peice
          else if (step == 4 && checkersBoard(start._1 + deltaY/4)(start._2 + deltaX/4) != -1 && checkersBoard(start._1 + 3*deltaY/4)(start._2 + 3*deltaX/4) != -1){
            if ( turn == 0 && checkersBoard(start._1 + deltaY/4)(start._2 + deltaX/4)%2 == 0 && checkersBoard(start._1 + 3*deltaY/4)(start._2 + 3*deltaX/4)%2 == 0){
              
              checkersBoard(start._1 + deltaY/4)(start._2 + deltaX/4) = -1
              checkersBoard(start._1 + 3* deltaY/4)(start._2 + 3* deltaX/4) = -1
              ret = true
            } 
            if ( turn == 1 && checkersBoard(start._1 + deltaY/4)(start._2 + deltaX/4)%2 == 1 && checkersBoard(start._1 + 3*deltaY/4)(start._2 + 3*deltaX/4)%2 == 1){
              
              checkersBoard(start._1 + deltaY/4)(start._2 + deltaX/4) = -1
              checkersBoard(start._1 + 3* deltaY/4)(start._2 + 3* deltaX/4) = -1
              ret = true
            } 
            else ret = false
          }
          else ret = false
        }

        //0 white 1 black
        //checks for directions up or down for normal troops
        if ( checkersBoard(start._1)(start._2) == 0 )
          if (deltaY > 0) ret = false

        if ( checkersBoard(start._1)(start._2) == 1 )
          if (deltaY < 0) ret = false


        if ( turn ==0 && end._1==7 ) {
          checkersBoard(start._1)(start._2) = 3 //black king
          println("black king")
          ret = true
        }

        if ( turn ==1 && end._1==0) {
          checkersBoard(start._1)(start._2) = 2 //white king
          println("white king")
          ret = true
        } 
        
        /*checks if end place is empty*/
        if(checkersBoard(end._1)(end._2) != -1) ret = false

        if(ret) {
          checkersBoard(end._1)(end._2) = checkersBoard(start._1)(start._2)
          checkersBoard(start._1)(start._2) = -1
          ret = true
        }
        else ret = false
      }
      
      ret

    }: Boolean

     //Queens Controller
    val Queens_Controller = (Board:Seq[Array[Int]],input: Array[String],Turn:Int) => {
      var rows = 8
      var cols = 8
      var ret = true

      if(!InBoard(input(0),rows,cols))ret = false

      if(ret){
        /*tuples containing position of peice in the Chess array (row,column)*/
        var start:(Int, Int) = (Math.abs(input(0)(1).asDigit-rows),input(0)(0)-'a')
        var queensCnt = Board.flatten.filter(x => x==1).size
    
        if(Board(start._1)(start._2) == 0){
          if(queensCnt == 8){
            ret = false
          }
          else{
            var acc = true
            var dir:Array[(Int, Int)] = Array((-1,-1),(-1,1),(1,-1),(1,1),
                                                   (0,1),(1,0),(-1,0),(0,-1))

            val loop = new Breaks;

            loop.breakable {
              for(tup <- dir){
                var i = start._1
                var j = start._2
                i+=tup._1;j+=tup._2

                while(i>=0 && i<rows && j>=0 && j<cols){

                  if(Board(i)(j) == 1)acc = false
                  i+=tup._1;j+=tup._2
                }

                if(acc == false)loop.break()
              }    
            }
                      
            if(acc) Board(start._1)(start._2) = 1
            else ret = false
          }
        }
        else{
          Board(start._1)(start._2) = 0
        }
      }
      
      ret
    }: Boolean

    //start menu
    new MainFrame {
      title = "Game Engine"

      var input = "Connect4" //dummy
      
      val Games: List[String] = List("Chess", "Connect4", "XO", "Checkers", "Suduko", "8Queens")

      val gamesCnt = Games.size

      def generateBoard(emptySpaces: Int = Random.between(60, 70)): Array[Array[Int]] = {

        def randomPermutation(): Array[Int] = {
          val nums = Array.range(1, 10)
          for (i <- nums.length - 1 to 1 by -1) {
            val j = Random.nextInt(i + 1)
            val tmp = nums(i)
            nums(i) = nums(j)
            nums(j) = tmp
          }
          nums
        }
        
        def randomlyRemoveNumbers(board: Array[Array[Int]], emptySpaces: Int): Unit = {
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

        def isValid(board: Array[Array[Int]], row: Int, col: Int, num: Int): Boolean = {
          val boxRowStart = 3 * (row / 3)
          val boxColStart = 3 * (col / 3)
          for (i <- 0 until 9) {
            if (board(row)(i) == num || board(i)(col) == num || board(boxRowStart + i / 3)(boxColStart + i % 3) == num) {
              return false
            }
          }
          true
        }

        def fillBoard(board: Array[Array[Int]], row: Int, col: Int): Boolean = {
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

      var sudukoBoard = generateBoard(/*add number here for emptyspaces or leave empty for random*/)

      //Chess Array
      var chessImageArrayW = Array((1,"RookWhite"),(2,"KnightWhite"),(3,"BishopWhite"),(4,"QueenWhite"),
                                  (5,"KingWhite"),(3,"BishopWhite"),(2,"KnightWhite"),(1,"RookWhite"))
      var chessImageArrayB = Array((7,"RookBlack"),(8,"KnightBlack"),(9,"BishopBlack"),(10,"QueenBlack"),
                                  (11,"KingBlack"),(9,"BishopBlack"),(8,"KnightBlack"),(7,"RookBlack"))


      var chessBoard = Array.ofDim[(Int, String)](8,8)
      chessBoard(0) = chessImageArrayW
      chessBoard(1) = Array.fill[(Int, String)](8)(6,"PawnWhite")
      chessBoard(6) = Array.fill[(Int, String)](8)(12,"PawnBlack")
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
      //initial board
      
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

      var Queens = Array.ofDim[Int](8,8)  
/*
      def stringfy(arg: Array[Array[Int]], N: Int): String = {
        var out: String = "[";
        for (i <- 0 to N - 1) {
          out += "["
          for (j <- 0 to N - 1) {
            if (arg(i)(j) == 0) {
              out += "_";
            } else {
              if (arg(i)(j) >= 10)
                out += (arg(i)(j) / 10).toString();
              else
                out += arg(i)(j).toString();
            }

            if (j != N - 1) {
              out += ",";
            }
          }
          out += "]"
          if (i != N - 1) {
            out += ",";
          }
        }
        out += "]"
        return out;
      }

      def parseStringToArray(input: String): Array[Array[Int]] = {
        val rowPattern = "\\[(.*?)\\]".r
        val numberPattern = "\\d+".r

        val rows = rowPattern.findAllIn(input).toArray
        val array2D = Array.ofDim[Int](rows.length, rows.length)

        for ((rowString, rowIndex) <- rows.zipWithIndex) {
          val numbers = numberPattern.findAllIn(rowString).toArray
          for ((numString, colIndex) <- numbers.zipWithIndex) {
            array2D(rowIndex)(colIndex) = numString.toInt
          }
        }

        array2D
      }

      def prolSuduko(board: Array[Array[Int]]): Unit = {
        val q1 = new Query("consult('E:/DK files/21011054/4th semester/paradigms/para legit/Game-Engine/Scala/scalaengine/src/main/resources/Prolog/suduko.pl')")
        System.out.println("consult "+ (if(q1.hasSolution) "succeed" else "failed")) 
        val str = stringfy(board, 9)
        try {
          val q = new Query( "Rows = " + str + ",sudoku(Rows),maplist(label, Rows),maplist(portray_clause, Rows).")
          val qs = q.oneSolution().toString()
          var newboard = parseStringToArray(qs)
          sudukoBoard = newboard
        }
        catch ( Throwable => println("no sol") )        
      }

      def prolQueens(): Unit = {
        val q1 = new Query("consult('E:/DK files/21011054/4th semester/paradigms/para legit/Game-Engine/Scala/scalaengine/src/main/resources/Prolog/8queens.pl')")
        System.out.println("consult "+ (if(q1.hasSolution) "succeed" else "failed")) 
        val str = "Rows = [[_,_,1,_,_,_,_,_]," +
                          "[_,_,_,_,_,_,_,_]," +
                          "[_,_,_,_,_,_,_,_]," +
                          "[_,_,_,_,_,_,_,_]," +
                          "[_,_,_,_,_,_,_,_]," +
                          "[_,_,_,_,_,_,_,_]," +
                          "[_,_,_,_,_,_,_,_]," +
                          "[_,_,_,1,_,_,_,_]],"
        try {
          val q = new Query( str + "queens(Rows),maplist(label, Rows),maplist(portray_clause, Rows).")
          val qs = q.oneSolution()
          println(qs.toString())
        }
        catch ( 
          Throwable => println("no sol") 
          )
        println(",,,,,,,,,,,,,,,,,,,,,,,,,,")      
      }*/

      //prolQueens()
      //prolSuduko(sudukoBoard) 

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
              j+=1
              if(j == gamesCnt)j=0
              repaint()
            }

            case Key.W =>{
              j-=1;
              if(j < 0)j=gamesCnt-1;
              repaint()
            }

            case Key.Enter =>{
              input = Games(j)

              input match {
                case "Chess"    => abstractEngine_no_solver(input,Chess_Drawer,Chess_Controller,chessBoard)
                case "8Queens"  => abstractEngine(input,Queens_Drawer,Queens_Controller,Queens)
                case "Connect4" => abstractEngine_no_solver(input,Connect4_Drawer,Connect4_Controller,connect4Array)
                case "XO"       => abstractEngine(input,XO_Drawer,XO_Controller,XoArray)
                case "Suduko"   => abstractEngine(input,Suduko_Drawer,Suduko_Controller,sudukoBoard)
                case "Checkers" => abstractEngine(input,Checkers_Drawer,Checkers_Controller,checkersBoard)
              }

              dispose()
            }
            case _ => {}

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






