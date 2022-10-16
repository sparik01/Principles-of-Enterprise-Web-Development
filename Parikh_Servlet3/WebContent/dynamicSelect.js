/**
 * Functions populating dynamic drop down boxes
 * 
 * Reference of tutorial that included code that helped with the dynamic drop down boxes: 
 * http://www.developphp.com/video/JavaScript/Form-Select-Change-Dynamic-List-Option-Elements-Tutorial
 * 
 */
//populating drop down box for number of days in a month
	function generateDay(d1,d2){
		var d1 = document.getElementById(d1);
		var d2 = document.getElementById(d2);
		d2.innerHTML = "";
		if(d1.value == "6"){
			var optionArray = ["|Day","01|1","02|2","03|3","04|4","05|5","06|6","07|7","08|8","09|9","10|10",
				"11|11","12|12","13|13","14|14","15|15","16|16","17|17","18|18","19|19","20|20"
				,"21|21","22|22","23|23","24|24","25|25","26|26","27|27","28|28","29|29","30|30"];
		} else if(d1.value == "7"){
			var optionArray = ["|Day","01|1","02|2","03|3","04|4","05|5","06|6","07|7","08|8","09|9","10|10",
				"11|11","12|12","13|13","14|14","15|15","16|16","17|17","18|18","19|19","20|20"
				,"21|21","22|22","23|23","24|24","25|25","26|26","27|27","28|28","29|29","30|30","31|31"];
		} else if(d1.value == "8"){
			var optionArray = ["|Day","01|1","02|2","03|3","04|4","05|5","06|6","07|7","08|8","09|9","10|10",
				"11|11","12|12","13|13","14|14","15|15","16|16","17|17","18|18","19|19","20|20"
				,"21|21","22|22","23|23","24|24","25|25","26|26","27|27","28|28","29|29","30|30","31|31"];
		} else if(d1.value == "9"){
			var optionArray = ["|Day","01|1","02|2","03|3","04|4","05|5","06|6","07|7","08|8","09|9","10|10",
				"11|11","12|12","13|13","14|14","15|15","16|16","17|17","18|18","19|19","20|20"
				,"21|21","22|22","23|23","24|24","25|25","26|26","27|27","28|28","29|29","30|30"];
		}
		
		for(var option in optionArray){
			var pair = optionArray[option].split("|");
			var newOption = document.createElement("option");											1
			newOption.value = pair[0];
			newOption.innerHTML = pair[1];
			d2.options.add(newOption);
		}
	}