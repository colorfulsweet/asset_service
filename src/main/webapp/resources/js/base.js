(function($){

var sysFunction = function(){
	/**
	 * 重置表单输入框内容
	 * @param target 目标按钮DOM对象
	 */
	this.resetForm = function(target){
		var form = $(target).parents("form");
		var inputs = form.find("input,select");
		inputs.each(function(index, item){
			$(item).val("");
		});
	};
	
}
	
$.extend(new sysFunction);
})(jQuery);