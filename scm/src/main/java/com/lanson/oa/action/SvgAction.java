package com.lanson.oa.action;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SvgAction {
	
	@RequestMapping(value="/svg",method=RequestMethod.POST)
    private void svgServer(HttpServletRequest request,HttpServletResponse response) throws IOException{
        String svgString = request.getParameter("svg");
       
        String type = request.getParameter("type");
        response.setContentType(type);
        String filename = new Date().toLocaleString().replace(" ","_")+"."+type.substring(6);
        response.setHeader("Content-disposition","attachment;filename=" + filename);
//       
//        JPEGTranscoder t = new JPEGTranscoder();
//        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,new Float(.8));
//        TranscoderInput input = new TranscoderInput(new StringReader(svgString));
//        try { 
//            TranscoderOutput output = new TranscoderOutput(response.getOutputStream());
//            t.transcode(input, output);
//            response.getOutputStream().flush();
//            response.getOutputStream().close();
//        }catch (Exception e){
//            response.getOutputStream().close();
//            e.printStackTrace();
//        }
    }

}
