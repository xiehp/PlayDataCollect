
set /p videoPath=

echo %cd%
echo %~dp0

set oldPath=%cd%

cd /d %videoPath%

for %%c in (*.mkv) do mkvextract tracks "%%c" 2:"%%c_track3.ass" 3:"%%c_track4.ass" 4:"%%c_track5.ass"

cd /d %oldPath%

rem 运行结束

pause