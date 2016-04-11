 Ext.Loader.setConfig({
    enabled: true
});

Ext.require([
      '*',
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.form.field.Number',
    'Ext.selection.CheckboxModel'
]);



Ext.onReady(function() {
    Ext.QuickTips.init();

var store = Ext.create('Ext.data.Store', {
    fields: [
        { name: 'username'},
        { name: 'password'},
        { name: 'supplyId', type: 'int' },
        { name: 'perId', type: 'int' },
         { name: 'id', type: 'int' },
        { name: 'supplyName' },
         { name: 'perName' }
    ],
      proxy: {
        type:'ajax',
        url: '/user/userlist.do',
        reader:{
               type:'json'
          }
    },
   autoLoad :true
});

Ext.create('Ext.form.Panel', {
   id:'form-grid',
    renderTo: 'grid-example',
    frame: true,
    width: 700,
      height: 450,
    layout: 'column',
    items: [
        {
            columnWidth: 0.6,
            xtype: 'gridpanel',
            viewConfig: {
            stripeRows: true
        },
            store: store,
            title: '用户列表',
            collapsible: true,
            columns: [
                { text: '用户名',width: 80, dataIndex: 'username' },
                { text: '密码', width:80,dataIndex: 'password' },
                { text: '所属供应商', width: 100,dataIndex: 'supplyName'},
                { text: '用户组', width: 100,dataIndex: 'perName'},
                { text: 'id',  hidden: true,dataIndex: 'id'},
                { text: '供应商id',  hidden: true,dataIndex: 'supplyId'},
                { text: '用户组id',  hidden: true,dataIndex: 'perId'}
            ],
            listeners: {
                selectionchange: function (model, records){
                    if(records[0]){
                        this.up('form').getForm().loadRecord(records[0]);
                    }
                }
            }
        },
        {
            columnWidth: 0.4,
            margin: '0 0 0 0',
            xtype: 'form',
            title: '详细信息',
            layout: 'anchor',
            bodyPadding: '40 10  10',
            defaults: {
                width: 240,
                labelWidth: 90
            },
              defaultType: 'textfield',
        items: [{
            fieldLabel: 'id',
            name: 'id',
             hidden: true
        },{
            fieldLabel: '用户名',
            name: 'username',
            allowBlank: false,
            tooltip: '请输入用户名'
        },{
            fieldLabel: '密码',
            name: 'password',
            allowBlank: false,
            tooltip: '请输入密码'
        },{
           xtype: 'combo',
            fieldLabel: '供应商',
            name: 'supplyId',
             disabled:true,
            tooltip: "请选择对应的供应商",
             mode : 'local',     
        triggerAction: 'all', 
        style:'background:#ADD8E6',
       displayField:   'supplyName',
       valueField:     'supplyValue',
        store:          Ext.create('Ext.data.Store', {
                                    fields : ['supplyName', 'supplyValue'],
                                    proxy: {
						        type:'ajax',
						        url:'/user/supply.do',
						        reader:{
						               type:'json'
						          }
						    },
						   autoLoad :true
                                })
        },{
           xtype: 'combo',
            fieldLabel: '用户组',
            name: 'perId',
             mode : 'local',     
        triggerAction: 'all', 
       displayField:   'perName',
       valueField:     'id',
        store:          Ext.create('Ext.data.Store', {
                                    fields : ['id', 'perName'],
                        proxy: {
						        type:'ajax',
						        url: '/user/permissed.do',
						        reader:{
						               type:'json'
						          }
						    },
						   autoLoad :true
                                })
        }],
             buttons: [{
            text: '保存',
            handler: function() {
             if( this.up('form').getForm().isValid()){
                  this.up('form').getForm().submit({  
                                         url : '/user/edit.do',  
                                         waitTitle: '请稍等...',  
                                          waitMsg: '正在提交信息...',  
                                          success: function(fp, o){  
				                                 if(o.result.msg==0){      
				                               Ext.MessageBox.alert('信息提示', '修改成功!');
				                                store.load();
				                                 }else if(o.result.msg==1){
				                              Ext.MessageBox.alert('信息提示', '修改失败,用户名已经被使用!');
				                                 }
                                         },
                                          failure: function() {  
                                                Ext.MessageBox.alert('信息提示', '修改失败！'); 
                                          }  
                                         
                                          });
                                }
            }
        },{
            text: '取消',
            handler: function() {
              this.up('form').getForm().reset();
            }
        }]
        }
    ]
});

});


    