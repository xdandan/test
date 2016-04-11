Ext.Loader.setConfig({
    enabled: true
});
Ext.Loader.setPath('Ext.ux',  '/res/js/ux');
Ext.require([
      '*',
    'Ext.data.*',
    ' Ext.state.*'
]);

Ext.onReady(function() {
    Ext.QuickTips.init();

    var bd = Ext.getBody();

    /*
     * ================  Simple form  =======================
     */

    var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';


    var simple = Ext.widget({
        xtype: 'form',
        layout: 'form',
        collapsible: true,
        id: 'simpleForm',
        url: 'save-form.php',
        frame: true,
        title: '添加账号',
        bodyPadding: '5 5 5',
          margin: 15,
        width: 350,
        fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 75
        },
        defaultType: 'textfield',
        items: [{
            fieldLabel: '用户名',
            afterLabelTextTpl: required,
            name: 'username',
            allowBlank: false,
            tooltip: '请输入用户名'
        },{
            fieldLabel: '密码',
            afterLabelTextTpl: required,
            name: 'password',
            allowBlank: false,
            tooltip: '请输入密码'
        },{
        	 id: 'supplyId',
           xtype: 'combo',
            fieldLabel: '供应商',
            name: 'supplyId',
            tooltip: "请选择对应的供应商",
             mode : 'local',     
        triggerAction: 'all', 
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
             allowBlank: false,
            tooltip: "请选择对应的用户组",
             mode : 'local',     
        triggerAction: 'all', 
       displayField:   'perName',
       valueField:     'id',
        store:          Ext.create('Ext.data.Store', {
                                    fields : ['id', 'perName'],
                        proxy: {
						        type:'ajax',
						        url:'/user/permissed.do',
						        reader:{
						               type:'json'
						          }
						    },
						   autoLoad :true
                                })
        }],

        buttons: [{
            text: '添加',
            handler: function() {
               if( this.up('form').getForm().isValid()){
            	   var a=Ext.getCmp('supplyId').value;
            	   if(a==null){
            		   Ext.getCmp('supplyId').setValue('-1');
            	   }
                  this.up('form').getForm().submit({
                                         url :'/user/add.do',  
                                         waitTitle: '请稍等...',  
                                          waitMsg: '正在提交信息...',  
                                          success: function(fp, o){  
				                                 if(o.result.msg==1){
				                             Ext.getCmp('supplyId').setValue('');
				                            Ext.MessageBox.alert('信息提示', '保存成功!');
				                                 }else if(o.result.msg==2){
				                              Ext.MessageBox.alert('信息提示', '添加失败,该供应商账号已经存在!');
				                                 }else if(o.result.msg==3){
				                                	 Ext.getCmp('supplyId').setValue('');
				                                    Ext.MessageBox.alert('信息提示', '此账号已经存在,请重新选择账号');
				                                 }
                                         },
                                          failure: function() {  
                                        	 Ext.getCmp('supplyId').setValue('');
                                                Ext.MessageBox.alert('信息提示', '添加失败！'); 
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
    });

    simple.render(document.body);
});




    