ToDoList 모델과 User 모델의 관계성 설정
===

### ToDoList 모델에 `@ManyToOne` 어노테이션을 사용할 경우.

  * ToDoList 모델이 Forgin Key로 User를 포함한다.

    * ToDoList 모델의 DB의 컬럼들.

      |idx|completedDate|createdDate|description|status|user_idx|
      |:---:|:---:|:---:|:---:|:---:|:---:|

      ```java
      public class ToDoList {

        ...
        ...

        @ManyToOne
        private User user;

      }
       ```


  * User 모델의 ToDoList 객체를 `@OneToMany`로 매핑을 해주고, mappedby 속성으로 ToDoList 모델의 User 필드의 변수명을 준다.
        
    * User 모델의 DB의 컬럼들.

      |idx|email|name|password|
      |:---:|:---:|:---:|:---:|

      ```java
      public class User {

        ...
        ...

        @OneToMany(mappedby = "user")
        private List<ToDoList> todolists;

      }
      ```

  * ToDoList 모델이 Forgin Key로 User 모델을 소유할 경우.

    * 현재 User가 ToDoList를 등록할 때마다, ToDoList 테이블에 현재 User의 Idx만 저장되므로, ToDoList가 등록되도 유일한 User 정보가 User 테이블에 유지된다.

    * User 테이블

      |idx|email|name|pwd|
      |:---:|:---:|:---:|:---:|
      |1|user1@gmail.com|user1|1234|
      |2|user2@gmail.com|user2|12345|
      |3|user3@gmail.com|user3|12345|

    * ToDoList 테이블 

      |idx|completedDate|createdDate|description|status|user_idx|
      |:---:|:---:|:---:|:---:|:---:|:---:|
      |1|-|time|내용 1|true|1|
      |2||time|내용 2|true|2|
      |3|time|time|내용 3|true|3|
      |...|...|...|...|...|
      |15|time|time|내용 15|false|1|



### User 모델에 `@ManyToOne` 어노테이션을 사용할 경우.

  * ToDoList 모델의 User 객체를 `@OneToMany`로 매핑을 해주고, mappedby 속성으로 User 모델의 ToDoList 필드의 변수명을 준다.
        
    * ToDoList 모델의 DB의 컬럼들.

      |idx|completedDate|createdDate|description|status|
      |:---:|:---:|:---:|:---:|:---:|

      ```java
      public class ToDoList {

        ...
        ...

        @OneToMany(mappedBy = "todolist");
        private List<User> user;

      }
       ```

  * User 모델이 Forgin Key로 ToDoList를 포함한다.

    * User 모델의 DB의 컬럼들.

      |idx|email|name|password|to_do_list_idx|
      |:---:|:---:|:---:|:---:|:---:|

      ```java
      public class User {

        ...
        ...

        @ManyToOne
        private ToDoList toDoList;

      }
      ```
  
  * User 모델이 Forgin Key로 ToDoList 모델을 소유할 경우.

    * 현재 User의 ToDoList를 등록할 때마다, User 테이블에 to_do_list idx만 다른 값으로 테이블이 늘어나게 되면서, 중복값이 늘어난다.

    * User 테이블

      |idx|email|name|pwd|to_do_list_idx|
      |:---:|:---:|:---:|:---:|:---:|
      |1|user1@gmail.com|user1|1234|1|
      |2|user2@gmail.com|user2|12345|2|
      |1|user1@gmail.com|user1|1234|3|
      |...|...|...|...|...|
      |3|user3@gmail.com|user3|123456|15|

    * ToDoList 테이블 

      |idx|completedDate|createdDate|description|status|
      |:---:|:---:|:---:|:---:|:---:|
      |1|-|time|내용 1|true|
      |2||time|내용 2|true|
      |3|time|time|내용 3|true|
      |...|...|...|...|...|
      |15|time|time|내용 15|false|
