/**
 * Created by pedro on 9/05/17.
 */

const view = require("./AppViewHandler");
const fileSystem = require('fs');
const excutor = require('child_process');





module.exports = {

    initialize: function () {

        let column0 = fileSystem.readdirSync("/");
        let column1 = fileSystem.readdirSync("/"+column0[0]);
        let currentPath = "/"+column0[0]+"/"+column1[0];
        let column2 = [""];

        if( fileSystem.statSync(currentPath).isFile() ){
            column2 = ["file:///"+currentPath]
        }else{
            column2 = fileSystem.readdirSync(currentPath);
        }

        view.changeAppState(new view.AppState(column0,column1,column2,currentPath));
    },
    goFather: function () {

        let currentPath = view.currentAppState.currentPath
            .split("/")
            .filter( (e,index)=> index < view.currentAppState.currentPath.length-1)
            .join("/");

        let column0 = view.currentAppState.column1;
        let column1 = view.currentAppState.column2;

        console.log(currentPath.split("/").filter((e,index)=> index < currentPath.length).join("/") -1);
        let column2 = fileSystem.readdirSync(
                currentPath.split("/").filter((e,index)=> index <= currentPath.length -1).join("/")
            );

        view.changeAppState(new view.AppState(column0,column1,column2,currentPath))



    },
    goSon: function () {

        let column0 = view.currentAppState.column1;
        let column1 = view.currentAppState.column2;
        let currentPath = view.currentAppState.currentPath+"/"+column1[0];
        let column2 = [""];

        if( fileSystem.statSync(currentPath).isFile() ){
            column2 = ["file:///"+currentPath]
        }else{
            column2 = fileSystem.readdirSync(currentPath);
        }
        view.changeAppState(new view.AppState(column0,column1,column2,currentPath));
    },

    goUp: function () {



    },
    goDown: function () {




    }

};