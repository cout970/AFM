/**
 * Created by pedro on 26/04/17.
 */



window.addEventListener('resize',function () {
    document.getElementById('consola').innerHTML = getWindowHeight();
});

function getWindowHeight() {
    return window.innerHeight;
}
