function logout() {
    window.location.href = 'j_spring_security_logout';
}

function resetWizard() {
    window.location.reload();
}

function resetTab() {
    window.location.reload();
}

function enter2tab() {
    if (window.event.keyCode == 13) {
        window.event.keyCode = 9;
    }
}


function bootstrap() {
    /*deployQZ('qz_container');
     useDefaultPrinter();*/
}

function PdfUtil(url) {

    var iframe;

    var __construct = function (url) {
        iframe = getContentIframe(url);
    };

    var getContentIframe = function (url) {
        var iframe = document.createElement('iframe');
        iframe.src = url;
        return iframe;
    };

    this.display = function (parentDomElement) {
        parentDomElement.appendChild(iframe);
    };

    this.print = function () {
        try {
            iframe.contentWindow.print();
        } catch (e) {
            throw new Error("Printing failed.");
        }
    };

    __construct(url);
}

function printEditorContent(id) {

    var jframe = document.createElement("IFRAME");
    jframe.style.width = "1px";
    jframe.style.height = "1px";
    jframe.style.position = "absolute";
    jframe.style.left = "-9999px";
    document.body.appendChild(jframe)

    function htmlDecode(input) {
        var e = document.createElement('div');
        e.innerHTML = input;
        return e.childNodes[0].nodeValue;
    }

    var content = htmlDecode(document.getElementById(id + "_input").innerHTML);

    jframe.contentWindow.document.write(content);
    jframe.contentWindow.document.close();

    var frameContent = jframe.contentWindow;
    frameContent.focus();
    frameContent.print();

    setTimeout(
        function () {
            jframe.remove();
        },
        (60 * 1000)
    );
}

jQuery.expr[':'].regex = function (elem, index, match) {
    var matchParams = match[3].split(','),
        validLabels = /^(data|css):/,
        attr = {
            method: matchParams[0].match(validLabels) ?
                matchParams[0].split(':')[0] : 'attr',
            property: matchParams.shift().replace(validLabels, '')
        },
        regexFlags = 'ig',
        regex = new RegExp(matchParams.join('').replace(/^\s+|\s+$/g, ''), regexFlags);
    return regex.test(jQuery(elem)[attr.method](attr.property));
}

function idEndsWith(str) {
    if (document.querySelectorAll) {
        return document.querySelectorAll('[id$="' + str + '"]');
    }
    else {
        var all,
            elements = [],
            i,
            len,
            regex;

        all = document.getElementsByTagName('*');
        len = all.length;
        regex = new RegExp(str + '$');
        for (i = 0; i < len; i++) {
            if (regex.test(all[i].id)) {
                elements.push(all[i]);
            }
        }
        return elements;
    }
}

function printComponentContentById(componentId) {

    var jframe = document.createElement("IFRAME");
    jframe.style.width = "1px";
    jframe.style.height = "1px";
    jframe.style.position = "absolute";
    jframe.style.left = "-9999px";
    document.body.appendChild(jframe)

    function htmlDecode(input) {
        var e = document.createElement('div');
        e.innerHTML = input;
        return e.childNodes[0].nodeValue;
    }

    var container = idEndsWith(componentId)[0];
    var content = htmlDecode(container.innerHTML);

    jframe.contentWindow.document.write(content);
    jframe.contentWindow.document.close();

    var frameContent = jframe.contentWindow;
    frameContent.focus();
    frameContent.print();

    setTimeout(
        function () {
            jframe.remove();
        },
        (60 * 1000)
    );
}

function printContent(content) {

    var jframe = document.createElement("IFRAME");
    jframe.style.width = "1px";
    jframe.style.height = "1px";
    jframe.style.position = "absolute";
    jframe.style.left = "-9999px";
    document.body.appendChild(jframe)

    function htmlDecode(input) {
        var e = document.createElement('div');
        e.innerHTML = input;
        return e.childNodes[0].nodeValue;
    }

    // var content = htmlDecode(document.getElementById(id + "_input").innerHTML);

    jframe.contentWindow.document.write(content);
    jframe.contentWindow.document.close();

    var frameContent = jframe.contentWindow;
    frameContent.focus();
    frameContent.print();

    setTimeout(
        function () {
            jframe.remove();
        },
        (60 * 1000)
    );
}

function reset() {
    window.location.reload();
}