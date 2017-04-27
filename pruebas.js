
const testFolder = '/home/pedro';
const fs = require('fs');


fs.readdir(testFolder, (err, files) => {
    files.forEach(file => {
    console.log(file);
});
})


// http://nodejs.org/api.html#_child_processes

var sys = require('util')

var exec = require('child_process').exec;

var child;


child = exec("./capture.zsh 'vim -'", function (error, stdout, stderr) {

    console.log('stdout: ' + stdout);

    console.log('stderr: ' + stderr);

    if (error !== null) {

        console.log('exec error: ' + error);

    }

});

