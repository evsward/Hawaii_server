package com.evsward.server.util;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dance.core.utils.spring.SpringContextHolder;
import com.evsward.server.dao.MemCompDao;
import com.evsward.server.service.CompetitionService;
import com.evsward.server.vo.CompetitionInfo;
import com.evsward.server.vo.CompetitionMember;
import com.evsward.server.vo.MemberInfo;

public class HIFileUtils {

	private static Logger logger = LoggerFactory.getLogger(HIFileUtils.class);
	
	/**
	 * 保存选手的头像图片
	 * @param dicPath
	 * @param fileName
	 * @param oldfile
	 * @return
	 */
	public static boolean saveFile(String dicPath, String fileName, File oldfile){
		try{
			File destDic = new File(dicPath);
			System.out.println(destDic.getPath());
			
			if(!destDic.exists()){
				destDic.mkdirs();
			}
			File destFile = new File(dicPath + System.getProperty("file.separator") + fileName);
			if(!destFile.exists()){
				destFile.createNewFile();
			}
			FileUtils.copyFile(oldfile, destFile);
			zoomImage(dicPath + System.getProperty("file.separator") + fileName, Application.PATH_ROOT + Application.PATH_IMAGE + System.getProperty("file.separator") + Application.PATH_IMAGE_SMALL + System.getProperty("file.separator") + fileName, 60);
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
     * 图片缩放 
     */  
    public static void zoomImage(String src,String dest,int w) throws IOException {
        double wr=0,hr=0;
        int h = w;
        File srcFile = new File(src);
        File destFile = new File(dest);
        BufferedImage bufImg = ImageIO.read(srcFile);
        double ratio = Double.parseDouble(bufImg.getHeight()+"") / Double.parseDouble(bufImg.getWidth()+"");
        h = (int)(h * ratio);
        Image Itemp = bufImg.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH);
        wr=w*1.0/bufImg.getWidth();
        hr=h*1.0 / bufImg.getHeight();
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        ImageIO.write((BufferedImage) Itemp,dest.substring(dest.lastIndexOf(".")+1), destFile);
    }
    
    /**
     * 导出比赛选手信息Excel
     * @param compID
     */
    public static void exportExcel(int compID){
    	OutputStream outPutStream = null;
    	HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		String pathDic = Application.PATH_ROOT + Application.PATH_EXPORTFILE + System.getProperty("file.separator");
    	try{
    		CompetitionService compService = (CompetitionService)SpringContextHolder.getBean("compService");
    		CompetitionInfo compInfo = compService.getCompInfoByCompID(compID);
    		if(compInfo == null){
    			logger.error("比赛数据导出失败，比赛为null，compID="+compID);
    			return;
    		}
    		String fileName = compInfo.getCompID() + ".xls";
    		MemCompDao memCompDao = (MemCompDao)SpringContextHolder.getBean("memCompDao");
    		List<Map<String, Object>> list = memCompDao.getExportData(compID);
    		Map<String, Object> map = null;
    		if(list != null){
    			File file = new File(pathDic + fileName);
    			if(!file.exists()){
    				file.createNewFile();
    			}
    			outPutStream = new FileOutputStream(file);
    			String title = "选手姓名,选手卡号,选手性别,选手联系方式,选手筹码,选手参赛状态";
    			String[] arrTitle = title.split(",");
    			workbook = new HSSFWorkbook();
    			sheet = workbook.createSheet();
    			//创建标题行
    			sheet = ExcelUtil.createExcelTitleRow(sheet, arrTitle, 0);
    			List dataList = null;
    			for (int i = 0; i < list.size(); i++) {
					map = list.get(i);
					dataList = new ArrayList();
					dataList.add(map.get("MEMNAME"));
					dataList.add(map.get("CARDNO"));
					Integer memSex = (Integer)map.get("MEMSEX");
					dataList.add(MemberInfo.MemberSex.MAN.equals(String.valueOf(memSex)) ? "男" : "女");
					dataList.add(map.get("MEMMOBILE"));
					Integer chip = (Integer)map.get("CHIP");
					dataList.add(String.valueOf(chip));
					Integer mcState = (Integer)map.get("MCSTATE");
					String mcStateStr = "";
					if(mcState == CompetitionMember.MemCompState.DEL){
						mcStateStr = CompetitionMember.MemCompState.DELSHOW;
					}else if(mcState == CompetitionMember.MemCompState.OUT){
						mcStateStr = CompetitionMember.MemCompState.OUTSHOW;
					}else if(mcState == CompetitionMember.MemCompState.REGED){
						mcStateStr = CompetitionMember.MemCompState.REGEDSHOW;
					}else if(mcState == CompetitionMember.MemCompState.ADVAN){
						mcStateStr = CompetitionMember.MemCompState.ADVANSHOW;
					}
					dataList.add(mcStateStr);
					sheet = ExcelUtil.createExcelDataRow(sheet, dataList, i+1);
				}
    			workbook.write(outPutStream);
				outPutStream.flush();
    		}
    	}catch(Exception e){
    		logger.error(e.getMessage(), e);
    	}finally{
    		if(outPutStream != null){
    			try {
					outPutStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
    		}
    	}
    }
    
	public static void main(String[] args) throws IOException {
		String memimage = "D:\\netty\\mavenwork\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\hiserver\\memimage\\";
		String small = "D:\\netty\\mavenwork\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\hiserver\\memimage\\small\\";
		String[] arr = new String[]{"000001.jpg","000002.jpg","000004.jpg","000005.jpg","000007.jpg","000011.jpg","000012.jpg","000013.jpg","000014.jpg","000015.jpg","000016.jpg","000017.jpg","000018.jpg","000019.jpg","000020.jpg","000021.jpg","000022.jpg","000023.jpg","000024.jpg"};
		for (int i = 0; i < arr.length; i++) {
    		zoomImage(memimage+arr[i], small+arr[i],80);
		}
//		zoomImage("C:\\000001.jpg", "c:\\SMALL\\000001.jpg",60);
	}
}
