const getKeyInputValue = () => document.getElementById("keyInput").value;
const getValueInputValue = () => document.getElementById("valueInput").value;
const getSearchKeyInputValue = () => document.getElementById("searchKeyInput").value;
const getUpdateKeyInputValue = () => document.getElementById('updateKeyInput').value;
const getUpdateValueInputValue = () => document.getElementById('updateValueInput').value;
const getDeleteKeyInputValue = () => document.getElementById('deleteKeyInput').value;

function insertKey() {
    const resultField = document.getElementById("insertResult");
    const http = new XMLHttpRequest();
    http.open("GET", `/api/insert?key=${getKeyInputValue()}&value=${getValueInputValue()}`, true);
    resultField.innerText = "making request...";
    http.onreadystatechange = () => {
        if (http.readyState === 4 && http.status === 200) {
            resultField.innerText = http.responseText;
        }
    };
    http.send(null);
}

function searchKey() {
    const resultField = document.getElementById("searchResult");
    const http = new XMLHttpRequest();
    http.open("GET", `/api/search?key=${getSearchKeyInputValue()}`, true);
    resultField.innerText = 'Making request...';
    http.onreadystatechange = () => {
        if (http.readyState === 4 && http.status === 200) {
            resultField.innerText = http.responseText;
        }
    };
    http.send(null);
}

function updateKey() {
    const resultField = document.getElementById('updateKeyResult');
    const http = new XMLHttpRequest();
    http.open("GET", `/api/update?key=${getUpdateKeyInputValue()}&value=${getUpdateValueInputValue()}`, true);
    resultField.innerText = "making request...";
    http.onreadystatechange = () => {
        if (http.readyState === 4 && http.status === 200) {
            resultField.innerText = http.responseText;
        }
    };
    http.send(null);
}

function deleteKey() {
    const resultField = document.getElementById("deleteKeyResult");
    const http = new XMLHttpRequest();
    http.open("GET", `/api/delete?key=${getDeleteKeyInputValue()}`, true);
    resultField.innerText = 'Making request...';
    http.onreadystatechange = () => {
        if (http.readyState === 4 && http.status === 200) {
            resultField.innerText = http.responseText;
        }
    };
    http.send(null);
}

function loadAllValues() {
    const tableContainer = document.getElementById('tableContainer');
    const http = new XMLHttpRequest();
    http.open("GET", '/api/list');
    http.onreadystatechange = () => {
        if (http.readyState === 4 && http.status === 200) {
            const data = http.responseText.split('\n')
                .map(row => `<tr><td>${row.substring(0, row.indexOf(';'))}</td><td>${row.substring(row.indexOf(';')+1)}</td></tr>`)
                .join('');
            tableContainer.innerHTML = `<table class="blueTable"><tr><th>Key</th><th>Value</th></tr>${data}</table>`;
        }
    };
    http.send(null);
}

function drawLine(div1, div2, i){
    const child = document.createElement('svg');
    child.innerHTML = `<svg><line x1="${div1.getBoundingClientRect().x + div1.getBoundingClientRect().width/2}" y1="${div1.getBoundingClientRect().y + 40}" x2="${div2.getBoundingClientRect().x + div2.getBoundingClientRect().width/2}" y2="${div2.getBoundingClientRect().y}" style="stroke:rgb(255,0,0);stroke-width:2" /></svg>`;
    document.getElementsByTagName('body')[0].appendChild(child);
}

function drawLines(element) {
    let arr = element.children[1].children;
    for (let i = 0; i < arr.length; i++) {
        if (arr[i].className === 'btree-node-empty') continue;
        drawLine(element, arr[i]);
        if (arr[i] && arr[i].children[1]) {
            drawLines(arr[i]);
        }
    }
}

window.onload  = () => {
    setTimeout(() => {
        drawLines(document.getElementsByClassName('btree-node-root')[0]);
    }, 100);
};