function RenderResult() {

	this.PLAIN = function(data, place) {
		
		//Se objeto, imprime
		if ($.isPlainObject(data)) {
			place.html(JSON.stringify(data, null, 2));
		//Se array, lista
		} else if ($.isArray(data)) {
			var $str = "";
			var $length = data.length;
			$.each(data, function(index, value) {
				//Se array de objeto, imprime
				if ($.isPlainObject(value))
					$str = $str + JSON.stringify(value, null, 2);
				else
					$str = $str + value;
				
				if ($length != index)
					$str = $str + "<br/>";
			});
			
			place.html($str);
		}else{
			place.html(data);
		}	
	},
	
	this.TABLE = function(data, place) {
		
		var $table = $('<table>');
		$table.addClass("table table-striped table-bordered");
		
		var $thead = $('<thead>');
		var $tr = $('<tr>');
		
		$.each(data.header, function(index, value) {
			var $row = $('<th>').text(value);
			$tr.append($row);
		});
		$thead.append($tr);
		$tr = $('<tr>');
		$table.append($thead);
				
		var $tbody = $('<tbody>');
		
		$.each(data.rows, function (index, value) {	
			$tr = $('<tr>');
			$.each(value, function (i, v) {
				
				var $row = $('<td>').text(v);
				$tr.append($row);
			});
			$tbody.append($tr);
       	});		
		$table.append($tbody);
		
		place.html($table);
		
		// verifica se tem linhas na tabela
		if ( data.rows.length > 0 ){
			$table.DataTable(
					{
						"language": app.settings.languagePtBr,
						"paging": true,
						"pageLength": 10,
						"searching": true,
						"ordering": false,
				        "info":     true,
				        "fnInitComplete": function(oSettings) {
				        	if (!data.showHeader)
				        		$table.children("thead").hide();
				        }
					});
		}
	},
	
	this.CHART = function(data, place) {
		
		var $canvas = document.createElement("canvas");
		$canvas.id = "chart";
		$canvas.className = "col-md-12";
		
		var myChart = new Chart($canvas, data);
		
		place.html($canvas);		
	},
	
	this.MAP = function(data, place) {
		console.log(data);
		
		$mapPlace = $('<div id="map"></div>');
		$mapPlace.css("height", 800);
		place.append($mapPlace);

		var $idResult = place.attr("id");
			
		// Render map
		var mymap = L.map('map').setView([data.latitude, data.longitude], data.initialZoom);
		
		L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoiZmt2aWxsYW5pIiwiYSI6ImNqenQ2YTliejAyNDczbG1qeW40aGpid2YifQ.rQwyF9X2CuV1s7vjS1G9oA', {
			attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
//			maxZoom: 18,
			id: 'mapbox.outdoors',
			accessToken: 'pk.eyJ1IjoiZmt2aWxsYW5pIiwiYSI6ImNqenQ2YTliejAyNDczbG1qeW40aGpid2YifQ.rQwyF9X2CuV1s7vjS1G9oA'
		}).addTo(mymap);
		
		$.each(data.markers, function(index, value) {
			var marker = L.marker([value.latitude, value.longitude]).addTo(mymap);
			marker.bindPopup(value.popup);
		});
		
		// DEBUG: mostra lat/long ao clicar no mapa
//		mymap.on('click', function(e) {
//			alert('Lat: ' + e.latlng.lat + ' , Long: ' + e.latlng.lng);
//		});
		
	}
}