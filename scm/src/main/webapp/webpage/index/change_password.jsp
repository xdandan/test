<%@page contentType="text/html;charset=utf-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>修改密码</title>
    <%@include file="../common/head.jsp"%>
   <script type="text/javascript" src="<%=basePath %>res/myjs/chang-password.js"></script>
        <script type="text/javascript">
        Ext.Loader.setConfig({
    enabled: true
});
Ext.Loader.setPath('Ext.ux',  'res/js/ux');
Ext.require([
      '*',
    'Ext.data.*',
    ' Ext.state.*'
]);



Ext.apply(Ext.form.VTypes, {
     repetition: function(val, field) {     //返回true，则验证通过，否则验证失败
         if (field.repetition) {               //如果表单有使用repetition配置，repetition配置是一个JSON对象，该对象提供了一个名为targetCmpId的字段，该字段指定了需要进行比较的另一个组件ID。
             var cmp = Ext.getCmp(field.repetition.targetCmpId);   //通过targetCmpId的字段查找组件
             if (Ext.isEmpty(cmp)) {      //如果组件（表单）不存在，提示错误
                 Ext.MessageBox.show({
                     title: '错误',
                     msg: '发生异常错误，指定的组件未找到',
                     icon: Ext.Msg.ERROR,
                     buttons: Ext.Msg.OK
                 });
                 return false;
             }
             if (val == cmp.getValue()) {  //取得目标组件（表单）的值，与宿主表单的值进行比较。
                 return true;
             } else {
                 return false;
             }
         }
     },
     repetitionText: '输入的两次密码不一样'
 })
 
 
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
        title: '修改密码',
        bodyPadding: '5 5 5',
          margin: 15,
        width: 350,
        fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 95
        },
        defaultType: 'textfield',
        items: [{
            fieldLabel: '旧密码',
            emptyText: '请输入原来密码',
            afterLabelTextTpl: required,
            name: 'oldPassword',
            allowBlank: false,
            tooltip: '请输入用户名'
        },{
             id: 'newPassword',
            fieldLabel: '新密码',
             inputType: 'password',
            afterLabelTextTpl: required,
            name: 'newPassword',
            allowBlank: false,
            tooltip: '请输入密码'
        },{
            fieldLabel: '确认新密码',
                afterLabelTextTpl: required,
             inputType: 'password',
            name: 'verPassword',
            allowBlank: false,
              vtype: 'repetition',  //指定repetition验证类型  
              repetition: { targetCmpId: 'newPassword' }  //配置repetition验证，提供目标组件（表单）ID  
        }],

        buttons: [{
            text: '确定',
            handler: function() {
               if( this.up('form').getForm().isValid()){
                  this.up('form').getForm().submit({  
                                         url : <%=basePath %>+'password/chang.do',  
                                         waitTitle: '请稍等...',  
                                          waitMsg: '正在提交信息...',  
                                          success: function(fp, o){  
				                                 if(o.result.msg==1){
				                            Ext.MessageBox.alert('信息提示', '密码修改成功!');
				                                 }else if(o.result.msg==0){
				                              Ext.MessageBox.alert('信息提示', '密码更新失败!');
				                                 }else if(o.result.msg==2){
				                                    Ext.MessageBox.alert('信息提示', '旧密码错误');
				                                 }
                                         },
                                          failure: function() {  
                                                Ext.MessageBox.alert('信息提示', '密码修改失败！'); 
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
        
        
        
        </script>

</head>
<body >
    <div id="grid-example"></div>
</body>
</html>