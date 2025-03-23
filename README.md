### 교정인을 위한 치과 정보 및 교정 정보를 한 눈에 접할 수 있는 서비스, 바른이

# 🎯 프로젝트 목표 및 역할

- N + 1 문제 해결 및 PageableExecutionUtils 활용한 성능 최적화
- Spring Security + JWT 기반 인증 및 인가 구현
- 이미지 업로드 및 조회 성능 개선 (AWS Presigned URL, CDN + Lambda 활용)
- AWS Elastic Beanstalk 및 Docker 기반 무중단 CI/CD 인프라 구축
- Private / Public Subnet을 활용 운영 데이터베이스 구성
- 멀티모듈 구조 도입을 통한 코드 중복 제거 및 유지보수성 향상
- TDD 기반 개발 프로세스 학습 및 적용
- Facade 패턴 적용을 통한 도메인 책임 분리

# 🛠️ 사용 기술

Front-End : <img src="https://img.shields.io/badge/Swift-FA7343?style=for-the-flat&logo=swift&logoColor=white">
<br>
Back-End : <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-flat&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-flat&logo=spring-boot"> <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-flat&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-flat&logo=redis&logoColor=white">
<br>
배포
환경 : <img src="https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-flat&logo=amazonaws&logoColor=white"> <img src="https://img.shields.io/badge/Linux-FCC624?style=for-the-flat&logo=linux&logoColor=black"> <img src="https://img.shields.io/badge/Docker-2CA5E0?style=for-the-flat&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/Github%20Actions-282a2e?style=for-the-flat&logo=githubactions&logoColor=367cfe">

## 🏗️ 아키텍처

<img width="1646" alt="arch" src="https://github.com/user-attachments/assets/9da552af-4821-4fa4-82b0-3538863b3219">

| 이미지 업로드                                                                                                         | 이미지 조회                                                                                                          |
|-----------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| <img width="800" alt="7" src="https://github.com/user-attachments/assets/ea37e3a6-f61d-4aea-84f0-c23b2a1891c4"> | <img width="800" alt="2" src="https://github.com/user-attachments/assets/b7c580a2-0575-4ea8-913c-8a73e8ba1b60"> |

# 📺 프로젝트 화면 구성

| 메인 화면                                                                                                                        | 치과 목록 화면                                                                                                                     |
|------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| <img width="500" alt="7" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/1c9f2951-1a3b-4565-947e-5da7133d5466"> | <img width="500" alt="2" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/052714ef-6506-46bd-a346-c5f14448f9fb"> |

| 로그인 화면                                                                                                                       | 치과 정보 화면                                                                                                                     | 커뮤니티 화면                                                                                                                      | 예약 화면                                                                                                                        |
|------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------|
| <img width="500" alt="3" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/d637dd40-a7c2-4378-9583-4a3727a1344d"> | <img width="500" alt="4" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/d774b6cf-01e1-4a10-bb9b-a457cf1a06a9"> | <img width="500" alt="5" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/e7f24a17-4743-4db1-ae98-dbf20108c650"> | <img width="500" alt="6" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/d9b304c4-a4f4-43b6-bb06-0beccbf26c27"> |

| ERD                                                                                                                            | API                                                                                                                                     |
|--------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| <img width="860" alt="ERD" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/a02dc13e-7c9c-49ed-aaf7-0a3db343a8aa"> | <img width="1278" alt="api 명세서" src="https://github.com/seheonnn/bareuni-BE/assets/101795921/a32c67d1-ee62-4d8a-b77d-03a00131fab3"> |

# 📢 프로젝트 홍보 포스터

![포스터2](https://github.com/seheonnn/bareuni-BE/assets/101795921/f1c12a42-7d31-4996-b508-12cd299153e0)
![포스터1](https://github.com/seheonnn/bareuni-BE/assets/101795921/7b39ec07-cf5c-4b99-a34b-bbbc860abe93)

# Bareuni-BE

## Commit Message Convention

|    Type    | Description                                |
|:----------:|--------------------------------------------|
|   `Feat`   | 새로운 기능 추가                                  |
|   `Fix`    | 버그 수정                                      |
|    `Ci`    | CI관련 설정 수정                                 |
|   `Docs`   | 문서 (문서 추가, 수정, 삭제)                         |
|  `Style`   | 스타일 (코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없는 경우)    |
| `Refactor` | 코드 리팩토링                                    |
|   `Test`   | 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없는 경우) |
|  `Chore`   | 기타 변경사항 (빌드 스크립트 수정 등)                     |
