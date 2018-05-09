package xie.playdatacollect.common.data;

import java.util.Date;
import java.util.Map;

/**
 * 收集到的数据，从页面拉取到的数据，整理到该类，方便导入到数据库
 */
public interface CollectedData {

	void cloneTo(CollectedData collectedData);

	/** 获得数据时间 */
	long getTime();

	void setTime(long time);

	/** 获得数据时间 */
	Date getDate();

	/** 获得基础数据 */
	Map<String, Object> getBaseData();

	void setBaseData(Map<String, Object> datas);

	/** 获得扩展数据 */
	Map<String, Object> getExtendData();

	void setExtendData(Map<String, Object> datas);

	void addExtendData(String key, Object value);

	/** 获得所有数据 */
	Map<String, Object> getAllData();

	/** 获得值 */
	Object getValue(String key);

	void addCNName(String en, String cn);

	String getCNName(String en);

	String getENName(String cn);

	///////////////////基础数据方法///////////////////////
	String getSite();

	void setSite(String site);

	String getType();

	void setType(String type);

	String getName();

	void setName(String name);

	/** 播放数 */
	Long getPlayCount();

	/** 播放数 */
	void setPlayCount(Long playCount);

	/** 评论数 */
	Integer getCommentCount();

	/** 评论数 */
	void setCommentCount(Integer commentCount);

	/** 分享数 */
	Integer getShareCount();

	/** 分享数 */
	void setShareCount(Integer shareCount);

	/** 弹幕数 */
	Integer getDanmuCount();

	/** 弹幕数 */
	void setDanmuCount(Integer danmuCount);

	/** 收藏数 */
	Integer getCollectCount();

	/** 收藏数 */
	void setCollectCount(Integer collectCount);

	/** 喜欢数 */
	Integer getLikeCount();

	/** 喜欢数 */
	void setLikeCount(Integer likeCount);

	/** 追番数 */
	Integer getChasingCount();

	/** 追番数 */
	void setChasingCount(Integer chasingCount);

	/** 最大分数 */
	Double getMaxScore();

	/** 最大分数 */
	void setMaxScore(Double maxScore);

	/** 分数 */
	Double getScore();

	/** 分数 */
	void setScore(Double score);

	/** 评分人数 */
	Integer getScoreUserCount();

	/** 评分人数 */
	void setScoreUserCount(Integer scoreUserCount);

	/** 排名 */
	Integer getRanking();

	/** 排名 */
	void setRanking(Integer ranking);
}
