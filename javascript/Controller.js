class Controller {
    drawerMap=null
    constructor(drawerMap,actions) {
        this.actions=actions;// actions that user can do with its validations
        this.drawerMap=drawerMap;
    }

    run(action){
        for(let key in this.actions){
            if(parselyEqual(action,key)){
                let params=parse(action,key);
                this.actions[key](this,...params);
                break;
            }
        }

    }

}