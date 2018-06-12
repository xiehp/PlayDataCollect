package xie.playdatacollect.influxdb.pojo.measuerment;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import xie.common.component.influxdb.pojo.measurement.XBaseMeasurementEntity;

import java.time.Instant;
import java.util.Date;

@Measurement(name = "day_base_data")
public class MDayPlayData extends XBaseMeasurementEntity {

	private Date date;

	@Column(name = "time")
	private Instant time;


	@Column(name = "aid", tag = true)
	private String aid;

	@Column(name = "网站", tag = true)
	private String site;

	@Column(name = "类型", tag = true)
	private String type;

	@Column(name = "名字", tag = true)
	private String name;

	@Column(name = "播放数")
	private Long playCount;

	public static String BASE_COL_PLAY_COUNT = "playCount";
	@Column(name = "评论数")
	private Integer commentCount;

	public static String BASE_COL_COMMENT_COUNT = "commentCount";
	@Column(name = "分享数")
	private Integer shareCount;

	@Column(name = "弹幕数")
	private Integer danmuCount;

	@Column(name = "收藏数")
	private Integer collectCount;

	@Column(name = "喜欢数")
	private Integer likeCount;

	@Column(name = "追番数")
	private Integer chasingCount;

	@Column(name = "最大评分")
	private Double maxScore;

	@Column(name = "评价分")
	private Double score;

	@Column(name = "评分人数")
	private Integer scoreUserCount;

	@Column(name = "排名")
	private Integer ranking;

	@Column(name = "承包数")
	private Integer 承包数;

	@Column(name = "硬币数")
	private Integer coinCount;

	@Column(name = "albumId")
	private Integer albumId;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Instant getTime() {
		return time;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPlayCount() {
		return playCount;
	}

	public void setPlayCount(Long playCount) {
		this.playCount = playCount;
	}

	public static String getBaseColPlayCount() {
		return BASE_COL_PLAY_COUNT;
	}

	public static void setBaseColPlayCount(String baseColPlayCount) {
		BASE_COL_PLAY_COUNT = baseColPlayCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public static String getBaseColCommentCount() {
		return BASE_COL_COMMENT_COUNT;
	}

	public static void setBaseColCommentCount(String baseColCommentCount) {
		BASE_COL_COMMENT_COUNT = baseColCommentCount;
	}

	public Integer getShareCount() {
		return shareCount;
	}

	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}

	public Integer getDanmuCount() {
		return danmuCount;
	}

	public void setDanmuCount(Integer danmuCount) {
		this.danmuCount = danmuCount;
	}

	public Integer getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getChasingCount() {
		return chasingCount;
	}

	public void setChasingCount(Integer chasingCount) {
		this.chasingCount = chasingCount;
	}

	public Double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Double maxScore) {
		this.maxScore = maxScore;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getScoreUserCount() {
		return scoreUserCount;
	}

	public void setScoreUserCount(Integer scoreUserCount) {
		this.scoreUserCount = scoreUserCount;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public Integer get承包数() {
		return 承包数;
	}

	public void set承包数(Integer 承包数) {
		this.承包数 = 承包数;
	}

	public Integer getCoinCount() {
		return coinCount;
	}

	public void setCoinCount(Integer coinCount) {
		this.coinCount = coinCount;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}
}
