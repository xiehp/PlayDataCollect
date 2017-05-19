
set /p videoPath=

echo %cd%
echo %~dp0

set oldPath=%cd%

cd /d %videoPath%

#for %%c in (*.mkv) do echo %%~nc
for %%c in (*.mkv) do mkvextract tracks "%%c" 2:"%%~nc_track3_chi.ass" 3:"%%~nc_track4_chi.ass" 4:"%%~nc_track5_jpn.ass"

cd /d %oldPath%

rem 运行结束

pause