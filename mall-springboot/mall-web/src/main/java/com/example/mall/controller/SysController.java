package com.example.mall.controller;

import com.example.common.utils.JsonModel;
import com.example.common.utils.email.SendmailUtil;
import com.example.common.utils.email.VerifyCodeUtil;
import com.example.common.utils.redis.RedisUtils;
import com.example.mall.domain.UserDO;
import com.example.mall.service.UserService;
import com.example.mall.utils.EmailCodeMapUtil;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("sys")
public class SysController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisUtils redisUtils;

	/**
	 *去到首页
	 */
	@RequestMapping(value="index", method= RequestMethod.GET)
	public String logout(){
		return "sys/index";
	}

	/**
	 *注册发邮件
	 */
	@RequestMapping(value="getCode", method= RequestMethod.POST)
	@ResponseBody
	public JsonModel getCode(String email){
		JsonModel jsonModel = new JsonModel();
		//判断邮箱是否已存在
		UserDO userDO = userService.findUserByEmail(email);
		if(userDO != null){
			jsonModel.setCode(1);
			jsonModel.setMsg("该邮箱已被注册，请勿重新注册");
			return jsonModel;
		}
		//生成验证码
		String verifyCode = VerifyCodeUtil.generateVerifyCode(4);

		//邮件主题
		String emailTitle = "【广西科技大学校园二手交易平台】";

		//邮件内容
		String emailContent = "亲爱的用户，您好! 非常感谢您注册广西科技大学二手交易平台，您的验证码为 ["+verifyCode+"]，请勿泄露，谨防被骗，" +
				"验证码有效时间为5分钟，如已过期请重新获取";

		try {
			//发送邮件
			SendmailUtil.sendEmail(email, emailTitle, emailContent);
			jsonModel.setMsg("验证码发送成功，请注意查收，如果长时间未收到，请重新获取");
			//写进redis缓存
//			redisUtils.setEmailCode(email,verifyCode);
			//todo 改成写进map,临时使用
			EmailCodeMapUtil.emailCodeMap.put(email, verifyCode);
		} catch (Exception e) {
			jsonModel.setCode(1);
			jsonModel.setMsg(e.getMessage());
		}
		return jsonModel;
	}

	/**
	 *注册发邮件
	 */
	@RequestMapping(value="getCodeUpdatePassword", method= RequestMethod.POST)
	@ResponseBody
	public JsonModel getCodeUpdatePassword(String email){
		JsonModel jsonModel = new JsonModel();
		//判断邮箱是否已存在
		UserDO userDO = userService.findUserByEmail(email);
		if(userDO == null){
			jsonModel.setCode(1);
			jsonModel.setMsg("该邮箱不存在，请检查是否输入错误");
			return jsonModel;
		}
		//生成验证码
		String verifyCode = VerifyCodeUtil.generateVerifyCode(4);

		//邮件主题
		String emailTitle = "【广西科技大学校园二手交易平台】";

		//邮件内容
		String emailContent = "亲爱的用户，您好! 广西科技大学二手交易平台提醒您正在修改密码，您的验证码为 ["+verifyCode+"]，请勿泄露，谨防被骗，" +
				"验证码有效时间为5分钟，如已过期请重新获取";

		try {
			//发送邮件
			SendmailUtil.sendEmail(email, emailTitle, emailContent);
			jsonModel.setMsg("验证码发送成功，请注意查收，如果长时间未收到，请重新获取");
			//写进redis缓存
//			redisUtils.setEmailCode(email,verifyCode);
			//todo 改成写进map,临时使用
			EmailCodeMapUtil.emailCodeMap.put(email, verifyCode);
		} catch (Exception e) {
			jsonModel.setCode(1);
			jsonModel.setMsg(e.getMessage());
		}
		return jsonModel;
	}

	/**
	 * 下载图片
	 */
	@RequestMapping(value="uploadImg", method= RequestMethod.POST)
	@ResponseBody
	public JsonModel uploadImg(@RequestParam("file")MultipartFile file){
		JsonModel jsonModel = new JsonModel();
		try {
			//写入磁盘
//			file.transferTo(new File("C:\\Users\\12198\\Desktop\\乐优商城\\campu\\static\\img\\"+file.getOriginalFilename()));
			//1、把FastDFS提供的jar包添加到工程中
			//2、初始化全局配置。加载一个配置文件。
			try {
				ClientGlobal.init("fdfs.conf");
			} catch (MyException e) {
				e.printStackTrace();
			}
			//3、创建一个TrackerClient对象。
			TrackerClient trackerClient = new TrackerClient();

			//4、创建一个TrackerServer对象。
			TrackerServer trackerServer = trackerClient.getConnection();
			//5、声明一个StorageServer对象，null。
			StorageServer storageServer = null;
			//6、获得StorageClient对象。
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);

			//7、直接调用StorageClient对象方法上传文件即可。
			String[] result = new String[0];
			try {
				//获取文件后缀

				String arry = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")).substring(1);
				result = storageClient.upload_file(null,file.getBytes(),arry,null);
			} catch (MyException e) {
				e.printStackTrace();
			}
			StringBuilder sb = new StringBuilder("http://112.74.113.75/");
			sb.append(result[0]).append("/").append(result[1]);
			System.out.println("图片访问地址: "+sb.toString());
			jsonModel.setMsg("图片上传成功");
			jsonModel.setData(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}


		return jsonModel;
	}
}
