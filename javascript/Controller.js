class Controller {
    constructor(actions, rows, columns) {
        this.actions=actions;// actions that user can do with its validations

        this.rows = rows;
        this.columns = columns;

        this.drawerMap = [];
        for (let i = 0; i < this.rows; i++)
            this.drawerMap.push([]);
    
        for (let i = 0; i < this.rows; i++)
            for (let j = 0; j < this.columns; j++)
                this.drawerMap[i].push(null);

        this.player = 2;
    }

    run(action){

        this.player = (this.player % 2) + 1;

        for(let key in this.actions){
            if(parselyEqual(action,key)){
                let params=parse(action,key);
                this.actions[key](this.drawerMap,...params);
                break;
            }
        }

    }

}