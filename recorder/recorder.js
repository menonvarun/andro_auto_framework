var botbot = {

    addListener: function() {
        window.irecorder.printHtml('adding listener');
        $('body').on('click', function() {
            var attrLoc = ['id','class','name','href'];
            var typeLoc = ['input','img','a','select'];
            var ele = event.target;
            var temp = null;
            var attrvalue = [];
            for (typ in typeLoc) {
                if (typeLoc[typ] == $(ele)[0].nodeName.toLowerCase()) {
                    temp = ele;
                    attrvalue.push('tag:' + $(ele)[0].nodeName.toLowerCase());
                    break;
                }
            }
            for (att in attrLoc) {
                if ($(ele).attr(attrLoc[att])) {
                    attrvalue.push(attrLoc[att] + ':' + $(ele).attr(attrLoc[att]));
                }
            }
            if (attrvalue.length != 0 && (temp === null)) {
                temp = ele;
                attrvalue.push('tag:' + $(ele)[0].nodeName.toLowerCase());
            }
            if ($(ele).text() != '') {
                attrvalue.push('text:' + $(ele).text());
            }
            
            window.irecorder.printHtml('click element value is :- ' + attrvalue.toString());
            window.irecorder.record('clickonelement',attrvalue.toString());
        });

        $('input[type="password"],input[type="text"]').on('change', function() {
            var attrLoc = ['id','class','name','href'];
            var typeLoc = ['input','img','a','select'];
            var ele = event.target;
            var temp = null;
            var attrvalue = [];
            for (typ in typeLoc) {
                if (typeLoc[typ] == $(ele)[0].nodeName.toLowerCase()) {
                    temp = ele;
                    attrvalue.push('tag:' + $(ele)[0].nodeName.toLowerCase());
                    break;
                }
            }
            for (att in attrLoc) {
                if ($(ele).attr(attrLoc[att])) {
                    attrvalue.push(attrLoc[att] + ':' + $(ele).attr(attrLoc[att]));
                }
            }
            if (attrvalue.length != 0 && (temp === null)) {
                temp = ele;
                attrvalue.push('tag:' + $(ele)[0].nodeName.toLowerCase());
            }
            if ($(ele).val() != '') {
                attrvalue.push('value:' + $(ele).val());
            }
            window.irecorder.printHtml('text element value is :- ' + attrvalue.toString());
            window.irecorder.record('entertext',attrvalue.toString());
        });
    }
};