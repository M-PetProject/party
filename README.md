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

---

## Package 생성 규칙

### 업무 단위로 package 생성

- controller, service, dao 단위로 package 를 구성하지 않고
- 업무 단위로 package 를 구성
- 예시:) 멤버 관련 업무는 com.study.party.member

```
com.study.party
  member
    MemberController
    MemberService
    MemberDao (MyBatis 의 경우 package 내 Dao 를 생성합니다)
    vo
      MemberVo
  jpa 
    entity
      MemberEntity (JPA 의 경우 entity 의 경로를 asterisk(*)으로 줄 수 없는 번거로움이 있기 때문에 하나로 뭉쳐놨습니다)
    repositoy
      MemberRepository (JPA 의 경우 repository 의 경로를 asterisk(*)으로 줄 수 없는 번거로움이 있기 때문에 하나로 뭉쳐놨습니다)
```

---

## 로그인 사용자 정보 받아오기

- 현재 Spring Security 를 사용하고 있습니다
- Spring Security 에서는 인증된 사용자 정보를 세션에 담아놓고 세션이 유지되는 동안 사용자 객체를 DB로 접근하지 않고 바로 사용할 수 있도록 합니다
- TokenProvider 에서 해당 로직은 처리하고 있습니다

```java
// TokenProvider.generateEntityToken 메서드를 확인합니다
// JWT 를 생성할 때 Claims 에 필요한 정보를 넣습니다 
Claims claims = Jwts.claims().setSubject(String.valueOf(memberVo.getMemberIdx()));
claims.put("member_idx"  , memberVo.getMemberIdx());
claims.put("member_id"   , memberVo.getMemberId() );
claims.put("member_name" , memberVo.getMemberName() );

// TokenProvider.getAuthentication 메서드를 확인합니다
// JWT 를 읽어올 때 사용자 정보가 담긴 CustomUserDetailsVo 를 만들어줍니다
CustomUserDetailsVo principal = new CustomUserDetailsVo(
    nvl(claims.get("member_name"), ""),
    "",
    Arrays.asList()
);
principal.setMemberIdx(Long.parseLong(nvl(claims.get("member_idx"), "0")));
principal.setMemberId(nvl(claims.get("member_id"), ""));
principal.setMemberName(nvl(claims.get("member_name"), ""));
```

- 실제 사용할 때는 Controller 에서 @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo 를 선언하여 사용합니다

```java
public class MemberController {
    ...
    @GetMapping("/member")
    public ResponseEntity getMember(
        HttpServletRequest request,
        @AuthenticationPrincipal CustomUserDetailsVo customUserDetailsVo
    ) {
      System.out.println("customUserDetailsVo.getMemberIdx() :: "+customUserDetailsVo.getMemberIdx());
    ...
}
```

---

## Rest API URL Naming Rule

- URL 에 자원에 대한 행위를 표시하지 않는다
  - GET    : 조회
    - 예시:) 회원 목록 조회 
      - GET : /get/member/list (X)
      - GET : /members         (O)
    - 예시:) 회원 단건 조회
      - GET : /get/member (X)
      - GET : /member/{member_id} or /member?member_id=abcde (O)
  - POST   : 생성 
    - 예시:) 회원 정보 생성
      - POST : /create/member (X)
      - POST : /member        (O)
  - PUT    : 수정
    - 예시:) 회원 정보 수정
      - PUT : /update/member (X)
      - PUT : /member        (O)
  - DELETE : 삭제
    - 예시:) 회원 정보 삭제
      - DELETE : /delete/member (X)
      - DELETE : /member/{member_id} or /member?member_id=abcde (O)
- URL 마지막 문자로 / 를 포함하지 않는다
  - 예시:) GET : /member/{member_id}/ (X)
  - 예시:) GET : /member/{member_id}  (O)
- URL 경로에는 소문자로 작성한다
  - 예시:) GET : /grpCds  (X)
  - 예시:) GET : /grp-cds (O)
- 밑줄(_)은 사용하지 않고 하이픈(-)을 사용한다
  - 예시:) GET : /grp_cds (X)
  - 예시:) GET : /grp-cds (O)
- 리소스 간의 관계는 / 로 표현한다
  - 특정 사용자가 좋아요를 표시한 목록을 조회하는 API의 경우
    - 예시:) GET : /member-likes/{member_id} (X)
    - 예시:) GET : /member/{member_id}/likes (O)

---

## Naming Rule

### Class Naming Rule
- Camel Case 를 사용하여 작성한다
- Controller => 업무명Controller (예시: CommCodeController)
- Service => 업무명Service (예시: CommCodeService)
- DAO => 업무명Dao (예시: CommCodeDao)
- Vo => 업무명Vo (예시: CommCodeVo)
- Repository(JPA) => 업무명Repository (예시: CommCodeRepository)
- Entity(JPA) => 업무명Entity (예시: CommCodeEntity)

### Method Naming Rule
- Camel Case 를 사용하여 작성한다
- 조회 : 
  - 목록 조회 :get업무명복수형 (예시 : getMembers)
  - 단건 조회 :get업무명단수형 (예시 : getMember)
- 생성 : create없무명 (예시 : createMember)
- 수정 : update업무명 (예시 : updateMember)
- 삭제 : delete업무명 (예시 : deleteMember)

### Field Naming Rule
- Camel Case 를 사용하여 작성한다
- 예시 : 
  - Table : 회원_번호 => user_no
  - Vo    : userNo

---

## 결과 응답에 대해서는 HTTP Status Code 를 사용합니다

- 아래 정의된 코드만 사용합니다

### 성공 응답
- 200 : 성공(정상)

### 사용자 에러 응답
- 400 : 필수입력값 오류, 유효성 검사 오류 
- 401 : 권한 없음
- 404 : 리소스 없음

### 서버 에러
- 500 : 서버 에러

### 사용 예제(Controller)

- Controller 에서 직접 CommResponseVo를 생성할 경우
- Controller 에서 필수 입력 파라미터에 대한 DB 조회 없는 유효성 검사를 진행할 경우(null 체크, length 체크, ... 등)  

```java
// com.study.party.comm.vo.CommResponseVo 를 활용하여 Controller 에서 return
// Http Status Code 에 맞는 결과를 return
// 200 : 정상의 경우 
return CommResponseVo.builder()
                     .body(결과 Data)
                     .build()
                     .ok();

// 400 : 잘못된 문법으로 인하여 서버가 요청하여 이해할 수 없음을 의미합니다.
// Parameter 입력값 오류 등 
return CommResponseVo.builder()
                     .body(결과 Data)
                     .build()
                     .badRequest();


// 401 : 비록 HTTP 표준에서는 '미승인(unauthorized)'를 명확히 하고 있지만, 
//       의미상 이 응답은 '비인증(unauthenticated)'를 의미합니다. 
//       클라이언트는 요청한 응답을 받기 위해서는 반드시 스스로를 인증해야 합니다.``
return CommResponseVo.builder()
                     .body(결과 Data)
                     .build()
                     .unauthorized();

// 404 : 서버는 요청받은 리소스를 찾을 수 없습니다. 
//       브라우저에서는 알려지지 않은 URL을 의미합니다. 
//       이것은 API에서 종점은 적절하지만 리소스 자체는 존재하지 않음을 의미할 수 있습니다. 
//       서버들은 인증받지 않은 클라이언트로부터 리소스를 숨기기 위하여 이 응답을 403 대신에 전송할 수도 있습니다. 
//       이 응답 코드는 웹에서 반복적으로 발생하기 때문에 가장 유명할지도 모릅니다.
return CommResponseVo.builder()
                     .body(결과 Data)
                     .build()
                     .notFound();

// 500 : 웹 사이트 서버에 문제가 있음을 의미하지만 
//       서버는 정확한 문제에 대해 더 구체적으로 설명할 수 없습니다.
return CommResponseVo.builder()
                     .body(결과 Data)
                     .build()
                     .internalServerError();
```

- CommResultVo 를 사용할 경우

```java
// com.study.party.comm.vo.CommResponseVo 를 활용하여 Controller 에서 return


CommResponseVo.builder()
              .resultVo(CommResultVo.builder()
                                    .code(200/400/401/404/500)
                                    .data(결과 Data)
                                    .msg("결과에 대한 메시지")
                                    .build())
              .build()
              .toResponseEntity();
```

### 사용 예제(Service)

- CommResultVo 사용 권장

```java

// 200 : 정상 응답
return CommResultVo.builder()
                   .code(200)
                   .data(결과 Data)
                   .msg("결과에 대한 메시지")
                   .build();

// 400 : 파라미터 오류
// Controller 파라미터 유효성 검사에서는 정상이었으나 로직이나 DB 에서 올바르지 않은 데이터가 전달된 경우
// 예:) member_info 테이블에 member_idx 는 5까지만 생성되었는데 파라미터로 6이 전달된 경우
return CommResultVo.builder()
                   .code(400)
                   .data(결과 Data)
                   .msg("결과에 대한 메시지")
                   .build();

// 401 : 권한 없음
// 리소스에 대한 등록, 수정, 삭제의 권한이 없는 경우
// 예:) 공지사항 작성자만 수정이 가능한데 작성자가 아닌 다른 사용자가 수정을 요청한 경우
return CommResultVo.builder()
                   .code(401)
                   .data(결과 Data)
                   .msg("결과에 대한 메시지")
                   .build();

// 500 : 서버 에러
// 로직, DB 처리 중 오류가 발생한 경우
return CommResultVo.builder()
                   .code(500)
                   .data(결과 Data)
                   .msg("결과에 대한 메시지")
                   .build();

```

---

### 테스트 방법

- Service
  - Junit   : src/test/java/com/study/party 아래 class 생성 후 사용
- Controller
  - Swagger : http://localhost:8080/swagger-ui/index.html
    - Authorize : Bearer+" "+토큰값(예시:)Bearer 토큰값)

---

## 참고자료

- .gitignore 파일을 생성해주는 사이트
  - https://www.toptal.com/developers/gitignore/
- Rest API 
  - https://benggri.tistory.com/124
- @AuthenticationPrincipal 관련 자료
  - https://velog.io/@jyleedev/AuthenticationPrincipal-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EC%A0%95%EB%B3%B4-%EB%B0%9B%EC%95%84%EC%98%A4%EA%B8%B0
  - https://dev-gyus.github.io/spring/2021/03/09/Spring-ConfigurationProperties0.html