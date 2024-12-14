<h1 align="center"><img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Travel%20and%20places/Milky%20Way.png" alt="Milky Way" width="25" height="25" /> Team29_Android <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Travel%20and%20places/Milky%20Way.png" alt="Milky Way" width="25" height="25" /></h1>

![image](https://github.com/user-attachments/assets/b3d84499-a30d-4649-abfc-e4599f2594b2)

## 목차
- [🔹 NOTAI 소개](#-notai-소개)
- [👥 팀원](#-팀원)
- [📌 핵심 기능](#-핵심-기능)
- [🖇️ 링크 모음](#️-링크-모음)
- [📁 파일구조](#-파일구조)
- [💠 ERD](#-erd)
- [📄 API 모아보기](#-api-모아보기)
- [🛠️ 기술스택](#️-기술스택)
- [☁️ 각 트랙별 코드 설명](#️-각-트랙별-코드-설명)

<br>
<br>

# 🔹 NOTAI 소개

## 타겟층 및 제작 목적
**NOTE + AI**를 의미하는 **NOTAI**는 **대학생을 주 타깃**으로 하여 <br> 효율적이고 효과적인 **강의 학습** 및 **시험 대비**를 도와주는<br> **안드로이드 태블릿에 최적화**된 애플리케이션입니다.

<br>

## 제공 서비스

**1️. 동기화된 강의 자료 3-TYPE**
- 한눈에 보이는
  - 강의 녹음 → PDF 파일 — 사용자 필기


> <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Old%20Key.png" alt="Old Key" width="20" height="20" /> **흩어진 강의자료, 확인하기 불편하셨죠? 강의 흐름에 꼭 맞춰 정리되는 자료로 더 편리하게 학습하세요!**

**2️. AI 학습 보조 자료 3-STEP**
- 음성 필기
  - 강의 녹음 파일을 STT (Speech to Text) 변환

- AI 요약
  - 강의 자료 3가지를 통합한 요약본 제공

- AI 예상 문제
  - 강의 자료 3가지를 통합한 예상 문제 생성

**3️. 해결하려는 문제점 및 기대효과**

| 문제점 BIG 3 | ➡️ | 기대효과 |
   |--------------|----|----------|
| 1. 필기하느라 수업을 놓친 흑우가 있다?! 🐂 | ➡️ | 1. **필기는 AI에게 맡겨요!**<br>강의 집중 UP 📈 |
| 2. 너무 긴 강의내용... 다 못 보게 되는데... 😅 | ➡️ | 2. **AI 요약으로 핵심만!**<br>학습 효율 UP 🔝 |
| 3. 이거 시험에는 어떻게 나올지 도저히 모르겠어!! 💢 | ➡️ | 3. **AI가 만들어주는 문제로**<br>더 완벽한 시험대비 💯 |



<br>

# 👥 팀원

**Backend**

| <img src="https://github.com/hynseoj.png" width="80"> <br/> [hynseoj](https://github.com/hynseoj) | <img src="https://github.com/rladbrua0207.png" width="80"> <br/> [rladbrua0207](https://github.com/rladbrua0207) | <img src="https://github.com/mingjuu.png" width="80"> <br/> [mingjuu](https://github.com/mingjuu) | <img src="https://github.com/Shsin9797.png" width="80"> <br/> [Shsin9797](https://github.com/Shsin9797) | <img src="https://github.com/yunjunghun0116.png" width="80"> <br/> [yunjunghun0116](https://github.com/yunjunghun0116) |
|:-:|:-:|:-:|:-:|:-:|
| Backend | Backend | Backend | Backend | Backend |

**Frontend (Android)**

| <img src="https://github.com/aengzu.png" width="80"> <br/> [aengzu](https://github.com/aengzu) | <img src="https://github.com/Kjamm.png" width="80"> <br/> [Kjamm](https://github.com/Kjamm) |
|:-:|:-:|
| Android | Android |

<br>

# 📌 핵심 기능

| 기능 사진 | 기능 설명 |
|--------|---------------------|
| <img src="https://github.com/user-attachments/assets/d5a05573-fdfb-4c69-bd73-5ac7eb8bf5b1" width="300" alt="노트 앱 기능">  | <b>기본적인 노트 앱 기능</b><div style="margin: 5px 0;"><hr></div> <ul><li>PDF 파일 조회 및 텍스트 주석을 통한 기본 필기 기능</li><li>슬라이더를 통한 유연한 화면 구성 제공</li></ul> |
| <img src="https://github.com/user-attachments/assets/2f329d1f-b585-4fec-b6e5-21ae05828fa0" width="300" alt="노트 앱 기능">  | <b>폴더 및 파일 관리 기능</b><div style="margin: 5px 0;"><hr></div><ul><li>문서탭에서 파일 및 폴더 조회 및 관리(생성, 삭제, 이름 변경)</li><li>서버와 동기화를 통해 데이터 유지</li></ul> |
| <img src="https://github.com/user-attachments/assets/dc047197-578f-4206-83c4-deaf947b15b0" width="300" alt="녹음 변환 기능">  | <b>AI 를 통한 녹음 변환(STT) 기능</b><div style="margin: 5px 0;"><hr></div><ul><li>녹음 도중 페이지 이동 이벤트를 기록해 각 페이지마다 STT 조회 가능</li></ul> |
| <img src="https://github.com/user-attachments/assets/db6b163e-6cf6-41fc-b133-7f6bc7866201" width="300" alt="페이지 요약 기능">  | <b>AI를 통한 페이지 요약 및 문제 생성 기능</b><div style="margin: 5px 0;"><hr></div><ul><li>강의 자료 외에도 기록된 녹음을 바탕으로 AI 요약 및 문제 생성</li></ul>|

<br>

# 🖇️ 링크 모음
| 구분              |  | 링크                                                                                                                                                                                                                                                                                              |
|-----------------|----|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 앱 다운로드(원스토어) 링크 | ➡️ | [앱 다운로드 링크 <img src="https://raw.githubusercontent.com/Tarikul-Islam-Anik/Animated-Fluent-Emojis/master/Emojis/Objects/Mobile%20Phone%20with%20Arrow.png" alt="Mobile Phone with Arrow" width="25" height="25" />](https://onesto.re/0000779576)                                                                            |
| 소개 자료           | ➡️ | [소개 자료 링크](https://www.canva.com/design/DAGWX39vH6w/nzAtcH09BaQlGldnBlVIfw/view?utm_content=DAGWX39vH6w&utm_campaign=designshare&utm_medium=link&utm_source=editor)                                                                                                                             |
| ERD 설계서         | ➡️ | [ERD 설계서 링크](https://www.erdcloud.com/d/Tndj8pDGQq5sokaLs)                                                                                                                                                                                                                                      |
| Figma 기획 파일     | ➡️ | [Figma 기획 파일 링크](https://www.figma.com/design/Mg7XxixwZjEsm2HR0Z15lh/NOTAI_v2?node-id=1-3&t=uZTAsKdk558vKEAr-1)                                                                                                                                                                                 |
| 백엔드 서버 URL      | ➡️ | [http://121.183.242.176](http://121.183.242.176)                                                                                                                                                                                                                                                |
| API 문서(Swagger) | ➡️ | [http://121.183.242.176/swagger-ui/index.html#/annotation-controller/getAnnotations](http://121.183.242.176/swagger-ui/index.html#/annotation-controller/getAnnotations)                                                                                                                        |
<br>

# 📁 파일구조

### Android

**클린아키텍처 & MVVM & 멀티모듈**
- 클린 아키텍처를 통해 비즈니스 로직, 데이터 처리, ui 명확히 분리
- 멀티모듈 구조를 통해 공통 기능을 별도의 모듈로 분리하여 각 부분의 독립성을 높임
- MVVM을 통해 테스트와 유지보수가 용이해짐
- 전체 모듈을 빌드할 필요가 없어 빌드시간을 줄이는데 용이해짐
```
📦 notai
├───📂app
│   └───📂src
│       └───📂main
│           ├───📂java/com/iguana/notai
│           │   └───📄NotaiApplication.kt
│           └───📂res
│               ├───📂layout
│               └───📂values
│
├───📂build-logic (여러 모듈에서 공통적으로 사용되는 빌드 설정(플러그인 적용, 의존성 관리, 컴파일 옵션 등)을 중앙에서 관리)
│
├───📂core
│   ├───📂data
│   │       ├───📂di (의존성 주입과 관련된 모듈)
│   │       ├───📂local (db 관련 모듈)
│   │       │   ├───📂dao
│   │       │   ├───📂db
│   │       │   ├───📂entity
│   │       │   └───📂files
│   │       ├───📂mapper (데이터 모델 분리 관련 모듈)
│   │       ├───📂remote
│   │       │   |───📂api (API 서비스 인터페이스 정의)
│   │       │   └───📂model (DTO 정의)
│   │       ├───📂repository (domain/repository에서 원격 API, 로컬 db에 대해 제공한 추상화 계층을 구체화) 
│   │       └───📂utils (포매팅이나 확장 함수 관련)
│   │
│   ├───📂domain
│   │       ├───📂model (비즈니스 로직에 사용되는 데이터 모델)
│   │       ├───📂repository
│   │       ├───📂usecase (도메인 로직을 처리하는 유스케이스)
│   │       └───📂utils
│   │
│   ├───📂designsystem (앱 전반의 색상, 테마, 폰트 설정)
│   │
│   └───📂ui
│       ├───📄BaseActivity.kt (사이드탭을 포함하는 공통 액티비티)
│       ├───📄SideTabLayoutFragment.kt (사이드탭은 공통적으로 사용되므로 따로 정의하여 재사용)
│       └───📄StatusBarManager.kt
│
└───📂feature (각 기능들을 모듈로 분리)
    ├───📂ai
    ├───📂community
    ├───📂dashBoard
    ├───📂documents
    ├───📂favorites
    ├───📂login
    ├───📂notetaking
    ├───📂settings
    └───📂userInfo
```

### Backend

**CQRS 패턴 & Command 패턴**
- 데이터 변경과 조회 작업을 분리하여 최적화된 작업 수행 가능
- 추후 데이터 저장소를 분리하여 효율을 높일 수 있음
- presentation 계층과 application 계층의 요청을 분리하여 독립성 보장
- 비즈니스 로직의 확장 및 변경이 유연

```
📦notai
└── 📁src
    ├── 📁main
    │   ├── 📁java
    │   │   └── 📁notai
    │   │       ├── 📄BackendApplication.java
    │   │       ├── 📁annotation
    │   │       ├── 📁auth `사용자 로그인 관련 resolver 와 JWT 컴포넌트`
    │   │       ├── 📁client `외부로의 API 요청 처리`
    │   │       │   ├── 📄HttpInterfaceUtil.java
    │   │       │   ├── 📁ai `AI 서버 API`
    │   │       │   ├── 📁oauth `사용자 로그인 관련 OAuth API`
    │   │       │   └── 📁slack `백엔드 서버 Slak Webhook`
    │   │       ├── 📁comment
    │   │       ├── 📁common
    │   │       │   ├── 📁config `Swagger, QueryDSL 등의 애플리케이션 설정`
    │   │       │   ├── 📁converter `JSON 직렬화/역직렬화 처리`
    │   │       │   ├── 📁domain `RootEntity 등 공통 도메인 관련 폴더`
    │   │       │   ├── 📁exception `전역 예외 처리`
    │   │       │   │   ├── 📄ApplicationException.java
    │   │       │   │   ├── 📄ErrorMessages.java
    │   │       │   │   ├── 📄ExceptionControllerAdvice.java
    │   │       │   │   ├── 📄ExceptionResponse.java
    │   │       │   │   └── 📁type
    │   │       │   └── 📁utils
    │   │       ├── 📁document
    │   │       │   ├── 📁application `비즈니스 로직 계층`
    │   │       │   │   ├── 📄DocumentQueryService.java `C,U,D 전용 서비스 (CQRS 패턴)`
    │   │       │   │   ├── 📄DocumentService.java `Read 전용 서비스 (CQRS 패턴)`
    │   │       │   │   └── 📁result `application 계층의 응답 DTO`
    │   │       │   │       ├── 📄DocumentFindResult.java
    │   │       │   │       ├── 📄DocumentSaveResult.java
    │   │       │   │       └── 📄DocumentUpdateResult.java
    │   │       │   ├── 📁domain `도메인 모델 계층`
    │   │       │   │   ├── 📄Document.java
    │   │       │   │   └── 📄DocumentRepository.java `JPA 레포지토리 인터페이스`
    │   │       │   ├── 📁presentation `프레젠테이션 계층`
    │   │       │   │   ├── 📄DocumentController.java `API 엔드포인트`
    │   │       │   │   ├── 📁request `API 요청 DTO`
    │   │       │   │   │   ├── 📄DocumentSaveRequest.java
    │   │       │   │   │   └── 📄DocumentUpdateRequest.java
    │   │       │   │   └── 📁response `API 응답 DTO`
    │   │       │   │       ├── 📄DocumentFindResponse.java
    │   │       │   │       ├── 📄DocumentSaveResponse.java
    │   │       │   │       └── 📄DocumentUpdateResponse.java
    │   │       │   └── 📁query `조회 전용 쿼리 레포지토리 (CQRS 패턴)`
    │   │       │       ├── 📄DocumentQueryRepository.java
    │   │       │       └── 📄DocumentQueryRepositoryImpl.java
    │   │       ├── 📁folder
    │   │       ├── 📁llm
    │   │       ├── 📁member
    │   │       ├── 📁ocr
    │   │       ├── 📁pageRecording
    │   │       ├── 📁pdf
    │   │       ├── 📁post
    │   │       ├── 📁problem
    │   │       ├── 📁recording
    │   │       ├── 📁stt
    │   │       ├── 📁sttTask
    │   │       └── 📁summary
    │   └── 📁resources
    │       ├── 📄application-local.yml
    │       └── 📄application.yml
    └── 📁test
        └── 📁java
            └── 📁notai
                ├── 📄BackendApplicationTests.java
                ├── 📁annotation
                ├── 📁client
                ├── 📁folder
                ├── 📁llm
                ├── 📁ocr
                ├── 📁pageRecording
                ├── 📁recording
                └── 📁stt
```

<br>

# 💠 ERD
![image](https://github.com/user-attachments/assets/e0e18ad0-c6f1-4514-bb49-8e75860d4259)

<br>

# 📄 API 모아보기

**회원 API**

| 기능               | URL                                  | Method | Status | 설명 |
|------------------|--------------------------------------|--------|--------|------|
| 카카오 로그인      | `/api/members/oauth/login/{oauthProvider}` | POST   | 완료   |      |
| 토큰 리프레쉬      | `/api/members/token/refresh`        | POST   | 완료   |      |
| 본인 프로필 조회   | `/api/members/me`                   | GET    | 완료   |      |


**Document API**

| 기능                       | URL                                                     | Method | Status | 설명 |
|--------------------------|---------------------------------------------------------|--------|--------|------|
| 폴더에 Document 업로드     | `/api/folders/{folderId}/document`                      | POST   | 완료   |      |
| 폴더의 목록 조회           | `/api/folders/{folderId}`                               | GET    | 완료   |      |
| 여러 Document 조회         | `/api/folders/{folderId}/documents`                    | GET    | 완료   |      |
| Document 상세 조회         | `/api/folders/{folderId}/documents/{documentId}`       | GET    | 완료   |      |
| Document 수정             | `/api/folders/{folderId}/documents/{documentId}`       | PUT    | 완료   |      |
| Document 삭제             | `/api/folders/{folderId}/documents/{documentId}`       | DELETE | 완료   |      |
| 폴더 생성                  | `/api/folders`                                         | POST   | 완료   |      |
| 폴더 조회                  | `/api/folders?parentFolderId={parentFolderId}`         | GET    | 완료   |      |
| 폴더 삭제                  | `/api/folders/{folderId}`                              | DELETE | 완료   |      |
| 속해있는 폴더 변경(위치변경) | `/api/folders/{folderId}/move`                         | POST   | 완료   |      |

**Annotation API**

| 기능           | URL                                                   | Method | Status | 설명 |
|----------------|-------------------------------------------------------|--------|--------|------|
| 주석 추가       | `/api/documents/{documentId}/annotations`             | POST   | 완료   |      |
| 주석 목록 조회   | `/api/documents/{documentId}/annotations`             | GET    | 완료   |      |
| 주석 수정       | `/api/documents/{documentId}/annotations/{annotationId}` | PUT    | 완료   |      |
| 주석 삭제       | `/api/documents/{documentId}/annotations/{annotationId}` | DELETE | 완료   |      |



**Record API**

| 기능 | URL | Method | Status | 설명 |
|------|-----|--------|--------|------|
| 페이지 넘김 이벤트 | `/api/documents/{documentId}/recordings/page-turns` | POST | 완료 | |
| 녹음 파일 업로드 | `/api/documents/{documentId}/recordings` | POST | 완료 | |
| STT 결과 업로드 X (서버에서 처리) | `/api/documents/{documentId}/recordings/{recordingId}/stt-results` | POST | 완료 | |


**Summarize API**

| 기능 | URL | Method | Status | 설명 |
|------|-----|--------|--------|------|
| STT결과 | `/api/ai/` | POST | 완료 |  |
| 요약 정리 및 문제생성 (client → server) | `/api/ai/llm` | POST | 완료 |  |
| OCR (server → AI server) | `/api/ai/ocr` | POST | 시작 전 |  |
| 요약 정리 및 문제생성 결과 전달 (AI server → server) | `/api/ai/llm/callback` | POST | 완료 | 요약정리 페이지별로 완성될 때마다 AI서버에서 웹서버로 내용을 전달하는 API |
| 요약 정리 및 문제생성 상태 확인 (client → server) | `/api/ai/llm/status/{documentId}` | GET | 완료 |  |
| 페이지별 요약 정리 및 문제생성 상태 확인 (client → server) | `/api/ai/llm/status/{documentId}/{pageNumber}` | GET | 완료 |  |
| 생성된 요약 정리 결과 조회 (client → server) | `/api/ai/llm/results/{documentId}` | GET | 완료 |  |
| 페이지별 생성된 요약 정리 및 문제 조회 (client → server) | `/api/ai/llm/results/{documentId}/{pageNumber}` | GET | 완료 |  |
| task 상태 확인 | `/api/ai/tasks/{taskId}` | GET | 완료 | 우선은 존재하지 않는 task로 요청하더라도 PENDING입니다 |

<br>


# 🛠️ 기술스택
![image](https://github.com/user-attachments/assets/b001bb8b-950a-493a-900b-3e0094a60194)

<br>

## 🏛️ Software Architecture
### 주요 기술 스택
- **Kotlin**: 안드로이드 클라이언트 개발
- **Spring Boot**: 백엔드 애플리케이션 개발
- **MySQL**: 데이터베이스 관리
- **NGINX**: 리버스 프록시 및 로드 밸런싱
- **Docker**: 컨테이너화 및 마이크로서비스 관리
- **Flask**: AI 서버용 경량 웹 프레임워크
- **PyTorch**: 딥러닝 프레임워크
- **Tesseract OCR**: PDF 텍스트 추출
- **OpenAI Whisper**: 음성 인식 및 STT
- **GitHub Actions**: CI/CD 자동화

<br>

### 아키텍처 구성
#### 1. 클라이언트
- **Kotlin** 안드로이드 앱

#### 2. Spring 메인 서버
- **Docker** 컨테이너 환경
- **NGINX** → **Spring Boot** 서버
- **주요 기능**
  - **REST API** 제공
  - **MySQL** DB 관리
  - 사용자 관리: **Kakao OAuth** 기반 인증
  - PDF/음성 파일 관리
  - 실시간 필기 처리
  - 폴더/문서 구조화
  - 녹음/STT 관리:
    - 페이지 전환 이벤트 기반 녹음 구간 태깅
    - 페이지별 **STT** 결과 매핑
    - **OCR** 처리: Tesseract 기반 비동기 OCR

#### 3. Flask AI 서버
- **STT 처리**:
  - **Whisper** 모델 활용
  - 워커당 30% GPU VRAM 제한으로 3개 병렬 처리
  - 음성 전처리 (노이즈 제거, 정규화)
- **LLM 처리**:
  - **OpenAI API** 통합
  - 강의 요약 및 시험문제 생성
- **작업 관리**:
  - **Celery** + **Redis** 기반 비동기 큐

<br>

#### 4. CI/CD
- **GitHub Actions** 자동화

<br>


# ☁️ 각 트랙별 코드 설명

## Android

### 주안점을 두고 개발한 기능
- 멀티모듈구조 : 협업 역량을 최대화 하기 위해 멀티 모듈 구조를 선택했으며, 기능을 연결되게 개발해야 본인 코드에 대한 이해도가 높아진다 생각해 노트 필기 화면을 들어가기 전/후로 나누어 개발을 진행함
- 클린아키텍처+mvvm
- STT(Speech To Text) 조회 기능 : 안드로이드 시장에는 제대로된 노트앱 + 녹음 변환 기능을 제공해주는 곳이 없어 집중적으로 개발함.
- 페이지 이동 이벤트 : 페이지 단위로 ocr과 녹음 싱크를 합쳐 제대로 된 요약을 제공해주기 위해 개발
- AI 서버와 연결 : 결국 대부분의 핵심 기능들은 AI를 통해 진행되기 때문에 서버와 안정적으로 연결되도록 개발


## Backend

### 주안점을 두고 개발한 기능
1. STT 결과 매칭 알고리즘
   사용자가 PDF 페이지를 넘길 때마다 기록되는 타임스탬프와 STT 결과의 단어별 타임스탬프를 비교하여 해당 페이지에서 발화된 내용을 자동으로 분류합니다.
2. AI 기반 학습 자료 생성
   각 페이지마다 문서 OCR 텍스트, 강의 음성의 STT 결과, 사용자 필기 데이터를 통합적으로 분석합니다. 이 데이터를 AI 모델에 전달하여 페이지별 맥락을 고려한 요약본과 예상 시험 문제를 생성합니다. 세 가지 데이터를 통합 분석함으로써 실제 학습 컨텍스트를 반영한 학습 자료를 제공합니다.


### 핵심 기능 처리 흐름

#### 강의 녹음 및 필기
1. 사용자가 PDF 문서 업로드
2. 페이지 전환시마다 타임스탬프 기록
3. 녹음 파일 생성 및 업로드
4. AI 서버에서 음성-텍스트 변환
5. STT 결과의 단어별 타임스탬프와 페이지 전환 타임스탬프 매칭
6. 페이지별 음성 텍스트 데이터 분류

#### AI 기반 요약본 및 시험 문제 생성
1. OCR로 PDF 텍스트 추출
2. STT로 변환된 강의 음성
3. 사용자 필기 데이터 수집
4. 위 3가지 데이터 통합 처리
5. OpenAI API 기반 요약본 생성 및 예상 시험 문제 생성


> 💡 Note: AI 관련 구현의 상세 코드는 [AI 서버 레포지토리](https://github.com/29ana-notai/Team29_AI)에서 확인할 수 있습니다.


