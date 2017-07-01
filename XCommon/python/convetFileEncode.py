import os
import sys
import codecs
import chardet


def convert(file, in_enc="UTF-16", out_enc="utf-8-sig"):
    try:
        if file.endswith("_encode_back"):
            return

        print("convert " + file)

        # f = codecs.open(file, "r", in_enc)
        f = open(file, "rb")
        temp = f.read()
        f.close()

        encodeResult = chardet.detect(temp)

        if encodeResult["encoding"].lower() == "utf-8-sig":
            # 已经是带签名utf-8不需要转换
            print("{0}已经是带签名utf-8不需要转换".format(file))
            return


        utf8Str = temp.decode(encodeResult["encoding"])


        # new_content = codecs.open(file, "r", encoding=encodeResult["encoding"])

        # 将原始文件改名
        os.rename(file, file + "_encode_back")

        # 重写文件
        reFile = codecs.open(file, 'w', out_enc)
        reFile.write(utf8Str)
        reFile.close()

    except Exception as err:
        print("IO error:{0}".format(err))


def main(dirPath):
    for root, dirs, files in os.walk(dirPath, False):
        for file in files:
            convert(root + os.sep + file)


if __name__ == "__main__":
    dirPath = "M:\\AnimeShotSite\\anime\\C\\CLANNAD\\第二季\\字幕\\test"
    # 直接在py脚本后指定路径
    if (len(sys.argv) > 1) :
        dirPath = sys.argv[1]
    main(dirPath)
