ace.require("ace/ext/language_tools");

var editor = ace.edit("editor");
editor.setOptions({
    enableBasicAutocompletion: true
});

editor.setTheme("ace/theme/eclipse");
editor.getSession().setMode("ace/mode/java");
document.getElementById('editor').style.fontSize = '18px';
editor.setAutoScrollEditorIntoView(true);

var codeTemplates = {};
var viewConfig = {
    showOutput: false,
    showInput: false
};

var prevLang;

function showOutput(output) {
    var stdout = '';
    var stderr = '';

    if (output.status === 0) {
        stdout = output.output;
    } else if (output.status === 1) {
        stderr = '<p class="error">Compiler error !</p>';
        stderr += output.error;
    } else if (output.status === 2) {
        stderr = '<p class="error">Runtime error !</p>';
        stderr += output.error;
    } else if (output.status === 3) {
        stderr = '<p class="error">Timeout !</p>';
        stderr += output.error;
    }

    var out = '';
    if (stderr) {
        out += '<b>Stderror</b>\n' + stderr;
    }

    if (stdout) {
        out += stdout;
    }

    if (!viewConfig.showOutput) {
        $('#btn-show-output').click();
    }

    $('#output-p').html(out);

//    $('#output-data').show();
//    if (!$('#bottom-pane').hasClass('opened')) {
//        $('#bottom-pane').addClass('opened');
//    }
    windowResized();
}

function loadLangs() {
    $('#btn-run').prop('disabled', true);
    $.ajax({
        type: "GET",
        url: '/apis/langs'
    }).done(function (data) {
        showLangs(data);
        $('#btn-run').prop('disabled', false);
    }).fail(function (data) {
        alert("error");
        $('#btn-run').prop('disabled', false);
    });
}

function showLangs(langs) {
    for (var i = 0; i < langs.supportedLangs.length; i++) {
        var supportedLang = langs.supportedLangs[i];
        $('#lang').append($('<option>', {
            value: supportedLang.id,
            text: supportedLang.name
        }));
        codeTemplates[supportedLang.id] = supportedLang.template;
    }
    $('#lang').val(langs.supportedLangs[0].id);
    onLangChanged();
}

$(document).ready(function () {


    $('#btn-output').click(function () {
        $('#output-data').toggle();
        $('#bottom-pane').toggleClass('opened');
        windowResized();
    });

    $('#btn-run').click(function () {

        onRunning();

        var codeRunRequest = {
            lang: $('#lang').find(":selected").val(),
            code: editor.getValue(),
            input: $('#text-input').val()
        };

        runCode(codeRunRequest);

//        $.ajax({
//            type: "POST",
//            url: '/apis/run',
//            data: JSON.stringify(codeRunRequest),
//            contentType: 'application/json'
//        }).done(function (data) {
//            showOutput(data);
//            $('#btn-run').prop('disabled', false);
//        }).fail(function (data) {
//            alert("error");
//            $('#btn-run').prop('disabled', false);
//        });
    });

    $('#btn-hide-output').click(function () {
        $('#btn-hide-output').hide();
        $('#btn-show-output').show();
        viewConfig.showOutput = false;
        windowResized();
    });

    $('#btn-show-output').click(function () {
        $('#btn-hide-output').show();
        $('#btn-show-output').hide();
        viewConfig.showOutput = true;
        windowResized();
    });

    $('#btn-hide-input').click(function () {
        $('#btn-hide-input').hide();
        $('#btn-show-input').show();
        viewConfig.showInput = false;
        windowResized();
    });

    $('#btn-show-input').click(function () {
        $('#btn-hide-input').show();
        $('#btn-show-input').hide();
        viewConfig.showInput = true;
        windowResized();
    });

    loadLangs();

    $(window).resize(function () {
        windowResized();
    });

    windowResized();

});

$('#lang').change(function () {
    onLangChanged();
});

function onLangChanged() {
    var lang = $('#lang').find(":selected").val();

    if (prevLang) {
        codeTemplates[prevLang] = editor.getValue();
    }

    editor.setValue(codeTemplates[lang], -1);

    prevLang = lang;

    if (lang === 'java7') {
        editor.getSession().setMode("ace/mode/java");
    } else if (lang === 'python3') {
        editor.getSession().setMode("ace/mode/python");
    } else if (lang === 'c' || lang === 'c++') {
        editor.getSession().setMode("ace/mode/c_cpp");
    } else if (lang === 'c#') {
        editor.getSession().setMode("ace/mode/csharp");
    }
}


function windowResized() {



    var aceWrapper = $('#ace_wrapper');
    var aceEditor = $('#editor');
    var outputWrapper = $('#output-wrapper');
    var outputData = $('#output-data');
    var inputWrapper = $('#input-wrapper');
    var textInput = $('#text-input');

    var rightDiff;
    var inputHeight;
    var outputHeight;
    var textInputWidth;
    var textInputHeight;



    if (viewConfig.showInput || viewConfig.showOutput) {
        rightDiff = outputWrapper.width() + 4;
    } else {
        rightDiff = 0;
    }

    var aceHeight = window.innerHeight - aceWrapper.position().top;// - bottomPane.height();
    var aceWidth = window.innerWidth - rightDiff;

    if (viewConfig.showOutput) {
        outputWrapper.show();
        outputHeight = aceHeight;
    } else {
        outputWrapper.hide();
        outputHeight = 0;
    }

    if (viewConfig.showInput) {
        inputWrapper.show();
        inputHeight = 225;

        if (viewConfig.showOutput) {
            outputHeight -= inputHeight;
        } else {
            inputHeight = aceHeight;
        }

        textInputHeight = inputHeight - 62;
        textInputWidth = rightDiff - 23;
    } else {
        inputWrapper.hide();
        inputHeight = 0;
    }


//    var bottomPane = $('#bottom-pane');


//    var outputWrapperHeight = aceHeight - inputWrapper.height() - 2;
    var outputTextHeight = outputHeight - 52;




    aceWrapper.css('height', aceHeight + 'px');
    aceWrapper.css('width', aceWidth + 'px');

    aceEditor.css('height', aceHeight + 'px');
    aceEditor.css('width', aceWidth + 'px');



    editor.resize();
    if (viewConfig.showOutput) {
        outputWrapper.css('height', outputHeight + 'px');
        outputData.css('max-height', outputTextHeight + 'px');
    }
    if (viewConfig.showInput) {
        inputWrapper.css('height', inputHeight + 'px');
        textInput.css('width', textInputWidth + 'px');
        textInput.css('height', textInputHeight + 'px');

//        outputData.css('max-height', outputTextHeight + 'px');
    }
}

var stompClient;
function connect() {
    var socket = new SockJS('/coderun');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
//        window.alert("connected !");

        stompClient.subscribe('/user/queue/reply', function (reply) {
            showOutput(JSON.parse(reply.body));
            onFinished();
        });
    });
}

function runCode(codeRunRequest) {
    stompClient.send('/iapis/run', {}, JSON.stringify(codeRunRequest));
}

function onRunning() {
    $('#btn-run').prop('disabled', true);
    $('#icon-running').show();
    $('#icon-run').hide();
}

function onFinished() {
    $('#btn-run').prop('disabled', false);
    $('#icon-running').hide();
    $('#icon-run').show();
}

connect();