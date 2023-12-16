

"use strict"
//Setting background colors to each div
document.getElementById("leftSide").style.border = "thick solid #000000";
leftSide.style.backgroundColor = "grey";
document.getElementById("rightSide").style.border = "thick solid #000000";
rightSide.style.backgroundColor = "lightblue";

//Open up a web socket

let ws;

function mainFunction() {
        ws =  new WebSocket("ws://localhost:8080");

//Assign boolean to web socket status
    let wsOpen = false;

//Get textfield elements
    const userText = document.getElementById("user");
    const roomText = document.getElementById("room");
    const messageText = document.getElementById("message");


//Attach action to join button
    const joinButton = document.getElementById("join");
    joinButton.onclick = function () {
        let user = userText.value;
        let room = roomText.value;
        let validInput = true;
        for (let i = 0; i < room.length; i++) {
            if (room[i] < 'a' || room[i] > 'z') {
                validInput = false;
            }
        }
        if (!validInput) {
            room.value = "Try again!";
            alert("Room text field can only accept lowercase letters")
        } else {
            if (wsOpen) {
                console.log("sending join");
                console.log(user + room)

                let messageJSONJoin = {
                    type: "join",
                    user: userText.value,
                    room: roomText.value,
                }
                ws.send(JSON.stringify((messageJSONJoin)));

            } else {
                console.log("ws is not open!");
            }
        }
    }

//Attach action to send button
    const sendButton = document.getElementById("send");
    sendButton.onclick = function () {
        let message = messageText.value;

        if (wsOpen) {
            //send values
            console.log("sending send");
            let messageJSONMessage = {
                type: "message",
                user: userText.value,
                room: roomText.value,
                message : messageText.value
            }
            ws.send(JSON.stringify((messageJSONMessage)));
        } else {
            message.value = "Could not open the websocket!";
        }
    }

//Attach action to leave button 
    const leaveButton = document.getElementById("leave");
    leaveButton.onclick = function () {
        if (wsOpen) {
            //send values
            console.log("sending leave");
            let messageJSONLeave = {
                type: "leave",
                user: userText.value,
                room: roomText.value,
            }

            ws.send(JSON.stringify(messageJSONLeave));
            console.log("user has left room");
            // ws.close;
        }
    }


//Add event listeners
    userText.addEventListener("keypress", handleKeyPress);
    roomText.addEventListener("keypress", handleKeyPress);
    messageText.addEventListener("keypress", handleKeyPressMessage);

//Anonymous function to open websocket on browser open
    ws.onopen = function () {
        wsOpen = true;
       //  console.log("sending wsopen");
       //  let messageJSONMessage = {
       //      type: "message",
       //      user: userText.value,
       //      room: roomText.value,
       //      message: messageText.value
       //  }
       // ws.send(JSON.stringify(messageJSONMessage));
    }

//Event occurs when client receives data from server 
    ws.onmessage = function (e) {
        console.log("Message received: " + e.data);
        let receivedMessage = e.data;


        //Parse message to a JSON object
        let JObject = JSON.parse(receivedMessage);
        let userObject = JObject.user;
        let roomObject = JObject.room;
        let messageObject = JObject.message;
        console.log("User: " + userObject);
        console.log("Room: " + roomObject)

        //Create paragraphs for log and message divs
        let logParagraph = document.createElement("p");
        let messageParagraph = document.createElement("p");
        //rightSide.scrollTip = rightSide.scrollHeight;


        //Add join message
        if (JObject.type === "join") {
            logParagraph.textContent = userObject + " has joined room " + roomObject;
            leftSide.appendChild(logParagraph);
        }

        //Add message to message
        if (JObject.type === "message" && JObject.userObject !== "null") {
            messageParagraph.textContent = userObject + ": " + messageObject;
            rightSide.appendChild(messageParagraph);
        }

        //Message from leave button getting clicked
        if (JObject.type === "leave") {
            logParagraph.textContent = userObject + " has left room";
            messageParagraph.textContent = userObject + " has left room";
            rightSide.appendChild(messageParagraph);
            leftSide.appendChild(logParagraph);
        }


    }

//Handle key press function for join section 
    function handleKeyPress(e) {

        if (e.code == "Enter") {
            let user = userText.value;
            let room = roomText.value;
            let validInput = true;
            for (let i = 0; i < room.length; i++) {
                if (room[i] < 'a' || room[i] > 'z') {
                    validInput = false;
                }
            }

            if (wsOpen && validInput) {
                //send values
                console.log("sending");
                console.log(user + room);

                let messageJSONJoin = {
                    type: "join",
                    user: userText.value,
                    room: roomText.value,
                }

                ws.send(JSON.stringify((messageJSONJoin)));


                //ws.send("join " + user + " " + room);
                // console.log(userText.value);
                // console.log(roomText.value);
                // console.log(messageText.value);
            } else {
                alert("Room text field can only accept lowercase letters");
                message.value = "Could not open the websocket!";
            }
        }

    }

//Handle key press for message section 
    function handleKeyPressMessage(e) {


        if (e.code == "Enter") {

            let message = messageText.value;

            if (wsOpen) {
                //send values
                console.log("sending");


                let messageJSONMessage = {
                    type: "message",
                    user: userText.value,
                    room: roomText.value,
                    message : messageText.value
                }

                ws.send(JSON.stringify((messageJSONMessage)));
            } else {
                message.value = "Could not open the websocket!";
            }
        }
    }

    function handleError() {
        message.value = "Problem connecting to server";
    }
}

// ws.send(JSON.stringify((messageJSONMessage)));
// console.log(JSON.stringify(messageJSONMessage))


window.onload = mainFunction();
//MessageDiv.scrollTip = MessageDiv.scrollHeight