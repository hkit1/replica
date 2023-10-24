# 과제

과제에 온 것을 환영합니다.

## 카테고리 기능

카테고리에는 알림, 쪽지, 피드, 설정 기능이 있다.

### 쪽지

개인간 채팅 기능.

### 피드

피드에는 여러 사용자들이 작성한 글을 확인할 수 있으며, 댓글 및 추천 스크랩이 가능하다.<br>

### 알림

이 기능에는 다른 사용자가 본인의 @(계정명) 으로 글이 작성 되었거나 추천을 받았을 경우, 해당 카테고리에 띄운다.<br>

### 설정

자신이 작성한 글 전체 공개/비공개, 이름 변경 등이 있어야 한다.<br>

### 검색

해당 단어가 포함된 모든 게시글을 검색해야 하며, 여유가 있을 경우 날짜, 작성자별로 검색할 수 있는 기능을 제작한다.

## UI

왼쪽에 카테고리를 배치 (검색을 최상단에)<br>
오른쪽에 내용<br>
어느 위치에 있어도 바로 메인 페이지로 갈 수 있는 버튼 및 로고가 있어야 한다.

## 게시글

게시글은 markdown 을 사용하며, 코드 블럭 앞에 언어를 적으면 해당 언어에 맞게 하이라이트 되어야 한다.<br>

모든 게시글은 기본적으로 미등록 상태이며, 글을 작성할 때 글 쓰기 버튼 옆에 있는 설정을 사용하여 공개 범위를 설정할 수 있다.<br>
공개 범위는 "공개", "미등록", "비공개" 로 설정한다.

공개의 경우 피드에서 확인할 수 있으며, 미등록은 해당 계정의 프로필에 직접 접근해야 확인할 수 있고, 비공개는 본인만 확인할 수 있게 된다.

## 기술 스택

front: bootstrap, 웹(html/css), rest api 또는 websocket<br>
back: spring, postgresql

## 코드 공유

Github
