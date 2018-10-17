// A2: Connect Four
const monthsLong = ["January","February","March","April","May","June","July",
    "August","September","October","November","December"];
const monthsShort = ["Jan.","Feb.","Mar.","Apr.","May","June","July","Aug.","Sept.",
    "Oct.","Nov.","Dec."];
const re = /^(0?[1-9]|1[0-2]|January|Jan.|February|Feb.|March|Mar.|April|Apr.|May|June|July|August|Aug.|September|Sept.|October|Oct.|November|Nov.|December|Dec.)(-| |\/)(0?[1-9]|[12][0-9]|3[01]),?\2((?:20|19|18)?[\d][\d])$/;
// count remaining tokens
var counterP1 = 21;
var counterP2 = 21;
var leftToWin;
var rToken = "<img src='images/red-circle.png' alt='red-token'>";
var bToken = "<img src='images/black-circle.png' alt='black-token'>";
var gameTime = 0;
var end = false;
var tempNameP1 = "p1";
var tempNameP2 = "p2";
var minWin = new Array();
minWin.push(3);

// Player 1
var nameP1 = prompt("What's your name as Player 1?", "");
var dateInput = prompt("And what's your birthday, " + nameP1 + "?", "");
dateInput = dateInput.match(re);
inputCheck();
var month = dateInput[1];
// if month input is a number
if (month.match(/\d\d/)) {
    month = parseInt(month) - 1;
}
// if month input is a string, eg. "April"
else {
    monthsLong.forEach(element => {
        if (month == element) {
            month = monthsLong.indexOf(element);
            return;
        }
    });
    monthsShort.forEach(element => {
        if (month == element) {
            month = monthsShort.indexOf(element);
            return;
        }
    });
}
var day = parseInt(dateInput[3]);
var year = parseInt(dateInput[4]);
var bdayP1 = new Date(year, month, day);

// Player 2
var nameP2 = prompt("What's your name as Player 2?", "");
dateInput = prompt("And what's your birthday, " + nameP2 + "?", "");
dateInput = dateInput.match(re);
inputCheck();
month = dateInput[1];
if (month.match(/\d\d/)) {
    month = parseInt(month) - 1;
}
else {
    monthsLong.forEach(element => {
        if (month == element) {
            month = monthsLong.indexOf(element);
            return;
        }
    });
    monthsShort.forEach(element => {
        if (month == element) {
            month = monthsShort.indexOf(element);
            return;
        }
    });
}
day = parseInt(dateInput[3]);
year = parseInt(dateInput[4]);
var bdayP2 = new Date(year, month, day);

// Initialize the game interface
var roundPlayer = tempNameP1;
var roundPlayerName = nameP1;
alert(roundPlayerName + "'s round, click OK to start the round");
document.getElementById("usage").textContent = "- Click a column number to place a token -";
// Start the timer
timerOn();
document.getElementById("countP1").textContent = nameP1 + "'s tokens left: " + counterP1;
document.getElementById("countP2").textContent = nameP2 + "'s tokens left: " + counterP2;
// render the grid
var grids = document.querySelector('#grids');
var table = document.createElement("table");
table.setAttribute("id", "table");
grids.appendChild(table);
var columnControl = document.createElement("tr");
for(var j = 0; j < 7; j++) {
    var column = document .createElement("th");
    column.index = j+1;
    if (j == 0) column.innerHTML = "A";
    else if (j == 1) column.innerHTML = "B";
    else if (j == 2) column.innerHTML = "C";
    else if (j == 3) column.innerHTML = "D";
    else if (j == 4) column.innerHTML = "E";
    else if (j == 5) column.innerHTML = "F";
    else if (j == 6) column.innerHTML = "G";
    // last token's row number
    column.last = 6;
    column.addEventListener("click", function(e) {
        if (roundPlayer == tempNameP1) {
            if (counterP2 == 0) {
                alert("Tie!");
                // No winner, thus not store the game record
                showList();
            }
            if (e.currentTarget.last != 0) {
                document.querySelector("#table").rows[e.currentTarget.last].cells[e.currentTarget.index - 1].innerHTML = rToken;
                document.querySelector("#table").rows[e.currentTarget.last].cells[e.currentTarget.index - 1].tokenColor = "red";
                // decrement the remaining token number
                counterP1--;
                // the top row number of that column
                e.currentTarget.last--;
                endRound();
            }
            else
                alert("This column is full, please choose another column");
        }
        else {
            if (e.currentTarget.last != 0) {
                document.querySelector("#table").rows[e.currentTarget.last].cells[e.currentTarget.index - 1].innerHTML = bToken;
                document.querySelector("#table").rows[e.currentTarget.last].cells[e.currentTarget.index - 1].tokenColor = "black";
                counterP2--;
                e.currentTarget.last--;
                endRound();
            }
            else
                alert("This column is full, please choose another column");
        }
    })
    columnControl.appendChild(column);
    document.querySelector("#table").appendChild(columnControl);
}
for (var i = 0; i < 6; i++) {
    var row = document.createElement("tr");
    for (var m = 0; m < 7; m++) {
        var cell = document.createElement("td");
        cell.innerHTML = "<img src = 'images/white-circle.png' alt='white-token'>";
        cell.tokenColor = "white";
        row.appendChild(cell);
    }
    document.querySelector("#table").appendChild(row);
}

// Show info after a round
function endRound() {
    winningCheck();
    if (leftToWin == 0) {
        setTimeout(function () {
            alert(roundPlayerName + " makes a Connect Four in " + gameTime + " sec!");
        }, 100);
        end = true;
        storeRecord();
        showList();
        return;
    }
    else {
        setTimeout(function () {
            alert(roundPlayerName + " needs " + leftToWin + " tokens to win!");
            switchPlayer();
            alert("Next is " + roundPlayerName + "'s round, click OK to start the round");
        }, 200);
    }
    document.getElementById("countP1").textContent = nameP1 + " tokens left: " + counterP1;
    document.getElementById("countP2").textContent = nameP2 + " tokens left: " + counterP2;
}

function switchPlayer() {
    if (roundPlayer == tempNameP1){
        roundPlayer = tempNameP2;
        roundPlayerName = nameP2;
    }
    else{
        roundPlayer = tempNameP1;
        roundPlayerName = nameP1;
    }
}

function winningCheck() {
    var tempCount = 3;
    var curColor;
    if (roundPlayer == tempNameP1)
        curColor = "red";
    else
        curColor = "black";
    // column check
    for (var c1 = 0; c1 < 7; c1++) {
        // start with the bottom
        for (var r1 = 6; r1 > 1; r1--) {
            if (document.querySelector("#table").rows[r1].cells[c1].tokenColor == curColor){
                if (document.querySelector("#table").rows[r1 - 1].cells[c1].tokenColor != curColor) {
                    tempCount = 3;
                }
                else {
                    tempCount--;
                    if (tempCount == 0) {
                        leftToWin = 0;
                        return;
                    }
                }
            }
        }
    }
    tempCount = 3;
    // row check
    for (var r2 = 1; r2 < 7; r2++) {
        // start with the bottom
        for (var c2 = 0; c2 < 6; c2++) {
            if (document.querySelector("#table").rows[r2].cells[c2].tokenColor == curColor) {
                if (document.querySelector("#table").rows[r2].cells[c2+1].tokenColor != curColor) {
                    tempCount = 3;
                }
                else {
                    tempCount--;
                    if (tempCount == 0) {
                        leftToWin = 0;
                        return;
                    }
                }
            }
        }
    }
    tempCount = 3;
    // diagonal check
    rDiagonalCheck(3, 0, curColor, 3, 6, 4);
    rDiagonalCheck(2, 0, curColor, 3, 6, 5);
    rDiagonalCheck(1, 0, curColor, 3, 6, 6);
    rDiagonalCheck(1, 3, curColor, 3, 4, 6);
    rDiagonalCheck(1, 2, curColor, 3, 5, 6);
    rDiagonalCheck(1, 1, curColor, 3, 6, 6);
    lDiagonalCheck(3, 6, curColor, 3, 6, 2);
    lDiagonalCheck(2, 6, curColor, 3, 6, 1);
    lDiagonalCheck(1, 6, curColor, 3, 6, 0);
    lDiagonalCheck(1, 3, curColor, 3, 4, 0);
    lDiagonalCheck(1, 4, curColor, 3, 5, 0);
    lDiagonalCheck(1, 5, curColor, 3, 6, 0);
    if (leftToWin == 0)
        return;
    leftCheck(curColor);
    minWin.sort();
    leftToWin = minWin[0];
    minWin = [];
    minWin.push(3);
    return;
}

function lDiagonalCheck(rowD, colD, colorD, countD, rMaxD, cMinD) {
    var rowMax = rMaxD; var colMin = cMinD;
    var tempRow = rowD; var tempCol = colD;
    var tempColor = colorD; var tempCounter = countD;
    while (tempRow < rowMax && tempCol > colMin) {
        if (document.querySelector("#table").rows[tempRow].cells[tempCol].tokenColor == tempColor) {
            if (document.querySelector("#table").rows[tempRow + 1].cells[tempCol - 1].tokenColor != tempColor) {
                tempCounter = 3;
            }
            else {
                tempCounter--;
                if (tempCounter == 0) {
                    leftToWin = 0;
                    return;
                }
            }
        }
        tempRow++; tempCol--;
    }
    return;
}

function rDiagonalCheck(rowD, colD, colorD, countD, rMaxD, cMaxD) {
    var rowMax = rMaxD; var colMax = cMaxD;
    var tempRow = rowD; var tempCol = colD;
    var tempColor = colorD; var tempCounter = countD;
    while (tempRow < rowMax && tempCol < colMax) {
        if (document.querySelector("#table").rows[tempRow].cells[tempCol].tokenColor == tempColor) {
            if (document.querySelector("#table").rows[tempRow + 1].cells[tempCol + 1].tokenColor != tempColor) {
                tempCounter = 3;
            }
            else {
                tempCounter--;
                if (tempCounter == 0) {
                    leftToWin = 0;
                    return;
                }
            }
        }
        tempRow++; tempCol++;
    }
    return;
}

function leftCheck(clr) {
    // column check
    var cPattern = "";
    for (var lc = 0; lc < 7; lc++) {
        for (var lr = 6; lr > 0; lr--) {
            // if red/black
            if (document.querySelector("#table").rows[lr].cells[lc].tokenColor == clr) {
                cPattern = cPattern.concat("1");
            }
            else if (document.querySelector("#table").rows[lr].cells[lc].tokenColor == "white") {
                cPattern = cPattern.concat("0");
            }
            // if black/red
            else
                cPattern = cPattern.concat("2");
        }
        if (cPattern.match(/^1*2*1+0+$/)) {
            if (cPattern.match(/^1*2*1{3}0+$/))
                minWin.push(1);
            else if (cPattern.match(/^1*2*1{2}0+$/))
                minWin.push(2);
            else (cPattern.match(/^1*2*1{1}0+$/))
                minWin.push(3);
        }
        cPattern = "";
    }
    // row check
    for (var rr = 6; rr > 0; rr--) {
        for (var rc = 0; rc < 7; rc++) {
            // if red/black
            if (document.querySelector("#table").rows[rr].cells[rc].tokenColor == clr) {
                cPattern = cPattern.concat("1");
            }
            else if (document.querySelector("#table").rows[rr].cells[rc].tokenColor == "white") {
                cPattern = cPattern.concat("0");
            }
            // if black/red
            else
                cPattern = cPattern.concat("2");
        }
        if (cPattern.match(/110{1}1/) || cPattern.match(/10{1}11/))
            minWin.push(1);
        else if (cPattern.match(/110{2}1/) || cPattern.match(/10{2}11/) || cPattern.match(/110011/) || cPattern.match(/110{3}1/) || cPattern.match(/110{3}1/))
            minWin.push(2);
        else if (cPattern.match(/110{3}1/) || cPattern.match(/10{3}11/) || cPattern.match(/110{2}1/) || cPattern.match(/10{2}11/) || cPattern.match(/10101/))
            minWin.push(2);
        else if (cPattern.match(/11011/) || cPattern.match(/0111/) || cPattern.match(/1110/) || cPattern.match(/1001/))
            minWin.push(1);
        else if (cPattern.match(/0110/) || cPattern.match(/1100/) || cPattern.match(/0011/))
            minWin.push(2);
        cPattern = "";
    }
    // rdiagonal check
    rr = 3; rc = 0;
    while (rr < 7 && rc < 4) {
        // if red/black
        if (document.querySelector("#table").rows[rr].cells[rc].tokenColor == clr) {
            cPattern = cPattern.concat("1");
        }
        else if (document.querySelector("#table").rows[rr].cells[rc].tokenColor == "white") {
            cPattern = cPattern.concat("0");
        }
        // if black/red
        else
            cPattern = cPattern.concat("2");
        rr++; rc++;
    }
    if (cPattern.match(/1100/) || cPattern.match(/0110/) || cPattern.match(/0011/) || cPattern.match(/1010/) || cPattern.match(/0101/))
        minWin.push(2);
    else if (cPattern.match(/1110/) || cPattern.match(/0111/) || cPattern.match(/1101/) || cPattern.match(/1011/))
        minWin.push(1);
    else if (cPattern.match(/011/) || cPattern.match(/110/))
        minWin.push(2);
    cPattern = "";
    // ==
    rr = 1; rc = 3;
    while (rr < 5 && rc < 7) {
        // if red/black
        if (document.querySelector("#table").rows[rr].cells[rc].tokenColor == clr) {
            cPattern = cPattern.concat("1");
        }
        else if (document.querySelector("#table").rows[rr].cells[rc].tokenColor == "white") {
            cPattern = cPattern.concat("0");
        }
        // if black/red
        else
            cPattern = cPattern.concat("2");
        rr++; rc++;
    }
    if (cPattern.match(/1100/) || cPattern.match(/0110/) || cPattern.match(/0011/) || cPattern.match(/1010/) || cPattern.match(/0101/))
        minWin.push(2);
    else if (cPattern.match(/1110/) || cPattern.match(/0111/) || cPattern.match(/1101/) || cPattern.match(/1011/))
        minWin.push(1);
    else if (cPattern.match(/011/) || cPattern.match(/110/))
        minWin.push(2);
    cPattern = "";
    // ==
    rr = 3; rc = 6;
    while (rr < 7 && rc > 2) {
        // if red/black
        if (document.querySelector("#table").rows[rr].cells[rc].tokenColor == clr) {
            cPattern = cPattern.concat("1");
        }
        else if (document.querySelector("#table").rows[rr].cells[rc].tokenColor == "white") {
            cPattern = cPattern.concat("0");
        }
        // if black/red
        else
            cPattern = cPattern.concat("2");
        rr++; rc--;
    }
    if (cPattern.match(/1100/) || cPattern.match(/0110/) || cPattern.match(/0011/) || cPattern.match(/1010/) || cPattern.match(/0101/))
        minWin.push(2);
    else if (cPattern.match(/1110/) || cPattern.match(/0111/) || cPattern.match(/1101/) || cPattern.match(/1011/))
        minWin.push(1);
    else if (cPattern.match(/011/) || cPattern.match(/110/))
        minWin.push(2);
    cPattern = "";
    // ==
    rr = 1; rc = 3;
    while (rr < 5 && rc > -1) {
        // if red/black
        if (document.querySelector("#table").rows[rr].cells[rc].tokenColor == clr) {
            cPattern = cPattern.concat("1");
        }
        else if (document.querySelector("#table").rows[rr].cells[rc].tokenColor == "white") {
            cPattern = cPattern.concat("0");
        }
        // if black/red
        else
            cPattern = cPattern.concat("2");
        rr++; rc--;
    }
    if (cPattern.match(/1100/) || cPattern.match(/0110/) || cPattern.match(/0011/) || cPattern.match(/1010/) || cPattern.match(/0101/))
        minWin.push(2);
    else if (cPattern.match(/1110/) || cPattern.match(/0111/) || cPattern.match(/1101/) || cPattern.match(/1011/))
        minWin.push(1);
    else if (cPattern.match(/011/) || cPattern.match(/110/))
        minWin.push(2);
    cPattern = "";
    //console.log(minWin);
}

function inputCheck() {
    if (dateInput == null) {
        alert("Invalid birthday input");
        document.getElementById("msg").textContent = "Input of birthday is invalid, please refresh the page to retry";
    }
}

function timerOn() {
    setInterval(function () {
        if (!end){
            gameTime++;
            document.getElementById("timer").textContent = "Game time: " + gameTime + " sec";
        }
    }, 1000);
}

function storeRecord() {
    var winInfo = JSON.stringify([roundPlayerName, gameTime]);
    // if no record stored yet
    if (localStorage.length == 0) {
        localStorage.setItem(1, winInfo);
        return;
    }
    if (localStorage.length < 11 && gameTime >= JSON.parse(localStorage.getItem(localStorage.length))[1]) {
        localStorage.setItem(localStorage.length+1, winInfo)
        return;
    }
    for (var n = Math.min(10, localStorage.length); n > 0; n--) {
        // if beats a record
        var tempRecord = JSON.parse(localStorage.getItem(n));
        if (gameTime < tempRecord[1]) {
            var tempLog = localStorage.getItem(n);
            localStorage.setItem(n + 1, tempLog);       
            localStorage.setItem(n, winInfo);
        }
        else
            return;
    }
}

function showList() {
    // clear the game interface
    setTimeout(function() {
        document.getElementById("cfGame").innerHTML = "";
        document.getElementById("listTitle").textContent = "Top 10 List";
        for (var k = 1; k < 11; k++) {
            if (k > localStorage.length) {
                document.getElementById(k.toString()).textContent = "<Empty>";
            }
            else {
                document.getElementById(k.toString()).textContent = JSON.parse(localStorage.getItem(k))[0] + ": " + JSON.parse(localStorage.getItem(k))[1] + " sec";
            }
        }
        var btn = document.createElement("button");
        btn.innerHTML = "New Game";
        var btnBody = document.getElementById("button");
        btnBody.appendChild(btn);
        btn.addEventListener("click", function() {
            if (localStorage.length > 10)
                localStorage.removeItem(11);
            document.body.innerHTML = "";
            location.reload();
        });
    }, 1200);
}
