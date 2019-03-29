## project-day-11

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