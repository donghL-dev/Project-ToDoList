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

<details><summary>project-day-1 </summary>
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

<details><summary>project-day-2 </summary>
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

<details><summary>project-day-3 </summary>
<p>

  * View 수정 및 [업데이트](./image/4.png)
</p>
</details>

<details><summary>project-day-4 </summary>
<p>

  * View 수정 및 업데이트

    * css 설정 값 추가 및 수정 
    
    * Header와 Footer 생성.
</p>
</details>

<details><summary>project-day-5 </summary>
<p>

  * to do list 목록의 CRUD를 위한 ToDoListRestController 클래스 생성.

    * to do list 목록의 생성을 위한 값을 View에서 Input을 통해 받아옴.

    * 받아온 Input값을 postToDoList 메소드를 통해서 및 PostMapping으로 url 매핑 처리후 ToDoListService에서 로직 처리.

    * ToDoListService에서 로직을 통해 DB에 값을 저장 한 뒤 View가 Redirect 된 후 저장된 목록 출력.
</p>
</details>

<details><summary>project-day-6 </summary>
<p>

  * to do list 목록의 삭제 기능과 상태와 내용 업데이트 기능 구현

    * 삭제를 하기 위해서 @PathVariable를 통해서 idx 값을 받아온 뒤 DeleteMapping으로 매핑시킨 뒤, deleteToDoList 메소드를 생성해서 로직 처리.

    * 상태와 내용 업데이트를 위해서  @PathVariable를 통해서 idx 값을 받아온 뒤 PutMapping으로 매핑시킨 뒤, putDescription, putStatus 메소드를 생성해서 로직 처리.

  * 기존의 View에서 [업데이트](./image/5.png)
</p>
</details>

<details><summary>project-day-7 </summary>
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

<details><summary>project-day-8 </summary>
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

<details><summary>project-day-9 </summary>
<p>

  * 로그인을 하지 않고 To Do List url로 이동할 경우, 로그인 화면으로 redirect 되도록 설정.

  * To Do List에 현재 로그인을 한 유저가 등록한 To Do 항목과 Complete 항목이 보이도록 설정.

  * To Do가 등록될 때, DB의 To Do List의 테이블에도 현재 로그인 한 유저의 Idx가 [저장](./image/tododb.png)됨.
</p>
</details>

<details><summary>project-day-10 </summary>
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

<details><summary>project-day-11 </summary>
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

<details><summary>project-day-12 </summary>
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

<details><summary>project-day-13 </summary>
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

