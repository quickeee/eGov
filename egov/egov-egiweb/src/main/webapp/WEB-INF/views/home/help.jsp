<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2015>  eGovernments Foundation
  ~
  ~     The updated version of eGov suite of products as by eGovernments Foundation
  ~     is available at http://www.egovernments.org
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program. If not, see http://www.gnu.org/licenses/ or
  ~     http://www.gnu.org/licenses/gpl.html .
  ~
  ~     In addition to the terms of the GPL license to be adhered to in using this
  ~     program, the following additional terms are to be complied with:
  ~
  ~         1) All versions of this program, verbatim or modified must carry this
  ~            Legal Notice.
  ~
  ~         2) Any misrepresentation of the origin of the material is prohibited. It
  ~            is required that all modified versions of this material be marked in
  ~            reasonable ways as different from the original version.
  ~
  ~         3) This license does not grant any rights to any user of the program
  ~            with regards to rights under trademark law for use of the trade names
  ~            or trademarks of eGovernments Foundation.
  ~
  ~   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>
<style>
html,body{
	background:#fff;
}
.page-container .main-content{
    position: absolute;
    height: calc(100%);
}
</style>
<script>
$(document).ready(function(){

	$('.source').click(function(){
		$('li').removeClass('tab-current-newreq');
		$(this).addClass('tab-current-newreq');
		var src = $(this).data('src');
		var fn = window[src];
		if (typeof fn === "function") fn(src);
	});
	
});

function property(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'http://www.egovernments.org/docs/usermanuals/ap/ptis.pdf');
}


function water(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'http://www.egovernments.org/docs/usermanuals/ap/wcms.pdf');
}

function grievance(src){
	//bootbox.alert(src);
	$('#getiframe').attr('src', 'http://www.egovernments.org/docs/usermanuals/ap/pgrs.pdf');
}

</script>
<div class="row text-center" style="height:calc(100%);">

	<div class="citizen-screens tabs tabs-style-topline">
		<nav>
			<ul>
				<li class="tab-current-newreq source" data-src="property" tooltip="Property and Vacant Land Tax">
					<a href="javascript:void(0);">
						<div class="text-center"><i class="fa fa-building-o"></i></div>
						<span class="hidden-xs">Property and Vacant Land Tax</span>
					</a>
				</li>
				<li class="source" data-src="water">
					<a href="javascript:void(0);">
						<div class="text-center"><i class="fa fa-tint"></i></div>
						<span class="hidden-xs">Water Charges</span>
					</a>
				</li>
				<li class="source" data-src="grievance">
					<a href="javascript:void(0);">
						<div class="text-center"><i class="fa fa-pencil"></i></div>
						<span class="hidden-xs">Public Grievance and Redressal System</span>
					</a>
				</li>
			</ul>
		</nav>
	</div>
	
	<div class="col-md-12" style="height:calc(100%);margin-top:10px;margin-bottom:20px;">
		<iframe id="getiframe" src="http://www.egovernments.org/docs/usermanuals/ap/ptis.pdf" width="100%" height="100%">
	</div>
	
</div>