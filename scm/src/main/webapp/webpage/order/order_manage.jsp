<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>操作订单进度</title>
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
        url: 'order/orderlist.do',
        reader:{
               type:'json',
              totalProperty:'count',
                root:'root'
          }
    },
   autoLoad :false
    });
    
 
    var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
         saveBtnText: '保存',  
        cancelBtnText: "取消", 
        clicksToMoveEditor: 1,
        errorsText:'错误',
         dirtyText:'你要确认或取消更改',
        autoCancel: false,
        
   listeners:{
                edit:function(editor,e,opts){
                            var formData = e.record.getData();  
                            Ext.Ajax.request({  
                                url: 'order/update.do',  
                                params: {  
                                   orderId:formData.id,
                                    supOrderNo:formData.supOrderNo,
                                    supDate:formData.supDate,
                                    bl:formData.bl,
                                    js:formData.js,
                                    quan:formData.quan,
                                    bi:formData.bi,
                                    dj:formData.dj,
                                   wf:formData.wf,
                                   pg:formData.pg,
                                  packNumber:formData.packNumber,
                                  outNumber:formData.outNumber,
                                 oweNumber:formData.oweNumber,
                                 supRemark:formData.supRemark,
                                 replyDate1:formData.replyDate1,
                                 replyDate2:formData.replyDate2,
                                 orderNumber:formData.orderNumber,
                                 lockDate:formData.lockDate
                                },
                                 success: function(response){  
                                 var result = Ext.decode(response.responseText);
                                 if(result.success){
                                     Ext.MessageBox.alert('提示', '数据保存成功!');
                                 }else{
                                 Ext.MessageBox.alert('提示', '数据保存失败,请重新操作.');
                                 }
                                 },
                                 failure: function (response){
                                 Ext.MessageBox.alert('提示', '数据保存失败,请重新操作.');
                                 }
                });
            }
        }
    });
    
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
         ourOrder: Ext.getCmp('ourOrder').value};
        Ext.apply(store.proxy.extraParams, new_params);
    });
    
    
     var twoTbar = new Ext.Toolbar({  
            items: [ {   
                        xtype:'label',text:'外贸订单号:' 
                    },  
                    {  
                        xtype:'textfield',id:'KeyWord'  
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
        store: store,
        frame: true,
        margin: 5,
         tbar: {
            xtype: 'container',  
            items: [twoTbar,threeTbar]
            },  
        columns: [{
                xtype: 'rownumberer',locked   : true, text: '序号',align : 'center', width: 60 
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
				     return '<span  ><img alt="" src="<%=basePath %>res/image/record_red.png"></span>';  
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
            width    : 97,
            sortable : true,
            dataIndex: 'supDate',
	     editor : {xtype : 'datefield',id:'xdDate' },
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
			 //   	Ext.getCmp('supDate').setEditor(new Ext.form.field.Date());
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
            dataIndex: 'replyDate2',
             editor: {
                xtype: 'datefield'
            }
        },{
            text     : '出货日期', 
            width    : 140,
            sortable : false,
            renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
            dataIndex: 'curOutDate'
        },{
            text     : '本厂单号',
            width    : 97,
            sortable : true,
            dataIndex: 'supOrderNo',
            editor: {
                xtype: 'textfield'
            }
        },{
            text     : '板料',
            width    : 97,
            sortable : true,
            dataIndex: 'bl',
            editor: new Ext.form.field.ComboBox({
                    typeAhead: true,
                    triggerAction: 'all',
                    selectOnTab: true,
                    store: selectData,
                    lazyRender: true,
                    listClass: 'x-combo-list-small'
                })
        },{
            text     : '金属配件',
            width    : 97,
            sortable : true,
            dataIndex: 'js',
             editor: new Ext.form.field.ComboBox({
                    typeAhead: true,
                    triggerAction: 'all',
                    selectOnTab: true,
                    store: selectData,
                    lazyRender: true,
                    listClass: 'x-combo-list-small'
                })
        },{
            text     : '圈',
            width    : 97,
            sortable : true,
            dataIndex: 'quan',
             editor: new Ext.form.field.ComboBox({
                    typeAhead: true,
                    triggerAction: 'all',
                    selectOnTab: true,
                    store: selectData,
                    lazyRender: true,
                    listClass: 'x-combo-list-small'
                })
        },{
            text     : '脾',
            width    : 97,
            sortable : true,
            dataIndex: 'bi',
             editor: new Ext.form.field.ComboBox({
                    typeAhead: true,
                    triggerAction: 'all',
                    selectOnTab: true,
                    store: selectData,
                    lazyRender: true,
                    listClass: 'x-combo-list-small'
                })
        },{
            text     : '钉铰',
            width    : 97,
            sortable : true,
            dataIndex: 'dj',
             editor: new Ext.form.field.ComboBox({
                    typeAhead: true,
                    triggerAction: 'all',
                    selectOnTab: true,
                    store: selectData1,
                    lazyRender: true,
                    listClass: 'x-combo-list-small'
                })
        },{
            text     : '外发',
            width    : 97,
            sortable : true,
            dataIndex: 'wf',
            editor: new Ext.form.field.ComboBox({
                    typeAhead: true,
                    triggerAction: 'all',
                    selectOnTab: true,
                    store: selectData1,
                    lazyRender: true,
                    listClass: 'x-combo-list-small'
                })
        },{
            text     : '抛光',
            width    : 97,
            sortable : true,
            dataIndex: 'pg',
             editor: new Ext.form.field.ComboBox({
                    typeAhead: true,
                    triggerAction: 'all',
                    selectOnTab: true,
                    store: selectData,
                    lazyRender: true,
                    listClass: 'x-combo-list-small'
                })
        },{
            text     : '包装',
            width    : 97,
            sortable : true,
            dataIndex: 'packNumber',
            editor: {
                xtype: 'numberfield'
            }
        },{
            text     : '已出货',
            width    : 97,
            sortable : true,
            dataIndex: 'outNumber',
            editor: {
                xtype: 'numberfield'
            }
        },{
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
            dataIndex: 'supRemark',
            editor: {
                xtype: 'textfield'
            }
        }],
        bbar: [{
                xtype: 'pagingtoolbar',
                 store:Ext.data.StoreManager.lookup(store),
                displayMsg: '显示 {0} - {1} 条，共计 {2} 条',
                emptyMsg: "没有数据",
                beforePageText: "当前页",
                afterPageText: "共{0}页",
                displayInfo: true,
                plugins: new Ext.ux.ProgressBarPager()
            }],
        selModel: {
            selType: 'cellmodel'
        },
        title: '订单进度',
        renderTo: 'grid-example',
        viewConfig: {
            stripeRows: true,
            enableTextSelection:true
        },
        plugins: [rowEditing]
    });
  
        store.load({params:{start:0,limit:20}});  
        
        
        
});
    
    
    

</script>


</head>
<body >
    <div id="grid-example"></div>
</body>
</html>