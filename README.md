![리드미용 유영](https://github.com/Team-Recordy/Recordy-Android/assets/109855280/6eb852d9-2a07-4426-bc09-1b8c6dc982b2) </br>

## 🌊 유영
**유저가 ‘공간’을 ‘영상’으로 디깅하고, 나만의 ‘공간 취향’을 발견하는 서비스**

## 🔢 목차
[프로젝트 설명](#프로젝트-설명)</br>
[우리가 해결하고자 하는 문제](#우리가-해결하고자-하는-문제)</br>
[주요 기능](#주요-기능)</br>
[시연 영상](#시연-영상)</br>
[파트원 사진](#파트원-사진)</br>
[팀원별 역할 분담](#팀원별-역할-분담)</br>
[컨벤션 규칙 및 브랜치 전략](#컨벤션-규칙-및-브랜치-전략)</br>
[폴더링](#폴더링)</br>

## 🅿️ 프로젝트 설명 
'유영'은 내 취향에 맞는 공간을 촬영하고 업로드하는 숏폼을 활용하는 라이프스타일 플랫폼입니다. 사용자들은 새로운 장소를 자유롭게 둘러보고 취향을 찾으며, 다른 사용자들과 공간 경험을 나눌 수 있습니다. 동영상을 활용하여 방문 전에도 실제와 유사한 공간감을 느낄 수 있는 공간영상을 제공합니다. 또한 키워드와 취향에 맞는 유저 구독 기능을 통해 무분별한 알고리즘에서 벗어나 취향에 맞는 공간 정보만 탐색할 수 있도록 하는 가치를 제공합니다.</br></br>


## 🪼 우리가 해결하고자 하는 문제
- 취향에 맞는 공간 정보를 받을 수 있는 플랫폼의 분산</br>
- 공간감을 느낄 수 없는 가공된 사진</br>
- 기존 플랫폼의 영상 알고리즘으로 내 취향에 맞지 않는 공간 노출 </br></br>


## 📍 주요 기능 
1. 내 공간 경험 업로드하기: 사용자가 다양한 장소를 촬영한 공간감이 느껴지는 짧은 영상을 앱에 업로드할 수 있으며, 간편한 인터페이스로 촬영부터 업로드까지 손쉽게 진행할 수 있습니다.</br>
2. 취향 분석표 수집하기: 사용자 취향을 분석하여 맞춤형 취향 분석표를 제공합니다. 나만의 분석표를 받고 공간 취향을 알아볼 수 있습니다.</br>
3. 취향 기반 유저의 소식 받기: 다른 유저를 팔로우하고, 그들의 영상을 저장하고 소식을 받을 수 있습니다.</br>
4. 관심 있는 공간 저장하기: 마음에 드는 공간 영상을 저장하고, 쉽게 보관할 수 있습니다. </br></br>

## ⚙️ 기술 및 아키텍쳐 선정


## 👨‍👩‍👧‍👦 파트원 사진
<img src="https://github.com/user-attachments/assets/34aa8062-e643-4da9-ab22-af4aac4f47f5" alt="img_yoo0_android" style="width: 80%;"></br>


## 👤 팀원별 역할 분담
|<img src="https://avatars.githubusercontent.com/u/113014331?v=4" width="140" />|<img src="https://avatars.githubusercontent.com/u/75840431?v=4" width="140" />|<img src="https://avatars.githubusercontent.com/u/93514333?v=4" width="140" />|<img src="https://avatars.githubusercontent.com/u/109855280?v=4" width="140" />|<img src="https://avatars.githubusercontent.com/u/102652293?v=4" width="140"/>|
|:---------:|:---------:|:---------:|:---------:|:---------:|
|[👑우상욱](https://github.com/Sangwook123)|[김명석](https://github.com/cacaocoffee)|[이삭](https://github.com/lsakee)|[이나경](https://github.com/nagaeng)|[윤서희](https://github.com/seohee0925)|
| `홈, 영상` | `로그인` | `기록` | `프로필` | `팔로잉/팔로워, 상대` | </br>

## 🪽 의존 그래프


## ❗ 컨벤션 규칙 및 브랜치 전략

**깃 컨벤션:**  [Git Convention](https://bohyunnkim.notion.site/Git-Convention-d384b7b4b6c149009a88ec5409a9c694?pvs=74) </br>
**코드 컨벤션:**  [Code Convention](https://bohyunnkim.notion.site/Code-Convention-bc2e0e1601554f2792131c3942984dec) </br>
**브랜치 전략:**  [Branch Strategy](https://bohyunnkim.notion.site/Branch-Strategy-9d989f5c36ca44ffaae40e436056f966) </br></br>


## 🗂️ 폴더링

```bash
├── Recordy
├── 📁 app
├── 📁 build-logic
│   ├── 📁 convention
├── 📁 core
│   ├── 🗂️ buildconfig
│   ├── 🗂️ common
│   ├── 🗂️ datastore
│   ├── 🗂️ model
│   ├── 🗂️ network
│   ├── 🗂️ ui
│   ├── 🗂️ designsystem
├── 📁 data
│   ├── 🗂️ recordy
├── 📁 remote
│   ├── 🗂️ recordy
├── 📁 local
│   ├── 🗂️ recordy
├── 📁 domain
│   ├── 🗂️ recordy
├── 📁 feature
│   ├── 🗂️ navigator
│   ├── 🗂️ home
│   ├── 🗂️ login
│   ├── 🗂️ mypage
│   ├── 🗂️ profile
│   ├── 🗂️ upload
│   ├── 🗂️ video
├── 📁 gradle
│   ├──  libs.versions.toml
```
