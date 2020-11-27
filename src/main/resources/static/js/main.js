"use strict"
function loadTable() {
    const requestURL = 'http://localhost:8080/table/getAll';
    const xhr = new XMLHttpRequest();
    xhr.open('GET', requestURL, true);
    xhr.onload = () => {
        let rows = JSON.parse(xhr.responseText);
        console.log(rows);
        let html = '<tr>\n' +
                        '<th>№</th>\n' +
                        '<th>День</th>\n' +
                        '<th>Название</th>\n' +
                        '<th>Значение</th>\n' +
                    '</tr>';
        for (let i = 0; i < rows.length; i++){
            let row = rows[i];
            html += '<tr onclick="listenRow(event,' + row.id + ')">' +
                        '<td>' + (i+1) + '</td>' +
                        '<td>' + row.date + '</td>' +
                        '<td>' + row.name + '</td>' +
                        '<td>' + row.value + '</td>' +
                        '<td><button onclick="deleteRow('+ row.id + ')">X</button></td>'
                    '</tr>';
        }
        document.getElementById('table').innerHTML = html;

    };
    xhr.onerror = () => {
        console.log(xhr.responseText)
    };
    xhr.send();
    console.log(xhr);
}

function listenRow( event, rowId){
    let clickedElement = event.target;
    if(clickedElement.tagName == 'TD' && clickedElement.cellIndex != 0){
        let html;
        let parent = clickedElement.parentElement;
        switch (clickedElement.cellIndex){
            case 1:
                html = '<td><input type="date" value="' + clickedElement.innerText + '"></td>';
                break;
            case 2:
                html = '<td><input type="text" value="' + clickedElement.innerText + '"></td>';
                break;
            case 3:
                html = '<td><input type="number" value="' + clickedElement.innerText + '"></td>';
                break;
        }
        clickedElement.innerHTML = html;
        let children = clickedElement.children[0];
        children.onblur = function () {
            if(clickedElement.innerText != children.value){
                clickedElement.innerHTML = '<td>' + children.value + '</td>';
                let dataRow = {
                    id : rowId,
                    date: parent.children[1].innerText,
                    name: parent.children[2].innerText,
                    value: parent.children[3].innerText,
                }
                putRow(dataRow);
            } else {
                clickedElement.innerHTML = '<td>' + children.value + '</td>';
            }
        };
        children.focus();
    }
}
function submit(){
    let row = {
        date: document.getElementById('date').valueAsDate,
        name: document.getElementById('name').value,
        value: document.getElementById('value').value
    };
    inputRow(row)
}
async function inputRow(dataRow) {
    const requestURL = 'http://localhost:8080/table/insert';
    let response = await fetch(requestURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
                date: dataRow.date,
                name: dataRow.name,
                value: dataRow.value
            }
        )
    })
    console.log(response);
    loadTable();
    document.getElementById('dwindow').close();
}
async function deleteRow(idRow) {
    const requestURL = 'http://localhost:8080/table/delete/' + idRow;
    let response = await fetch(requestURL, {
        method: 'DELETE'
    });
    console.log(response);
    loadTable();
}
async function deleteAllRow() {
    const requestURL = 'http://localhost:8080/table/deleteAll';
    let response = await fetch(requestURL, {
        method: 'DELETE'
    });
    console.log(response);
    loadTable();
}
async function init(){
    const requestURL = 'http://localhost:8080/table/init';
    let response = await fetch(requestURL, {
        method: 'GET'
    });
    console.log(response);
    loadTable();
}
async function putRow(row){
    const requestURL = 'http://localhost:8080/table/update/' + row.id;
    let response = await fetch(requestURL, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(row)
    })
    console.log(response);
    loadTable();
}
