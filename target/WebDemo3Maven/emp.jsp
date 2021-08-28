<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/8/27
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>添加页面</title>
    <!-- 引入EasyUI(引入顺序不能变) -->
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css"/>
    <script type="text/javascript" src="easyui/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">

      /*********************初始化的数据start************************/
      $(function(){/* 页面一加载就进入后台获取的数据（部门下拉列表数据、福利复选框数据） */
        $('#win').window('close');  // close a window
        $('#btupdate').hide(); //隐藏修改按钮
        $('#dtable').panel('close');//隐藏查询列表
        $('#fmdep').hide(); //隐藏部门表单
        $('#dtable1').hide();//隐藏部门查询列表
        $.getJSON("doInit_emp.do",function(map){
          var deplst = map.deplst;
          var wflst = map.wflst;
          //处理部门下拉列表内容（使用EasyUI）
          $('#cc').combobox({
            data:deplst,/* 已有的数据列表加载（相当于遍历deplst，并取值的操作） */
            valueField:'depid',/* 数据值名称（往后台传的名称，相当于name） */
            textField:'depname',/*  展示数据的名称（展示depname部门名称）   */
            panelHeight:90,/* 下拉面板高度 */
            value:'请选择部门',/* 字段的默认值 */
            editable:false/* 定义用户是否可以直接输入文本到字段中 */
          });
          //处理福利复选框
          for (var i = 0; i < wflst.length; i++) {
            var wf = wflst[i];
            /* 拼复选框，向后台传递的是福利编号数组 */
            $('#welfare').append("<input type='checkbox' class='wids' name='wids' id='wids' value='"+wf.wid+"'>"+wf.wname);
          }
        });
        $('#btreset').click(function(){
          location.reload();
        });
      });
      /*********************初始化的数据stop************************/
      /*********************保存start************************/
      $(function(){
        $("#btsave").click(function(){
          $.messager.progress();	// 显示进度条
          $('#fmemp').form('submit', {
            url: "save_emp.do",   //表单提交的路径（相当于action）
            onSubmit: function(){   //表单提交失败则进onSubmit里的函数
              var isValid = $(this).form('validate');
              if (!isValid){
                $.messager.progress('close');//如果表单是无效的则隐藏进度条
              }
              return isValid;//返回false终止表单提交
            },
            success: function(code){//相当于回调函数
              if(code=="1"){
                $.messager.alert('提示','保存成功！');//后台返回"1"时，弹出提示框保存成功
                $('#dg').datagrid('reload');//重新载入当前页面数据
                $('#dtable').panel('open');//显示查询列表
                $('#fmemp').hide(); //隐藏表单
              }else{
                $.messager.alert('提示','保存失败！');
              }
              $.messager.progress('close');	// 如果提交成功则隐藏进度条
            }
          });
        });
      });
      /*********************保存end************************/
      /*********************员工分页查询start************************/
      $(function(){
        $('#dg').datagrid({    //EasyUI数据表格
          fitColumns:true,//自动展开/收缩列的大小
          url:'findPageAll_emp.do',
          striped:true,//斑马纹效果
          pagination:true,//底部显示分页工具栏
          pageList:[5,10,15,20,25],//单页显示记录数选择列表
          columns:[[    //field:列字段名(相当于name) title:列标题  width:列的宽度  align:对齐方式
            {field:'eid',title:'编号',width:100,align:'center'},
            {field:'ename',title:'姓名',width:100,align:'center'},
            {field:'gender',title:'性别',width:100,align:'center'},
            {field:'address',title:'地址',width:100,align:'center'},
            {field:'sdate',title:'生日',width:100,align:'center'},
            {field:'photo',title:'照片',width:100,align:'center',
              formatter: function(value,row,index){ //value:字段值  row:整个行记录的数据  index:行索引（编号）
                //row.photo 表示从该行记录中取photo的记录
                return '<img src=uppic/'+row.photo+' alt=我裂开了 width=50 height=40>'
              }
            },
            {field:'depname',title:'部门名称',width:100,align:'center'},
            /* 操作按钮 */
            {field:'code',title:'操作',width:150,align:'center',
              formatter: function(value,row,index){
                var bt1 = "<input type='button' onclick=delByEid("+row.eid+") value='删除'>"
                var bt2 = "<input type='button' onclick=findByEid("+row.eid+") value='编辑'>"
                var bt3 = "<input type='button' onclick=detailsByEid("+row.eid+") value='详情'>"
                return bt1+bt2+bt3;
              }
            }
          ]]
        });
      });
      /*********************员工分页查询end************************/
      /*********************操作按钮start************************/
      /* 删除  */
      function delByEid(eid){
        $.messager.confirm('确认','您确认想要删除记录吗？',function(r){
          if (r){
            $.getJSON('delByEid_emp.do?eid='+eid,function(code){
              if(code=="1"){
                $.messager.alert('提示','删除成功！');//后台返回"1"时，弹出提示框保存成功
                $('#dg').datagrid('reload');//重新载入当前页面数据
                $('#dtable').panel('open');//显示查询列表
              }else{
                $.messager.alert('提示','删除失败！');
              }
            });
          }
        });
      };

      /* 编辑  */
      function findByEid(eid){
        $('#btupdate').show(); //显示修改按钮
        $('#btsave').hide(); //隐藏保存按钮
        $('#fmemp').show();//显示表单
        $('#dtable').panel('close');//隐藏查询列表
        /* $('#dtable').hide();//隐藏查询列表 */
        $.getJSON('findByEid_emp.do?eid='+eid,function(findemp){
          //先删除复选框中的所有选中
          //找到复选框表单控件，每一次循环清理当前复选框的选中状态
          $(".wids").each(function(){
            $(this).prop('checked',false);
          });
          //将后台返回的内容 写入到指定表单中（EasyUI）
          $("#fmemp").form('load',{
            'eid':findemp.eid,
            'ename':findemp.ename,
            'gender':findemp.gender,
            'address':findemp.address,
            'sdate':findemp.sdate,
            'depid':findemp.depid,
            'emoney':findemp.emoney
          });
          //处理照片及其链接
          $("#aimg").attr('src','uppic/'+findemp.photo)
          $("#ahref").attr('href','uppic/'+findemp.photo)
          //处理复选框
          var wids = findemp.wids;
          $(".wids").each(function(){
            for (var i = 0; i < wids.length; i++) {
              if($(this).val()==wids[i]){
                $(this).prop('checked',true);
              }
            }
          });

        });

      };

      /* 详情  */
      function detailsByEid(eid){
        $.getJSON('detailsByEid_emp.do?eid='+eid,function(findemp){
          //给表单控件赋值
          $("#eidtext").html(findemp.eid);
          $("#enametext").html(findemp.ename);
          $("#gendertext").html(findemp.gender);
          $("#addresstext").html(findemp.address);
          $("#sdatetext").html(findemp.sdate);
          $("#phototext").html(findemp.photo);
          $("#depidtext").html(findemp.depid);
          $("#emoneytext").html(findemp.emoney);
          //获取福利
          var wflst = findemp.wflst;
          var wnames = [];//福利名称数组
          for (var i = 0; i < wflst.length; i++) {
            var wf = wflst[i];
            wnames.push(wf.wname);
          }
          /* 将福利名称数组写成字符串形式的（每个元素中间用逗号隔开） */
          var wnamesstr=wnames.join(',');
          $("#welfaretext").html(wnamesstr);
          $("#aimgdo").attr('src','uppic/'+findemp.photo);
          $("#ahrefdo").attr('href','uppic/'+findemp.photo);
          $('#win').window('open');  // open a window
        });
      };

      /*********************操作按钮end************************/
      /*********************修改start************************/
      $(function(){
        $("#btupdate").click(function(){
          $.messager.progress();	// 显示进度条
          $('#fmemp').form('submit', {
            url: "update_emp.do",   //表单提交的路径（相当于action）
            onSubmit: function(){   //表单提交失败则进onSubmit里的函数
              var isValid = $(this).form('validate');
              if (!isValid){
                $.messager.progress('close');//如果表单是无效的则隐藏进度条
              }
              return isValid;//返回false终止表单提交
            },
            success: function(code){//相当于回调函数
              if(code=="1"){
                $.messager.alert('提示','修改成功！');//后台返回"1"时，弹出提示框保存成功
                $('#dg').datagrid('reload');//重新载入当前页面数据
                $('#dtable').panel('open');//显示查询列表
                $('#fmemp').hide(); //隐藏表单
              }else{
                $.messager.alert('提示','修改失败！');
              }
              $.messager.progress('close');	// 如果提交成功则隐藏进度条
            }
          });
        });
      });
      /*********************修改end************************/

      /* 部门部分 */
      $(function(){
        /* 单击部门管理按钮 */
        $('#bttodep').click(function(){
          $('#fmemp').hide();//隐藏员工表单
          $('#dtable').panel('close');//隐藏查询列表
          $('#fmdep').show();//显示部门表单
          $('#btdupdate').hide();//隐藏修改按钮
        });
        /* 部门保存 */
        $('#btdsave').click(function(){
          $.messager.progress();	// 显示进度条
          $('#fmdep').form('submit', {
            url: "save_dep.do",   //表单提交的路径（相当于action）
            onSubmit: function(){   //表单提交失败则进onSubmit里的函数
              var isValid = $(this).form('validate');
              if (!isValid){
                $.messager.progress('close');//如果表单是无效的则隐藏进度条
              }
              return isValid;//返回false终止表单提交
            },
            success: function(code){//相当于回调函数
              if(code=="1"){
                $.messager.alert('提示','部门保存成功！','info',function(){
                  window.location.reload();
                });//后台返回"1"时，弹出提示框保存成功
                $('#fmdep').hide(); //隐藏部门表单
                $('#fmemp').show(); //显示员工表单
              }else{
                $.messager.alert('提示','部门保存失败！');
              }
              $.messager.progress('close');	// 如果提交成功则隐藏进度条
            }
          });
        });
      });
      $(function(){
        /* 部门展示列表 */
        $.getJSON('findAll_dep.do',function(dlst){
          var deplst = dlst;
          var tabhead = "<table id='dg1' border='1px' width='400px' align='center'>"
                  +"<tr bgcolor='#FFC' align='center'>"
                  +"<td colspan='3'>部门展示列表</td>"
                  +"</tr>"
                  +"<tr align='center'>"
                  +"<td>编号</td>"
                  +"<td>名称</td>"
                  +"	<td>操作</td>"
                  +"</tr>"
          var tabbody = "";
          for (var i = 0; i < deplst.length; i++) {
            var dep = deplst[i];
            tabbody += "<tr align='center'>"
                    +"<td>"+dep.depid+"</td>"
                    +"<td>"+dep.depname+"</td>"
                    +"<td>"
                    +"<input type='button' onclick=doFindByDepid("+dep.depid+") value='编辑'>"
                    +"</td>"
                    +"</tr>"
          }
          var tabtotal = tabhead+tabbody+"</table>";
          $('#dtable1').html(tabtotal);
        });
        //通过点击查询按钮进入查询部门
        $('#btfinddep').click(function(){
          $('#fmdep').hide();//隐藏部门表单
          $('#dtable1').show();//显示查询部门列表
        });
      });
      /* 部门查询单个 */
      function doFindByDepid(depid){
        $('#fmdep').show();//显示部门表单
        $('#dtable1').hide();//隐藏查询部门列表
        $('#btdsave').hide();//隐藏保存按钮
        $('#btdupdate').show();//显示修改按钮
        $.getJSON('findByDepid_dep.do?depid='+depid,function(dep){
          $('#depid').val(dep.depid);
          $('#depname').val(dep.depname);
        });
      };
      /* 部门修改 */
      $(function(){
        $('#btdupdate').click(function(){
          $.messager.progress();	// 显示进度条
          $('#fmdep').form('submit', {
            url: "update_dep.do",   //表单提交的路径（相当于action）
            onSubmit: function(){   //表单提交失败则进onSubmit里的函数
              var isValid = $(this).form('validate');
              if (!isValid){
                $.messager.progress('close');//如果表单是无效的则隐藏进度条
              }
              return isValid;//返回false终止表单提交
            },
            success: function(code){//相当于回调函数
              if(code=="1"){
                $.messager.alert('提示','部门修改成功！','info',function(){
                  window.location.reload();
                });//后台返回"1"时，弹出提示框保存成功
                $('#fmdep').hide(); //隐藏部门表单
                $('#fmemp').show(); //显示员工表单
              }else{
                $.messager.alert('提示','部门修改失败！');
              }
              $.messager.progress('close');	// 如果提交成功则隐藏进度条
            }
          });
        });
      });
    </script>

  </head>
  <body>
    <div id="dtable" class="easyui-panel"><table id="dg"></table></div>
    <hr/>
    <form action="" id="fmemp" method="post" enctype="multipart/form-data" >
      <table border="1px" width="600px" align="center">
        <tr bgcolor="#FFC" align="center">
          <td colspan="3">员工管理</td>
        </tr>
        <tr>
          <td>姓名</td>
          <td><input type="text" name="ename" id="ename" placeholder="姓名" class="easyui-validatebox" data-options="required:true"></td>
          <td rowspan="7">
            <a href="uppic/default.jpg" id="ahref">
              <img id="aimg" alt="图片不存在" src="uppic/default.jpg" width="170px">
            </a>
          </td>
        </tr>
        <tr>
          <td>性别</td>
          <td>
            <input type="radio" name="gender" id="gender" checked="checked" value="男" >男
            <input type="radio" name="gender" id="gender" value="女" >女
          </td>
        </tr>
        <tr>
          <td>地址</td>
          <td><input type="text" name="address" id="address" placeholder="家庭住址" ></td>
        </tr>
        <tr>
          <td>生日</td>
          <td><input type="date" name="sdate" id="sdate" value="1995-01-01" ></td>
        </tr>
        <tr>
          <td>照片</td>
          <td><input type="file" name="pic" id="pic" /></td>
        </tr>
        <tr>
          <td>部门</td>
          <td><input type="text" id="cc" name="depid"></td>
        </tr>
        <tr>
          <td>薪资</td>
          <td><input type="text" id="emoney" name="emoney" value="3000"></td>
        </tr>
        <tr>
          <td>福利</td>
          <td colspan="2">
            <span id="welfare"></span>
          </td>
        </tr>
        <tr bgcolor="#FFC" align="center">
          <td colspan="3">
            <!-- 隐藏表单域 -->
            <input type="hidden" id="eid" name="eid">
            <input type="button" id="btsave" name="btsave" value="保存">
            <input type="button" id="btupdate" name="btupdate" value="修改">
            <input type="reset" id="btreset" name="btreset" value="重置">
            <input type="button" id="bttodep" name="bttodep" value="部门管理">
          </td>
        </tr>
      </table>
    </form>
    <hr>
    <!-- 详情窗口 -->
    <div id="win" class="easyui-window" title="员工详情" style="width:600px;height:400px"
         data-options="iconCls:'icon-save',modal:true">
      <table border="1px" width="600px" align="center">
        <tr>
          <td>编号</td>
          <td colspan="2"><span id="eidtext"></span></td>
        </tr>
        <tr>
          <td width="100px">姓名</td>
          <td width="300px"><span id="enametext"></span></td>
          <td rowspan="7">
            <a href="" id="ahrefdo">
              <img id="aimgdo" alt="图片不存在" src="" width="170px">
            </a>
          </td>
        </tr>
        <tr>
          <td>性别</td>
          <td><span id="gendertext"></span></td>
        </tr>
        <tr>
          <td>地址</td>
          <td><span id="addresstext"></span></td>
        </tr>
        <tr>
          <td>生日</td>
          <td><span id="sdatetext"></span></td>
        </tr>
        <tr>
          <td>照片</td>
          <td><span id="phototext"></span></td>
        </tr>
        <tr>
          <td>部门</td>
          <td><span id="depidtext"></span></td>
        </tr>
        <tr>
          <td>薪资</td>
          <td><span id="emoneytext"></span></td>
        </tr>
        <tr>
          <td>福利</td>
          <td colspan="2"><span id="welfaretext"></span></td>
        </tr>
      </table>
    </div>
    <!-- 部门表单 -->
    <form action="" id="fmdep" method="post">
      <table border="1px" width="400px" align="center">
        <tr bgcolor="#FFC" align="center">
          <td colspan="2">部门管理</td>
        </tr>
        <tr>
          <td>部门名称</td>
          <td><input type="text" id="depname" name="depname" class="easyui-validatebox" data-options="required:true"></td>
        </tr>
        <tr bgcolor="#FFC" align="center">
          <td colspan="2">
            <!-- 隐藏表单域 -->
            <input type="hidden" id="depid" name="depid">
            <input type="button" id="btdsave" name="btdsave" value="保存">
            <input type="button" id="btdupdate" name="btdupdate" value="修改">
            <input type="reset" id="btreset" name="btreset" value="重置">
            <input type="button" id="btfinddep" name="btfinddep" value="查询">
          </td>
        </tr>
      </table>
    </form>
    <!-- 部门展示列表 -->
    <div id="dtable1"></div>
  </body>
</html>
