:: delete the file from local and git rep

:: the branch
deleteBranch=master

:: delete file, can use reg
git filter-branch --force --index-filter 'git rm --cached --ignore-unmatch app/playdatacollect-front/lib/SelfLiv/playdatacollect-common-1.1-SNAPSHOT.jar' --prune-empty --tag-name-filter cat -- --all
::git filter-branch --force --index-filter 'git rm --cached --ignore-unmatch sound/Music_*.mp3' --prune-empty --tag-name-filter cat -- --all

:: delete folder
::git filter-branch --force --index-filter 'git rm -r --cached --ignore-unmatch app/playdatacollect-front/lib/' --prune-empty --tag-name-filter cat -- --all


git push origin master --force --tags
git push origin master --force --all


:: 步骤三: 清理和回收空间
:: 虽然上面我们已经删除了文件, 但是我们的repo里面仍然保留了这些objects, 等待垃圾回收(GC), 所以我们要用命令彻底清除它, 并收回空间.

:: 命令如下:
$ rm -rf .git/refs/original/

$ git reflog expire --expire=now --all

$ git gc --prune=now

:: Counting objects: 2437, done.
:: # Delta compression using up to 4 threads.
:: # Compressing objects: 100% (1378/1378), done.
:: # Writing objects: 100% (2437/2437), done.
:: # Total 2437 (delta 1461), reused 1802 (delta 1048)

$ git gc --aggressive --prune=now

:: Counting objects: 2437, done.
:: # Delta compression using up to 4 threads.
:: # Compressing objects: 100% (2426/2426), done.
:: # Writing objects: 100% (2437/2437), done.
:: # Total 2437 (delta 1483), reused 0 (delta 0)











