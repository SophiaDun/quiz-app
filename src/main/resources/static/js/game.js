var socket = new SockJS('/game-websocket');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);

    // Subscribe to drawing topic
    stompClient.subscribe('/topic/canvas', function (drawMessage) {
        var data = JSON.parse(drawMessage.body);
        console.log("New drawing:", data);
    });

    // Subscribe to guessing topic
    stompClient.subscribe('/topic/guess', function (guessMessage) {
        var data = JSON.parse(guessMessage.body);
        console.log("New guess:", data);
    });

    // Subscribe to word topic
    stompClient.subscribe('/topic/word', function (wordMessage) {
        var data = JSON.parse(wordMessage.body);
        console.log("New word to guess:", data.word);
    });
});

// Sending a drawing message
function sendDrawing(drawingData) {
    stompClient.send("/app/draw", {}, JSON.stringify({'roomId': 'room1', 'drawingData': drawingData}));
}

// Sending a guess message
function sendGuess(player, guess) {
    stompClient.send("/app/guess", {}, JSON.stringify({'roomId': 'room1', 'player': player, 'guess': guess}));
}
