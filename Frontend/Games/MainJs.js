const lose_tag = document.getElementById("lose");
const win_tag = document.getElementById("win");
const user_tag = document.getElementById("user_tag");

async function getScore(gamename){
    const response = await fetch(`http://localhost:8080/${gamename}/stats`);
    const data = await response.json();
    return data;
}

function returnn(){
    window.location.href = "../../index.html";
}

function wait(seconds) {
    return new Promise(resolve => setTimeout(resolve, seconds * 1000));
}