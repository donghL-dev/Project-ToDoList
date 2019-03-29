## project-day-10

  * User 모델 객체와 ToDoList 모델 객체간 관계를 단방향 관계에서 양방향 관계로 재설정.

    * `@OneToMany` 어노테이션과 `@ManyToOne` 어노테이션을 사용하여 관계성 재설정.

    * Foreign Key를 가지고 있고 OwnerShip을 가지고 있는 모델 객체에 `@ManyToOne` 어노테이션을 사용.

    * OwnerShip을 가지고 있지 않은 모델 객체에 `@OneToMany` 어노테이션을 사용.

    * `@OneToMany`를 사용한 모델 객체에 `@OneToMany` 어노테이션의 속성 중 하나인 mappedby로 OwnerShip을 가지고 있는 객체의 변수명을 기입한다.

    * OwnerShip을 가지고 있지 않은 모델 객체에는 OwnerShip을 가지고 있는 모델 객체로 된 필드를 Collection 자료형으로 선언한다.

  * ToDoList 모델에 `@ManyToOne` 어노테이션을 사용하고, User 모델에 `@OneToMany` 어노테이션을 사용해서 N : 1 양방향 관계로 설정.

  * 테이블 및 코드 [확인](https://github.com/dongh9508/Project-ToDoList/tree/master/image/RelationShip)
