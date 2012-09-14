var botbotrunner = {
	
	clickwebelement: function(locator,index){
		var temEle=$(locator);
		var ele = this.getElement(temEle,index);
		console.log("Inside click on element "+locator+" index-"+index);
		this.clickonelement(ele,index);
	},

	clickwebtext: function(text,index){
		var temEle = $("*:contains('"+text+"')").filter(function(){return $(this).children().length < 1});
		var ele = this.getElement(temEle,index);
		console.log("Inside click on element "+text+" index-"+index);
		this.clickonelement(ele,index);
	},

	clickonelement: function(ele,index){
		
		if(ele==undefined){
			window.ibotbot.setMessage("Element not available on page");
		}else if(this.isDisplayed(ele)){
			 var evnt = document.createEvent('MouseEvents');
			 evnt.initEvent( 'click', true, true );
			 ele.dispatchEvent(evnt);
			 window.ibotbot.successfull();
		}else{
			window.ibotbot.setMessage("Element not visible.");
		}
	},
	
    enterwebtext:function (locator, index, text) {
        var temEle=$(locator);
		var ele = this.getElement(temEle,index);
        if (ele == undefined) {
            window.ibotbot.setMessage("Element not available on page");
        } else if (this.isDisplayed(ele)) {
            $(ele).val(text);
            window.ibotbot.successfull();
        } else {
           	window.ibotbot.setMessage("Element not visible.");
        }
    },
	
	iselementpresent:function(locator,index){
		var temEle=$(locator);
		var ele = this.getElement(temEle,index);
		if(ele!=undefined) window.ibotbot.elementFound();
	},
	
	istextpresent:function(text,index){
		var temEle = $("*:contains('"+text+"')").filter(function(){return $(this).children().length < 1});
		var ele = this.getElement(temEle,index);
		if(ele!=undefined) window.ibotbot.elementFound();
	},
	
	getElement:function(ele,index){
		var rEle=undefined;
		if(ele.length!=0 && index<ele.length){
			rEle=ele[index];
		}
		return rEle;
	},

    isDisplayed:function (ele) {
        return $(ele).is(':visible');
    }

}
