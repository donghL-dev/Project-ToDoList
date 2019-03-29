## Spring Security를 위한 WebSecurityConfig 클래스의 configure() 메소드 로직 정리.

  * WebSecurityConfig 클래스에서 configure 메소드내의 내용 보기.

    * `.antMatchers("/", "/home").permitAll().anyRequest().authenticated()` 이렇게 되있었을 경우.

    * `/`, `/home` url 이외의 요청이 들어올 시에, `anyRequest()`에 걸리고, 스프링 시큐리티는 여기서 시큐리티 컨텍스트라는 저장소에서 authenticated 라는 객체가 있는지 검사한다.

    * 만약, 없다면 `.formLogin()` 메소드로 인해서 로그인 페이지로 이동한다.

    * 로그인 페이지에서 user name과 패스워드를 입력하면, 그 내용으로 UserDetails 클래스를 읽어온다.

    * 그 다음, 입력한 패스워드와 UserDetails 클래스가 가지고 있는 패스워드를 비교한다.

    * 맞으면, 시큐리티 컨텍스트 authenticated 객체를 넣어주고, 인증하기 전, 요청했던 url로 이동한다.

    * 이후에, 인증한 필요하지 않은 url로 이동하고, 인증이 필요한 url로 이동하더라도, 시큐리티 컨텍스트에 authenticated 객체가 있기 때문에, 접근이 가능하다.

    * 시큐리티 컨텍스트에 authenticated 객체가 있다면 인증된 사용자로 확인된다.

    * 로그아웃시에는 시큐리티 컨텍스트에서 authenticated 객체가 사라진다. 그래서, 다시 인증이 필요한 url로 접근시에 로그인이 필요하다.