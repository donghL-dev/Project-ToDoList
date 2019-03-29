## project-day-8

  * 로그인 기능 구현

    * 로그인 관련 처리를 위한 LoginController 클래스 생성

    * 로그인 뷰 [생성](./image/7.png).

  * 회원가입 기능 구현

    * 회원가입 관련 처리를 위한 RegisterController 클래스 생성

    * 회원가입 뷰 [생성](./image/8.png).
  
  * 회원가입 확인

    * Username, Email, Password Ajax 객체 생성 후, 데이터 전송.

    * 필요한 값을 받아와서 회원가입 로직을 통해 유저에 필요한 값을 저장 후, 유저 [생성](./image/9.png).

  * 로그인 확인

    * Username, Password Ajax 객체 생성 후, 데이터 전송.

    * loginUser 함수 생성 후, 필요한 값을 받아온 후 로그인 체크.

      * Username이 존재하지 않을 경우, 로그인 실패.

      * Username이 존재할 경우, password가 일치 하지 않을시 로그인 실패.

      * Username이 존재할 경우, password가 일치하다면 로그인 성공.