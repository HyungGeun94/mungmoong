# mungmoong

개요:

언어 : 자바, 스프링 부트  

의존성 : websocket(stomp),JPA,querydsl,springdata,mail,security

뷰 템플릿 : html,css,js,thymeleaf

데이터베이스 : mysql 

도구 : redis,docker

==구현내용==  

- 환경설정
1. env파일을 통한 docker 변수 관리
2. application-secret.yml을 통한 application 환경변수 관리
3. 격리 환경 -> local(완료), dev(진행중), prod(진행중)

- 서버구축방안
1. aws ec2 사용
2. redis,rds,아마존 도커허브,s3,

