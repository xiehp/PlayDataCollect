
set /p videoPath=

cd %videoPath%

for %%c in (*.mkv) do mkvextract tracks "%%c" 2:"%%c_track3.ass" 3:"%%c_track4.ass" 4:"%%c_track5.ass"

pause