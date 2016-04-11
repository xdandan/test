package com.lanson.oa.service.out;

import com.lanson.eoa.dao.OrderOutDAO;

import com.lanson.eoa.dao.UserDAO;
import com.lanson.oa.pojo.Dept;
import com.lanson.oa.pojo.OrderInfoJson;

import com.lanson.oa.pojo.User;
import com.lanson.oa.pojo.excel.ExcelEntity;
import com.lanson.oa.pojo.out.OrderOut;
import com.lanson.oa.util.Common;
import jxl.Workbook;
import jxl.write.*;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OutListService {
	@Autowired
	private OrderOutDAO dao;
	@Autowired
	private UserDAO userDAO;


	/**
	 * 光学
	 * @param conNumber
	 * @param start
	 * @param end
	 * @param flag
	 * @return
	 */
   
	public String  outOrderList(String conNumber,int start,int end,String flag){
		if(conNumber==null){
			conNumber="";
		}
		String  orderTable=Common.tableOrderOut;
		String  viewTable=Common.tableViewInfo;
		 if(flag.equals(Common.tableFlag)){
			 orderTable= Common.tableOrderOutPl;
			 viewTable=Common.tableViewInfoPl;
		 }
		   List<OrderOut> list=dao.selectOutsByCon(start+1,start+end,conNumber,orderTable,viewTable);
	    	OrderInfoJson orderInfoJson=new OrderInfoJson();
	    	orderInfoJson.setRoot(list);
	    	orderInfoJson.setCount(dao.selectCounOuts(conNumber,orderTable,viewTable));
	   	    JSONObject  arr=JSONObject.fromObject(orderInfoJson);
	   	    return arr.toString();
	}


	public void exportExcel(String singleCode,HttpServletResponse response,String flag){

		String  orderTable=Common.tableOrderOut;
		String  viewTable=Common.tableViewInfo;
		if(flag.equals(Common.tableFlag)){
			orderTable= Common.tableOrderOutPl;
			viewTable=Common.tableViewInfoPl;
		}

		List<Integer> supplyIds = dao.selectSupplyIds(singleCode,orderTable,viewTable);


		//String excelName=conNumber+"分单"+outDate+".xls";
		String[] Title={"客户型号","生产工厂","订单号码","品名","数量/副","含税单价(元/副)","金额/元","备注"};
		exportExcel(response,supplyIds,Title,singleCode,orderTable, viewTable);



	}

	public  String exportExcel( HttpServletResponse response ,List<Integer> supplyIds,String[] Title,String singleCode,String  orderTable,String  viewTable) {

		String result="系统提示：Excel文件导出成功！";
		String fileName=singleCode+"分单.xls";

		// 以下开始输出到EXCEL

		try {


			OutputStream os = response.getOutputStream();// 取得输出流

			response.reset();// 清空输出流

			response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"),"ISO8859-1"));

			// 设定输出文件头

			response.setContentType("application/msexcel");// 定义输出类型
			//定义输出流，以便打开保存对话框_______________________end
			/** **********创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);


			for(int supplyId:supplyIds){
				List<Object>	 object =new ArrayList<Object>();
				String   supplyName="";
				String  conNumber=singleCode;
				String outDate="";
				double totalNumber=0;
				double amout=0;
				User user=new User();
				List<OrderOut> outList=dao.findListByConNumber(supplyId,conNumber,orderTable,viewTable);

				for(OrderOut orderOut : outList){
					ExcelEntity entity=new ExcelEntity();
					if(supplyName.equals("")){
						supplyName=orderOut.getOrderInfo().getSupplyItem().getSupplyName();
					}
					if(outDate.equals("")){
						outDate=orderOut.getOutDate();
					}
					if(user.getDept()==null){
						if(orderOut.getUserId()==1){
							Dept dept=new Dept();
							dept.setDeptId(1);
							dept.setDeptName("无");
                            user.setDept(dept);
							user.setLastName("系统管理员");
						}else{
							user=userDAO.selectUserDept(orderOut.getUserId());
						}
					}
					totalNumber+=orderOut.getOutNumber();
					entity.setItemStyle(orderOut.getOrderInfo().getStyle());
					entity.setOrderNo(orderOut.getOrderInfo().getOrderNo());
					entity.setOrderPrice(orderOut.getOrderInfo().getPrice());
					entity.setOurStyle(orderOut.getOrderInfo().getSupplyItem().getSupplyName());
					entity.setOutNumber(orderOut.getOutNumber());
					entity.setProName(orderOut.getOrderInfo().getPartName());
					BigDecimal b1 = new BigDecimal(orderOut.getOutNumber());
					BigDecimal b2 = new BigDecimal(orderOut.getOrderInfo().getPrice());
					entity.setTotalMoney(b1.multiply(b2).doubleValue());
					amout+=b1.multiply(b2).doubleValue();

					entity.setRemark(orderOut.getOrderInfo().getQcAdress());
					object.add(entity);
				}

				/** **********创建工作表************ */
				WritableSheet sheet = workbook.createSheet(supplyName, 0);
				sheet.getSettings().setShowGridLines(false);
				sheet.setRowView(0,600);
				sheet.setRowView(1,600);
				sheet.setRowView(2,525);
				sheet.setRowView(3,525);
				sheet.setRowView(4,720);

				sheet.setColumnView(0, 16);
				sheet.setColumnView(1, 11);
				sheet.setColumnView(2, 12);
				sheet.setColumnView(3, 20);
				sheet.setColumnView(4, 10);
				sheet.setColumnView(5, 18);
				sheet.setColumnView(6, 15);
				sheet.setColumnView(7, 15);

				/** **********设置纵横打印（默认为纵打）、打印纸***************** */

				jxl.SheetSettings sheetset = sheet.getSettings();
				sheetset.setProtected(false);

				/** ************设置单元格字体************** */

				WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 12,WritableFont.BOLD);
				WritableFont BoldFont = new WritableFont(WritableFont.createFont("楷体_GB2312"), 20,WritableFont.BOLD);
				WritableFont Boldmid = new WritableFont(WritableFont.createFont("宋体"), 14,WritableFont.BOLD);
				WritableFont numberFont = new WritableFont(WritableFont.createFont("Verdana"), 10,WritableFont.BOLD);

				/** ************以下设置三种单元格样式，灵活备用************ */

				// 用于标题居中

				WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
				wcf_center.setBorder(Border.NONE, BorderLineStyle.NONE); // 线条
				wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
				wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
				wcf_center.setWrap(false); // 文字是否换行

				// 用于标题居二次

				WritableCellFormat wcf_mid = new WritableCellFormat(Boldmid);
				wcf_mid.setBorder(Border.NONE, BorderLineStyle.NONE); // 线条
				wcf_mid.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
				wcf_mid.setAlignment(Alignment.LEFT); // 文字水平对齐
				wcf_mid.setWrap(false); // 文字是否换行

				// 用于正文居左

				WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
				wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
				wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
				wcf_left.setAlignment(Alignment.CENTRE); // 文字水平对齐
				wcf_left.setWrap(false); // 文字是否换行
				// 用于正文居左

				WritableCellFormat wcf_number = new WritableCellFormat(numberFont);
				wcf_number.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
				wcf_number.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
				wcf_number.setAlignment(Alignment.CENTRE); // 文字水平对齐
				wcf_number.setWrap(false); // 文字是否换行


				/** ***************以下是EXCEL开头大标题，暂时省略********************* */

				sheet.mergeCells(0, 0, 7, 0);
				sheet.addCell(new Label(0, 0, "温州市朗盛国际贸易有限公司", wcf_center));

				sheet.mergeCells(0, 1, 7, 1);
				sheet.addCell(new Label(0, 1, "出库单", wcf_center));
				/** ***************标题下信息，暂时省略********************* */
				sheet.mergeCells(0, 2, 2, 2);
				sheet.addCell(new Label(0, 2, "生产工厂:"+supplyName, wcf_mid));

				sheet.mergeCells(5, 2, 7, 2);
				sheet.addCell(new Label(5, 2, "合同号码:"+conNumber, wcf_mid));
				sheet.mergeCells(5, 3, 7, 3);
				sheet.addCell(new Label(5, 3, "出货日期:"+outDate, wcf_mid));

				/** ***************以下是EXCEL第一行列标题********************* */

				for (int i = 0; i < Title.length; i++) {
					sheet.addCell(new Label(i, 4,Title[i],wcf_left));
				}

				/** ***************以下是EXCEL正文数据********************* */

				Field[] fields=null;

				int i=5;

				for(Object obj:object){
					sheet.setRowView( i,720);
					fields=obj.getClass().getDeclaredFields();
					int j=0;
					for(Field v:fields){
						v.setAccessible(true);
						Object va=v.get(obj);
						if(va==null){
							va="";
						}
						if(j>3&&j<7){
							jxl.write.Number n = new jxl.write.Number(j, i, Double.parseDouble(va.toString()), wcf_number);
							sheet.addCell(n);
						}else{
							sheet.addCell(new Label(j, i,va.toString(),wcf_left));
						}
						j++;
					}
					i++;
				}
				sheet.setRowView( i,720);
				sheet.addCell(new Label(0, i,"总计:",wcf_left));
				sheet.addCell(new Label(1, i,"",wcf_left));
				sheet.addCell(new Label(2, i,"",wcf_left));
				sheet.addCell(new Label(3, i,"",wcf_left));
				jxl.write.Number n = new jxl.write.Number(4, i, totalNumber, wcf_left);
				sheet.addCell(n);
				sheet.addCell(new Label(5, i,"",wcf_left));
				jxl.write.Number n1 = new jxl.write.Number(6, i, amout, wcf_left);
				sheet.addCell(n1);
				sheet.addCell(new Label(7, i,"",wcf_left));

				sheet.setRowView( i+1,600);

				sheet.mergeCells(0, i+1, 1, i+1);
				sheet.mergeCells(2, i+1, 3, i+1);
				sheet.mergeCells(4, i+1, 5, i+1);
				sheet.mergeCells(6, i+1, 7, i+1);
				sheet.addCell(new Label(0, i+1,"制单:"+user.getLastName(),wcf_mid));
				sheet.addCell(new Label(2, i+1,"部门:"+user.getDept().getDeptName(),wcf_mid));
				sheet.addCell(new Label(4, i+1,"财务:",wcf_mid));
				sheet.addCell(new Label(6, i+1,"主管:",wcf_mid));


			}



			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();

		} catch (Exception e) {
			result="系统提示：Excel文件导出失败，原因："+ e.toString();
			System.out.println(result);
			e.printStackTrace();
		}
		return result;
	}





}
