Prjoect - To Do List
===
* Spring Boot를 이용하여 Web에서 동작 가능한 To Do List를 구현하기 위한 프로젝트입니다.

* 이 저장소는 To Do List 프로젝트를 진행하면서 소스코드를 관리하고 버전관리를 위한 저장소입니다.


### 개발환경

|도구|버전|
|:---:|:---:|
| Framework |Spring Boot 2.1.3 |
| OS |Windows 10|
|IDE |IntelliJ IDEA Ultimate |
|JDK |JDK 1.8|
|DataBase |MySQL 8.0.3|
|Build Tool |Gradle 5.2.1|

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

    * css 옵션 추가 및 헤더와 푸터 생성.

### project-day-5

  * to do list 목록의 CRUD를 위한 ToDoListRestController 클래스 생성.

    * to do list 목록의 생성을 위한 값을 View에서 Input을 통해 받아옴.

    * 받아온 Input값을 postToDoList 메소드를 통해서 및 PostMapping으로 url 매핑 처리후 ToDoListService에서 로직 처리.

    * ToDoListService에서 로직을 통해 DB에 값 저장 하고 View가 Redirect 된 후 저장된 목록 출력.



 

   







