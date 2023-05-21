


class Queens extends AbstractGameEngine {
    constructor(map, rows, columns) {

        super(map, rows, columns);
        this.spriteSheet = loadImage('/javascript/assets/chess_pieces.png');

    }

    Drawer(map) {
        super.Drawer();

        const canvasHeight = 700;
        const canvasWidth = 700;
        const offset = 60;
        const rows = 8;
        const columns = 8;
        const primaryColour = color(255, 255, 255, 255);
        const secondaryColour = color(60, 60, 60, 255);
        const tileWidth = (canvasWidth - offset) / columns;
        const tileHeight = (canvasHeight - offset) / rows;

        this.SquareBoardDrawer(rows, columns, primaryColour, secondaryColour, tileWidth, tileHeight, offset);
        this.DrawText(rows, columns, tileWidth, tileHeight, canvasWidth, canvasHeight, offset);
        this.PieceDrawer(map, tileWidth, tileHeight, offset);
    }

    PieceDrawer(map, tileWidth, tileHeight, offset) {

        const pieceWidth = 800 / 6;
        const pieceHeight = 267 / 2;


        for (let i = 0; i < map.length; i++) {
            for (let j = 0; j < map[i].length; j++) {
                if (map[i][j] != null) {
                    image(this.spriteSheet, j * tileWidth + offset / 2, i * tileHeight + offset / 2, tileWidth, tileHeight, map[i][j].x, map[i][j].y, pieceWidth, pieceHeight);
                }
            }
        }
    }

    Controller(map, input, player) {
        let pos = input.toLowerCase();
        let valid = true;
        // Validate parameters

        // validate first parameter
        valid &&= (pos.length == 2 && isAlpha(pos[0]) && isDigit(pos[1]) && orderAlpha(pos[0]) <= orderAlpha('h') && orderDigit(pos[1]) <= orderDigit('8') && orderDigit(pos[1]) >= orderDigit('1'));


        // Validate action
        // validate there is object to move
        let origin = map[8 - 1 - (orderDigit(pos[1]) - 1)][orderAlpha(pos[0])];
        if (origin != null) {///TO BE TESTED
            map[8 - 1 - (orderDigit(pos[1]) - 1)][orderAlpha(pos[0])] = null;
            return [true, map];
        }
        valid &&= origin == null;

        // validate the 4 directions H,V,45,135

        //  validate Horizontal and vertical
        for (let i = 0; i < 8; i++) {
            valid &&= map[8 - 1 - (orderDigit(pos[1]) - 1)][i] == null;
            valid &&= map[i][orderAlpha(pos[0])] == null;
        }

        //  validate 45 and 135
        let cof1 = Math.min(8 - 1 - (orderDigit(pos[1]) - 1), orderAlpha(pos[0]));
        let cof2 = Math.min((orderDigit(pos[1]) - 1), orderAlpha(pos[0]));

        for (let i = 0; i < 8; i++) {
            if (8 - 1 - (orderDigit(pos[1]) - 1) - cof1 + i < 8 && orderAlpha(pos[0]) - cof1 + i < 8)
                valid &&= map[8 - 1 - (orderDigit(pos[1]) - 1) - cof1 + i][orderAlpha(pos[0]) - cof1 + i] == null;

            if (8 - 1 - (orderDigit(pos[1]) - 1) + cof2 - i > 0 && orderAlpha(pos[0]) - cof2 + i < 8)
                valid &&= map[8 - 1 - (orderDigit(pos[1]) - 1) + cof2 - i][orderAlpha(pos[0]) - cof2 + i] == null;
        }

        // console.log("Not valid");


        if (!valid) return [false, map];
        // console.log("Valid");
        // Move
        // console.log('move')
        map[8 - 1 - (orderDigit(pos[1]) - 1)][orderAlpha(pos[0])] = new Queen(1, 1);
        // console.log('map',map);
        return [true, map];

    }

    async Solver(map) {
        let session = pl.create();
        console.log("solving...");
        let program = queens_program;
        /**
         * Is valid:
         * Is 8 queens?
         * * Is valid matrix? is_matrix(Mat) :-
         * * Has 8 queens?
         * every queen not attacked
         */
        function parseStringToArray(str) {
            // Remove the trailing string (if it is present)
            str = str.replace(/[\[\]]/g, "");


            str = str.split('|')[0];
            str = str.split(' = ')[1];

            // Return the 2D array
            return str.split(',');
        }
        
        // session.consult(program, {
        //     success: function () {
        //         // Query
        //         let goal = "is_nqueens(Ls,8).";
        //         session.query(goal, {
        //             success: function (goal) {
        //                 // Answers
        //                 console.log("my goaal:", goal);
        //                 session.answer({
        //                     success: function (answer) {
        //                         /* Answer */
        //                         console.log(session.format_answer(answer));

        //                         const arr1D = (parseStringToArray(session.format_answer(answer)));
        //                         const arr2D = [];

        //                         for (let i = 0; i < 8; i++) {
        //                             const row = [];
        //                             for (let j = 0; j < 8; j++) {
        //                                 row.push(arr1D[i * 8 + j]);
        //                             }
        //                             arr2D.push(row);
        //                         }
        //                         console.log(arr2D);
        //                         for (let i = 0; i < 8; i++) {
        //                             for (let j = 0; j < 8; j++) {
        //                                 if(arr2D[i][j]=='q'){console.log("queen"); map[i][j]=new Queen(1, 1);}
        //                             }

        //                         }
        //                         console.log("solution",map);

        //                     },
        //                     error: function (err) {
        //                         /* Uncaught error */
        //                         console.log("error", err);
        //                     },
        //                     fail: function () {
        //                         /* Fail */
        //                         console.log("fail");
        //                     },
        //                     limit: function () {
        //                         /* Limit exceeded */
        //                         console.log("limit");
        //                     },
        //                 });
        //             },
        //             error: function (err) {
        //                 /* Error parsing goal */
        //                 console.log("errorr")
        //             },
        //         });
        //     },
        //     error: function (err) {
        //         /* Error parsing program */
        //     },
        // });

        const goal = "is_nqueens(Ls,8),is_vertical_valid(Ls,0,0,1).";
        await session.promiseConsult(program);
        await session.promiseQuery(goal);

         {
            let answer = await session.promiseAnswer();
            console.log(session.format_answer(answer));
            const arr1D = (parseStringToArray(session.format_answer(answer)));
            const arr2D = [];

            for (let i = 0; i < 8; i++) {
                const row = [];
                for (let j = 0; j < 8; j++) {
                    row.push(arr1D[i * 8 + j]);
                }
                arr2D.push(row);
            }
            console.log(arr2D);
            for (let i = 0; i < 8; i++) {
                for (let j = 0; j < 8; j++) {
                    if (arr2D[i][j] == 'q') { console.log("queen"); map[i][j] = new Queen(1, 1); }
                }

            }
            console.log("solution", map);
        }
    }


}

