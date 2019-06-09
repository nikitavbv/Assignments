const COLOR_BLUE = 'BLUE';
const COLOR_RED = 'RED';

const SCREEN_MENU = 'menu';
const SCREEN_GAME = 'game';
const SCREEN_WIN = 'win';
const SCREEN_LOST = 'loose';

const DEFAULT_FIELD_WIDTH = 5;
const DEFAULT_FIELD_HEIGHT = 5;

const MIN_FIELD_SIZE = 4;
const MAX_FIELD_SIZE = 12;

let currentScreen = SCREEN_MENU;
let game = {
    fieldWidth: DEFAULT_FIELD_WIDTH,
    fieldHeight: DEFAULT_FIELD_HEIGHT
};

const getFieldValue = fieldID => document.getElementById(fieldID).value;
const getFieldDimensions = () => ['fieldWidth', 'fieldHeight'].map(id => Math.min(MAX_FIELD_SIZE, Math.max(MIN_FIELD_SIZE, parseInt(getFieldValue(id)))));
const getDifficulty = () => parseInt(getFieldValue('gameMode'));
const getScreenElement = screenID => document.getElementsByClassName(`screen-${screenID}`)[0];
const showScreen = screenID => getScreenElement(screenID).style['display'] = 'inherit';
const hideScreen = screenID => getScreenElement(screenID).style['display'] = 'none';
const getPlayerSnakeColor = () => game.color;
const getAISnakeColor = () => game.color === COLOR_BLUE ? COLOR_RED : COLOR_BLUE;
const getSnake = color => color === COLOR_BLUE ? game.blueSnake : game.redSnake;
const setSnake = (color, snake) => color === COLOR_BLUE ? game.blueSnake = snake : game.redSnake = snake;
const getSnakeLength = color => getSnake(color).length;
const getSnakeHead = color => getSnake(color)[getSnakeLength(color) - 1];
const getAISnake = () => getSnake(getAISnakeColor());
const getPlayerSnake = () => getSnake(getPlayerSnakeColor());
const setAISnake = snake => setSnake(getAISnakeColor(), snake);
const setPlayerSnake = snake => setSnake(getPlayerSnakeColor(), snake);
const getPlayerSnakeHead = () => getSnakeHead(getPlayerSnakeColor());
const distance = (a, b) => Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
const isNearby = (pointA, pointB) => distance(pointA, pointB) === 1;
const pointsEq = (pointA, pointB) => distance(pointA, pointB) === 0;

function switchToScreen(screen) {
    hideScreen(currentScreen);
    currentScreen = screen;
    showScreen(currentScreen);
}

function onStartGame(color) {
    [game.fieldWidth, game.fieldHeight] = getFieldDimensions();
    game.difficulty = getDifficulty();
    game.color = color;
    game.blueSnake = [[1, 1]];
    game.redSnake = [[game.fieldWidth - 2, game.fieldHeight - 2]];
    switchToScreen(SCREEN_GAME);
    updateGameScreen();
}

function containsPoint(arr, pointToSearch) {
    for (let point of arr) {
        if (point[0] === pointToSearch[0] && point[1] === pointToSearch[1]) {
            return true;
        }
    }
    return false;
}

function containsConseq(arr, firstPoint, secondPoint) {
    for (let i = 0; i < arr.length - 1; i++) {
        if ((arr[i][0] === firstPoint[0] && arr[i][1] === firstPoint[1]
            && arr[i+1][0] === secondPoint[0] && arr[i + 1][1] === secondPoint[1]) || (
            arr[i + 1][0] === firstPoint[0] && arr[i + 1][1] === firstPoint[1]
            && arr[i][0] === secondPoint[0] && arr[i][1] === secondPoint[1]
        )) {
            return true;
        }
    }

    return false;
}

function movePlayerSnakeTo([x, y]) {
    const req = new XMLHttpRequest();
    req.onreadystatechange = () => {
        if (req.readyState !== XMLHttpRequest.DONE) {
            return;
        }
        let aiPos = JSON.parse(req.responseText);
        if (aiPos.status === 'ai_win') {
            switchToScreen(SCREEN_LOST);
        } else if (aiPos.status === 'player_win') {
            switchToScreen(SCREEN_WIN);
        }

        const playerSnake = getPlayerSnake();
        const aiSnake = getAISnake();
        playerSnake.push([x, y]);
        aiSnake.push([aiPos.x, aiPos.y]);
        setPlayerSnake(playerSnake);
        setAISnake(aiSnake);
        updateGameScreen();
    };

    req.open("POST", "/api/game", true);
    req.send(JSON.stringify({
        width: game.fieldWidth,
        height: game.fieldHeight,
        player_snake: [...getPlayerSnake(), [x, y]],
        ai_snake: getAISnake(),
        difficulty: game.difficulty,
    }));
}

function onNodeClick() {
    if (this.classList.contains('blue-snake') || this.classList.contains('red-snake')) {
        return;
    }

    const x = parseInt(this.getAttribute('data-x'));
    const y = parseInt(this.getAttribute('data-y'));
    const snakeHead = getPlayerSnakeHead();

    if (!isNearby([x, y], snakeHead)) {
        return;
    }

    movePlayerSnakeTo([x, y]);
}

function onLinkClick() {
    if (this.classList.contains('blue-snake') || this.classList.contains('red-snake')) {
        return;
    }

    const [fromX, fromY, toX, toY] = ['fromX', 'fromY', 'toX', 'toY'].map(attr => {
        return parseInt(this.getAttribute(`data-${attr}`));
    });

    const snakeHead = getPlayerSnakeHead();
    if (pointsEq([fromX, fromY], snakeHead) && !(containsPoint(game.redSnake, [toX, toY]) || containsPoint(game.blueSnake, [toX, toY]))) {
        movePlayerSnakeTo([toX, toY]);
    } else if (pointsEq([toX, toY], snakeHead) && !(containsPoint(game.blueSnake, [fromX, fromY]) || containsPoint(game.redSnake, [fromX, fromY]))) {
        movePlayerSnakeTo([fromX, fromY]);
    }
}

function setListeners(obj, listener) {
    for (let node of obj) {
        node.onclick = listener;
    }
}

function setListenersByClassName(className, listener) {
    setListeners(document.getElementsByClassName(className), listener);
}

function updateListeners() {
    setListenersByClassName('game-row-node', onNodeClick);
    setListenersByClassName('game-row-link-h', onLinkClick);
    setListenersByClassName('game-row-link-v', onLinkClick);
}

function updateGameScreen() {
    let html = '';

    for (let y = 0; y < game.fieldHeight; y++) {
        let rowHtml = '';
        for (let x = 0; x < game.fieldWidth; x++) {
            let nodeClass = 'game-row-node';

            if (containsPoint(game.blueSnake, [x, y])) {
                nodeClass += ' blue-snake';
            } else if(containsPoint(game.redSnake, [x, y])) {
                nodeClass += ' red-snake';
            }

            rowHtml += `<div class="${nodeClass}" data-x="${x}" data-y="${y}"></div>`;

            if (x + 1 < game.fieldWidth) {
                let linkClass = 'game-row-link-h';

                if (containsConseq(game.blueSnake, [x, y], [x+1, y])) {
                    linkClass += ' blue-snake';
                } else if (containsConseq(game.redSnake, [x, y], [x+1, y])) {
                    linkClass += ' red-snake';
                }

                [fromX, fromY, toX, toY] = [x, y, x+1, y];
                rowHtml += `<div class="${linkClass}" data-fromX="${fromX}" data-fromY="${fromY}" data-toX="${toX}" data-toY="${toY}"></div>`;
            }
        }
        html += `<div class="game-row">${rowHtml}</div>`;
        if (y + 1 < game.fieldHeight) {
            rowHtml = '';
            for (let x = 0; x < game.fieldWidth; x++) {
                let linkClass = 'game-row-link-v';

                if (containsConseq(game.blueSnake, [x, y], [x, y+1])) {
                    linkClass += ' blue-snake';
                } else if (containsConseq(game.redSnake, [x, y], [x, y+1])) {
                    linkClass += ' red-snake';
                }

                [fromX, fromY, toX, toY] = [x, y, x, y+1];
                rowHtml += `<div class="${linkClass}" data-fromX="${fromX}" data-fromY="${fromY}" data-toX="${toX}" data-toY="${toY}"></div>`;

                if (x + 1 < game.fieldWidth) {
                    rowHtml += '<div class="game-row-link-h-space"></div>';
                }
            }
            html += `<div class="game-row">${rowHtml}</div>`;
        }
    }

    getScreenElement(SCREEN_GAME).innerHTML = `<div class='game-field'>${html}</div>`;
    setTimeout(updateListeners, 0);
}

document.getElementById('playBlue').onclick = onStartGame.bind(window, COLOR_BLUE);
document.getElementById('playRed').onclick = onStartGame.bind(window, COLOR_RED);
setListenersByClassName('playAgain', () => switchToScreen(SCREEN_MENU));