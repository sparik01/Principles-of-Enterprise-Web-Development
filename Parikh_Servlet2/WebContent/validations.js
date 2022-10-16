/**
 * Validation functions
 */

//validating that the number of people in the party between and including 1 and 10
	function validateParty(){
		 // Get the value of the input field with id="partySize"
		var size = document.getElementById("partySize");
		
		if (size.value < 1 || size.value > 10){
			alert("Please enter a party size between 1 and 10.");
			return false;
		} else {
			return true;
			
		}
	}
	
//validating that a tour is selected
	function validateTour() {
		var n1 = document.getElementById('tour').value;
		
		if(n1==""){
			alert("Please select a tour.");
			return false
		}
		return true;
	}

	//validating that a duration is selected
	function validateDuration() {
		var n1 = document.getElementById('duration').value;
		
		if(n1==""){
			alert("Please select a duration.");
			return false
		}
		return true;
	}
	
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

