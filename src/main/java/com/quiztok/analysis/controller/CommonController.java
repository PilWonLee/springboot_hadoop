package com.quiztok.analysis.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quiztok.analysis.util.CmmUtil;


@Controller
public class CommonController {
     
	private final Logger log = LogManager.getLogger(this.getClass());
	
    @RequestMapping(value="/")    
    public String root() {
        return "index";         
    }
    
    @RequestMapping(value="/request.do")    
    public @ResponseBody String  request(HttpServletRequest request)throws Exception {
    	
    	String originalFilePath1 = CmmUtil.nvl(request.getParameter("parm1"));
    	String originalFilePath2 = CmmUtil.nvl(request.getParameter("parm2"));
    	String filePath1 = "/home/ftpUser01/"+originalFilePath1;
    	String filePath2 = "/home/ftpUser01/"+originalFilePath2;
    	String uploadPathStr = CmmUtil.nvl(request.getParameter("parm3"));
    	
    	log.info("originalFilePath1 : "+originalFilePath1);
    	log.info("originalFilePath2 : "+originalFilePath2);
    	log.info("filePath1 : "+filePath1);
    	log.info("filePath2 : "+filePath2);
    	log.info("uploadPathStr : "+uploadPathStr);
    	
    	
    	if(filePath1.length()*filePath2.length()*uploadPathStr.length()==0){
    		return "parm's num is not enough. fail";
    	}
    	
    	try {
    		log.info("hadoop upload start..");
    		Configuration conf = new Configuration();
    		conf.set("fs.defaultFS", "hdfs://118.219.232.183:9010");
    		conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
    		conf.set("fs.file.impl",org.apache.hadoop.fs.LocalFileSystem.class.getName());
        	FileSystem hdfs = FileSystem.get(conf);
        	
        	Path path1 = new Path(filePath1);
        	Path path2 = new Path(filePath2);
        	Path uploadPath1 = new Path(originalFilePath1);
        	Path uploadPath2 = new Path(originalFilePath2);
        	
        	if(hdfs.exists(uploadPath1)) {
        		hdfs.delete(uploadPath1,true);
        	}else if(hdfs.exists(uploadPath2)){
        		hdfs.delete(uploadPath2,true);
        	}
        	
        	hdfs.copyFromLocalFile(path1, uploadPath1);
        	hdfs.copyFromLocalFile(path2, uploadPath2);
        	
        	hdfs.close();
        	
        	log.info("hadoop upload end ..!");
        	
    	}catch (Exception e) {
			log.info(e.toString());
		}
		
    	
    	
        return "success";         
    }
}



