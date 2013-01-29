var botbotrunner= {
	util:new botbotutil(),
	clickonelement: function(locator,index){
		var ele = this.util.getElement(locator,index);
		if(ele==undefined){
			window.ibotbot.setMessage("Element not available on page");
		}else if(this.util.isDisplayed(ele)){
			 var evnt = document.createEvent('MouseEvents');
			 evnt.initEvent( 'click', true, true );
			 ele.dispatchEvent(evnt);
		}else{
			window.ibotbot.setMessage("Element not visible.");
		}
	},

	enterwebtext: function(locator,index,text){
		var ele = this.util.getElement(locator,index);
		if(ele==undefined){
			window.ibotbot.setMessage("Element not available on page");
		}else if(this.util.isDisplayed(ele)){
			$(ele).val(text);
			window.ibotbot.successfull();
		}else{
			window.ibotbot.setMessage("Element not visible.");
		}
	},
	
	iselementpresent:function(locator,index){
		var ele = this.util.getElement(locator,index);
		if(ele!=undefined) window.ibotbot.elementPresent();
	}
	
	
}

function botbotutil(){
	
this.getElement=function(locator,index){
	var ele=$(locator);
	var rEle=undefined;
	if(ele.length!=0 && index<ele.length){
		rEle=ele[index];
	}
	return rEle;
};

this.isDisplayed=function(ele){
	return $(ele).is(':visible');
};

}