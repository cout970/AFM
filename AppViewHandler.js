/**
 * Created by pedro on 9/05/17.
 */


function AppState( column0, column1, column2, currentPath ){
  this.column0= column0;
  this.column1= column1;
  this.column2= column2;
  this.currentPath = currentPath
}

let currentAppState = new AppState([""],[""],[""],"");

function changeAppState( appState) {


    module.exports.currentAppState = appState;

    document.getElementById('consola').innerText = appState.currentPath;

    let area0 = document.getElementById("area0");
    area0.innerHTML = "";
    appState.column0.forEach( (fileSystemEntry) => area0.innerHTML += "<p class='archivo'>"+fileSystemEntry+"</p>");

    let area1 = document.getElementById("area1");
    area1.innerHTML = "";
    appState.column1.forEach( (fileSystemEntry) => area1.innerHTML += "<p class='archivo'>"+fileSystemEntry+"</p>");

    let area2 = document.getElementById("area2");
    area2.innerHTML = "";

    if(appState.column2[0].startsWith("file:///"))
       area2.innerHTML = '<webview class="preview" src="'+appState.column2[0]+ '"><webview>';
    else
        appState.column2.forEach( (fileSystemEntry) => area2.innerHTML += "<p class='archivo'>"+fileSystemEntry+"</p>");
}


const controller = require('./Controller');

window.onload =  ()=>controller.initialize();

comands = [
    ["left", controller.goFather],
    ["right", controller.goSon],
    ["down", controller.goDown],
    ["up", controller.goUp],
];

Mousetrap = require('mousetrap');
comands.forEach(
    (entry) => {
        Mousetrap.bind(entry[0],entry[1])
    }
);

module.exports.changeAppState = changeAppState;
module.exports.AppState = AppState;
module.exports.currentAppState = currentAppState;