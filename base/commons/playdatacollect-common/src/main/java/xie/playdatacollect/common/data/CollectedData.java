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

	Long getPlayCount();

	void setPlayCount(Long playCount);


	Integer getCommentCount();

	void setCommentCount(Integer commentCount);

	Integer getShareCount();

	void setShareCount(Integer shareCount);

	Integer getDanmuCount();

	void setDanmuCount(Integer danmuCount);

	Integer getCollectCount();

	void setCollectCount(Integer collectCount);

	Integer getLikeCount();

	void setLikeCount(Integer likeCount);

	Double getMaxScore();

	void setMaxScore(Double maxScore);

	Double getScore();

	void setScore(Double score);

	Integer getScoreUserCount();

	void setScoreUserCount(Integer scoreUserCount);

	Integer getRanking();

	void setRanking(Integer ranking);
}
