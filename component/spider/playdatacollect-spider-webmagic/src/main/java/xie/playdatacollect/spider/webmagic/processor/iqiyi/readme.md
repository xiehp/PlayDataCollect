#爱奇艺播放数据解析



##1.通过http://www.iqiyi.com/dongman获得新番数据
1. 获得列表数据
    1. 节目ID
    2. 节目名称
    3. 节目URL http://www.iqiyi.com/a_19rrh1sifx.html
    4. 最新剧集URL
    5. 最新剧集ID 935140700 http://www.iqiyi.com/v_19rrfh6nn4.html

##2.通过节目ID获取各种数据
1. 获取分数信息 http://score-video.iqiyi.com/beaver-api/get_sns_score?qipu_ids=218506801&appid=21
    1. qipu_ids 节目ID
    2. appid 不知道什么用
    3. 返回sns_score
        {"code":"A00000","data":[{"qipu_id":218506801,"sns_score":8.6}],"msg":"","count":0}

##3.通过剧集URL和剧集ID获得某些数据
1. 剧集URL http://www.iqiyi.com/a_19rrh1sifx.html
    1. 获取到节目ID
2. http://mixer.video.iqiyi.com/jp/mixin/videos/935140700
    1. 通用信息，剧集共享，可获得该节目的基本信息以及剧集的基本信息
        1. 播放量:playCount
        2. 节目ID:albumId
        3. 节目名:albumName
        3. 节目URL:albumUrl
        4. 节目图:albumImageUrl
        5. 最后一个剧集ID:latestId
        6. 最后一个剧集集数:latestOrder
        7. 最后一个剧集ID:latestUrl
    2. 剧集独立信息，可获得开始信息，上传下载信息
        1. 下载量:downCount
        2. 发布时间:issueTime



