$(document).ready(function(){
	
	$('#statusdiv').hide();
	var activeDiv = $('#reqAttr').val();
	if (activeDiv =='false' && 'true'){
		$('#statusdiv').hide();
	     $('#addnewid').hide();
		}
	
	else if(activeDiv=='true'){
		$('#resetid').hide();
		$('#statusdiv').show();
		 $('#addnewid').show();
		}
	
	$("#resetid").click(function(){
		$("#propertypipesizeform")[0].reset();
		window.open("/wtms/masters/propertyPipeSizeMaster/", "_self");
		})
	
 });

$('#listid').click(function() {
	window.open("/wtms/masters/propertyPipeSizeMaster/list", "_self");
 });

$('#addnewid').click(function() {
	window.open("/wtms/masters/propertyPipeSizeMaster/", "_self");
});

function addNew()
{
	window.open("/wtms/masters/propertyPipeSizeMaster/", "_self");
}

function edit(propertyPipeSize)
{
	
	window.open("/wtms/masters/propertyPipeSizeMaster/"+propertyPipeSize, "_self");
	
}
