<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>供应商-出货统计</title>
        
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
         {name: 'supplyId'},
            {name: 'supplyName'},
         {name: 'one'},
         {name: 'two'},
          {name: 'three'},
          {name: 'four'},
          {name: 'five'},
          {name: 'six'},
          {name: 'seven'},
          {name: 'eight'},
          {name: 'nine'},
          {name: 'ten'},
          {name: 'eleven'},
          {name: 'tw'},
        {name: 'oneNumber'},
        {name: 'twoNumber'},
        {name: 'threeNumber'},
        {name: 'fourNumber'},
        {name: 'fiveNumber'},
        {name: 'sixNumber'},
        {name: 'sevenNumber'},
        {name: 'eightNumber'},
        {name: 'nineNumber'},
        {name: 'tenNumber'},
        {name: 'elevenNumber'},
        {name: 'twNumber'}
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


    var selectData=[
        ['gx','光学'],
        ['lh','老花']
    ];

    // create the Data Store
    var store = Ext.create('Ext.data.Store', {
       model: 'Book',
        proxy: {
            type: 'ajax',
            url:'/order/shipAmont.do',
            extraParams: {
                forumId: forumId
            },
            reader: {
                type: 'json'
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
    var tbar=Ext.create('Ext.toolbar.Toolbar', {
        enableOverflow:true,
        items:[{ //
            xtype: 'buttongroup',
            columns: 5,
            floatable:true,
            defaults: {
                scale: 'small'
            },
            items: [{
                id:'tableFlag',
                xtype :'combobox',
                fieldLabel : '种类',
                labelWidth:50,
                triggerAction: 'all',
                selectOnTab: true,
                store: selectData,
                width : 130,
                lazyRender: true

            }, {
                id:'year1',
                xtype : 'combobox',
                fieldLabel : '年份',
                labelWidth:50,
                width : 130,
                mode : 'local',
                triggerAction: 'all',
                displayField:   'year',
                valueField:     'year',
                store:          Ext.create('Ext.data.Store', {
                    fields : ['year', 'year'],
                    proxy: {
                        type:'ajax',
                        url: '/order/year.do',
                        reader:{
                            type:'json'
                        }
                    },
                    autoLoad :true
                })
            },{
                xtype:'button',
                text:'搜索',
                handler: function () {
                    store.load({ params: { tableFlag: Ext.getCmp('tableFlag').value,
                        year:Ext.getCmp('year1').value }});
                }
            }]
        }]
    });

    // create the grid
    var grid = Ext.create('Ext.grid.Panel', {
        store: store,
        title: '外厂出货统计',
        renderTo: 'binding-example',
        tbar: tbar,
        frame: true,
        margin: 5,
       viewConfig:{
            stripeRows:true,//在表格中显示斑马线
          enableTextSelection:true //可以复制单元格文字
      },
        columns: [
     {  xtype: 'rownumberer',locked   : true, text: '序号',align : 'center', width: 60},
        {text     : '供应商',locked   : true,width    : 80,sortable : true,align : 'center', dataIndex: 'supplyName' },
        {text: '1月',
          columns: [
                    {text: '数量',width: 80,sortable : true,align : 'center', dataIndex: 'oneNumber',xtype: 'numbercolumn', format: '0,000.0'},
                    {text: '金额',width: 80,sortable : true,align : 'center', dataIndex: 'one',xtype: 'numbercolumn', format: '0,000.0'}
                   ]
        },
       {text: '2月',
                columns: [
                    {text: '数量',width: 80,sortable : true,align : 'center', dataIndex: 'twoNumber',xtype: 'numbercolumn', format: '0,000.0'},
                    {text: '金额',width: 80,sortable : true,align : 'center', dataIndex: 'two',xtype: 'numbercolumn', format: '0,000.0'}
                ]
       },
            {text: '3月',
                columns: [
                    {text: '数量',width: 80,sortable : true,align : 'center', dataIndex: 'threeNumber',xtype: 'numbercolumn', format: '0,000.0'},
                    {text: '金额',width: 80,sortable : true,align : 'center', dataIndex: 'three',xtype: 'numbercolumn', format: '0,000.0'}
                ]
            },
            {text: '4月',
                columns: [
                    {text: '数量',width: 80,sortable : true,align : 'center', dataIndex: 'fourNumber',xtype: 'numbercolumn', format: '0,000.0'},
                    {text: '金额',width: 80,sortable : true,align : 'center', dataIndex: 'four',xtype: 'numbercolumn', format: '0,000.0'}
                ]
            },
            {text: '5月',
                columns: [
                    {text: '数量',width: 80,sortable : true,align : 'center', dataIndex: 'fiveNumber',xtype: 'numbercolumn', format: '0,000.0'},
                    {text: '金额',width: 80,sortable : true,align : 'center', dataIndex: 'five',xtype: 'numbercolumn', format: '0,000.0'}
                ]
            },
            {text: '6月',
                columns: [
                    {text: '数量',width: 80,sortable : true,align : 'center', dataIndex: 'sixNumber',xtype: 'numbercolumn', format: '0,000.0'},
                    {text: '金额',width: 80,sortable : true,align : 'center', dataIndex: 'six',xtype: 'numbercolumn', format: '0,000.0'}
                ]
            },
            {text: '7月',
                columns: [
                    {text: '数量',width: 80,sortable : true,align : 'center', dataIndex: 'sevenNumber',xtype: 'numbercolumn', format: '0,000.0'},
                    {text: '金额',width: 80,sortable : true,align : 'center', dataIndex: 'seven',xtype: 'numbercolumn', format: '0,000.0'}
                ]
            },
            {text: '8月',
                columns: [
                    {text: '数量',width: 80,sortable : true,align : 'center', dataIndex: 'eightNumber',xtype: 'numbercolumn', format: '0,000.0'},
                    {text: '金额',width: 80,sortable : true,align : 'center', dataIndex: 'eight',xtype: 'numbercolumn', format: '0,000.0'}
                ]
            },
            {text: '9月',
                columns: [
                    {text: '数量',width: 80,sortable : true,align : 'center', dataIndex: 'nineNumber',xtype: 'numbercolumn', format: '0,000.0'},
                    {text: '金额',width: 80,sortable : true,align : 'center', dataIndex: 'nine',xtype: 'numbercolumn', format: '0,000.0'}
                ]
            },
            {text: '10月',
                columns: [
                    {text: '数量',width: 80,sortable : true,align : 'center', dataIndex: 'tenNumber',xtype: 'numbercolumn', format: '0,000.0'},
                    {text: '金额',width: 80,sortable : true,align : 'center', dataIndex: 'ten',xtype: 'numbercolumn', format: '0,000.0'}
                ]
            },
            {text: '11月',
                columns: [
                    {text: '数量',width: 80,sortable : true,align : 'center', dataIndex: 'elevenNumber',xtype: 'numbercolumn', format: '0,000.0'},
                    {text: '金额',width: 80,sortable : true,align : 'center', dataIndex: 'eleven',xtype: 'numbercolumn', format: '0,000.0'}
                ]
            },
            {text: '12月',
                columns: [
                    {text: '数量',width: 80,sortable : true,align : 'center', dataIndex: 'twNumber',xtype: 'numbercolumn', format: '0,000.0'},
                    {text: '金额',width: 80,sortable : true,align : 'center', dataIndex: 'tw',xtype: 'numbercolumn', format: '0,000.0'}
                ]
            }
            ]
       
    });
    
             store.loadPage(1);
});

    
    
    
    
    


 
      
   
   </script>


</head>
<body >
    <div id="binding-example"></div>

</body>
</html>