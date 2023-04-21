class Controller {
    drawer=null
    constructor(actions) {
        this.actions=actions;// actions that user can do with its validations
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