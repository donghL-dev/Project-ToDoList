Prjoect - To Do List
===
* Spring Boot를 이용하여 Web에서 동작 가능한 To Do List를 구현하기 위한 프로젝트입니다.

* 이 저장소는 To Do List 프로젝트를 진행하면서 소스코드를 관리하고 버전관리를 위한 저장소입니다.

* 목적 및 인원
  
  * Spring Boot & JPA 학습 및 Web Application(To Do List) 개발.

  * Spring Security 학습 및 로그인, 회원가입 구현.

  * 인원 : [민경환](https://www.github.com/ber01), [박동현](https://www.github.com/pdh6547), [신무곤](https://www.github.com/mkshin96), [신재홍](https://www.github.com/woghd9072), [양기석](https://www.github.com/yks095), [엄태균](https://www.github.com/etg6550), [임동훈](https://www.github.com/dongh9508), [최광민](https://www.github.com/rhkd4560), [하상엽](https://www.github.com/hagome0)

### 개발환경

|도구|버전|
|:---:|:---:|
| Framework |Spring Boot 2.1.3 |
| OS |Windows 10|
|IDE |IntelliJ IDEA Ultimate |
|JDK |JDK 1.8|
|DataBase |MySQL 8.0.3|
|Build Tool |Gradle 5.2.1|

### 주요기능 [확인](https://github.com/dongh9508/Project-ToDoList/tree/master/image/MainFunction)

### project-day-1 

  <details><summary>CLICK</summary>
  <p>

  * Spring-Boot-Web-TDL 프로젝트 생성.

  * ToDoList 클래스 설계 및 생성.

    * Idx(키) -> Integer

    * Description(내용) -> String

    * Status(완료 여부) -> Boolean

    * CreatedDate(생성 시간) -> LocalDateTime

    * CompletedDate(완료 시간) -> LocalDateTime
  </p>
  </details>

### project-day-2

  <details><summary>CLICK</summary>
  <p>

  * 프로젝트 패키지 생성.

    * 도메인, 컨트롤러, 서비스, 레포지토리 패키지 생성.

  * 프로젝트와 MySQL 연동 및 테스트

    * application.yml 및 build.gradle 수정

    * MySQL DB에 데이터 삽입 및 [확인.](./image/1.png)

  * 서비스 호출 및 뷰 반환을 위한 ToDoListController 클래스 생성.

  * 저장소 호출 및 데이터 반환을 위한 ToDoListService 클래스 생성.

  * 데이터를 보여주기 위한 list.html 생성

    * 부트 스트랩을 이용해서 뷰 꾸미기.
    
    * 부트 스트랩을 Spring 프레임워크에서 사용시에 경로 매핑을 주의해야함.

        * CSS 적용 시, 경로는 /static/css가 아니라 /css로 경로 설정해야 함.

            * `<link rel="stylesheet" href="/css/bootstrap.min.css"/>`
    
    * 부트스트랩을 활용한 뷰 [확인](./image/2.png)

  * IntelliJ IDEA 와 MySQL [연동](./image/3.png)
  </p>
  </details>

### project-day-3

  <details><summary>CLICK</summary>
  <p>

  * View 수정 및 [업데이트](./image/4.png)
  </p>
  </details>

### project-day-4

  <details><summary>CLICK</summary>
  <p>

  * View 수정 및 업데이트

    * css 설정 값 추가 및 수정 
    
    * Header와 Footer 생성.
  </p>
  </details>

### project-day-5

  <details><summary>CLICK</summary>
  <p>

  * to do list 목록의 CRUD를 위한 ToDoListRestController 클래스 생성.

    * to do list 목록의 생성을 위한 값을 View에서 Input을 통해 받아옴.

    * 받아온 Input값을 postToDoList 메소드를 통해서 및 PostMapping으로 url 매핑 처리후 ToDoListService에서 로직 처리.

    * ToDoListService에서 로직을 통해 DB에 값을 저장 한 뒤 View가 Redirect 된 후 저장된 목록 출력.
  </p>
  </details>

### project-day-6

  <details><summary>CLICK</summary>
  <p>

  * to do list 목록의 삭제 기능과 상태와 내용 업데이트 기능 구현

    * 삭제를 하기 위해서 @PathVariable를 통해서 idx 값을 받아온 뒤 DeleteMapping으로 매핑시킨 뒤, deleteToDoList 메소드를 생성해서 로직 처리.

    * 상태와 내용 업데이트를 위해서  @PathVariable를 통해서 idx 값을 받아온 뒤 PutMapping으로 매핑시킨 뒤, putDescription, putStatus 메소드를 생성해서 로직 처리.

  * 기존의 View에서 [업데이트](./image/5.png)
  </p>
  </details>

### project-day-7

  <details><summary>CLICK</summary>
  <p>

  * User 모델과 ToDoList 모델 1 : 1 관계 설정.

  * User 도메인 클래스 생성

    * Idx(키) -> Integer

    * Name(이름) -> String

    * Email(이메일) -> String

    * Password(비밀번호) -> String
  
  * 기존의 ToDoList 클래스에 User 컬럼 추가.

    ```java
    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    ```

  * User 객체 생성 후 MySQL DB에 데이터 삽입.

    * CommandLineRunner를 이용해서 생성 로직 처리.

  * User 객체를 이용하기 위해서 Controller, Repository, Service 클래스 생성.

  * ToDoListController 클래스와 ToDoListRestController의 수정을 통해서 User 객체 값 [저장](./image/6.png).
  </p>
  </details>

### project-day-8

  <details><summary>CLICK</summary>
  <p>

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
  </p>
  </details>

### project-day-9

  <details><summary>CLICK</summary>
  <p>

  * 로그인을 하지 않고 To Do List url로 이동할 경우, 로그인 화면으로 redirect 되도록 설정.

  * To Do List에 현재 로그인을 한 유저가 등록한 To Do 항목과 Complete 항목이 보이도록 설정.

  * To Do가 등록될 때, DB의 To Do List의 테이블에도 현재 로그인 한 유저의 Idx가 [저장](./image/tododb.png)됨.
  </p>
  </details>

### project-day-10

  <details><summary>CLICK</summary>
  <p>

  * User 모델 객체와 ToDoList 모델 객체간 관계를 단방향 관계에서 양방향 관계로 재설정.

    * `@OneToMany` 어노테이션과 `@ManyToOne` 어노테이션을 사용하여 관계성 재설정.

    * Foreign Key를 가지고 있고 OwnerShip을 가지고 있는 모델 객체에 `@ManyToOne` 어노테이션을 사용.

    * OwnerShip을 가지고 있지 않은 모델 객체에 `@OneToMany` 어노테이션을 사용.

    * `@OneToMany`를 사용한 모델 객체에 `@OneToMany` 어노테이션의 속성 중 하나인 mappedby로 OwnerShip을 가지고 있는 객체의 변수명을 기입한다.

    * OwnerShip을 가지고 있지 않은 모델 객체에는 OwnerShip을 가지고 있는 모델 객체로 된 필드를 Collection 자료형으로 선언한다.

  * ToDoList 모델에 `@ManyToOne` 어노테이션을 사용하고, User 모델에 `@OneToMany` 어노테이션을 사용해서 N : 1 양방향 관계로 설정.

  * 테이블 및 코드 [확인](https://github.com/dongh9508/Project-ToDoList/tree/master/image/RelationShip)
  </p>
  </details>

### project-day-11

  <details><summary>CLICK</summary>
  <p>

  * 현재 User가 자신이 등록한 ToDoList를 가지고 있어야 한다.

  * User 도메인 클래스의 `@OneToMany` 어노테이션의 속성으로 FetchType을 EAGER로 설정한다.

  * User 도메인 클래스에 있던 List<ToDoList> todolists 필드에 현재 유저가 작성한 ToDoList 목록을 넣기 위해 ArrayList로 생성한다.

  * User 도메인 클래스의 List<ToDoList> todolists 필드에 현재 유저가 작성한 ToDoList 목록을 넣기 위한 add 메소드 생성.

    ```java
    public class User {
      
      ...
      ...
      ...

      @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
      private List<ToDoList> toDoLists = new ArrayList<>();

      ...
      ...

      public void add(ToDoList toDoList) {
        toDoList.setUser(this);
        this.toDoLists.add(toDoList);
      }
    }
    ```
  </p>
  </details>

### prjoect-day-12

  <details><summary>CLICK</summary>
  <p>

  * /list url로 redirect 될 때마다 콘솔에 현재 User의 Idx와 User가 가지고 있는 ToDoList들의 객체들을 출력.

  * Spring Security 적용을 위한 테스트 프로젝트 생성.

    * Spring Security를 위한, 의존성 설정.

      ```java
      dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
        implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
        implementation 'org.springframework.boot:spring-boot-starter-web'
        compile 'org.springframework.security:spring-security-web:4.2.7.RELEASE'
        compile 'org.springframework.security:spring-security-config:4.2.7.RELEASE'
        compileOnly 'org.projectlombok:lombok'
        runtimeOnly 'org.springframework.boot:spring-boot-devtools'
        runtimeOnly 'com.h2database:h2'
        runtimeOnly 'mysql:mysql-connector-java'
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
      }
      ```
    
    * application.yml 설정.

      ```java
      server:
        port: 8080

      logging:
        level:
          root: WARN
          org.springframework.web: INFO
          org.springframework.security: INFO

      spring:
        thymeleaf:
          cache: false
      ```

    * templates에 필요한 index.html, login.html, user/index.html 생성.

    * 스프링 시큐리티를 이용한 로그인을 위한 코드 작성.

      * MainController 클래스 생성.

      * SecurityConfig 클래스 생성.

        * SecurityConfig 클래스에 WebSecurityConfigurerAdapter 클래스를 상속받고, `@EnableWebSecurity` 어노테이션을 적용함.

        * configure() 메소드와, configureGlobal() 메소드 작성.

          * configureGlobal() 메소드의 인메모리 인증 코드 부분의 password 부분을 인코딩 해주지 않아서 에러가 일어남.

          * PasswordEncoder 클래스를 이용해서 인코딩 에러가 일어나는 부분을 해결.
  </p>
  </details>

### prjoect-day-13

  <details><summary>CLICK</summary>
  <p>

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
  </p>
  </details>

### prjoect-day-14

  <details><summary>CLICK</summary>
  <p>

  * [GUIDE](https://spring.io/guides/gs/securing-web)를 통해 진행했던 실습 코드를 수정 및 리팩토링.

  * 지난 코드 내용 [리뷰](https://github.com/dongh9508/Project-ToDoList/tree/master/image/SECREVIEW)

  * 이전에 진행했던 UserDetails 클래스를 통해서 인 메모리에 유저를 저장했던 방식은 테스트 코드에서 주로 쓰이는 방식.

  * 현실적인 User를 생성하기 위해서 이전 코드에서 UserDetails 클래스 코드 부분 제거.

  * AccountController, AccountService, AccountRepository 생성.

  * AccountService 클래스에서 UserDetailsService 인터페이스를 상속받는다.

    * `loadUserByUsername()` 메소드를 오버라이딩한다.

      * `loadUserByUsername()` 메소드에서 파라미터로 받은 username으로 Account를 레포에서 찾아오고, 찾아온 Account를 UserDetalis 인터페이스로 변환한다.

      * Account는 현재 프로젝트만의 도메인이므로, 스프링 시큐리티는 이 도메인을 알 수가 없기 때문에, 스프링 시큐리티가 알 수 있도록 UserDetalis 인터페이스로 변환한다.

      * UserDetalis 객체가 생성된 코드.

        ```java
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return null;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        }
        ```
      * ROLE에 관한것을 원래는 따로 만들어야 하나, 간단하게 UserDetails 안에 ROLE을 만들어서 처리함.

      * User를 생성하기 위해서 WebSecurityConfig 클래스에서 `/careate` url도 인증없이 접속할 수 있도록 수정.

      * 프로젝트 빌드 후, `/create` url로 이동하면, 생성된 계정을 [확인](./image/22.png)이 가능하다.

        * 해당 url로 이동했을 때, 패스워드가 그대로 노출되기 때문에 문제가 됨. 이런식으로 해선 안됨.

      * 확인된 계정으로 로그인을 하더라도, 콘솔 로그창에 에러가 출력되면서 로그인이 되지 않음.

        * 에러를 확인해본 결과, password 인코딩 문제가 에러의 원인이 됨.

        * 해결을 위해선 WebSecurityConfig 클래스에 PasswordEncoder 타입의 passwordEncoder() 메소드를 `@Bean`으로 등록.

          ```java
          @Bean
          public PasswordEncoder passwordEncoder() {
              return PasswordEncoderFactories.createDelegatingPasswordEncoder();
          }
          ```
        * AccountService 클래스에서 account의 패스워드를 불러와서 PasswordEncoder 클래스를 통해서 인코딩을 거친 후 저장.
      
      * 프로젝트 재빌드 후, `/create` url로 이동하면, 생성된 계정의 패스워드가 인코딩 된 것을 [확인](./image/23.png)할 수 있다.

      * 인코딩을 거친 후에는 로그인 시에 콘솔에 나타났던 패스워드 인코딩 에러가 일어나지 않고 정상 로그인이 됨.   

      * UserDetalis 객체를 쓸 경우, 코드가 길어지기 때문에, 가독성이 떨어진다. 
      
      * 스프링 시큐리티 자체에 내장되어 있는 User라는 객체가 UserDetalis를 상속받으므로 User 객체로 대체하면 장황한 코드를 줄일 수 있음.

        * 첫번째 파라미터는 User ID, 두번째는 패스워드, 세번째는 해당 유저의 ROLE이 들어감.

        ```java
        return new User(account.getEmail(), account.getPassword(), authorities);
        ```

      * 생성된 ROLE에 의해서 WebSeucrityConfig 클래스에서 접근 권한에 대해 다르게 설정이 가능.

        * `.antMatchers("/admin/**").hasRole("ADMIN")` 와 같이 설정한다면, `/admin` 으로 시작되는 모든 url은 ADMIN ROLE을 가진 유저만 접근이 가능하단 의미가 됨.
  </p>
  </details>

### prjoect-day-15

  <details><summary>CLICK</summary>
  <p>

  * ToDoList 프로젝트에 Spring Security 적용.

  * 기존의 build.gradle 에 Spring Security 을 위한 의존성 추가.

    ```java
    compile("org.springframework.boot:spring-boot-starter-security")
    ```
  
  * 프로젝트 하부에 config 패키지를 생성하고 스프링 시큐리티를 설정을 위한 SecurityConfig 클래스 생성.

    * SecurityConfig 클래스에 `@EnableWebSecurity` 어노테이션을 사용하고, WebSecurityConfigurerAdapter 클래스를 상속받는다.

    * 위와 같이 진행할 경우, 사이트 전체가 잠겨서 configure() 메소드를 오버라이딩 해서 페이지의 인증을 해제.

    * 자원에 대한 접근 해제, 모든 경로에 대해 PermitAll.

  * UserRole 도메인을 생성.

    * User와 `@ManyToOne` 어노테이션을 사용해서 관게성을 가진다.

    * User 클래스 쪽에서 UserRole과 `@OneToMany` 관계를 갖고, 엔티티들의 영속관계를 한번에 처리하기 위해, casccade 속성을 주고, UserRole과 User를 둘다 즉시 조회하기 위해서 fetchtype은 EAGER로 설정한다.

    * 관계성을 매핑하고 난 이후, 'entityManagerFactory' 에러가 발생.

      * 기존의 ToDoList와의 관계성 매핑으로 인해서, UserRole과의 관계와 충돌 발생. ToDoList의 FetchType을 Lazy로 주면서 해결.
    
    * 도메인 간의 관계성 설정을 마친후, 계정 생성 시도.

      * Spring Security를 적용할 경우, 기존의 Ajax의 POST 호출 시 403 Forbidden 에러가 발생.

      * 원인은 csrf 필터로 인해서, csrf 토큰 값이 누락되어서 발생하는 문제. 

      * Ajax 요청 Header에 csrf token 정보를 포함해서 전송.

  * UserDTO 도메인 생성.

    * DTO : 각 계층간의 데이터 교환을 위해 사용되는 순수 객체, DTO는 각 계층간 데이터 전송을 위해 아무런 로직을 갖지 않고 오직 데이터를 담기 위해 사용되는 필드와 Getter/Setter 메서드만 가지는 객체이다.

    * DTO를 사용하는 이유.

      * 어떤 값을 요청할 때, 그 요청한 값을 DTO에 담지 않고 일일이 하나씩 응답해준다면, 네트워크에 엄청나게 많은 트래픽이 발생하기 떄문에 트래픽을 줄이기 위함.

      * @Vaild 어노테이션과 BindingResult 클래스 그리고 DTO 클래스 내에 있는 @NotEmpty, @NotNull, @Email 어노테이션을 통해서 회원가입을 위한 데이터에 유효성 검증을 위해서도 사용하였다.

  * 계정 생성 시에 DB를 조회해서 아이디와 이메일이 중복인지 아닌지 중복 검사 체크.

    * 이상이 없다면, UserRepository에 저장.

  * 계정 생성 성공.

    * 비밀번호가 암호화되서 저장된것을 [확인](./image/24.png)

  * 생성된 계정을 통해서 스프링 시큐리티가 적용된 로그인 로직 구성.

    * 기존의 UserService 클래스에 UserDetailsService 클래스를 상속.

      * loadUserByUsername(String username) 메소드를 오버라이딩.

      * 로그인을 할 때 입력한 username 값을 통해서 DB에서 현재 유저를 찾아온 후, 유저 클래스 생성하여 매핑.

      * 매핑된 유저에 ROLE 필드에 관한 값을 세팅 후, 스프링 시큐리티에서 제공하는 유저 객체에 파라미터로 유저의 아이디, 비밀번호, Roles의 리스트까지 넣고 리턴.

    * SecurityConfig 클래스에서 configureGloba() 메소드에 패스워드 인코딩 처리를 해줌.

    * SecurityConfig 클래스에서 configure() 메소드를 통해서 로그인의 성공 여부에 따른 URL 매핑을 통해 로그인 과정 마무리.

    * 성공적인 로그인을 할 경우, `/list`로 이동되어서 ToDoList의 View를 띄어줌.

    * ToDoList 쪽에 로그아웃 버튼 생성.

      * 로그아웃 버튼을 통해서 로그인 화면으로 이동.

    * 404 에러 페이지와 500 에러 페이지에 따른 뷰 이동.

      * ErroPagerController 클래스 생성 후, 에러 예외 처리 후 에러 뷰 매핑.

    * 스프링 시큐리티를 사용하여 로그인을 했을 때, 현재 사용자 정보를 세션에서 불러오기.

      * 로그인이 성공 한 이후 ToDoList View가 화면 출력 될 떄와 현재 로그인한 유저로 글을 등록 할 때, 현재 유저 정보를 세션에서 가져와야 한다.

      * ToDoListController 클래스에서 /todolist url을 GetMapping과 PostMapping을 해주는 메소드에서 파라미터로 @AuthenticationPrincipal 어노테이션과 함께 스프링 시큐리티에서 제공하는 유저 객체를 추가한다.

      * 그리고 그 유저 객체의 `.getUsername()` 메소드를 통해서 현재 로그인한 유저의 정보를 세션에서 불러올 수 있다.

        ```java
        @Controller
        @RequestMapping("/todolist")
        public class ToDoListController {

          ....
          ....

          @GetMapping
          public String list(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
              User user = userService.findUserId(currentUser.getUsername());
              model.addAttribute("todoList", toDoListService.findToDoList(user));
              return "todolist/list";
          }

          @PostMapping
          public ResponseEntity<?> postToDoList(@RequestBody @Valid ToDoList toDoList, BindingResult result,
                                          @AuthenticationPrincipal org.springframework.security.core.userdetails.User currentUser) {
              if(result.hasErrors()) {
                  userService.validation(result);
              }

              User user = userService.findUserId(currentUser.getUsername());
              toDoListService.PostToDoList(toDoList, user);
              return new ResponseEntity<>("{}", HttpStatus.CREATED);
          }

          ....
          ....

        }
        ```
  </p>
  </details>  
