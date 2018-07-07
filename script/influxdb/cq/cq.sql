-- 命令
SHOW CONTINUOUS QUERIES

-- 模板
SELECT max("XXX") AS "XXX"
INTO "play_data"."autogen"."XXX"
FROM "play_data"."autogen"."base_data"
WHERE XXX
GROUP BY time(1d)
FILL(previous)

CREATE CONTINUOUS QUERY "cq_XXX" ON "play_data"
RESAMPLE FOR 2h
BEGIN
  SELECT max("XXX") AS "XXX"
  INTO "play_data"."autogen"."XXX"
  FROM "play_data"."autogen"."base_data"
  WHERE XXX
  GROUP BY time(1d)
  FILL(previous)
END



-- 连续查询每小时的播放量数据
-- 原始sql
DROP MEASUREMENT "hour_base_data"

DROP CONTINUOUS QUERY "cq_hour_base_data" ON "play_data"

CREATE CONTINUOUS QUERY "cq_hour_base_data" ON "play_data"
BEGIN
  SELECT max("播放数") AS "播放数"
  INTO "play_data"."autogen"."hour_base_data"
  FROM "play_data"."autogen"."base_data"
  WHERE time > now() - 30d and "名字" <> '' and "类型" = 'program'
  GROUP BY time(1h), "名字", "网站", "类型"
  FILL(previous)
END

-- 连续查询每天的播放量数据
-- 原始sql
DROP MEASUREMENT "day_base_data"

DROP CONTINUOUS QUERY "cq_day_base_data" ON "play_data"

CREATE CONTINUOUS QUERY "cq_day_base_data" ON "play_data"
RESAMPLE FOR 1h
BEGIN
  SELECT max("播放数") AS "播放数"
  INTO "play_data"."autogen"."day_base_data"
  FROM "play_data"."autogen"."base_data"
  WHERE time > now() - 30d and "名字" <> '' and "类型" = 'program'
  GROUP BY time(1d), "名字", "网站", "类型"
  FILL(previous)
END

