<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>老花分单</title>
        
      <%@include file="../common/head.jsp"%>
    <link  rel="stylesheet"  href="<%=basePath %>res/css/extIcon.css"/>

   <script type="text/javascript">
   Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux', '/res/js/ux');
Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.panel.*',
     'Ext.view.View',
    'Ext.layout.container.Fit',
    'Ext.toolbar.Paging',
    'Ext.ux.form.SearchField',
    'Ext.ux.DataTip',
    'Ext.layout.container.Border'
]);

Ext.onReady(function(){


var forumId = 4;
    Ext.define('Book',{
        extend: 'Ext.data.Model',
       idProperty: 'id',
    fields: [
            {name: 'id'},
            {name: 'orderId'},
            {name: 'con_number'},
            {name: 'order_price'},
            {name: 'outDate',type: 'date', dateFormat: 'Y-m-d'},
            {name: 'outNumber',type: 'float'},
            {name: 'userId'},
            {name:'orderNo',mapping:'orderInfo.orderNo'},
            {name:'style',mapping:'orderInfo.style'},
            {name: 'itemNo',mapping:'orderInfo.itemNo'},
            {name: 'price',mapping:'orderInfo.price',type: 'float'},
            {name: 'partName',mapping:'orderInfo.partName'},
            {name: 'supplyName',mapping:'orderInfo.supplyItem.supplyName'},
            {name:'qcAdress',mapping:'orderInfo.qcAdress'}
    ]
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
                                url: '/supply/commentSave.do',  
                                params: {  
                                    orderId:formData.id,
                                    ourComment:formData.ourComment
                                },
                                 success: function(response){  
                                 var result = Ext.decode(response.responseText);
                               if(result.success){
                                   if(result.msg==1){
				                            Ext.MessageBox.alert('错误', '请先填写订单进度!');
				                      }else{
				                       Ext.MessageBox.alert('信息提示', '保存成功!');
				                      }
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

    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
       model: 'Book',
        pageSize: 20,
        proxy: {
            type: 'ajax',
            url:'/orderout/pl/list.do',
            extraParams: {
                forumId: forumId
            },
            reader: {
                type: 'json',
                root: 'root',
                totalProperty: 'count'
            }
        },
        listeners: {
            beforeload: function(){
                var params = store.getProxy().extraParams;
                if (params.query) {
                    delete params.forumId;
                } else {
                    params.forumId = forumId;
                }
            }
        }
    });
    var oneTbar = new Ext.Toolbar({
        items: [
            {
                iconCls:'icon-record_excel', text: "导出Excel", tooltip : '导出Excel表格', handler : process
            }]
    });

    var twoTbar = new Ext.Toolbar({
        items: [ {
            id:'query',
            width: 400,
            fieldLabel: '分单合同号码:',
            labelWidth: 120,
            xtype: 'searchfield',
            store: store
            } ]
    });


    // create the grid
    var grid = Ext.create('Ext.grid.Panel', {
        store: store,
        title: '老花分单',
        renderTo: 'binding-example',
        frame: true,
        margin: 5,
        tbar: {
            xtype: 'container',
            items: [oneTbar,twoTbar]
        },
            viewConfig:{
            stripeRows:true,//在表格中显示斑马线
          enableTextSelection:true //可以复制单元格文字
      },
         plugins: [rowEditing],  
        columns: [
    {  xtype: 'rownumberer',locked   : true, text: '序号',align : 'center', width: 60},
        {text     : '客户型号 ',width   : 140,sortable : true,locked   : true,align : 'center',dataIndex: 'style',menuDisabled:true},
        {text     : '生产工厂',width  : 150,sortable : true,locked   : true,align : 'center',dataIndex: 'supplyName',menuDisabled:true},
        {text     : '订单号码 ', width: 120,sortable : true,locked   : true, align : 'center',dataIndex: 'orderNo',menuDisabled:true},
        {text     : '品名',sortable : true,dataIndex: 'partName',locked   : true,align : 'center',menuDisabled:true},
        { text     : '数量/副', sortable : false,dataIndex: 'outNumber',locked   : true,align : 'center',menuDisabled:true},
        {text     : '含税单价(元/副)',width  :110,locked   : true, sortable : false,dataIndex: 'price',align : 'center',menuDisabled:true},
        {text     : '金额/元',  sortable : false,locked   : true,dataIndex: 'price',align : 'center',menuDisabled:true,
            renderer :
                            function change(value, cellmeta, record, rowIndex, columnIndex, store) {
                                var price=record.get('price');
                                var outNumber=store.getAt(rowIndex).get('outNumber');
                                var total=accMul(price,outNumber);
                                return '<span style="color:red;">' + total + '</span>';
                }
        },
        {text     : '备注', width  :97,sortable : true,locked   : true, dataIndex: 'qcAdress',align : 'center',menuDisabled:true}
            ],
        dockedItems: [ {
            dock: 'bottom',
            xtype: 'pagingtoolbar',
            store: store,
            pageSize: 20,
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，一共  {2} 条',
            emptyMsg: '无数据'
        }]
       
    });
    
             store.loadPage(1);
});



   //导出excel的函数
   function process(){
       Ext.MessageBox.confirm('确认提示', '你确定要导出Excel吗？',
               function deldbconfig(btn) {
                   if (btn == 'yes') {
                       var msgTip = Ext.MessageBox.show({
                           title : '提示',
                           width : 350,
                           msg : '正在生成，请稍候......'
                       })
                       var query= Ext.getCmp('query').value;
                       var _url = '/order/out/pl/export.do?query='+query;
                       window.location=(_url);

//       window
//         .open(_url, '_self',
//           'width=1,height=1,toolbar=no,menubar=no,location=no'); // 注意这里
                       // 用window.open()
                       // 否则不出现下载对话框
                       msgTip.hide();

                   }
               })

   }
   function accMul(arg1,arg2)
   {
       var m=0,s1=arg1.toString(),s2=arg2.toString();
       try{m+=s1.split(".")[1].length}catch(e){}
       try{m+=s2.split(".")[1].length}catch(e){}
       return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
   }


   </script>


</head>
<body >
    <div id="binding-example"></div>

</body>
</html>