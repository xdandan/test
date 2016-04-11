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

function dateFormat(value){
    if(null != value){
        return Ext.Date.format(new Date(value),'Y-m-d');
    }else{
        return null;
    }
}

Ext.onReady(function () {
    var store1 = Ext.create('Ext.data.Store', {
    extend: 'Ext.data.Model',
        fields: ['name', 'total'],
        proxy: {
            type: 'ajax',
            url:'/chart/eng/workgroup.do',
            reader: {
                type: 'json'
            }
        },
       autoLoad :true
    });

//    var data = [{"four":0,"month":3,"number":14500,"one":0,"supplyId":0,"three":0,"total":0,"two":0,"year":2016},{"four":0,"month":4,"number":4480,"one":0,"supplyId":0,"three":0,"total":0,"two":0,"year":2016},{"four":0,"month":5,"number":11800,"one":0,"supplyId":0,"three":0,"total":0,"two":0,"year":2016},{"four":0,"month":6,"number":68000,"one":0,"supplyId":0,"three":0,"total":0,"two":0,"year":2016},{"four":0,"month":7,"number":5000,"one":0,"supplyId":0,"three":0,"total":0,"two":0,"year":2016},{"four":0,"month":9,"number":2400,"one":0,"supplyId":0,"three":0,"total":0,"two":0,"year":2016}];
//    window.store1 = Ext.create('Ext.data.JsonStore', {
//        fields: ['month', 'number'],
//        data: data
//    });





    var donut = false,
            chart = Ext.create('Ext.chart.Chart', {

                animate: true,
                store: store1,
                shadow: true,
                legend: {
                    position: 'right'
                },
                insetPadding: 60,
                series: [{
                    type: 'pie',
                    field: 'total',
                    showInLegend: true,
                    donut: donut,
                    tips: {
                        trackMouse: true,
                        width: 140,
                        height: 28,
                        renderer: function(storeItem, item) {
                            //calculate percentage.
                            var total = 0;
                            store1.each(function(rec) {
                                total += rec.get('total');
                            });
                            this.setTitle( Math.round(storeItem.get('total') / total * 100) + '%' );
                        }
                    },
                    highlight: {
                        segment: {
                            margin: 20
                        }
                    },
                    label: {
                        field: 'name',
                        display: '11111',
                        contrast: true,
                        font: '16px Arial'
                    }
                }]
            });


    Ext.create('Ext.Window', {
        width: 900,
        height: 700,
        minHeight: 400,
        minWidth: 550,
        maximizable: true,
        autoShow: true,
        layout: 'fit',
        title: '订单量汇总',
        renderTo: Ext.getBody(),

        tbar: [{
            text: '保存为图片',
            handler: function() {
                Ext.MessageBox.confirm('Confirm Download', 'Would you like to download the chart as an image?', function(choice){
                    if(choice == 'yes'){
                        chart.save({
                            type: 'image/png'
                        });
                    }
                });
            }
        },
            {
            xtype:'label',text:'创建日期:'
        },
            {
                xtype:'datefield',id:'startDate',format: 'Y-m-d'
            },
            {
                xtype:'label',text:'-'
            },
            {
                xtype:'datefield',id:'endDate', format: 'Y-m-d'
            },{
            text: '重载数据',
            handler: function() {
                // Add a short delay to prevent fast sequential clicks
                store1.load({params : {startDate:dateFormat(Ext.getCmp('startDate').value),
                    endDate: dateFormat(Ext.getCmp('endDate').value)} });
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