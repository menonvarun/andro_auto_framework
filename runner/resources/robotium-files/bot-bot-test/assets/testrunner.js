var botbotrunner = {

    clickonelement:function (locator, index) {
        window.rinterface.printHtml("Locator is : " + locator + " - index is: " + index);
        var ele = this.getElement(locator, index);
        if (ele == undefined) {
            window.rinterface.printHtml("Element not available on page");
        } else if (this.isDisplayed(ele)) {
            var evnt = document.createEvent('MouseEvents');
            evnt.initEvent('click', true, true);
            ele.dispatchEvent(evnt);
        } else {
            window.rinterface.printHtml("Element not visible.");
        }
    },

    enterwebtext:function (locator, index, text) {
        window.rinterface.printHtml("Locator is : " + locator + " - index is: " + index);
        var ele = this.getElement(locator, index);
        if (ele == undefined) {
            window.rinterface.printHtml("Element not available on page");
        } else if (this.isDisplayed(ele)) {
            $(ele).val(text);
            window.rinterface.printHtml("successful");
        } else {
            window.rinterface.printHtml("Element not visible.");
        }
    },

    iselementpresent:function (locator, index) {
        window.rinterface.printHtml("Locator is : " + locator + " - index is: " + index);
        var ele = this.getElement(locator, index);
        if (ele != undefined) window.rinterface.printHtml("element present");
    },

    getElement:function (locator, index) {
        window.rinterface.printHtml("Locator is : " + locator + " - index is: " + index);
        var ele = $(locator);
        var rEle = undefined;
        if (ele.length != 0 && index < ele.length) {
            rEle = ele[index];
        }
        return rEle;
    },

    isDisplayed:function (ele) {
        return $(ele).is(':visible');
    }

}
