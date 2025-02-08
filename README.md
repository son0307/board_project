# 📋 Basic board Project

> 기본적인 CRUD 기능과 로그인 기능을 갖춘 게시판 프로젝트

![메인 화면](https://i.imgur.com/BGB2Vfs.png)
## 개요

### 1. 프로젝트 소개

웹 프로그래밍의 기본 소양이라 할 수 있는 CRUD 기능을 가진 게시판을 직접 만들며 다양한 프레임워크, 라이브러리 등에 대해 학습하고자 시작한 프로젝트입니다.

### 2. 프로젝트 기능

프로젝트의 주요 기능은 다음과 같습니다.

- **게시판** - CRUD 기능, 조회수 및 좋아요 수 카운트 기능, 검색 기능, 페이징 처리
- **사용자** - Spring Security를 이용한 회원가입 및 로그인 기능, OAuth2.0을 이용한 구글, 네이버 로그인 기능, 닉네임 수정 기능, 회원 가입 시 유효성 검증 및 중복 검사
- **댓글** - 댓글 등록 기능, 댓글 삭제 기능

### 3. 사용 기술

#### 3-1 백엔드

##### 프레임워크 및 라이브러리

- Java 17
- SpringBoot
- JPA(Spring Data JPA)
- Spring Security
- OAuth 2.0

##### DataBase

- MySQL

#### 3-2 프론트엔드

- Thymeleaf
- Bootstrap 4.6.2

### 4. 실행 화면

  <details>
    <summary>게시글</summary>   

1. 게시글 목록 (초기 페이지)
   ![image](https://i.imgur.com/BGB2Vfs.png)   
   등록된 게시글들을 보여준다. 페이징을 적용하여 한 페이지당 최대 10개씩 잘라 보여준다.


2. 게시글 검색
   ![image](https://i.imgur.com/xhT5mit.png)   
   입력된 키워드를 바탕으로 글 제목 기준으로 검색할 수 있다. 이 페이지에서도 페이징이 적용되어 한 페이지당 최대 10개씩 보여준다.


3. 게시글 상세정보
   ![image](https://i.imgur.com/e4irAbG.png)
   ![image](https://i.imgur.com/xDs2gy2.png)
   ![image](https://i.imgur.com/9wcQdGI.png)
   각 게시글 제목을 누르면 게시글 상세정보 페이지로 이동한다.<br>
   비로그인 상태에서 접근하면 게시글 내용과 좋아요 수, 댓글 확인만 가능하다.
   로그인 상태에서는 좋아요 버튼이 활성화 되고 댓글 입력 칸이 나타난다. 게시글 작성자인 경우에는 수정, 삭제 버튼이 추가로 나타난다.


4. 게시글 등록
   ![image](https://i.imgur.com/roh9ts7.png)   
   로그인한 상태에서 글을 작성하여 등록할 수 있다.


5. 게시글 수정
   ![image](https://i.imgur.com/QTkFYyw.png)   
   게시글 작성자의 경우, 작성한 게시글을 수정할 수 있다. 기존 게시글의 제목과 내용이 미리 입력된 채 게시글 등록 페이지가 나타난다.
   제목과 내용을 수정한 다음 게시글 등록 버튼을 클릭해 수정한 내용을 등록할 수 있다.


6. 게시글 삭제
   ![image](https://i.imgur.com/ewt8jnn.png)   
   게시글 작성자는 작성한 게시글을 삭제할 수 있다. 삭제 버튼을 클릭하면 경고창이 나타나고 확인 버튼을 클릭하여 삭제할 수 있다.
   삭제 후에는 초기 페이지로 redirect된다.

  </details>
  <br/>   

  <details>
    <summary>회원</summary>   

1. 회원가입   
   ![image](https://i.imgur.com/yjpDejN.png)   
   ![image](https://i.imgur.com/QJlkMd5.png)   
   ![image](https://i.imgur.com/OSJIaCN.png)   
   내비게이션 바의 '회원가입'을 클릭하면 회원가입 페이지로 이동한다. 각 입력 항목에 오류가 없으면 회원 정보를 저장하고 초기 페이지로 redirect된다.
   입력 항목에 오류가 있으면 오류가 있는 필드 아래에 어떤 오류가 생겼는지 나타난다.


2. 로그인  
   ![image](https://i.imgur.com/hshouwG.png)   
   ![image](https://i.imgur.com/HAeESSJ.png)   
   내비게이션 바의 '로그인'을 클릭하면 로그인 페이지로 이동한다. 회원가입 시 사용한 ID와 비밀번호를 이용하여 로그인을 수행한다.
   입력한 ID와 비밀번호가 일치하지 않는 경우 오류 메시지가 나타난다.


3. 소셜 로그인   
   ![image](https://i.imgur.com/4rCBpSq.png)   
   '로그인'옆의 '구글 로그인', '네이버 로그인'을 통해 소셜 계정으로 로그인을 할 수 있다.
   ![image](https://i.imgur.com/rofIggD.png)   
   소셜 계정으로 로그인 시 회원 가입을 하지 않은 상태면 회원 가입 페이지로 이동하게 된다.
   ![image](https://i.imgur.com/b7Sfmss.png)   
   소셜 계정 정보를 이용하여 회원 가입을 진행한다. 닉네임만 수정할 수 있으며 일반 회원 가입과 동일하게 유효성 검증 및 중복 검사를 수행한다.


4. 닉네임 수정   
   ![image](https://i.imgur.com/4d60dYB.png)   
   오른쪽 위의 닉네임을 클릭하면 회원 정보 페이지로 이동한다. 회원 정보 페이지에서는 닉네임을 수정할 수 있다.


5. 회원 탈퇴   
   ![image](https://i.imgur.com/20V3Sgt.png)
   회원 정보 페이지에서 회원 탈퇴를 진행할 수 있다. 탈퇴 버튼을 클릭하면 경고창이 뜨고 확인 버튼을 눌러 탈퇴할 수 있다.
   탈퇴 후에는 로그아웃된 상태로 초기 페이지로 redirect된다.

  </details>
  <br/>   

  <details>
    <summary>댓글 관련</summary>   

1. 댓글 조회   
   ![image](https://i.imgur.com/qdVTdKc.png)   
   사용자들이 작성한 댓글은 게시글 상세정보 페이지에서 확인할 수 있다.


2. 댓글 작성    
   ![image](https://i.imgur.com/9mjodlr.png)   
   로그인한 사용자는 댓글 입력 칸에 댓글을 작성하고 등록 버튼을 클릭하여 댓글을 등록할 수 있다.


3. 댓글 삭제   
   ![image](https://i.imgur.com/uEYwTIg.png)   
   댓글 작성자는 자신의 댓글을 삭제할 수 있다. 경고문이 뜨고 확인 버튼을 클릭하여 삭제 처리를 진행할 수 있다.

  </details>
  <br/>   

## 구조 및 설계

### 1. 패키지 구조

<details>

<summary>패키지 구조 보기</summary>   

```
├─main
│  ├─java
│  │  └─com
│  │      └─son
│  │          └─board
│  │              │  BoardApplication.java
│  │              │
│  │              ├─config
│  │              │      SecurityConfig.java
│  │              │
│  │              ├─controller
│  │              │      CommentController.java
│  │              │      PostController.java
│  │              │      PostLikeController.java
│  │              │      UserController.java
│  │              │
│  │              ├─domain
│  │              │      Comment.java
│  │              │      Post.java
│  │              │      PostLike.java
│  │              │      User.java
│  │              │
│  │              ├─dto
│  │              │      CommentRequestDto.java
│  │              │      PostLikeResponseDto.java
│  │              │      PostRequestDto.java
│  │              │      PostResponseDto.java
│  │              │      UserResponseDto.java
│  │              │      UserSignUpRequestDto.java
│  │              │      UserUpdateRequestDto.java
│  │              │
│  │              ├─oauth2
│  │              │  ├─controller
│  │              │  │      OAuth2Controller.java
│  │              │  │
│  │              │  ├─domain
│  │              │  │      CustomOAuth2User.java
│  │              │  │
│  │              │  ├─dto
│  │              │  │      OAuth2RegistrationDto.java
│  │              │  │      OAuth2SignUpRequestDto.java
│  │              │  │      OAuth2UserInfo.java
│  │              │  │
│  │              │  ├─service
│  │              │  │      CustomOAuth2SuccessHandler.java
│  │              │  │      CustomOAuth2UserService.java
│  │              │  │
│  │              │  ├─util
│  │              │  │      OAuth2UserInfoExtractor.java
│  │              │  │
│  │              │  └─validator
│  │              │          CheckOauth2NicknameValidator.java
│  │              │
│  │              ├─repository
│  │              │      CommentRepository.java
│  │              │      PostLikeRepository.java
│  │              │      PostRepository.java
│  │              │      UserRepository.java
│  │              │
│  │              ├─service
│  │              │      CommentService.java
│  │              │      PostLikeService.java
│  │              │      PostService.java
│  │              │      SecurityContextService.java
│  │              │      UserDetailsImpl.java
│  │              │      UserDetailsServiceImpl.java
│  │              │      UserService.java
│  │              │
│  │              └─validator
│  │                      AbstractValidator.java
│  │                      CheckNicknameValidator.java
│  │                      CheckUsernameValidator.java
│  │                      UpdateNicknameValidator.java
│  │                      UpdateUsernameValidator.java
│  │                      ValidationGroups.java
│  │                      ValidationSequences.java
│  │
│  └─resources
│      │  application-oauth.properties
│      │  application.properties
│      │
│      ├─static
│      │  ├─css
│      │  │      index.css
│      │  │      login.css
│      │  │      PostDetail.css
│      │  │      signup.css
│      │  │      UserDetail.css
│      │  │      write.css
│      │  │
│      │  └─script
│      │          index.js
│      │          login.js
│      │          PostDetail.js
│      │          UserDetail.js
│      │          write.js
│      │
│      └─templates
│          ├─infra
│          │      navbar.html
│          │
│          ├─oauth2
│          │      signup.html
│          │
│          ├─post
│          │      detail.html
│          │      index.html
│          │      write.html
│          │
│          └─user
│                  detail.html
│                  login.html
│                  signup.html
│
└─test
    └─java
        └─com
            └─son
                └─board
                    │  BoardApplicationTests.java
                    │
                    ├─dto
                    │      UserDtoTest.java
                    │
                    ├─Repository
                    │      PostRepositoryTest.java
                    │
                    └─service
                            CommentServiceTest.java
                            PostServiceTest.java
                            UserServiceTest.java

 ```

 </details>   
 <br/>    

### 2. DB 설계

![erd](https://i.imgur.com/gcf4ee9.png)   
![Post table](https://i.imgur.com/H442ycA.png)   
![User table](https://i.imgur.com/hgIgCGe.png)
![Comment table](https://i.imgur.com/fVZebIE.png)   
![PostLike table](https://i.imgur.com/BchXMD7.png)


### 3. API 설계

![게시글 API](https://i.imgur.com/85rrmLB.png)    
![회원 API](https://i.imgur.com/bT5Wi7H.png)   
![댓글 API](https://i.imgur.com/HNxJQgg.png)

## 마치며

### 후기

Spring에 대해 독학하고 난 후 기획부터 완성까지 전부 혼자서 진행한 첫 프로젝트입니다.   
원하는 만큼 기능을 추가하고 공부한 내용을 사용해보는 기회라 굉장히 재밌게 진행할 수 있었습니다.   
이전에 진행한 팀 프로젝트에서는 시간에 쫓겨 완성하는데에 급급해 효율성을 챙기지 못 했었는데 이번 프로젝트에서는 
어떤 것이 더 효율적인 설계인지에 대해 많은 고민을 쏟았고 덕분에 많은 공부가 되었습니다.   
책, 강의 등으로 읽고 이해에서 그치지 않고 실제로 활용해 보는 과정에서 더욱 깊은 이해를 할 수 있었으며   
'이 작업은 어떤 객체가 책임을 져야하는가', '코드가 너무 복잡해지지는 않는가' 등 이전 학기 때 배웠던 각종 이론들이   
어떻게 도움을 주는지도 느낄 수 있었습니다. 이전까지는 코드를 짜면서 이 객체가 무슨 일을 하는가에 대해 모호하게만   
정의하며 진행했지만 이번 프로젝트를 통해 명확한 책임을 부여하는 것이 더욱 명확한 코드를 만든다는   
것을 알게되었습니다.

이번 프로젝트는 단순한 CRUD 기능을 가진 게시판이었지만 저에게 있어 정말 많은 공부가 되었습니다.   
어떤 방식으로 공부해야 하는지, 어떤 생각을 가지고 코드를 짜야하는지 등 조금 더 나은 개발자가 된 것 같습니다.   
앞으로도 더욱 노력하여 다른 사람들에게 자신감을 가지고 보여줄 수 있는 어플리케이션을 만들겠습니다. 감사합니다.
