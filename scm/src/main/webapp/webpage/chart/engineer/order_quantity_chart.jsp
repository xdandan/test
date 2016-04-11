<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>订单-交货日期周统计</title>
        
      <%@include file="../../common/head.jsp"%>
      
   <script type="text/javascript">
Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.chart.*',
     'Ext.Window', 
     'Ext.layout.container.Fit', 
     'Ext.fx.target.Sprite', 
     'Ext.window.MessageBox'
]);

Ext.chart.LegendItem.prototype.getLabelText = function() {
	var me = this, series = me.series, idx = me.yFieldIndex;

	function getSeriesProp(name) {
		var val = series[name];
		return (Ext.isArray(val) ? val[idx] : val);
	}

	return getSeriesProp('dispalyField') || getSeriesProp('yField');
};



Ext.onReady(function () {
    var store = Ext.create('Ext.data.Store', {
    extend: 'Ext.data.Model',
        fields: ['month', 'number'],
        proxy: {
            type: 'ajax',
            url:'/chart/eng/qlist.do',
            reader: {
                type: 'json'
            }
        },
       autoLoad :true
    });
    
    
    
    
    
    var chart = Ext.create('Ext.chart.Chart',{
            xtype: 'chart',
            animate: true,
            shadow: true,
            store: store,

            axes: [{
                type: 'Numeric',
                position: 'left',
                fields: ['number'],
                title: '订单数量',
                grid: true
            }, {
                type: 'Category',
                position: 'bottom',
                fields: ['month'],
                  label: {
					         renderer: function(val) {
						         if(val==1){
						           return "一月份";
						         }
						          if(val==2){
						           return "二月份";
						         }
						          if(val==3){
						           return "三月份";
						         }
						          if(val==4){
						           return "四月份";
						         }
						          if(val==5){
						           return "五月份";
						         }
						          if(val==6){
						           return "六月份";
						         }
						          if(val==7){
						           return "七月份";
						         }
						          if(val==8){
						           return "八月份";
						         }
						          if(val==9){
						           return "九月份";
						         }
						          if(val==10){
						           return "十月份";
						         }
						          if(val==11){
						           return "十一月份";
						         }
						          if(val==12){
						           return "十二月份";
						         }
					         }
					     },
                  title:   "订单月份"
            }],
            series: [{
                type: 'column',
                axis: 'left',
                gutter: 80,
                xField: 'month',
                yField: 'number',
                stacked: true,
                tips: {
                    trackMouse: true,
                    width: 130,
                    height: 28,
                    renderer: function( storeItem, item) {

                      this.setTitle(item.value[1]);

                    }

                }
            }]
        });

    var selectData=[
        ['yb','手板'],
        ['ys','颜色板'],
        ['gm','工模板'],
        ['xs','销售样'],
        ['td','头单']
    ];


    var win = Ext.create('Ext.window.Window', {
        width: 800,
        height: 600,
        minHeight: 400,
        minWidth: 550,
        hidden: false,
        maximizable: true,
        title: '订单量汇总',
        autoShow: true,
        layout: 'fit',
        tbar: [{
            text: '保存为图片',
            handler: function() {
                Ext.MessageBox.confirm('操作提示', '是否另存为图片下载此图形?', function(choice){
                    if(choice == 'yes'){
                        chart.save({
                            type: 'image/png'
                        });
                    }
                });
            }
        },
            {
        id:'tableFlag',
        xtype :'combobox',
        fieldLabel : '订单类型',
        labelWidth:60,
        triggerAction: 'all',
        selectOnTab: true,
        store: selectData,
        width : 140,
        value : 'td',
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
        value : ${Year} ,
        store:          Ext.create('Ext.data.Store', {
            fields : ['year', 'year'],
            proxy: {
                type:'ajax',
                url: '/eng/year.do',
                reader:{
                    type:'json'
                }
            },
            autoLoad :true
        })
    },
       {
            text: '重载数据',
            handler: function() {
                // Add a short delay to prevent fast sequential clicks
                    store.load({params : {tableFlag:Ext.getCmp('tableFlag').value,
                        year: Ext.getCmp('year1').value} });
            }
        }],
        items: chart    
    });


});

    
    

   </script>


</head>
    <body >
    <div style="margin: 10px;">
    </div>
    </body>
</html>