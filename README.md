# 점심방 Pet Project

## 백엔드 Spring Boot

---

## 초기 세팅

### DB 세팅 필요!

- 로컬 디비를 사용할 경우
  - DB : MariaDB 
  - url : localhost:3306/party
  - username : study
  - password : study

- AWS 디비를 사용할 경우
  - Notion Spring 세팅 방법 참고

---

## 개발 가이드

### Class Naming Rule
- Camel Case 를 사용하여 작성한다
- Controller => 업무명Controller (예시: CommCodeController)
- Service => 업무명Service (예시: CommCodeService)
- DAO => 업무명Dao (예시: CommCodeDAO)
- Repository(JPA) => 업무명Repository (예시: CommCodeRepository)

### Method Naming Rule
- Camel Case 를 사용하여 작성한다
- 조회 : get업무명
- 생성 : create없무명
- 수정 : update업무명
- 삭제 : delete업무명

### Field Naming Rule
- Camel Case 를 사용하여 작성한다
- 예시 : 
  - Table : 회원_번호 => user_no
  - Vo    : userNo

---

### 테스트 방법

---

## 참고자료

- .gitignore 파일을 생성해주는 사이트
  - https://www.toptal.com/developers/gitignore/
