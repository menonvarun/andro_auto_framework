

function botbotutil() {
    this.attrLoc = ['id', 'class', 'name', 'href'];
    this.typeLoc = ['input', 'img', 'a', 'select'];
    this.attrvalue = {};
    this.data = {};

    this.getCollectedElementInfo = function (ele) {
        var aValues = [];
        this.getAllLocatorValues(ele);
        var temp =this.attrvalue;
		this.attrvalue={};
        return temp;
    };

    this.getLocator = function () {
        var tag = this.attrvalue['tag'];
        var id = !this.attrvalue['id'] ? '' : this.attrvalue['id'];
        var name = !this.attrvalue['name'] ? '' : this.attrvalue['name'];
        var cls = !this.attrvalue['class'] ? '' : this.attrvalue['class'].replace(/\s/g, '.');
        if (cls != '') this.attrvalue['class'] = cls;

        var locator = tag + (!cls ? '' : "." + cls) + (!id ? '' : "#" + id) + (!name ? '' : '[name=\'' + name + '\']');

        return locator;
    };

    this.getIndex = function (locator, ele) {
        var elements = $(locator);
        for (i in elements) {
            if (elements[i] === $(ele)[0]) {
                return i;
            }
        }
        return null;
    };

    this.getTextValue = function (ele) {
        var text = '';
        if ($(ele).val() != '') {
            text = $(ele).val();
        }
        return text;
    };

    this.getAllLocatorValues = function (ele) {

        for (typ in this.typeLoc) {
            if (this.typeLoc[typ] == $(ele)[0].nodeName.toLowerCase()) {
                temp = ele;
                this.attrvalue['tag'] = $(ele)[0].nodeName.toLowerCase();
                break;
            }
        }
        for (att in this.attrLoc) {
            if ($(ele).attr(this.attrLoc[att])) {
                this.attrvalue[this.attrLoc[att]] = $(ele).attr(this.attrLoc[att]);
            }
        }
        if (this.attrvalue.length != 0 && (temp === null)) {
            temp = ele;
            this.attrvalue['tag'] = $(ele)[0].nodeName.toLowerCase();
        }
        if ($(ele).text() != '') {
            this.attrvalue['text'] = $(ele).text();
        }

        var locator = this.getLocator();
        if (locator != '') {
            this.attrvalue['locator'] = locator;
        }
        var index = this.getIndex(locator, ele);
        if (index != null) {
            this.attrvalue['index'] = index;
        }
    };
}

var botbot = {

    addListener:function () {
        if (window._botbotEventInitialized) {
            return;
        }
        window._botbotEventInitialized = true;
        window._botbotutil=new botbotutil();
        document.getElementsByTagName('body')[0].addEventListener('click', function () {
            var ele = event.target;
            var aValues = [];
            var util = window._botbotutil;
            var data = {};
            var attrvalue = util.getCollectedElementInfo(ele);
            for (key in attrvalue) {
                aValues.push('"' + key + '=' + attrvalue[key] + '"');
            }
            data['command'] = 'clickwebelement';
            data['args[0]'] = !attrvalue['locator'] ? '' : attrvalue['locator'];
            if (attrvalue['index'] > 0) data['args[1]'] = attrvalue['index'];
            data['args[0]-data'] = aValues;
			window.irecorder.record(JSON.stringify(data));
        }, true);


        $('input[type="text"], input[type="password"]').on('change', function (e) {
            var ele = e.target;
            var util = window._botbotutil;
            var attrvalue = util.getCollectedElementInfo(ele);
            var data = {};

            var aValues = [];
            data['command'] = 'enterwebtext';
            data['args[0]'] = !attrvalue['locator'] ? '' : attrvalue['locator'];
            if (attrvalue['index'] > 0) {
                data['args[1]'] = attrvalue['index'];
                data['args[2]'] = util.getTextValue(ele);
            } else {
                data['args[1]'] = util.getTextValue(ele);
            }
            for (key in attrvalue) {
                aValues.push('"' + key + '=' + attrvalue[key] + '"');
            }
            data['args[0]-data'] = aValues;
            window.irecorder.record(JSON.stringify(data));
        });
    }
};