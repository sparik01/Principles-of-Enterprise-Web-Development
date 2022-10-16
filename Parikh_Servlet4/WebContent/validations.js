/**
 * Validation functions
 */


	//validating that a month is selected
	function validateMonth() {
		var n1 = document.getElementById('month').value;
		
		if(n1==""){
			alert("Please select a month.");
			return false
		}
		return true;
	}
	
	//validating that a day is selected
	function validateDay() {
		var n1 = document.getElementById('day').value;
		
		if(n1==""){
			alert("Please select a day of the month.");
			return false
		}
		return true;
	}
	
	//validating that a year is selected
	function validateYear() {
		var n1 = document.getElementById('year').value;
		
		if(n1==""){
			alert("Please select a year.");
			return false
		}
		return true;
	}
