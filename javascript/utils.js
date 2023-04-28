const parselyEqual=(input,format)=>{
    let j=0;
    for(let i=0;i<format.length;i++){
        if(i<format.length-1 && format[i]=='{' && format[i+1]=='}'){
            i++;
            while(j<input.length&&(i==format.length-1||input[j]!=format[i+1])){
                j++;
            }
        }else{
            if (input[j]!=format[i])return false;
            j++;
        }
    }
    return true;
}

const parse=(input,format)=>{
    let j=0;
    let params=[]
    if(!parselyEqual(input,format))return null;

    for(let i=0;i<format.length;i++){
        if(i<format.length-1 && format[i]=='{' && format[i+1]=='}'){
            i++;
            let param="";
            while(j<input.length&&(i==format.length-1||input[j]!=format[i+1])){
                param+=input[j];
                j++;
            }
            params.push(param);
        }else{
            if (input[j]!=format[i])return false;
            j++;
        }
    }
    return params;
}

const orderAlpha=(c)=>{

    return c.charCodeAt(0) - "a".charCodeAt(0);
}
const orderDigit=(c)=>{

    return c.charCodeAt(0) - "0".charCodeAt(0);
}
const isAlpha=(c)=>{
    return c.charCodeAt(0) >= "a".charCodeAt(0) &&c.charCodeAt(0) <= "z".charCodeAt(0);
}
const isDigit=(c)=>{
    return c.charCodeAt(0) >= "0".charCodeAt(0) &&c.charCodeAt(0) <= "9".charCodeAt(0);
}
///Test
// console.log(parse("r5-b6","r{}-b{}"));
// console.log(parse("r5-b6","{}"));