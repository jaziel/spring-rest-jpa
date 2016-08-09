# spring-rest-jpa

Demo application using Spring, rest and JPA. 
Added support to shared cache (ehcache + terracotta). The Dockerfile-terracotta will build a basic container for terracotta ( remember to expose 9510 and 9540). 

#USAGE
to start the docker image, is required a mysql container ( or other database, changing the applicationContext.xml to use a different vendor) and a terracotta server(4.3.2) (or change the ApplicationContext.xml to use the ehcache.xml instead of echcache-terracotta.xml). 
The database container must have a demo schema ( you can use the import.sql to import data) and be linked as database.
The terracotta server must be linked as terracotta-server.
command: docker run --link database:database --link terracotta-server:terracotta-server -p:8080:8080 --name springrestjpa springrestjpa:0.1

( or change the configurations in the ApplicationContext.xml).

TODO: Add the failsafe if terracotta server is offiline.
