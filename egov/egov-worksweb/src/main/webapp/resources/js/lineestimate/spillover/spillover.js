/*#-------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency, 
#    accountability and the service delivery of the government  organizations.
# 
#     Copyright (C) <2015>  eGovernments Foundation
# 
#     The updated version of eGov suite of products as by eGovernments Foundation 
#     is available at http://www.egovernments.org
# 
#     This program is free software: you can redistribute it and/or modify
#     it under the terms of the GNU General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     any later version.
# 
#     This program is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU General Public License for more details.
# 
#     You should have received a copy of the GNU General Public License
#     along with this program. If not, see http://www.gnu.org/licenses/ or 
#     http://www.gnu.org/licenses/gpl.html .
# 
#     In addition to the terms of the GPL license to be adhered to in using this
#     program, the following additional terms are to be complied with:
# 
# 	1) All versions of this program, verbatim or modified must carry this 
# 	   Legal Notice.
# 
# 	2) Any misrepresentation of the origin of the material is prohibited. It 
# 	   is required that all modified versions of this material be marked in 
# 	   reasonable ways as different from the original version.
# 
# 	3) This license does not grant any rights to any user of the program 
# 	   with regards to rights under trademark law for use of the trade names 
# 	   or trademarks of eGovernments Foundation.
# 
#   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
#-------------------------------------------------------------------------------*/
$deletedAmt = 0;
$locationId = 0;
$subTypeOfWorkId = 0;
$detailsRowCount = $('#detailsSize').val();

$(document).ready(function(){
	
	$locationId = $('#locationValue').val();
	$('#wardInput').trigger('blur');
	$subTypeOfWorkId = $('#subTypeOfWorkValue').val();
	$('#typeofwork').trigger('blur');
	$('#subTypeOfWork').trigger('blur');
	
	if($("#isBillsCreatedInput").val() == 'true') {
		$(".thGrossAmount").show();
		$(".tdGrossAmount").each(
				function() {
					$(this).find('input').attr('required', 'required');
					$(this).find('input').attr('data-optional', '0');
					$(this).show();
		});
	}
	$('#designation').val($('#designationValue').val());
	$('#designation').trigger('change');
	
	var authorityValue = $('#authorityValue').val();
	$('#authority option').each(function() {
		var value = $(this).val();
		if(value == authorityValue)
			$(this).attr('selected', 'selected');
	});


	return showSlumFieldsValue();
});

function renderPdf() {
	var id = $('#lineEstimateId').val();
	window.open("/egworks/lineestimate/lineEstimatePDF/" + id, '', 'height=650,width=980,scrollbars=yes,left=0,top=0,status=yes');
}

$('#Save').click(function(){
	var button = $(this).attr('id');
	var status = false;
	$("#lineEstimateForm").find('input, select, textarea, radio').each(function() {
		if($(this).attr('required') == 'required' && $(this).val() == '') {
			status = true;
		}
	});
	if (button != null && button == 'Save' && !status) {
		var flag = true;
		
		var adminSanctionDate = $('#adminSanctionDate').data('datepicker').date;
		var technicalSanctionDate = $('#technicalSanctionDate').data('datepicker').date;
		var technicalSanctionNumber = $('#technicalSanctionNumber').val();
		
		if(adminSanctionDate > technicalSanctionDate && technicalSanctionDate != '') {
			bootbox.alert($('#errorTechDate').val());
			$('#technicalSanctionDate').val("");
			return false;
		}

		var message = $('#errorActualAmount').val() + " ";

		$("input[name$='actualEstimateAmount']")
		.each(
				function() {
					var index = getRow(this).rowIndex - 1;
					var estimateAmount = $(
							'#estimateAmount' + index).val();
					var actualAmount = $(
							'#actualEstimateAmount' + index).val();
					if (parseFloat(estimateAmount.trim()) < parseFloat(actualAmount)) {
						var estimateNumber = $(
								'#estimateNumber' + index).val();
						message += estimateNumber + ", ";
						flag = false;
					}
				});
		message = message.replace(/,\s*$/, ". ");
		message += $('#errorActualAmountContinued').val();
		if (!flag) {
			bootbox.alert(message);
			return false;
		}
		
		message = $('#errorGrossBilledAmount').val() + " ";
		
		$("input[name$='grossAmountBilled']")
		.each(
				function() {
					var index = getRow(this).rowIndex - 1;
					var grossBilledAmount = $(
							'#grossAmountBilled' + index).val();
					var actualAmount = $(
							'#actualEstimateAmount' + index).val();
					if (parseFloat(grossBilledAmount) > parseFloat(actualAmount)) {
						var estimateNumber = $(
								'#estimateNumber' + index).val();
						message += estimateNumber + ", ";
						flag = false;
					}
				});
		message = message.replace(/,\s*$/, ". ");
		message += $('#errorActualAmountContinued').val();
		if (!flag) {
			bootbox.alert(message);
			return false;
		}
		
		if($('#isBillsCreated').prop("checked") == true && $('#isWorkOrderCreated').prop("checked") == false) {
			bootbox.alert($('#msgWorkOrderCreated').val());
			return false;
		}
		
		if(!flag)
			return false;
	}
	else {
		validateWorkFlowApprover(button);
	}
});

function validateEstimateNumber(obj) {
	$("input[name$='estimateNumber']")
	.each(
			function() {
				if($(this).val() == $(obj).val() && $(this).attr('name') != $(obj).attr('name')) {
					bootbox.alert("Estimate Numbers should be uniique");
					$(obj).val("");
				}
			});
}

function validateWINNumber(obj) {
	$("input[name$='projectCode.code']")
	.each(
			function() {
				if($(this).val() == $(obj).val() && $(this).attr('name') != $(obj).attr('name')) {
					bootbox.alert("WIN Numbers should be uniique");
					$(obj).val("");
				}
			});
}

$('#isBillsCreated').click(function() {
	if($(this).prop("checked") == true) {
		$(".thGrossAmount").show();
		$(".tdGrossAmount").each(
				function() {
					$(this).find('input').attr('required', 'required');
					$(this).find('input').attr('data-optional', '0');
					$(this).show();
		});
	} else {
		$(".thGrossAmount").hide();
		$(".tdGrossAmount").each(
				function() {
					$(this).find('input').val("");
					$(this).find('input').removeAttr('required');
					$(this).find('input').attr('data-optional', '1');
					$(this).hide();
		});
	}
});

$('#designation').change(function(){
	$.ajax({
		url: "../lineestimate/ajax-assignmentByDepartmentAndDesignation",     
		type: "GET",
		data: {
			approvalDesignation : $('#designation').val(),
			approvalDepartment : $('#executingDepartments').val()    
		},
		dataType: "json",
		success: function (response) {
			$('#authority').empty();
			$('#authority').append($("<option value=''>Select from below</option>"));
			$.each(response, function(index, value) {
				$('#authority').append($('<option>').text(value.name).attr('value', value.id));  
			});
			var authorityValue = $('#authorityValue').val();
			$('#authority').val(authorityValue);
		}, 
		error: function (response) {
			console.log("failed");
		}
	});
});

function getSubSchemsBySchemeId(schemeId) {
	if ($('#scheme').val() === '') {
		   $('#subScheme').empty();
		   $('#subScheme').append($('<option>').text('Select from below').attr('value', ''));
			return;
			} else {
				$.ajax({
					url: "../lineestimate/getsubschemesbyschemeid/"+schemeId,     
					type: "GET",
					dataType: "json",
					success: function (response) {
						$('#subScheme').empty();
						$('#subScheme').append($("<option value=''>Select from below</option>"));
						var responseObj = JSON.parse(response);
						$.each(responseObj, function(index, value) {
							$('#subScheme').append($('<option>').text(responseObj[index].name).attr('value', responseObj[index].id));
						});
					}, 
					error: function (response) {
						console.log("failed");
					}
				});
			}
}

function addLineEstimate() {
	var rowcount = $("#tblestimate tbody tr").length;
	if (rowcount < 30) {
		if (document.getElementById('estimateRow') != null) {
			// get Next Row Index to Generate
			var nextIdx = 0;
			if($detailsRowCount == 0)
				nextIdx = $("#tblestimate tbody tr").length;
			else
				nextIdx = $detailsRowCount++;
			
			
			
            var estimateNo = (new Date()).valueOf();
			// validate status variable for exiting function
			var isValid = 1;// for default have success value 0

			// validate existing rows in table
			$("#tblestimate tbody tr").find('input, select, textarea').each(
					function() {
						if (($(this).data('optional') === 0)
								&& (!$(this).val())) {
							$(this).focus();
							bootbox.alert($(this).data('errormsg'));
							isValid = 0;// set validation failure
							return false;
						}
			});

			if (isValid === 0) {
				return false;
			}
			
			// Generate all textboxes Id and name with new index
			$("#estimateRow").clone().find("input, errors, textarea").each(
					function() {

						if ($(this).data('server')) {
							$(this).removeAttr('data-server');
						}
						
							$(this).attr(
									{
										'id' : function(_, id) {
											return id.replace(/\d+/, nextIdx);
										},
										'name' : function(_, name) {
											return name.replace(/\d+/, nextIdx);
										},
										'data-idx' : function(_,dataIdx)
										{
											return nextIdx;
										}
									});

							// if element is static attribute hold values for
							// next row, otherwise it will be reset
							if (!$(this).data('static')) {
								$(this).val('');
								// set default selection for dropdown
								if ($(this).is("select")) {
									$(this).prop('selectedIndex', 0);
								}
							}
							
							$(this).attr('readonly', false);
							$(this).removeAttr('disabled');
							$(this).prop('checked', false);

					}).end().appendTo("#tblestimate tbody");
			
			generateSno();
			
		}
	} else {
		  bootbox.alert('limit reached!');
	}
}


function getRow(obj) {
	if(!obj)return null;
	tag = obj.nodeName.toUpperCase();
	while(tag != 'BODY'){
		if (tag == 'TR') return obj;
		obj=obj.parentNode ;
		tag = obj.nodeName.toUpperCase();
	}
	return null;
}

function generateSno()
{
	var idx=1;
	$(".spansno").each(function(){
		$(this).text(idx);
		idx++;
	});
}


function deleteLineEstimate(obj) {
    var rIndex = getRow(obj).rowIndex;
    
    var id = $(getRow(obj)).children('td:first').children('input:first').val();
    //To get all the deleted rows id
    var aIndex = rIndex - 1;
    if(!$("#removedLineEstimateDetailsIds").val()==""){
		$("#removedLineEstimateDetailsIds").val($("#removedLineEstimateDetailsIds").val()+",");
	}
    $("#removedLineEstimateDetailsIds").val($("#removedLineEstimateDetailsIds").val()+id);

	var tbl=document.getElementById('tblestimate');	
	var rowcount=$("#tblestimate tbody tr").length;

    if(rowcount<=1) {
		bootbox.alert("This row can not be deleted");
		return false;
	} else {
		tbl.deleteRow(rIndex);
		//starting index for table fields
		var idx=parseInt($detailsRowCount);
		
		generateSno();
		
		//regenerate index existing inputs in table row
		$("#tblestimate tbody tr").each(function() {
		
			hiddenElem=$(this).find("input:hidden");
			
			if(!$(hiddenElem).val())
			{
				$(this).find("input, errors, textarea").each(function() {
					   $(this).attr({
					      'name': function(_, name) {
					    	  return name.replace(/\[.\]/g, '['+ idx +']'); 
					      },
						  'data-idx' : function(_,dataIdx)
						  {
							  return idx;
						  }
					   });
			    });
				idx++;
			}
		});
		calculateEstimatedAmountTotal();
		return true;
	}	
}

function calculateEstimatedAmountTotal(){
	validateEstimateAmount();
	var estimateTotal=0;
	$( "input[name$='estimateAmount']" ).each(function(){
		estimateTotal = estimateTotal + parseFloat(($(this).val()?$(this).val():"0"));
	});
	$('#estimateTotal').html(estimateTotal);
}

function calculateActualEstimatedAmountTotal(obj) {
	decimalvalue(obj);
	var actualEstimateTotal = 0;
	$( "input[name$='actualEstimateAmount']" ).each(function(){
		actualEstimateTotal = actualEstimateTotal + parseFloat(($(this).val()?$(this).val():"0"));
	});
	$('#actualEstimateTotal').html(actualEstimateTotal);
}

function validateEstimateAmount() {
	$( "input[name$='estimateAmount']" ).on("keyup", function(){
	    var valid = /^[1-9](\d{0,9})(\.\d{0,2})?$/.test(this.value),
	        val = this.value;
	    
	    if(!valid){
	        console.log("Invalid input!");
	        this.value = val.substring(0, val.length - 1);
	    }
	});
}

$('#wardInput').blur(function(){
	   if ($('#ward').val() === '') {
		   $('#locationBoundary').empty();
		   $('#locationBoundary').append($('<option>').text('Select from below').attr('value', ''));
			return;
		} else {
			$.ajax({
				type: "GET",
				url: "/egworks/lineestimate/ajax-getlocation",
				cache: true,
				dataType: "json",
				data:{'id' : $('#ward').val()}
			}).done(function(value) {
				console.log(value);
				$('#locationBoundary').empty();
				$('#locationBoundary').append($('<option>').text('Select from below').attr('value', ''));
				$.each(value, function(index, val) {
					var selected="";
					if($locationId)
					{
						if($locationId==val.id)
						{
							selected="selected";
						}
					}
				    $('#locationBoundary').append($('<option '+ selected +'>').text(val.name).attr('value', val.id));
				});
			});
		}
	});

function disableSlumFields() {
	var slum = document.getElementById("slum");
	var slumfields = document.getElementById("slumfields");
	slumfields.style.display = slum.checked ? "block" : "none";
	document.getElementById("typeOfSlum").disabled = true;
	document.getElementById("beneficiary").disabled = true;
	$('#typeOfSlum').removeAttr('required');
	$('#beneficiary').removeAttr('required');
	$('#nonslum').attr('checked', 'checked');
}

function showSlumFields() {
	replaceTypeOfSlumChar();
	replaceBeneficiaryChar();
	var slum = document.getElementById("slum");
	var slumfields = document.getElementById("slumfields");
	slumfields.style.display = slum.checked ? "block" : "none";
	document.getElementById("typeOfSlum").disabled = false;
	document.getElementById("beneficiary").disabled = false;
	$('#typeOfSlum').attr('required', 'required');
	$('#beneficiary').attr('required', 'required');
	$('#slum').attr('checked', 'checked');
	
}

function replaceTypeOfSlumChar() {
	$('#typeOfSlum option').each(function() {
	   var $this = $(this);
	   $this.text($this.text().replace(/_/g, ' '));
	});
}

function replaceBeneficiaryChar() {
	$('#beneficiary option').each(function() {
	   var $this = $(this);
	   $this.text($this.text().replace(/_/g, '/'));
	});
}


$('#typeofwork').blur(function(){
	 if ($('#typeofwork').val() === '') {
		   $('#subTypeOfWork').empty();
		   $('#subTypeOfWork').append($('<option>').text('Select from below').attr('value', ''));
			return;
			} else {
			$.ajax({
				type: "GET",
				url: "/egworks/lineestimate/getsubtypeofwork",
				cache: true,
				dataType: "json",
				data:{'id' : $('#typeofwork').val()}
			}).done(function(value) {
				console.log(value);
				$('#subTypeOfWork').empty();
				$('#subTypeOfWork').append($("<option value=''>Select from below</option>"));
				$.each(value, function(index, val) {
					var selected="";
					if($subTypeOfWorkId)
					{
						if($subTypeOfWorkId==val.id)
						{
							selected="selected";
						}
					}
				     $('#subTypeOfWork').append($('<option '+ selected +'>').text(val.description).attr('value', val.id));
				});
			});
		}
	});

$(document).ready(function(){
    var ward = new Bloodhound({
        datumTokenizer: function (datum) {
            return Bloodhound.tokenizers.whitespace(datum.value);
        },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: '/egworks/lineestimate/ajax-getward?name=%QUERY',
            filter: function (data) {
                return $.map(data, function (ct) {
                    return {
                        name: ct.name,
                        value: ct.id
                    };
                });
            }
        }
    });
    
    ward.initialize();
	var ward_typeahead = $('#wardInput').typeahead({
		hint : false,
		highlight : false,
		minLength : 3
	}, {
		displayKey : 'name',
		source : ward.ttAdapter(),
	});
	
	typeaheadWithEventsHandling(ward_typeahead,
	'#ward');
});

function validateQuantity() {
	$( "input[name$='quantity']" ).on("keyup", function(){
	    var valid = /^[1-9](\d{0,9})(\.\d{0,2})?$/.test(this.value),
	        val = this.value;
	    
	    if(!valid){
	        console.log("Invalid input!");
	        this.value = val.substring(0, val.length - 1);
	    }
	});
}

function validateadminSanctionNumber() {
	$( "input[name$='adminSanctionNumber']" ).on("keyup", function(){
		var valid = /^[a-zA-Z0-9\\/-]*$/.test(this.value),
	        val = this.value;
	    if(!valid){
	        console.log("Invalid input!");
	        this.value = val.substring(0, val.length - 1);
	    }
	});
}

function validatecouncilResolutionNumber() {
	$( "input[name$='councilResolutionNumber']" ).on("keyup", function(){
		var valid = /^[a-zA-Z0-9\\/-]*$/.test(this.value),
	        val = this.value;
	    if(!valid){
	        console.log("Invalid input!");
	        this.value = val.substring(0, val.length - 1);
	    }
	});
}

function showSlumFieldsValue() {
	var slum = $('#radioValue input:radio:checked').val()
	if ('SLUM_WORK' == slum) {
		showSlumFields();
		return true;
	} else if('NON_SLUM_WORK' == slum){
		disableSlumFields();
		return true;
	} else
		return false;
}

function validateWorkFlowApprover(name) {
	document.getElementById("workFlowAction").value = name;
	var approverPosId = document.getElementById("approvalPosition");
	var button = document.getElementById("workFlowAction").value;
	if (button != null && button == 'Submit') {
		$('#approvalDepartment').attr('required', 'required');
		$('#approvalDesignation').attr('required', 'required');
		$('#approvalPosition').attr('required', 'required');
		$('#approvalComent').removeAttr('required');
		
		var lineEstimateStatus = $('#lineEstimateStatus').val();
		if(lineEstimateStatus == 'BUDGET_SANCTIONED') {
			var lineEstimateDate = $('#lineEstimateDate').val();
			var councilResolutionDate = $('#councilResolutionDate').val()
			if (councilResolutionDate != "") {
				if (councilResolutionDate < lineEstimateDate) {
					bootbox.alert($('#errorCouncilResolutionDate').val());
					$('#councilResolutionDate').val("");
					return false;
				}
				return true;
			}
		}
	}
	if (button != null && button == 'Reject') {
		$('#approvalDepartment').removeAttr('required');
		$('#approvalDesignation').removeAttr('required');
		$('#approvalPosition').removeAttr('required');
		$('#approvalComent').attr('required', 'required');
		$('#adminSanctionNumber').removeAttr('required');
	}
	if (button != null && button == 'Cancel') {
		$('#approvalDepartment').removeAttr('required');
		$('#approvalDesignation').removeAttr('required');
		$('#approvalPosition').removeAttr('required');
		$('#approvalComent').attr('required', 'required');
		
		bootbox.confirm($('#confirm').val(), function(result) {return result});
	}
	if (button != null && button == 'Forward') {
		$('#approvalDepartment').attr('required', 'required');
		$('#approvalDesignation').attr('required', 'required');
		$('#approvalPosition').attr('required', 'required');
		$('#approvalComent').removeAttr('required');
	}

	document.forms[0].submit;
	return true;
}
