

function download(content, fileName, contentType) {
    var a = document.createElement("a");
    var file = new Blob([content], {type: contentType});
    a.href = URL.createObjectURL(file);
    a.download = fileName;
    a.click();
};download(document.documentElement.innerHTML, 'lc.txt', 'text/plain');


myInputElement.select();

document.execCommand(“Copy”);

function test() {
    alert('as');
};test();


function copyText() {
 alert('1');
 var myTemporaryInputElement = document.createElement(“input”);
 myTemporaryInputElement.type = “text”;
 myTemporaryInputElement.value = document.documentElement.innerHTML;
 alert('3');
 document.body.appendChild(myTemporaryInputElement);

 myTemporaryInputElement.select();
 document.execCommand(“Copy”);

 document.body.removeChild(myTemporaryInputElement);
};copyText(document.documentElement.innerHTML);


avascript:function copyText() {
    var txt = document.documentElement.innerHTML;
    var compileErrorRun = 'Compile Error';
    var wrongAnswerRun = '<div class="wrong-answer__6zc1">Wrong Answer</div>';

    var wrongAnswerSubmission = '<div class="error__2Ft1">Wrong Answer</div>';
    var result = 'N/A';

    if (txt.includes(compileErrorRun)){
        result = '[ERROR]';
    } else if (txt.includes(wrongAnswerRun) || txt.includes(wrongAnswerSubmission)){
        result = '[WARNING]';
    } else {
        result = '[INFO]';
    }
    window.prompt("Copy to clipboard",result);
};copyText();
