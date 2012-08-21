var botbot = {

    addListener: function() {
    	if(window._botbotEventInitialized){
    		return;
    	}
    	window._botbotEventInitialized = true;
		document.getElementsByTagName('body')[0].addEventListener('click', function() {
            var attrLoc = ['id','class','name','href'];
            var typeLoc = ['input','img','a','select'];
            var ele = event.target;
            var temp = null;
            var attrvalue = {};
	    	var data={};
            function getAllValues(){
                var aValues=[];
           	    for(key in attrvalue){
           		    aValues.push('"'+key+'='+attrvalue[key]+'"');
           	    }
				data['command']='clickwebelement';
				data['args[0]']=!attrvalue['locator']? '':attrvalue['locator'];
				if(attrvalue['index']>0) data['args[1]']=attrvalue['index'];
				data['args[0]-data']=aValues;
                return JSON.stringify(data);
            }

            function getLocator(){
                var tag = attrvalue['tag'];
                var id = !attrvalue['id'] ? '' : attrvalue['id'];
                var name = !attrvalue['name'] ? '' : attrvalue['name'];
                var cls = !attrvalue['class'] ? '' : attrvalue['class'].replace( /\s/g, '.');

                var locator = tag + (!cls ? '' : "." + cls) + (!id ? '' : "#" + id) + (!name ? '' : '[name=\'' + name + '\']');

                return locator;
            }

            function getIndex(locator,ele){
                var elements=$(locator);
                for(i in elements){
                    if(elements[i]===$(ele)[0]){
                        return i;
                    }
                }
                return null;
            }

            for (typ in typeLoc) {
                if (typeLoc[typ] == $(ele)[0].nodeName.toLowerCase()) {
                    temp = ele;
                    attrvalue['tag']=  $(ele)[0].nodeName.toLowerCase();
                    break;
                }
            }
            for (att in attrLoc) {
                if ($(ele).attr(attrLoc[att])) {
                    attrvalue[attrLoc[att]] = $(ele).attr(attrLoc[att]);
                }
            }
            if (attrvalue.length != 0 && (temp === null)) {
                temp = ele;
                attrvalue['tag'] = $(ele)[0].nodeName.toLowerCase();
            }
            if ($(ele).text() != '') {
                attrvalue['text'] = $(ele).text();
            }
            var locator=getLocator();
            if(locator!=''){
               attrvalue['locator']=locator;
            }
            var index=getIndex(locator,ele);
            if(index!=null){
                attrvalue['index']=index;
            }
            window.irecorder.record(getAllValues());

        },true);

        var inpList=document.getElementsByTagName('input');
        var inpListLnth=inpList.length;
        for (i=0; i<inpListLnth; i++)
  		{
  			var inpItem=inpList.item(i);
  			var typeValue=inpItem.getAttribute('type');
  			if(typeValue=='password'||typeValue=='text'){
        	inpItem.addEventListener('change', function(e) {
	            var attrLoc = ['id','class','name','href'];
	            var typeLoc = ['input','img','a','select'];
	            var ele = e.target;
	            var temp = null;
	            var attrvalue = {};
		    	var data = {};
	            function getAllValues(){
	                var aValues=[];
					data['command']='enterwebtext';
					data['args[0]']=!attrvalue['locator']? '':attrvalue['locator'];
	                if(attrvalue['index']>0){
	                	data['args[1]']=attrvalue['index'];
	                	data['args[2]']=getTextValue(ele);
	                } else{
	                	data['args[1]']=getTextValue(ele);
	                }
	                for(key in attrvalue){
	           		    aValues.push('"'+key+'='+attrvalue[key]+'"');
	           	    }
					data['args[0]-data']=aValues;
	                return JSON.stringify(data);
	            }
	
	            function getLocator(){
	                var tag = attrvalue['tag'];
	                var id = !attrvalue['id'] ? '' : attrvalue['id'];
	                var name = !attrvalue['name'] ? '' : attrvalue['name'];
	                var cls = !attrvalue['class'] ? '' : attrvalue['class'].replace( /\s/g, '.');
	
	                var locator = tag + (!cls ? '' : "." + cls) + (!id ? '' : "#" + id) + (!name ? '' : '[name=\'' + name + '\']');
	
	                return locator;
	            }
	            
	            function getTextValue(ele){
					var text='';
					if ($(ele).val() != '') {
	        	        text= $(ele).val();
	            	}
	            	return text;
		    	}
	
	            function getIndex(locator,ele){
	                var elements=$(locator);
	                for(i in elements){
	                    if(elements[i]===$(ele)[0]){
	                        return i;
	                    }
	                }
	                return null;
	            }
	
	            for (typ in typeLoc) {
	                if (typeLoc[typ] == $(ele)[0].nodeName.toLowerCase()) {
	                    temp = ele;
	                    attrvalue['tag']=  $(ele)[0].nodeName.toLowerCase();
	                    break;
	                }
	            }
	            for (att in attrLoc) {
	                if ($(ele).attr(attrLoc[att])) {
	                    attrvalue[attrLoc[att]] = $(ele).attr(attrLoc[att]);
	                }
	            }
	            if (attrvalue.length != 0 && (temp === null)) {
	                temp = ele;
	                attrvalue['tag'] = $(ele)[0].nodeName.toLowerCase();
	            }
	            var locator=getLocator();
	             if(locator!=''){
	               attrvalue['locator']=locator;
	            }
	            var index=getIndex(locator,ele);
	            if(index!=null){
	                attrvalue['index']=index;
	            }
	            window.irecorder.record(getAllValues());
	        },true);  			
  			}
  		}
  		window.irecorder.recorderAdded();

    }
};