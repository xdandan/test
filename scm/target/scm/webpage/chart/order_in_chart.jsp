<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>订单-下单日期周统计</title>
        
      <%@include file="../common/head.jsp"%>
      
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
        fields: ['month', 'one', 'two', 'three', 'four'],
        proxy: {
            type: 'ajax',
            url:'/chart/monthOrder.do',
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
            legend: {
                position: 'right'
            },
            axes: [{
                type: 'Numeric',
                position: 'left',
                fields: ['four', 'three', 'two', 'one'],
                title: '下单数量',
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
                  title:   "下单月份"
            }],
            series: [{
                type: 'column',
                axis: 'left',
                gutter: 80,
                xField: 'month',
                yField: ['one', 'two', 'three', 'four'],
                dispalyField :['第一周','第二周','第三周','第四周'],
                stacked: true,
                tips: {
                    trackMouse: true,
                    width: 130,
                    height: 28,
                    renderer: function(storeItem, item) {
                   switch(item.yField){
                      case 'one' :   this.setTitle( '第一周:'+item.value[1]);  break;
                      case 'two'   :   this.setTitle('第二周:'+item.value[1]);  break;
                      case 'three'   :   this.setTitle( '第三周:' +item.value[1]);  break;
                       case 'four'   :   this.setTitle( '第四周:'+item.value[1]);  break;
                  }
                    }
                      
            
                    
                }
            }]
        });



    var win = Ext.create('Ext.window.Window', {
        width: 800,
        height: 600,
        minHeight: 400,
        minWidth: 550,
        hidden: false,
        maximizable: true,
        title: '订单-下单日期周统计',
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