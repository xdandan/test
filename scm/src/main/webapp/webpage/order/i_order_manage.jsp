<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>订单管理</title>
      <%@include file="../common/head.jsp"%>
          <link  rel="stylesheet"  href="<%=basePath %>res/css/extIcon.css"/>
<script type="text/javascript">
    Ext.Loader.setConfig({enabled: true});
                                                    
      Ext.Loader.setPath('Ext.ux', '/res/js/ux');

    Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.toolbar.*',
    'Ext.window.MessageBox',
    'Ext.ux.ProgressBarPager',
    'Ext.state.*'
]);

Ext.override(Ext.grid.RowEditor, {
      syncButtonPosition : function(scrollDelta) {
    var me = this, floatingButtons = me.getFloatingButtons(), scrollingViewElDom = me.scrollingViewEl.dom, myscrollHeight = Math
      .max(scrollingViewElDom.scrollHeight, scrollingViewElDom.clientHeight), overflow = this.getScrollDelta()
      - (myscrollHeight - scrollingViewElDom.scrollTop - scrollingViewElDom.clientHeight);
    if (overflow > 0) {
     if (!me._buttonsOnTop) {
      floatingButtons.setButtonPosition('top');
      me._buttonsOnTop = true;
     }
     scrollDelta = 0;
    } else if (me._buttonsOnTop) {
     floatingButtons.setButtonPosition('bottom');
     me._buttonsOnTop = false;
    }
    return scrollDelta;
   }
  });
  

  
Ext.onReady(function() {
    Ext.QuickTips.init();
    
    
    var selectData=[
                        ['ok','ok'],
                        ['进行中','进行中'],
                        ['未生产','未生产']
                        ];
    var selectData1=[
                    ['ok','ok'],
                    ['进行中','进行中'],
                    ['未生产','未生产'],
                    ['无此工序','无此工序']
                    ];
    
    
     Ext.define('Order', {  
         extend: 'Ext.data.Model',  
         proxy: {  
             type: 'ajax',  
             reader: 'json'  
         },  
         fields: [  
          {name: 'id'},
         {name: 'orderNo'},
         {name: 'style'},
          {name: 'orderColor'},
          {name: 'orderNumber'},
           {name: 'orderDate', type: 'date', dateFormat: 'Y-m-d'},
           {name: 'deliveryDate', type: 'date', dateFormat: 'Y-m-d'},
           {name: 'supOrderNo',mapping:'supOrderWork.supOrderNo'},
           {name: 'supDate',mapping:'supOrderWork.supDate',type: 'date', dateFormat: 'Y-m-d'},
           {name: 'bl',mapping:'supOrderWork.bl'},
           {name: 'js',mapping:'supOrderWork.js'},
           {name: 'quan',mapping:'supOrderWork.quan'},
           {name: 'bi',mapping:'supOrderWork.bi'},
           {name: 'dj',mapping:'supOrderWork.dj'},
           {name: 'wf',mapping:'supOrderWork.wf'},
           {name: 'pg',mapping:'supOrderWork.pg'},
           {name: 'packNumber',mapping:'supOrderWork.packNumber',type: 'float'},
           {name: 'outNumber',mapping:'supOrderWork.outNumber',type: 'float'},
           {name: 'oweNumber',mapping:'supOrderWork.oweNumber',type: 'float'},
           {name: 'supRemark',mapping:'supOrderWork.supRemark'},
           {name: 'supplyComment',mapping:'supOrderWork.supplyComment'},
           {name:'replyDate1',mapping:'supOrderWork.replyDate1',type: 'date',dateFormat: 'Y-m-d'},
           {name:'replyDate2',mapping:'supOrderWork.replyDate2',type: 'date',dateFormat: 'Y-m-d'},
            {name:'curOutDate',mapping:'supOrderWork.curOutDate',type: 'date',dateFormat: 'Y-m-d H:i:s'},
           {name: 'lockDate',mapping:'orderStatus.lockDate'},
           {name: 'lockOrder',mapping:'orderStatus.lockOrder'},
           {name: 'orderLevel',mapping:'orderStatus.orderLevel'}
         ]  
     });  


    // create the data store
    var store = Ext.create('Ext.data.Store', {
     storeId:'orderStore',
        model: 'Order',  
         pageSize: 20,
          proxy: {
        type:'ajax',
        url: '/order/orderlist.do',
        reader:{
               type:'json',
              totalProperty:'count',
                root:'root'
          }
    },
   autoLoad :false
    });
    
    function dateFormat(value){ 
    if(null != value){ 
        return Ext.Date.format(new Date(value),'Y-m-d'); 
    }else{ 
        return null; 
    } 
} 
 
  
store.on('beforeload', function (store, options) {
        var new_params = { orderNo: Ext.getCmp('KeyWord').value,
        startDate: dateFormat(Ext.getCmp('startDate').value),
        endDate: dateFormat(Ext.getCmp('endDate').value),
         ourOrder: Ext.getCmp('ourOrder').value,
         style: Ext.getCmp('style').value};
        Ext.apply(store.proxy.extraParams, new_params);
    });
    
    //每一个列都会出现鼠标悬浮上去显示内容
/** 
 * //适用于Extjs4.x
* @class Ext.grid.GridView 
* @override Ext.grid.GridView 
* GridPanel单元格不能选中复制问题 
* 单元格数据显示不完整 ,增加title 浮动提示信息 
*/ //每一个列都会出现鼠标悬浮上去显示内容
/** 
 * //适用于Extjs4.x
* @class Ext.grid.GridView 
* @override Ext.grid.GridView 
* GridPanel单元格不能选中复制问题 
* 单元格数据显示不完整 ,增加title 浮动提示信息 
*/ 
Ext.override(Ext.grid.GridPanel, {
    afterRender : Ext.Function.createSequence(Ext.grid.GridPanel.prototype.afterRender,
        function() {
            /* 默认显示提示
            if (!this.cellTip) {
                return;
            }*/
            
            var view = this.getView();
            
            this.tip = new Ext.ToolTip({
                target: view.el,
                delegate : '.x-grid-cell-inner',
                trackMouse: true, 
                renderTo: Ext.getBody(),  
                listeners: {  
                    beforeshow: function updateTipBody(tip) {
                        //取cell的值
                        //fireFox  tip.triggerElement.textContent
                        //IE  tip.triggerElement.innerText 
                        var tipText = (tip.triggerElement.innerText || tip.triggerElement.textContent);
                        if (tipText=='' ||tipText==undefined || tipText==null) {
                            return false;
                        }
                        
                        tip.update(tipText);
                    }
                }
            });
        })
});

function     toolajax(tourl){
           var mGrid=Ext.getCmp('manage-grid');
          var records = mGrid.getSelectionModel().getSelection();
          var ids = "";
           for(var i=0;i<records.length;i++){
          ids = ids +records[i].data["id"]+ ',';
          }
          Ext.Ajax.request({  
                                url:tourl ,  
                                params: {  
                                    orderIds:ids
                                },
                                 success: function(response){  
                                 var result = Ext.decode(response.responseText);
                                 if(result.success){
                                     Ext.MessageBox.alert('提示', '数据保存成功!');
                                     store.reload();
                                 }else{
                                 Ext.MessageBox.alert('提示', '数据保存失败,请重新操作.');
                                 }
                                 },
                                 failure: function (response){
                                 Ext.MessageBox.alert('提示', '数据保存失败,请重新操作.');
                                 }
                });

}


 var oneTbar = new Ext.Toolbar({  
        items: [ {text:'批准日期',iconCls:'icon-accept',
		        listeners:{click:function () {
		          toolajax('/order/approved.do');
		        }}
        },"-",
         {text:'关闭订单',iconCls:'icon-lock',
		              listeners:{click:function () {
		          toolajax('/order/closeOpen.do?status=close');
		            }}
        },"-",
             {text:'开启订单',iconCls:'icon-lock-open',
		              listeners:{click:function () {
		          toolajax('/order/closeOpen.do?status=active');
		            }}
        },"-",
         {text:'标记为一般',iconCls:'icon-record_green',
	              listeners:{click:function () {
	          toolajax('/order/sign.do?color=1');
	          }}
        },"-",
             {text:'标记为重要',iconCls:'icon-record_blue',
		              listeners:{click:function () {
		            toolajax('/order/sign.do?color=2');
		              }}
        },"-",
             {text:'标记为紧急',iconCls:'icon-record_blue',
		              listeners:{click:function () {
		            toolajax('/order/sign.do?color=3');
		             }}
         }] 
    });  
    
    var twoTbar = new Ext.Toolbar({  
            items: [ {   
                        xtype:'label',text:'外贸订单号:' 
                    },  
                    {  
                        xtype:'textfield',id:'KeyWord'  
                    },  
                      {   
                        xtype:'label',text:'型号:'  
                    },  
                    {  
                        xtype:'textfield',id:'style'  
                    }, 
                      {   
                        xtype:'label',text:'下单日期:'  
                    },  
                     {  
                        xtype:'datefield',id:'startDate',format: 'Y-m-d'
                    },  
                     {   
                        xtype:'label',text:'-'  
                    },  
                     {  
                        xtype:'datefield',id:'endDate', format: 'Y-m-d'
                    } ]
    });  
    
    var threeTbar = new Ext.Toolbar({  
            items: [ 
                    {   
                        xtype:'label',text:'本厂单号:'  
                    },  
                     {  
                        xtype:'textfield',id:'ourOrder'  
                    },  
                    {  
                        xtype: 'button',
                        text:'搜索',
                         handler:function(){  
                  //        var txtSearch = Ext.String.trim(Ext.getCmp("KeyWord").value);  
                          store.loadPage(1)
                 //          store.load({params:{start:0,limit:20,orderNo: txtSearch}})
                        }  
                    }  ]
    });  
    



    // create the Grid
    var grid = Ext.create('Ext.grid.Panel', {
       id:'manage-grid',
        store: store,
        frame: true,
        margin: 5,
         tbar: {
            xtype: 'container',  
            items: [oneTbar,twoTbar,threeTbar]
            },  
        columns: [{
              text     : '序号',  xtype: 'rownumberer',align : 'center', width: 60 
            },{
            text     : 'id', 
             locked   : true,
             width: 30,
             sortable : false,
            hidden: true,
            dataIndex: 'id'
        },
        {
            text     : '紧急程度', 
            locked   : true,
            align : 'center',
            width    : 75,
            sortable : true,
            dataIndex: 'orderLevel',
            renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
				   var orderLevel=record.get('orderLevel');
				   if(orderLevel=='3'){
				     return '<span ><img alt="" src="<%=basePath %>res/image/record_red.png"></span>';  
				   }else if(orderLevel=='2'){
				       return '<span ><img alt="" src="<%=basePath %>res/image/record_blue.png"></span>';  
				   }else{
				         return '<span ><img alt="" src="<%=basePath %>res/image/record_green.png"></span>';  
				   }
				
		  }		   
        },{
            text     : '外贸订单号', 
            locked   : true,
            width    : 100,
            sortable : false,
            dataIndex: 'orderNo',
            renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
				   var orderNo=record.get('orderNo');
				   var   lockOrder=record.get('lockOrder');
				   if(lockOrder=='close'){
				     return '<span >' + orderNo +'<img alt="" src="<%=basePath %>res/image/lock.png"></span>';  
				   }else{
				      return '<span >' + orderNo +'</span>';  
				   }
				
		  }		   
        },{
            text     : '型号', 
            locked   : true,
            width    : 80,
            sortable : false,
            dataIndex: 'style'
        },{
            text     : '颜色', 
            locked   : true,
            width    : 80,
            sortable : false,
            dataIndex: 'orderColor'
        },{
            text     : '订单数', 
            locked   : true,
            width    : 80,
            sortable : false,
            dataIndex: 'orderNumber'
        },{
            text     : '下单日期', 
            locked   : true,
            width    : 97,
            sortable : true,
            renderer : Ext.util.Format.dateRenderer('Y-m-d'),
            dataIndex: 'orderDate'
        },{
            text     : '合同完成日期', 
            locked   : true,
            width    : 110,
            sortable : false,
            renderer : Ext.util.Format.dateRenderer('Y-m-d'),
            dataIndex: 'deliveryDate'
        },{
            text     : '协定日期',
            locked   : true,
            width    : 130,
            sortable : true,
            dataIndex: 'supDate',
              renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
				   var supDate=record.get('supDate');
				   var lockDate=record.get('lockDate');
				   	var createTime='';
				   if(supDate!=null&&supDate!=''){
				   	var createTime = Ext.Date.format(new Date(supDate),"Y-m-d");
				   }
			
				if(lockDate=='lock'){
				   return '<span style="color:green">' + createTime +'<img alt="" src="<%=basePath %>res/image/accept.png"></span>';
				}else{
				   return '<span >' + createTime +'</span>';
				}
		  }		   
        },
        {
            text     : '上次复期',
            width    : 97,
            sortable : true,
            renderer : Ext.util.Format.dateRenderer('Y-m-d'),
            dataIndex: 'replyDate1'
        },
        {
            text     : '现在复期',
            width    : 97,
            sortable : true,
            renderer : Ext.util.Format.dateRenderer('Y-m-d'),
            dataIndex: 'replyDate2'
        }, {
            text     : '出货日期', 
            width    : 140,
            sortable : false,
            renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
            dataIndex: 'curOutDate'
        },{
            text     : '本厂单号',
            width    : 97,
            sortable : true,
            dataIndex: 'supOrderNo'
        },{
            text     : '板料',
            width    : 97,
            sortable : true,
            dataIndex: 'bl'
        },{
            text     : '金属配件',
            width    : 97,
            sortable : true,
            dataIndex: 'js'
        },{
            text     : '圈',
            width    : 97,
            sortable : true,
            dataIndex: 'quan'
        },{
            text     : '脾',
            width    : 97,
            sortable : true,
            dataIndex: 'bi'
        },{
            text     : '钉铰',
            width    : 97,
            sortable : true,
            dataIndex: 'dj'
        },{
            text     : '外发',
            width    : 97,
            sortable : true,
            dataIndex: 'wf'
        },{
            text     : '抛光',
            width    : 97,
            sortable : true,
            dataIndex: 'pg'
        },{
            text     : '包装',
            width    : 97,
            sortable : true,
            dataIndex: 'packNumber'
        },{
            text     : '已出货',
            width    : 97,
            sortable : true,
            dataIndex: 'outNumber'
        },
       {
            text     : '欠数', 
            width    :60,
            sortable : false,
            renderer : 
            function change(value, cellmeta, record, rowIndex, columnIndex, store) {
                            var orderNumber=record.get('orderNumber');
                            var outNumber=store.getAt(rowIndex).get('outNumber');
                            var numbwe=orderNumber-outNumber;
				            return '<span style="color:red;">' + numbwe + '</span>';
				    },
            dataIndex: 'oweNumber'
        },{
            text     : '备注',
            width    : 97,
            sortable : true,
            dataIndex: 'supRemark'
        }],
        bbar: [{
            xtype: 'pagingtoolbar',
                pageSize: 10,
                store: store,
                displayInfo: true,
                plugins: new Ext.ux.ProgressBarPager()
            }],
        selModel: Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
        title: '订单进度',
        renderTo: 'grid-example',
        viewConfig: {
            stripeRows: true,
            enableTextSelection:true
      //      getRowClass: function changeRowClass(record, rowIndex, rowParams, store){
			//    if (record.get("orderLevel") == "3") {        
			//        return 'row-red';
		//	    }else if(record.get("orderLevel") == "2"){
			//         return 'row-blue';
			//    }
          //  }
        }
    });
    
        store.load({params:{start:0,limit:20}});  

});
    
    
    

</script>
<style type="text/css">
.row-red .x-grid-cell{
  background-color: red  !important;
}
.row-blue .x-grid-cell{
 background-color: blue  !important;
}

</style>


</head>
<body >
    <div id="grid-example"></div>
  
</body>
</html>