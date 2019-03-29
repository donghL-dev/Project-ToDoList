## project-day-12

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