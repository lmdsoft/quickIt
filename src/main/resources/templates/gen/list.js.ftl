$(function () {
    $("#jqGrid").jqGrid({
        url: '../${module}/${pathName}/list',
        datatype: "json",
        colModel: [
<#list  columns as column>
<#if column.columnName == pk.columnName>
			{ label: '${column.attrname}', name: '${column.attrname}', index: '${column.columnName}', width: 50, key: true },
<#else>
			{ label: '${column.comments}', name: '${column.attrname}', index: '${column.columnName}', width: 80 }<#if column_has_next>,</#if>

</#if>
</#list>
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		${classname}: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.${classname} = {};
		},
		update: function (event) {
			var ${pk.attrname} = getSelectedRow();
			if(${pk.attrname} == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(${pk.attrname})
		},
		saveOrUpdate: function (event) {
			var url = vm.${classname}.${pk.attrname} == null ? "../${module}/${pathName}/save" : "../${module}/${pathName}/update";
			$.ajax({
				type: "POST",
			    url: url,
			    data: JSON.stringify(vm.${classname}),
			    success: function(r){
			    	if(r.code === 0){
						alert(r, function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ${pk.attrname}s = getSelectedRows();
			if(${pk.attrname}s == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: "../${module}/${pathName}/delete",
				    data: JSON.stringify(${pk.attrname}s),
				    success: function(r){
						if(r.code == 0){
							alert(r, function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(${pk.attrname}){
			$.get("../${module}/${pathName}/info/"+${pk.attrname}, function(r){
                if(r.code == 0){
                    vm.${classname} = r.${classname};
                }else{
                    alert(r.msg);
                }
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});