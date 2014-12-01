@var formId = formId!'save-form'; //form表单id
@var reloadUrl = reloadUrl!'false'; //是否url刷新,默认false当前右侧刷新

<div class="center" style="padding-top: 15px;">
	<button class="btn btn-primary btn-sm bigger-110" style="margin-right: 20px;" id="${formId}-save">
		<i class="ace-icon fa fa-floppy-o align-top bigger-125"></i> 确 定
	</button>
	<button class="btn btn-primary btn-sm bigger-110" style="margin-left: 20px;" id="${formId}-cancel">
		<i class="ace-icon fa fa-times align-top bigger-125"></i> 取 消
	</button>
</div>
<script type="text/javascript">
$(function(){
	$("#${formId}").Validform({
		ajaxPost : true,
		beforeSubmit:function(curform){
			var loadi = layer.load(5,2);
			$("#${formId}").data('loadi',loadi);
		},
		callback:function(data){
			layer.close($("#${formId}").data('loadi'));
			if(data>0) {
				layer.msg('操作成功', 1, 1,function(){
					if("${reloadUrl}" == "true"){
						reloadUrl();
					}else{
						$curmenu.trigger('click');
					}
					layer.closeAll();
				});
			}else{
				layer.msg('操作失败', 3, 1);
			}
		},
		tiptype : function(msg, o, cssctl) {
			if (!o.obj.is("form")) {
				if (o.type != 2) {
					layer.tips(msg, o.obj, {
						guide:0,
						time: 5,
						style : ['background-color:#F26C4F; color:#fff','#F26C4F' ],
					});
				}
			}
		},
		tipSweep : true
	});

	$("#${formId}-save").click(function() {
		$("#${formId}").submit();
		return false;
	});
	
	$("#${formId}-cancel").click(function(){
		layer.closeAll();
		return false;
	});

	$("#${formId} input,#${formId} textarea").click(function() {
		layer.closeTips();
		return false;
	});
});
</script>