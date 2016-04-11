<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>订单-月统计</title>
        
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



Ext.onReady(function(){

  var store1 = Ext.create('Ext.data.Store', {
       extend: 'Ext.data.Model',
        fields: ['month', 'total'],
        proxy: {
            type: 'ajax',
            url:'/chart/monthOrder.do',
            reader: {
                type: 'json'
            }
        },
       autoLoad :true
    });



 var chart = Ext.create('Ext.chart.Chart', {
            style: 'background:#fff',
            animate: true,
            shadow: true,
            store: store1,
            axes: [{
                type: 'Numeric',
                position: 'left',
                fields: ['total'],
                label: {
                    renderer: Ext.util.Format.numberRenderer('0,0')
                },
                title: '订单数量',
                grid: true,
                minimum: 0
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
                title:   ${Year}+'年订单统计'
            }],
            series: [{
                type: 'column',
                axis: 'left',
                highlight: true,
                tips: {
                  trackMouse: true,
                  width: 140,
                  height: 28,
                  renderer: function(storeItem, item) {
                    this.setTitle( storeItem.get('total'));
                  }
                },
                label: {
                  display: 'insideEnd',
                  orientation: 'horizontal',
                  'text-anchor': 'middle',
                    field: 'total',
                    renderer: Ext.util.Format.numberRenderer('0'),
                    orientation: 'vertical',
                    color: '#333'
                },
                xField: 'month',
                yField: 'total'
            }]
        });
        
        
        var mySave = function(surface, config) {
                                config = config || {};
                                var exportTypes = {
                                        'image/png': 'Image',
                                        'image/jpeg': 'Image',
                                        'image/svg+xml': 'Svg'
                                    },
                                    prefix = exportTypes[config.type] || 'Svg',
                                    exporter = Ext.draw.engine[prefix + 'Exporter'];          
                                    exporter.defaultUrl = '<%=basePath+"svg.do" %>';
                                return exporter.generate(surface, config);
                            };


    var win = Ext.create('Ext.window.Window', {
        width: 800,
        height: 600,
        minHeight: 400,
        minWidth: 550,
        hidden: false,
        maximizable: true,
        title: '订单统计(月份)',
        autoShow: true,
        layout: 'fit',
        tbar: [{
            text: '保存为图片',
            handler: function() {
                Ext.MessageBox.confirm('操作提示', '是否另存为图片下载此图形?', function(choice){
                    if(choice == 'yes'){
                         mySave(chart.surface,{
                                type: 'image/jpeg'
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