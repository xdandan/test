<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>供应商-订单批注</title>
        
      <%@include file="../common/head.jsp"%>
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
          {name: 'ourComment',mapping:'supOrderWork.ourComment'}
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
                                    supplyComment:formData.supplyComment
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
            url:'/qc/supplyComment.do',
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


    // create the grid
    var grid = Ext.create('Ext.grid.Panel', {
        store: store,
        title: '跟单批注',
        renderTo: 'binding-example',
        frame: true,
        margin: 5,
       viewConfig:{
            stripeRows:true,//在表格中显示斑马线
          enableTextSelection:true //可以复制单元格文字
      },
         plugins: [rowEditing],  
        columns: [
    {  xtype: 'rownumberer',locked   : true, text: '序号',align : 'center', width: 60},
        {text     : '供应商跟单批注',width    : 140,sortable : true,locked   : true,dataIndex: 'supplyComment',editor: {xtype: 'textfield'}},
        {text     : '朗盛光学跟单批注',width    : 150,sortable : true,locked   : true,dataIndex: 'ourComment'},
        {text     : 'id', width: 30,sortable : false,hidden: true, dataIndex: 'id'},
        {text     : '外贸订单号',sortable : true,dataIndex: 'orderNo'},
        { text     : '型号', sortable : false,dataIndex: 'style'},
        {text     : '颜色',  sortable : false,dataIndex: 'orderColor'},
        {text     : '订单数',  sortable : false,dataIndex: 'orderNumber'},
        {text     : '下单日期', sortable : true, 
        renderer : Ext.util.Format.dateRenderer('Y-m-d'),dataIndex: 'orderDate'},
        { text     : '合同完成日期',width : 80,sortable : false,
        renderer : Ext.util.Format.dateRenderer('Y-m-d'),dataIndex: 'deliveryDate'},
              {text     : '工厂复期',width    : 97,sortable : true,
        renderer : Ext.util.Format.dateRenderer('Y-m-d'),dataIndex: 'supDate'},
         {text     : '本厂单号',width    : 97,sortable : true,dataIndex: 'supOrderNo'},
       { text     : '板料',width    : 97, sortable : true,dataIndex: 'bl'},
       {text     : '金属配件',width    : 97,  sortable : true, dataIndex: 'js' },
       {text     : '圈', width    : 97,sortable : true,dataIndex: 'quan'},
       {  text     : '脾',width    : 97,sortable : true, dataIndex: 'bi' },
       {text     : '钉铰',width    : 97,sortable : true,dataIndex: 'dj'},
       {text     : '外发',width    : 97, sortable : true,dataIndex: 'wf'},
       {text     : '抛光',width    : 97,sortable : true,dataIndex: 'pg'},
        {text     : '包装', width    : 97,sortable : true,dataIndex: 'packNumber' },
        {text     : '已出货',width    : 97,sortable : true,dataIndex: 'outNumber'},
           { text     : '欠数',width    :60, sortable : false,
            renderer : 
            function change(value, cellmeta, record, rowIndex, columnIndex, store) {
                            var orderNumber=record.get('orderNumber');
                            var outNumber=store.getAt(rowIndex).get('outNumber');
                            var numbwe=orderNumber-outNumber;
				            return '<span style="color:red;">' + numbwe + '</span>';
				    },
            dataIndex: 'oweNumber'},
        {text     : '工厂备注', width  :97,sortable : true, dataIndex: 'supRemark'}
            ],
        dockedItems: [{
            dock: 'top',
            xtype: 'toolbar',
            items: {
                width: 400,
                fieldLabel: '外贸订单号',
                labelWidth: 120,
                xtype: 'searchfield',
                store: store
            }
        }, {
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

    
    
    
    
    


 
      
   
   </script>


</head>
<body >
    <div id="binding-example"></div>

</body>
</html>