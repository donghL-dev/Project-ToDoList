Prjoect - To Do List
===
* Spring Boot를 이용하여 Web에서 동작 가능한 To Do List를 구현하기 위한 프로젝트입니다.

* 이 저장소는 To Do List Web Application을 개발하면서 소스코드 관리 및 버전관리를 위한 저장소입니다.

* 목적
  
  * Spring Boot & JPA 학습 및 Web Application(To Do List) 개발.

  * Spring Security 학습 및 로그인, 회원가입 구현.

  * JUnit 프레임워크를 이용하여 프로젝트 내에서 테스트 해야할 케이스들의 테스트 코드 작성 및 학습.

## 개발환경

|도구|버전|
|:---:|:---:|
| Framework |Spring Boot 2.1.3 |
| OS |Windows 10|
|IDE |IntelliJ IDEA Ultimate |
|JDK |JDK 1.8|
|DataBase |MySQL Server 5.7|
|Build Tool |Gradle 5.2.1|

## 실행 방법
<details><summary>세부정보</summary>

* 준비사항.
    
    * Gradle or IntelliJ IDEA

    * JDK (>= 1.8)

    * Spring Boot (>= 2.x)

* 저장소를 `clone`

    ```bash
    $ git clone https://github.com/donghL-dev/Project-ToDoList.git
    ```
* 프로젝트 내 Project-Matching\src\main\java\com\matching\config 경로에 `HttpConfig.java` 삭제 또는 내용 주석처리.

* DB는 MySQL을 쓴다고 가정.

    * 다른 DB를 사용한다면, 그 DB에 맞게 설정을 해야함.

* 프로젝트 내 src\src\main\resources 경로에 `application.yml` 생성.

    * 밑의 양식대로 내용을 채운 뒤, `application.yml`에 삽입.
    <br>

    ```yml
    spring:
        datasource:
            url: jdbc:mysql://localhost/본인_DB
            username: 본인_DB_User
            password: 본인_DB_User_Password
            driver-class-name: com.mysql.jdbc.Driver
        jpa:
            hibernate:
            ddl-auto: create
    ```

* IntelliJ IDEA(>= 2018.3)에서 해당 프로젝트를 `Open`

    * 또는 터미널을 열어서 프로젝트 경로에 진입해서 다음 명령어를 실행.

    * Windows 10

        ```bash
        $ gradlew bootRun
        ```

    * Ubuntu 18.04

        ```bash
        $ ./gradlew bootRun
        ```

</details>

## 주요기능 

 * [확인](https://github.com/dongh9508/Project-ToDoList/tree/master/image/MainFunction)

## 개발과정
<details><summary>세부정보</summary>

### project-day-1 

  * Spring-Boot-Web-TDL 프로젝트 생성.

  * ToDoList 클래스 설계 및 생성.

    * Idx(키) -> Integer

    * Description(내용) -> String

    * Status(완료 여부) -> Boolean

    * CreatedDate(생성 시간) -> LocalDateTime

    * CompletedDate(완료 시간) -> LocalDateTime


### project-day-2

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


### project-day-3

  * View 수정 및 [업데이트](./image/4.png)


### project-day-4

  * View 수정 및 업데이트

    * css 설정 값 추가 및 수정 
    
    * Header와 Footer 생성.


### project-day-5

  * to do list 목록의 CRUD를 위한 ToDoListRestController 클래스 생성.

    * to do list 목록의 생성을 위한 값을 View에서 Input을 통해 받아옴.

    * 받아온 Input값을 postToDoList 메소드를 통해서 및 PostMapping으로 url 매핑 처리후 ToDoListService에서 로직 처리.

    * ToDoListService에서 로직을 통해 DB에 값을 저장 한 뒤 View가 Redirect 된 후 저장된 목록 출력.


### project-day-6

  * to do list 목록의 삭제 기능과 상태와 내용 업데이트 기능 구현

    * 삭제를 하기 위해서 @PathVariable를 통해서 idx 값을 받아온 뒤 DeleteMapping으로 매핑시킨 뒤, deleteToDoList 메소드를 생성해서 로직 처리.

    * 상태와 내용 업데이트를 위해서  @PathVariable를 통해서 idx 값을 받아온 뒤 PutMapping으로 매핑시킨 뒤, putDescription, putStatus 메소드를 생성해서 로직 처리.

  * 기존의 View에서 [업데이트](./image/5.png)


### project-day-7

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


### project-day-8

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


### project-day-9

  * 로그인을 하지 않고 To Do List url로 이동할 경우, 로그인 화면으로 redirect 되도록 설정.

  * To Do List에 현재 로그인을 한 유저가 등록한 To Do 항목과 Complete 항목이 보이도록 설정.

  * To Do가 등록될 때, DB의 To Do List의 테이블에도 현재 로그인 한 유저의 Idx가 [저장](./image/tododb.png)됨.


### project-day-10

  * User 모델 객체와 ToDoList 모델 객체간 관계를 단방향 관계에서 양방향 관계로 재설정.

    * `@OneToMany` 어노테이션과 `@ManyToOne` 어노테이션을 사용하여 관계성 재설정.

    * Foreign Key를 가지고 있고 OwnerShip을 가지고 있는 모델 객체에 `@ManyToOne` 어노테이션을 사용.

    * OwnerShip을 가지고 있지 않은 모델 객체에 `@OneToMany` 어노테이션을 사용.

    * `@OneToMany`를 사용한 모델 객체에 `@OneToMany` 어노테이션의 속성 중 하나인 mappedby로 OwnerShip을 가지고 있는 객체의 변수명을 기입한다.

    * OwnerShip을 가지고 있지 않은 모델 객체에는 OwnerShip을 가지고 있는 모델 객체로 된 필드를 Collection 자료형으로 선언한다.

  * ToDoList 모델에 `@ManyToOne` 어노테이션을 사용하고, User 모델에 `@OneToMany` 어노테이션을 사용해서 N : 1 양방향 관계로 설정.

  * 테이블 및 코드 [확인](https://github.com/dongh9508/Project-ToDoList/tree/master/image/RelationShip)


### project-day-11

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


### prjoect-day-12

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


### prjoect-day-13

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


### prjoect-day-14

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


### prjoect-day-15

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

      * ToDoListController 클래스에서 /todolist URL에 대해서 GetMapping과 PostMapping을 진행하는 메소드에서 파라미터로 @AuthenticationPrincipal 어노테이션과 함께 스프링 시큐리티에서 제공하는 유저 객체를 추가한다.

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
  

### prjoect-day-16

  * 유효성 검증을 위한 코드 일부 수정.

    * UserDTO 클래스에서 컬럼들에 선언해줬던  `@NotEmpty` 어노테이션에서 `@NotBlank` 어노테이션으로 변경.

      * `@NotEmpty` 어노테이션의 경우, null 값과 empty 값은 검증에서 허용하지 않지만, white space 값은 허용하기 떄문에, `@NotBlank` 어노테이션으로 변경.<br><br>
    
      |     | `@NotNull` | `@NotEmpty` |`@NotBlank` |
      :------: | :------------: | :----------: | :-----: |
      | null  | 허용하지 않음 |허용하지 않음 | 허용하지 않음 |
      |  "" | 허용 | 허용하지 않음 | 허용하지 않음 |
      | " "(space)  | 허용 | 허용 | 허용하지 않음 |


  * User 클래스 컬럼 일부 수정.

    * 컬럼으로 들어가는 필드들에 적용되어있는 `@Column` 어노테이션의 속성값을 `@Column(nullable = false)` 으로 변경.

    * User 도메인으로 인해 DB 테이블이 생성될 때, null 이 되어선 안되는 컬럼들에 대해서 Not Null 제약을 걸어주기 위해서 nullable 속성값을 `@Column` 어노테이션에 추가함. 


### prjoect-day-17

  * 유효성 검증을 위한 코드 일부 추가 및 유효성 검증 부분 마무리.

    * 기존의 DTO 객체단에서 진행하던 검증에 더 해서 프론트 단에서 추가 검증을 진행하기 위해서 프론트 코드 추가.

    * 회원가입 할 때 아이디와 비밀번호 길이에 대한 검증 및 이메일 형식 검증 부분 추가.

    * 로그인을 진행할 때, 아이디 길이와 비밀번호 길이를 프론트 단에서도 검증하는 코드 추가.

    * 로그인 실패시와 로그아웃시에도 그 정보를 출력하는 html 태그 추가.

    * 회원가입시에 Register를 누르기 전에도, 아이디가 중복인지 이메일이 중복인지 뷰를 통해서 나타날 수 있도록 코드 수정.


### prjoect-day-18

  * 부분적 테스트 코드 작성 시작.

    * LoginControllerTests 클래스 생성.

      * Ajax 혹은 client의 Request를 테스트 하기 위해서 WebApplicationContext 클래스와 MockMvc 클래스를 사용.

      * root url 접근시의 get 매핑 테스트 코드 작성.

      * 로그인 페이지 접근시의 get 메핑 테스트 코드 작성.

  * 이후에도 여러 테스트 코드를 추가적으로 작성해서 추가할 예정.


### prjoect-day-19

  * 소스코드 통합.

  * 계층형 ToDoList 구현하기.

    * 계층형 ToDoList를 위한 Coment 도메인 생성.

      * `idx(키)` -> `Long`

      * `content(내용)` -> `String`

      * `createdDate(생성시간)` -> `LocalDateTime`

      * `modifiedDate(수정시간)` -> `LocalDateTime`

    * Comment 도메인을 생성함으로써 CommentRepository, CommentController, CommentService 클래스 생성.

  * Comment 등록을 위한 View [생성](./image/25.png)

  * View에서 데이터 값을 전송하기 위한, CommentDTO 클래스 생성.

  * 댓글 등록 및 데이터 베이스 값 저장.

    * Comment 도메인과 ToDoList 도메인의 관계성 매핑.

      * Comment 도메인에서 `@ManyToOne` 관계 매핑.

      * ToDoList 도메인에서 `@OneToMany` 관계 매핑.

    * CommentController 클래스에서 댓글 등록을 위한 Post 요청 메소드 생성.

      * Post 요청 메소드 내에서 등록을 위한 비지니스 로직을 처리하기 위해 서비스 호출.

      * 댓글 등록할 때, 댓글의 데이터에 대한 유효성 검증을 위한, 서비스 호출. (`validation()`)

      * 검증이 끝났다면, `@Builder` 어노테이이션을 이용한 빌더 패턴을 이용해서 Comment 객체 생성 후, ToDoList의 add 메소드 실행.

      * 마지막으로 CommentRepository 에 필요한 로직을 처리한 Comment 객체 저장.

      * 댓글 등록 View에서 AJAX 호출을 통해서 Post 메소드 호출을 한 뒤, DB에 Comment 값 [저장완료](./image/26.png)


### prjoect-day-20

  * 사용하지 않은 css, js 파일 모두 삭제.

  * 중복되는 코드 부분들은 하나로 통일.

  * 깔끔한 뷰를 위해서 html 및 css 그리고 js 코드 부분 수정.

  * 댓글을 입력하면 입력한 View가 DB 값에 저장 이후, To Do List에 출력되도록 설정.

  * 댓글 수정 및 삭제 구현.

    * 댓글 수정 및 삭제를 위한 비지니스 로직 추가 및 put 매핑과 delete 매핑 추가.

    * 댓글 수정 및 삭제를 하기 위해 View에서 AJAX 호출을 통해서 put 또는 delete 메소드 호출을 한 뒤, DB에 Comment 수정 또는 삭제 적용.

  * 계층형 ToDo로 작성된 댓글에 대해서도 완료 여부에 대한 설정 가능.

    * Comment 도메인에 Status 컬럼 추가 및 체크 박스 여부에 따라 Status 값이 False 또는 True로 변경.


### prjoect-day-21

  * 계층형 ToDoList 완료 여부 설정 및 컬럼 추가.

  * 계층형 ToDoList 삽입, 삭제, 수정, 완료시에 페이지가 Reload가 되어서 접히는 이슈 해결.

    * 데이터를 CommentDTO 객체를 이용해서 서버에 보낸 뒤, 다시 CommentDTO를 반환받아서 그 객체를 토대로 자바스크립트 객체 생성.

    * 자바스크립트로 생성한 Comment 객체를 이용해서 페이지를 리로드 하지 않고, 삽입, 수정, 삭제 한 로직에 따른, 뷰를 반영. 


### prjoect-day-22

  * ToDoList 수정 진행시에, 수정 버튼 디자인 변경 및 다른 삭제, 코맨트, 체크박스 등의 버튼들을 비활성화 처리.

  * 계층형 ToDoList 수정시에도, 수정 버튼 디자인 변경 및 다른 삭제 및 체크박스 버튼 들을 비활성화 처리.

  * 일부 HTML의 CSS 속성 및 View 변경.


### prjoect-day-23

  * 개선사항 및 모든 문제들은 [issue](https://github.com/dongh9508/Project-ToDoList/issues)로 등록해서 관리.

  * 등록된 이슈들을 토대로 이슈들을 처리하기 위한 Branch 생성 후, 문제가 없을 경우, Master Branch로 Merge 하는 방식으로 프로젝트를 진행할 예정임.

  * UserDTO 클래스의 유효성 검증 어노테이션인 `@Pattern` 어노테이션 추가.

    * 테스트 코드를 원할하게 동작하기 위해서, 기존의 js가 처리해주던 프론트에서의 검증 뿐만 아니라, 백앤드에서도 아이디 및 비밀번호를 검증해주기 위해 추가. 

  * [#1](https://github.com/dongh9508/Project-ToDoList/issues/1) 이슈 처리 및 개선사항 Master Branch로 합병.

    *  [#1](https://github.com/dongh9508/Project-ToDoList/issues/1) 이슈인 회원가입 테스트 코드 작성 완료.

### prjoect-day-24

  * [#3](https://github.com/dongh9508/Project-ToDoList/issues/3) 이슈 처리 및 개선사항 Master Branch로 합병.

    * [#3](https://github.com/dongh9508/Project-ToDoList/issues/3) 이슈인 로그인 테스트 코드 작성 완료.

    * 로그인 테스트 코드 작성 중에 MockMvc를 이용한 쿠키 상태 값을 불러오는 것이 안되어서 쿠키 값 갱신 테스트는 완료하지 못하였음.

### prjoect-day-25

  * [#5](https://github.com/dongh9508/Project-ToDoList/issues/5) 이슈 처리 및 개선사항 Master Branch로 합병.

    * [#5](https://github.com/dongh9508/Project-ToDoList/issues/5) 이슈인 ToDoList 테스트 코드 작성 완료.

      * ToDoList Get 요청

        * `/`, `/todolist` 요청.

      * ToDo 등록

        * ToDo 문자열 길이 0

          * ToDo의 최소 길이 미달로 등록 실패.

        * ToDo 문자열 길이 256

          * ToDO의 최대 길이 초과로 등록 실패.

        * ToDo 문자열 길이 1 ~ 255 

          * ToDo의 유효한 문자열 길이 범위이므로 등록 성공.

        * 정상 등록된 ToDoList 객체 비교.

          * 등록된 ToDoList, ToDoList.CreatedDate - `NotNull`

          * ToDoList.CompletedDate - `Null`

          * 현재 로그인한 User.Idx와 ToDoList를 등록한 User의 Idx 일치.

      * ToDo 완료.

        * ToDo 생성.

          * 생성 직후, ToDo의 Status는 `false`, ToDo의 CompletedDate도 `Null`

        * 생성된 ToDo 완료 처리, `put("/todolist/status/{idx}")` 요청.

          * ToDo의 Status는 `true`, ToDo의 CompletedDate는 `LocalDateTime.now()`

      * ToDo 삭제.

        * ToDo 생성.

          * 생성된 ToDo 객체는 `NotNull`

        * ToDo 삭제 요청, `delete("/todolist/{idx}")` 요청.

          * ToDo 삭제 확인(`Null`)

      * ToDo 수정

        * ToDo 생성

          * ToDo 등록 내용 확인.

        * ToDo 수정 요청, `put("/todolist/{idx}")` 요청.

          * 변경된 description 저장.

          * DB에서 변경된 description 확인. 

### prjoect-day-26

  * [#8](https://github.com/dongh9508/Project-ToDoList/issues/8) 이슈 처리 및 개선사항 Master Branch로 합병.

    * [#8](https://github.com/dongh9508/Project-ToDoList/issues/8) 이슈인 로그인 테스트 코드 작성 완료.

    * Comment 등록.

      * 등록 요청 성공 및 DB 내 등록 여부 확인 .

      * 내용 등록 시 길이(빈 문자열, 최대 글자 초과) 검사.

      * ToDo 와의 관계성 확인(ToDoListIdx에 대한 List의 갯수).

    * Comment 완료.

      * 완료 요청 성공 및 DB 내 상태 변경 및 완료 날짜 확인.

    *  Comment 수정

        * 내용 등록 길이(빈 문자열, 최대 글자 초과) 검사.

        * 수정 요청 성공 및 DB 안의 수정 내용 및 수정 날짜 확인.

    * Comment 삭제

      * 삭제 요청 성공 및 DB 내 삭제 여부 확인.

### prjoect-day-27

  * [#10](https://github.com/dongh9508/Project-ToDoList/issues/10) 이슈 처리 및 개선사항 Master Branch로 합병.

    * [#10](https://github.com/dongh9508/Project-ToDoList/issues/10) 이슈인 아이디, 비밀번호 찾기 기능 추가.

    * 로그인 페이지 뷰 일부 변경

    * `login.js` 생성 후, 로그인 페이지에서 처리되던 모든 자바스크립트 로직을 `login.js` 파일로 옮김.

    * 아이디 찾기.

      * 계정 생성 시 등록했던 이메일을 통해서 아이디 찾기.

        * 이메일이 존재하면 아이디를 이메일로 전송.

        * 이메일이 존재하지 않는다면 아이디 찾기 실패.

    * 비밀번호 찾기.

      * 계정 생성 시 등록했던 아이디와 이메일을 통해서 비밀번호 찾기.

        * 아이디와 이메일이 존재하고 일치하면, 이메일에 인증번호를 전송 후, 일치하면, 비밀번호를 새롭게 설정.

        * 아이디와 이메일이 둘 중 하나라도 존재하지 않거나, 아이디와 이메일이 일치 하지 않을시 비밀번호 찾기 실패.

### prjoect-day-28

  * [#12](https://github.com/dongh9508/Project-ToDoList/issues/12) 이슈 처리 및 개선사항 Master Branch로 합병.

    * [#12](https://github.com/dongh9508/Project-ToDoList/issues/12) 이슈인 비밀번호 찾는 로직 개선 및 수정.

  * 비밀번호 찾는 로직 변경으로 인한 코드 일부 수정.

    * 인증번호를 인증하기 위해 Map 자료구조를 사용하였으나, 이 부분보다 더 나은 방법으로 해결하기 위해 로직 변경.

    * 비밀번호 데이터를 올바르게 전송하기 위한, FindPasswordDTO 객체를 생성.

    * LoginService 클래스를 빈으로 등록할 때, 스코프를 싱글톤으로 주지 않고, 세션으로 주어서 접속한 세션마다 빈이 생성되도록 로직 변경.

      * 이렇게 변경 후, 접속한 세션에 따라서 LoginService 빈이 새롭게 주입되고, 각 세션은 각기 다른 하나의 LoginService 빈들을 주입 받는다. 즉, 접속한 유저마다 다른 LoginService 빈들을 주입받고, 그 LoginService 빈들을 이용해서 비밀번호 및 아이디를 찾는 로직을 수행한다. 

      * 그렇기 떄문에, 다른 유저들과 LoginService 빈을 공용으로 사용할 일이 없기 때문에, 인증 로직이 각 유져별로(세션별로) 완전히 독립적으로 수행된다.

      * 또 세션이 종료되면(브라우저가 종료되는 등) 해당 세션의 LoginService 빈도 소멸하기 떄문에, 그 안에서 처리되던 인증 번호 및 인증 로직도 전부 소멸한다.

        * 인증번호를 받고, 브라우저를 종료 후, 다시 접속해서 이전에 받은 인증번호를 이용하려 하면 인증이 되지 않는다.

### 이 이후부터는 [Issues](https://github.com/dongh9508/Project-ToDoList/issues)에서 업데이트 사항들을 확인할 수 있습니다.

</details>
