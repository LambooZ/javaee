package cn.edu.bjtu.weibo.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bjtu.weibo.dao.CommentDAO;
import cn.edu.bjtu.weibo.dao.UserDAO;
import cn.edu.bjtu.weibo.dao.WeiboDAO;
import cn.edu.bjtu.weibo.service.LikeActionService;
import cn.edu.bjtu.weibo.service.MessageToMeService;

@Service("LikeActionService")
public class LikeActionServiceImpl implements LikeActionService{
	@Autowired
	private UserDAO Udao;
	@Autowired
	private CommentDAO Cdao;
	@Autowired
	private WeiboDAO Wdao;	
	@Autowired
	private MessageToMeService service ;
	
	public boolean LikeWeiboOrCommentAction(String userId, String weiboOrCommentId) {
		
		if(weiboOrCommentId.startsWith("w")){
			Wdao.updateLikeList(weiboOrCommentId, userId);//�����޵��б�
			Wdao.updateLikeNmuber(weiboOrCommentId);//�����޵���Ŀ
			Udao.insertLikeWeibo(userId, weiboOrCommentId);//insert��deleteӦ�úϲ���update��
			//Udao.deleteLikeWeibo(userId, weiboOrCommentId);
			
			String Owner = Wdao.getOwner(weiboOrCommentId);
			service.likeMyWeiboInform(userId, Owner, weiboOrCommentId);//����Ϣ
			return true;
		}else if(weiboOrCommentId.startsWith("c")) {
			Cdao.updateLikeList(weiboOrCommentId, userId);//�����޵��б�
			Cdao.updateLikeNumber();//�����޵���Ŀ
			
			String Owner = Cdao.getOwner(weiboOrCommentId);
			service.likeMyCommentInform(userId, Owner, weiboOrCommentId);//����Ϣ
			return true;
		}else {
			return false;
		}
	}
}
