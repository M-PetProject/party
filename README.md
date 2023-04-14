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
  - Notion [Back-End Spring Boot] 설정 방법

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
- 조회 : get업무명 (예시 : getMember)
- 생성 : create없무명 (예시 : createMember)
- 수정 : update업무명 (예시 : updateMember)
- 삭제 : delete업무명 (예시 : deleteMember)

### Field Naming Rule
- Camel Case 를 사용하여 작성한다
- 예시 : 
  - Table : 회원_번호 => user_no
  - Vo    : userNo

---

## HTTP Status Code

### 성공 응답
- 200 : 성공(정상)

### 사용자 에러 응답
- 400 : 필수입력값 오류, 유효성 검사 오류 
- 401 : 권한 없음
- 404 : 리소스 없을 때

### 서버 에러
- 500 : 서버 에러

### 사용 예제

```
com.study.party.comm.vo.CommResponseVo 를 활용하여 Controller 에서 return

// Http Status Code 에 맞는 결과를 return

// 200 : 정상의 경우 
CommResponseVo.builder()
              .body(결과 Data)
              .build()
              .ok();

// 400 : 잘못된 문법으로 인하여 서버가 요청하여 이해할 수 없음을 의미합니다.
// Parameter 입력값 오류 등 
CommResponseVo.builder()
              .body(결과 Data)
              .build()
              .badRequest();


// 401 : 비록 HTTP 표준에서는 '미승인(unauthorized)'를 명확히 하고 있지만, 
//       의미상 이 응답은 '비인증(unauthenticated)'를 의미합니다. 
//       클라이언트는 요청한 응답을 받기 위해서는 반드시 스스로를 인증해야 합니다.
CommResponseVo.builder()
              .body(결과 Data)
              .build()
              .unauthorized();

// 404 : 서버는 요청받은 리소스를 찾을 수 없습니다. 
//       브라우저에서는 알려지지 않은 URL을 의미합니다. 
//       이것은 API에서 종점은 적절하지만 리소스 자체는 존재하지 않음을 의미할 수 있습니다. 
//       서버들은 인증받지 않은 클라이언트로부터 리소스를 숨기기 위하여 이 응답을 403 대신에 전송할 수도 있습니다. 
//       이 응답 코드는 웹에서 반복적으로 발생하기 때문에 가장 유명할지도 모릅니다.
CommResponseVo.builder()
              .body(결과 Data)
              .build()
              .notFound();

// 500 : 웹 사이트 서버에 문제가 있음을 의미하지만 
//       서버는 정확한 문제에 대해 더 구체적으로 설명할 수 없습니다.
CommResponseVo.builder()
              .body(결과 Data)
              .build()
              .internalServerError();
```


---

### 테스트 방법

- Service
  - Junit   : src/test/java/com/study/party 아래 class 생성 후 사용
- Controller
  - Swagger : http://localhost:8080/swagger-ui/index.html

---

## 참고자료

- .gitignore 파일을 생성해주는 사이트
  - https://www.toptal.com/developers/gitignore/
