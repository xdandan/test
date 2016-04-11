<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>供应商-填写整改意见</title>
        
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

   var selectAdressData=[
                        ['本厂内部','本厂内部 '],
                        ['供应商外部','供应商外部']
                        ];
var selectResultData=[
                        ['合格','合格'],
                        ['返工返修','返工返修'],
                        ['让步接收','让步接收']
                        ];


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
           {name: 'supOrderNo'},
           {name: 'supDate',type: 'date', dateFormat: 'Y-m-d'},
           {name: 'bl'},
           {name: 'js'},
           {name: 'quan'},
           {name: 'bi'},
           {name: 'dj'},
           {name: 'wf'},
           {name: 'pg'},
           {name: 'packNumber',type: 'float'},
           {name: 'outNumber',type: 'float'},
           {name: 'oweNumber',type: 'float'},
           {name: 'supRemark'},
           {name: 'qcAdress'},
           {name: 'qcUser'},
           {name: 'check_result'},
           {name: 'question'},
           {name: 'pass'},
           {name: 'checkDate',type: 'date', dateFormat: 'Y-m-d'},
           {name: 'qcRemark'}
    ]
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
                                url: '/qc/supplyView.do',  
                                params: {  
                                    orderId:formData.id,
                                    qcRemark:formData.qcRemark
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
    

    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
       model: 'Book',
        pageSize: 20,
        proxy: {
            type: 'ajax',
            url:'/qc/orderlist.do',
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
        title: '供应商整改意见',
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
        {  text: 'QC 检验',   locked   : true,
                columns: [
                {text     : '检验地',width    : 75,locked   : true,  sortable : true,  dataIndex: 'qcAdress'}, 
                { text     : '品质标准',width    : 75,locked   : true,sortable : true,dataIndex: 'change'}, 
                { text     : '检验员',width    : 80,locked   : true,dataIndex: 'qcUser'},
                { text     : '检验方法',width    : 80,locked   : true,dataIndex: 'pctChange'},
                { text     : '检验结果',width    : 80,locked   : true,dataIndex: 'check_result'},
                { text     : '问题点代码',width    : 90,locked   : true,dataIndex: 'question'},
                 { text     : '合格率%',width    : 70,locked   : true,
                   renderer : function(val) {
                    if (val > 50) {
                        return '<span style="color:green;">' + val + '%</span>';
                    } else if (val <= 50) {
                        return '<span style="color:red;">' + val + '%</span>';
                    }return val;
                },dataIndex: 'pass'},
                  { text     : '检验日期',width    : 80,locked   : true, renderer : Ext.util.Format.dateRenderer('Y-m-d'),dataIndex: 'checkDate'}
                ]
           },
        {text     : '供应商整改意见',width    : 120,sortable : true,locked   : true,dataIndex: 'qcRemark',editor: {xtype: 'textfield'}},
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