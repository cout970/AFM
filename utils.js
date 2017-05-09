/**
 * Created by pedro on 26/04/17.
 */

const sys = require('util')
const fs = require('fs');
const exec = require('child_process').exec;

window.addEventListener('resize',function () {


    limpiarSujerecias();

    var sys = require('util')

    .exec;

    child = exec("./capture.zsh 'vim -'", function (error, stdout, stderr) {

        stdout.split("\n").forEach((elemento)=>{anhadirSujerencia(elemento)})
    });



})



var bufferTeclas  = ""
document.onkeypress = function(e) {
    e = e || window.event;
    var charCode = (typeof e.which == "number") ? e.which : e.keyCode;

    if(charCode == 13){
        var exec = require('child_process').exec;
        child = exec(bufferTeclas, function (error, stdout, stderr) {

        });
        bufferTeclas = "";
    }

    if(charCode == 8 || charCode == 46){
        bufferTeclas = bufferTeclas.substring(0, bufferTeclas.length()-1);
    }


    bufferTeclas = bufferTeclas + String.fromCharCode(charCode);

    limpiarSujerecias();
    var exec = require('child_process').exec;
    child = exec("./capture.zsh '"+ bufferTeclas + "'", function (error, stdout, stderr) {

        stdout.split("\n").forEach((elemento)=>{anhadirSujerencia(elemento)})
    });


    document.getElementById('consola').innerHTML = bufferTeclas;

};

function getWindowHeight() {
    return window.innerHeight;
}


function limpiarSujerecias() {
    document.getElementById("autocompletar").innerHTML = '';
}

function anhadirSujerencia(sujerencia) {

    var para = document.createElement("p");
    var node = document.createTextNode(sujerencia);
    para.appendChild(node);
    para.className = "sujerencia";

    var element = document.getElementById("autocompletar");
    element.appendChild(para);


}