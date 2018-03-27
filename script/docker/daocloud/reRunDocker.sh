docker rm -f playdata

docker rmi daocloud.io/94540413/play_data_collect_pi:latest

docker run -d --name playdata -p 13001:13001  daocloud.io/94540413/play_data_collect_pi

docker logs -f playdata
