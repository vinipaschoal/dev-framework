function RenderResult() {
	
	this.Table = function(data) {
		console.log(data);
	},
	this.ChartBar = function(data) {
		console.log(data);
		
		var $canvas = document.createElement("canvas");
		$canvas.id = "chartBar";
		$canvas.className = "col-md-12";
		
		/*var dynamicColors = function() {
            var r = Math.floor(Math.random() * 255);
            var g = Math.floor(Math.random() * 255);
            var b = Math.floor(Math.random() * 255);
            return "rgb(" + r + "," + g + "," + b + ")";
         };
         
         for (var i in data) {
             ict_unit.push("ICT Unit " + data[i].ict_unit);
             efficiency.push(data[i].efficiency);
             coloR.push(dynamicColors());
          }
		*/
		var myChart = new Chart($canvas, {
		    type: 'bar',
		    data: data,
		});
		
		return $canvas;
		
	},
	this.Map = function(data) {
		console.log(data);
	}
}