var eb = new vertx.EventBus(window.location.protocol + '//' +
    window.location.hostname + ':' + 
    window.location.port + '/eventbus');

eb.onopen = function() {
	var renderListItem = function(mindMap) {
		var li = $('<li>');
		var deleteMindMap = function() {
			eb.send('mindMaps.delete', {id: mindMap.id}, function() {
				li.remove();
			});
			return false;
		};
		$('<span>').text(mindMap.name).appendTo(li);
		$('<button>').text('삭제').on('click', deleteMindMap).appendTo(li);
		li.appendTo('.mind-maps');
	};
	
	var deleteMindMap = function() {
		var li = $('<li>');

	}
	
	$('.create-form').submit(function() {
		var nameInput = $('[name=name]', this);
		eb.send('mindMaps.save', {name: nameInput.val()}, function(result) {
			renderListItem($.parseJSON(result));
			nameInput.val('');
		});
		return false;
	});
	
	eb.send('mindMaps.list', {}, function(res) {
		var mindMaps = $.parseJSON(res);
		$.each(mindMaps, function() {
			renderListItem(this);
		});
	});
};
