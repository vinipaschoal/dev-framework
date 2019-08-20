function RenderResult() {
	
	this.Table = function(data) {
		
		var $table = $('<table>');
		$table.addClass("table table-striped table-bordered");
		
		var $thead = $('<thead>');
		var $tbody = $('<tbody>');
		var $tr = $('<tr>');

		var n = data.temperaturas.length;
		
		$.each(data.temperaturas[0], function(index, value) {
			var row = $('<th>').text(index);
			$tr.append(row);
		});
		$thead.append($tr);
		$tr = $('<tr>');
		
		$table.append($thead);
		$table.append($tbody);
		
		var $dataTable = $table.DataTable(
				{
					"language": app.settings.languagePtBr,
					"paging": false,
					"searching": false,
					"ordering": false,
			        "info":     false
				});
		
		$.each(data.temperaturas, function (index, value) {
			
			var arrValues = [];
			$.each(value, function (i, v) {
				arrValues.push(v);
			});

			$dataTable.row.add(arrValues).draw( false );
       	});
		
		return $table;
		
	},
	this.ChartBar = function(data) {
		
		var barChartsData;
		var groupData = _.groupBy(data.temperaturas, function(d){return d.latitude});
		
		var $months = ['janeiro', 'fevereiro', 'março', 'abril', 'maio', 'junho', 'julho', 'agosto', 'setembro', 'outubro', 'novembro', 'dezembro'];			
		var i = (Math.floor(Math.random() * (Object.keys(groupData).length - 1 + 1)) + 1) - 1;
		
		var count = 0;
		$.each(groupData, function(index, value) {
		
			if (count == i){
				console.log(i);
				var $dataMaxima = [];
				var $dataMinima = [];
				$.each(value, function(i, v) {
					$dataMaxima.push(v.maxima);
					$dataMinima.push(v.minima);
				});
				barChartsData = {
			        labels: $months,
			        datasets: [
						{
				            label: 'Máximas',
				            backgroundColor: '#36a2eb',
							borderColor: '#36a2eb',
							borderWidth: 1,
				            data: $dataMaxima
				        },
				        {
				            label: 'Mínimas',
				            backgroundColor: '#ff6384',
							borderColor: '#ff6384',
							borderWidth: 1,
				            data: $dataMinima
				        }
					]
			    };
			}
			count++;

		});
		
		var $canvas = document.createElement("canvas");
		$canvas.id = "chartBar";
		$canvas.className = "col-md-12";
		
		var myChart = new Chart($canvas, {
		    type: 'bar',
		    data: barChartsData,
		});
		
		return $canvas;
		
	},
	this.Map = function(data) {
		console.log(data);
	}
}