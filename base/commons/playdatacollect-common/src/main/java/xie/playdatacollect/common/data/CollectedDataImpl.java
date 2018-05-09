package xie.playdatacollect.common.data;

import xie.common.utils.date.DateUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CollectedDataImpl implements CollectedData {

	/** 基本数据的中英文对照关系 */
	private static final Map<String, String> baseDataCNNameMap = new LinkedHashMap<>();

	static {
		baseDataCNNameMap.put("site", "网站"); // bilibili iqiyi youku qq
		baseDataCNNameMap.put("type", "类型"); // 节目program 剧集episode
		baseDataCNNameMap.put("name", "名字"); // 动画名：XXX 剧集名：XXX 第X集
		baseDataCNNameMap.put("playCount", "播放数");
		baseDataCNNameMap.put("commentCount", "评论数");
		baseDataCNNameMap.put("shareCount", "分享数");
		baseDataCNNameMap.put("danmuCount", "弹幕数");
		baseDataCNNameMap.put("collectCount", "收藏数");
		baseDataCNNameMap.put("likeCount", "喜欢数");
		baseDataCNNameMap.put("chasingCount", "追番数");
		baseDataCNNameMap.put("maxScore", "最大评分");
		// TODO 暂时改为其他的 baseDataCNNameMap.put("score", "评分");
		baseDataCNNameMap.put("score", "评价分");
		baseDataCNNameMap.put("scoreUserCount", "评分人数");
		baseDataCNNameMap.put("ranking", "排名");
	}

	private Date date;
	/** 用于存放基本数据，可通过函数的get set操作 */
	private Map<String, Object> baseData = new LinkedHashMap<>();
	/** 用于存放扩展数据，只能通过map的get put操作 */
	private Map<String, Object> extendData = new LinkedHashMap<>();
	//	private Map<String, Object> allData = new LinkedHashMap<>();
	/** 用于存放en-cn的关系 */
	private Map<String, String> cnNameMap = new LinkedHashMap<>();
	/** 用于存放cn-en的关系 */
	private Map<String, String> enNameMap = new LinkedHashMap<>();

	private String site;
	private String type;
	private String name;

	private Long playCount;
	public static String BASE_COL_PLAY_COUNT = "playCount";
	private Integer commentCount;
	public static String BASE_COL_COMMENT_COUNT = "commentCount";
	private Integer shareCount;
	private Integer danmuCount;
	private Integer collectCount;
	private Integer likeCount;
	private Integer chasingCount;
	private Double maxScore;
	private Double score;
	private Integer scoreUserCount;
	private Integer ranking;

	public CollectedDataImpl() {
		date = new Date();
		baseDataCNNameMap.forEach((key, value) ->{
			cnNameMap.put(key, value);
			enNameMap.put(value,key);
		});
	}

	@Override
	public void cloneTo(CollectedData collectedData) {
		collectedData.setTime(getTime());
		collectedData.setBaseData(getBaseData());
		collectedData.setExtendData(getExtendData());
		for (String key : cnNameMap.keySet()) {
			collectedData.addCNName(key, cnNameMap.get(key));
		}
	}

	@Override
	public String toString() {
		return DateUtil.convertToString(date, "yyyy-MM-dd HH:mm:ss.SSS Z") + " (" + date.getTime() + "), " + getAllData().toString();
	}

	@Override
	public long getTime() {
		return date.getTime();
	}

	@Override
	public void setTime(long time) {
		if (time > 1000000000000L)
			date = new Date(time);
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public Map<String, Object> getBaseData() {
		return baseData;
	}

	@Override
	public void setBaseData(Map<String, Object> datas) {
		baseData = datas;
	}

	@Override
	public Map<String, Object> getExtendData() {
		return extendData;
	}

	@Override
	public void setExtendData(Map<String, Object> datas) {
		extendData = datas;
	}

	@Override
	public void addExtendData(String key, Object value) {
		extendData.put(key, value);
	}

	@Override
	public Map<String, Object> getAllData() {
		Map<String, Object> allData = new LinkedHashMap<>();
		allData.putAll(baseData);
		allData.putAll(extendData);
		return allData;
	}

	@Override
	public Object getValue(String key) {
		Object val = baseData.get(key);
		if (val == null) {
			val = extendData.get(key);
		}
		if (val == null) {
			val = baseData.get(getCNName(key));
		}
		if (val == null) {
			val = baseData.get(getENName(key));
		}
		if (val == null) {
			val = extendData.get(getCNName(key));
		}
		if (val == null) {
			val = extendData.get(getENName(key));
		}
		return val;
	}

	@Override
	public void addCNName(String en, String cn) {
		cnNameMap.put(en, cn);
		enNameMap.put(cn, en);
	}

	@Override
	public String getCNName(String en) {
		return cnNameMap.get(en);
	}

	@Override
	public String getENName(String cn) {
		return enNameMap.get(cn);
	}

	//**********************************************************
	//************************ 基本数据方法 *********************
	//**********************************************************

	@Override
	public String getSite() {
		return site;
	}

	@Override
	public void setSite(String site) {
		baseData.put("site", site);
		this.site = site;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		baseData.put("type", type);
		this.type = type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		baseData.put("name", name);
		this.name = name;
	}

	@Override
	public Long getPlayCount() {
		return playCount;
	}

	@Override
	public void setPlayCount(Long playCount) {
		baseData.put("playCount", playCount);
		this.playCount = playCount;
	}

	@Override
	public Integer getCommentCount() {
		return commentCount;
	}

	@Override
	public void setCommentCount(Integer commentCount) {
		baseData.put("commentCount", commentCount);
		this.commentCount = commentCount;
	}

	@Override
	public Integer getShareCount() {
		return shareCount;
	}

	@Override
	public void setShareCount(Integer shareCount) {
		baseData.put("shareCount", shareCount);
		this.shareCount = shareCount;
	}

	@Override
	public Integer getDanmuCount() {
		return danmuCount;
	}

	@Override
	public void setDanmuCount(Integer danmuCount) {
		baseData.put("danmuCount", danmuCount);
		this.danmuCount = danmuCount;
	}

	@Override
	public Integer getCollectCount() {
		return collectCount;
	}

	@Override
	public void setCollectCount(Integer collectCount) {
		baseData.put("collectCount", collectCount);
		this.collectCount = collectCount;
	}

	@Override
	public Integer getLikeCount() {
		return likeCount;
	}

	@Override
	public void setLikeCount(Integer likeCount) {
		baseData.put("likeCount", likeCount);
		this.likeCount = likeCount;
	}

	@Override
	public Integer getChasingCount() {
		return chasingCount;
	}

	@Override
	public void setChasingCount(Integer chasingCount) {
		baseData.put("chasingCount", chasingCount);
		this.chasingCount = chasingCount;
	}

	@Override
	public Double getMaxScore() {
		return maxScore;
	}

	@Override
	public void setMaxScore(Double maxScore) {
		baseData.put("maxScore", maxScore);
		this.maxScore = maxScore;
	}

	@Override
	public Double getScore() {
		return score;
	}

	@Override
	public void setScore(Double score) {
		baseData.put("score", score);
		this.score = score;
	}

	@Override
	public Integer getScoreUserCount() {
		return scoreUserCount;
	}

	@Override
	public void setScoreUserCount(Integer scoreUserCount) {
		baseData.put("scoreUserCount", scoreUserCount);
		this.scoreUserCount = scoreUserCount;
	}

	@Override
	public Integer getRanking() {
		return ranking;
	}

	@Override
	public void setRanking(Integer ranking) {
		baseData.put("ranking", ranking);
		this.ranking = ranking;
	}
}
