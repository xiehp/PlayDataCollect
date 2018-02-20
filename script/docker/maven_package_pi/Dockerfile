#FROM resin/rpi-raspbian
#FROM resin/resinos 
FROM resin/raspberrypi3-openjdk 
 
 
# 更新源，安装ssh server 
RUN apt update 
RUN apt upgrade -y 
RUN apt install -y openssh-server 
RUN apt install -y vim man net-tools iputils-ping 

#RUN apt install java 
RUN apt install git 
RUN apt install maven 
 
# 创建sshd目录，否则好像会启动失败 
RUN mkdir /var/run/sshd \ 
# 修改sshd配置文件，可以使用密码 
&& sed -i 's/PermitRootLogin prohibit-password/PermitRootLogin yes/' /etc/ssh/sshd_config \ 
&& sed -i 's/#PermitRootLogin yes/PermitRootLogin yes/' /etc/ssh/sshd_config \ 
# 设置root ssh远程登录密码为123456 
&& echo "root:112233" | chpasswd  
 
 
#Change timezone CST 
#WORKDIR /user/ 
#COPY ["/usr/share/zoneinfo/Asia/Shanghai", "/etc/localtime"] 
COPY ["Shanghai", "/etc/localtime"] 
#RUN \cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime 
 
 
# 容器需要开放SSH 22端口 
EXPOSE 22 
 
 
# 默认执行脚本 
CMD ["/usr/sbin/sshd", "-D"]
