echo off

cd  C:\Program Files\MySQL\MySQL Server 5.7\bin

echo ����·�������ݿ�
set backdir=C:\ProgramData\MySQL\back
set dbname=play_data_collect

echo ���ݵ�ǰ����ʱ�䣬�����ļ����ƣ�......
set YYYYmmdd=%date:~0,4%%date:~5,2%%date:~8,2%
set hhmiss=%time:~0,2%%time:~3,2%%time:~6,2%
set filename=%dbname%_%YYYYmmdd%_%hhmiss%
set fileFullPathName=%backdir%\%filename%.sql
set rarFullPathName=%backdir%\%filename%.rar
echo %fileFullPathName%

echo ִ�б���
mysqldump -h localhost -P 11521 -u backupuser --password="tfytf*^^&^*(*ddgj" %dbname% > "%fileFullPathName%"

echo ѹ���ļ�
rar a -df -m3 -s "%rarFullPathName%" "%fileFullPathName%"

echo back end