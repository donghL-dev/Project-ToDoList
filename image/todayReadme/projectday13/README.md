## project-day-13

  * day-12에서 생성한 프로젝트에서 [GUIDE](https://spring.io/guides/gs/securing-web)를 통해 코드 실습 진행.

  * 템플릿에 `home.html` 과 `hello.html` 를 생성하고, MvcCofig 클래스를 생성.

  * MvcConfig 클래스를 생성한 뒤에, `@Configuration` 어노테이션을 붙여주고, `WebMvcConfiguer` 인터페이스를 상속받는다.

  * 그 이후, `addViewController(ViewControllerRegistry registry)` 메소드를 오버라이딩 한 뒤, registry에 `ViewController`를 추가한다.

    * `registry.addViewController("/home").setViewName("home");`

    * /home url로 이동했을 때, `home`이라는 이름의 view name을 가진 `home.hml`로 매핑해주는 방식이다.

    * 그 밖에 다른 ViewController도 추가한다.

  * 그 이후, 스프링 시큐리티 의존성을 추가하고, 실행해보면 모든 페이지가 잠금이 걸린다.

  * 시큐리티 설정을 위한 WebSecurityConfig 클래스를 생성하고 WebSecurityConfigurerAdapter 클래스를 상속받는다.

    * WebSecurityConfig 클래스에 `@Configuration` 어노테이션과 `@EnableWebSecurity` 어노테이션을 사용한다.

    * `@EnableWebSecurity` 어노테이션을 사용하는 순간, 스프링 부트가 제공하는 스프링 시큐리티 설정을 따라간다고 가정한다.

    *  WebSecurityConfig 클래스에 HttpSecurity를 파라미터로 갖는 configure() 메소드 오버라이딩.

      * `.authorizeRequests()` 은 요청에 대해 어떻게 보안을 걸 것인가에 대한 설정 메소드이다.

      * `.antMatchers()` 는 특정한 패턴에 대한 요청들의 처리하기 위한 설정값이다.

      * `.antMatchers().permitAll()` 는 `.antMatchers()`로 지정된 패턴에 대한 요청들은 전부다 허용하라는 의미이다.

        * `.antMatchers("/", "/home").permitAll()` / 와 /home에 대해서는 전부 허용하라, 로그인을 안한 사용자에게도 보이도록 설정.

      * `.anyRequest().authenticated()` 이외의 요청들은 인증이 필요한 요청이라, 인증된 사용자만 가능하다는 의미.

      * `.formLogin()` 속성은 Login에 관한 속성이다. `.anyRequest().authenticated()` 속성으로 인해서 이외의 요청들은 formLogin으로 설정된 loginPage로 이동.

      * `.loginPage()` 은 loginPage로 가는 Url이 어떤것인가에 대한 설정이다.
      
      * `.logout()` 메소드는 로그아웃에 대한 속성 값에 대한 설정이다.

        * `.logoutUrl()` 메소드는 로그아웃시 Redirect 되는 Url을 결정한다.

    * WebSecurityConfig 클래스에 userDetailsService() 메소드 오버라이딩.

      * UserDetails 클래스를 통해 user 생성.

        * `.withDefaultPasswordEncorder()` 속성을 통해 패스워드 인코딩 설정.

        * `.userName()` 을 통해 user 이름 설정.

        * `.password()` 을 통해 패스워드 설정

        * `.roles()` 을 통해 권한 설정.

        * InMemoryUserDetailsManager 클래스를 통해 인 메모리에 유저를 저장.

    * `login.html` 생성

    * `hello.html` 코드 변경, 로그인된 사용자의 정보 표시 및 로그아웃 버튼 생성.

    * 결과

      * 로그인을 하기전까지는 `/` 과 `/home` 과  `/loign` url만 접근 가능.

      * 로그인을 하기전까지는 허용된 url 이외의 url 접근시, `/loign` url로 Redirect

      * 로그인을 성공하면, 기존에 `/home`에서 이동하고자 했던, `/hello` url로 접근할 수 있다.

      * 로그아웃을 하면, 다시 `/login` url로 돌아간다.

      * 즉, 스프링 시큐리티를 통해서 로그인을 성공시키면, 유저는 인증된 사용자가 되고, 인증된 사용자가 가질수 있는 권한을 가지게 된다.

      * 위 코드는 인증된 사용자에게 `/home` 으로 접근을 할 수 있게 하는 권한을 부여하였다.