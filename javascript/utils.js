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