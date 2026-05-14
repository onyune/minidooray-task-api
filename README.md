# 🚀 Mini Dooray 프로젝트

Spring Boot와 JPA를 기반으로 한 협업 도구(미니 두레이) 팀 프로젝트입니다. 본 프로젝트는 회원 관리, 프로젝트 관리, 업무(Task) 및 댓글 기능을 제공하며 마이크로서비스 아키텍처(MSA) 구조를 본떠 3개의 서버로 분리하여 구현되었습니다.

---

## 🛠 Tech Stack

### Backend
- **Framework:** Spring Boot
- **Data Access:** Spring Data JPA
- **Session Management:** Redis (Gateway 인증 및 세션 관리)

### Frontend
- **Template Engine:** Thymeleaf

### Testing & Tools
- **Test:** JUnit 5 (`@DataJpaTest`, `@WebMvcTest`, `@SpringBootTest`)
- **Documentation:** REST-API 실행 요청 및 결과용 `*.http` 파일
- **Database:** H2 / MySQL (설정에 따라 변경 가능)

---

## 🏗 Architecture

시스템은 역할에 따라 독립된 세 개의 서버로 운영됩니다.

1.  **Gateway Server (Front-end & Auth)**
    - 클라이언트의 모든 요청을 수신하는 창구입니다.
    - Thymeleaf를 통해 화면을 렌더링하고, Redis를 이용해 사용자 세션을 관리합니다.
    - 인증된 요청에 한해 `RestTemplate`을 통해 내부 API 서버(Account, Task)와 통신합니다.

2.  **Account-API Server**
    - 회원의 가입, 상태 관리(가입, 탈퇴, 휴면) 및 인증 정보를 담당하는 REST API 서버입니다.

3.  **Task-API Server**
    - 프로젝트 생성, 업무(Task) 등록, 태그, 마일스톤, 댓글 등 핵심 비즈니스 로직을 담당하는 REST API 서버입니다.

---

## ✨ Key Features

### 1. 회원 관리 (Account)
- **회원가입:** 아이디, 이메일, 비밀번호를 입력하여 가입합니다.
- **로그인/로그아웃:** 회원 인증 및 세션 유지를 처리합니다.
- **상태 관리:** 가입, 휴면, 탈퇴 상태에 따른 접근 권한을 관리합니다.

### 2. 프로젝트 관리 (Project)
- **생성:** 프로젝트를 생성한 사용자는 자동으로 '관리자' 권한을 갖습니다.
- **멤버 초대:** 관리자는 기존 회원을 프로젝트 멤버로 등록할 수 있습니다.
- **상태 제어:** 프로젝트의 상태(활성, 휴면, 종료)를 관리합니다.
- **목록 필터링:** 사용자는 본인이 멤버로 참여 중인 프로젝트만 조회할 수 있습니다.

### 3. 업무 및 협업 (Task & Collaboration)
- **업무(Task) CRUD:** 프로젝트 멤버는 업무를 생성, 수정, 삭제, 조회할 수 있습니다.
- **태그(Tag):** 업무의 속성을 분류하기 위해 여러 개의 태그를 부착할 수 있습니다.
- **마일스톤(Milestone):** 프로젝트의 이정표 역할을 하며, 업무당 1개를 지정할 수 있습니다.
- **댓글(Comment):** 업무 내에서 멤버 간 의견을 교환할 수 있으며, 작성자 본인만 수정/삭제가 가능합니다.

---

## 📂 Deliverables

- **ERD:** 데이터베이스 관계 설계도
- **DDL:** 테이블 생성을 위한 SQL 스크립트 파일
- **Source Code:** 실행 가능한 Java 소스 코드
- **API Test:** 각 서버의 엔드포인트 테스트를 위한 `*.http` 파일 (REST Client)

---

## 🚀 Getting Started

1. **Redis 실행:** Gateway 서버의 세션 관리를 위해 Redis가 실행 중이어야 합니다.
2. **Account-API 실행:** 회원 관리 서버를 먼저 구동합니다.
3. **Task-API 실행:** 프로젝트 및 업무 관리 서버를 구동합니다.
4. **Gateway 실행:** 프론트엔드 서버를 구동한 후 `http://localhost:8080`을 통해 접속합니다.