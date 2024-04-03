# 2023_RYANBUCKS_v2.0
[2023_RYANBUCKS_v1.0](https://github.com/sypark8393/2023_RYANBUCKS_v1.0)의 리팩토링 버전입니다.

<br>

### 📅 리팩토링 기간
202/12/14 ~ 2024/01/31

<br>

## 📌 주요 개선 내용
+ 공통
  + **MVC1 → MVC2**
  + 디렉터리 구조 변경
  + 디자인 통일
+ 비회원 서비스
  + 메뉴(목록) 카테고리 체크박스의 기능이 라디오 버튼처럼 동작하는 문제 수정 
  + 메뉴(상세페이지) 리뷰 페이징 동작 방식 변경 (동기 → 비동기)
  + 진행중/종료된 이벤트를 같이 볼 수 있도록 이벤트 목록 페이지 개선
  + 공지사항 목록 페이징 기능 추가
+ 회원 서비스
  + 회원가입 시 아이디 중복 체크 기능 추가
  + 아이디, 비밀번호 유효성 검증 기능 추가
  + 회원가입 약관 내용 추가
  + 개인정보 확인 및 수정 기능 구현
  + 주문 내역 페이징 기능 추가
  + 장바구니 프로세스 동작 방식 변경 (동기 → 비동기)

<br>

## 📁 디렉터리 구조
+ RYANBUCKS_v2.0
  + WebContent
    + WEB-INF
    + coffee_board
      + espresso
      + story
    + common
      + css
      + img
      + js
    + event_board
    + img
      + coffee
        + epresso
        + story
      + event
      + main
      + mem
      + menu
      + my
      + review
    + member
      + find
      + info
      + join
      + login
      + term
    + my_page
      + order
      + review
    + notice_board
    + shop
      + cart
      + menu
      + order
      + term
    + sitemap
  + src
    + common
    + controller
    + dao
    + dto
    + utils

 <br>

 ## 🎞️ 시연 영상
 + 비회원 서비스
   + [2023_RYANBUCKS_v2.0 │ [비회원 서비스] 시연 영상](https://youtu.be/nKvk7SRVKkg)
 + 회원 서비스
   + [2023_RYANBUCKS_v2.0 │ [회원 서비스] 시연 영상](https://youtu.be/M3x8euhMD5k)
 
