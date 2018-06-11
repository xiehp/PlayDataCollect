echo off

cd  C:\Program Files\MySQL\MySQL Server 5.7\bin

echo 配置路径和数据库
set backdir=C:\ProgramData\MySQL\back
set dbname=play_data_collect

echo 根据当前日期时间，生成文件名称，......
set YYYYmmdd=%date:~0,4%%date:~5,2%%date:~8,2%
set hhmiss=%time:~0,2%%time:~3,2%%time:~6,2%
set filename=%dbname%_%YYYYmmdd%_%hhmiss%
set fileFullPathName=%backdir%\%filename%.sql
set rarFullPathName=%backdir%\%filename%.rar
echo %fileFullPathName%

echo 执行备份
mysqldump -h localhost -P 11521 -u backupuser --password="tfytf*^^&^*(*ddgj" %dbname% > "%fileFullPathName%"

echo 压缩文件
rar a -df -m3 -s "%rarFullPathName%" "%fileFullPathName%"

echo back end