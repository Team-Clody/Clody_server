#  Team_Clody 🍀
- 감사일기에 AI클로디가 답장해주는 서비스

## Contributors ✨

<div align=center>
  
| [@hyukjinKimm](https://github.com/hyukjinKimm) | [@hyunw9](https://github.com/hyunw9) |[@Yangdaehan](https://github.com/Yangdaehan) |
| :---: | :---: |:---:|

<img width="300" height="400" src="https://github.com/Team-Clody/Clody_server/assets/107605573/5238b646-ba71-40c5-8fcd-3126b202ccbf">|
<img width="300" height="400" src="https://github.com/Team-Clody/Clody_server/assets/107605573/52bd1605-faca-46fa-9243-217460ce49e6">|
<img width="300" height="400" src="https://github.com/Team-Clody/Clody_server/assets/107605573/6109a1f3-2159-4f51-8c7a-40d7de70e8fb">
</div>

<br>

## API Docs 🎁
[Clody 서버 팀의 API 명세서가 궁금하다면? ✔️](https://tangy-lasagna-a67.notion.site/API-3a294e63321a4be892b540d3946823dd?pvs=4)

<br>

## Team Convention 📋
[Clody 서버 팀의 컨벤션이 궁금하다면? ✔️](https://tangy-lasagna-a67.notion.site/50fcaf0dff8f4475a01779446a79f534?pvs=4)

<br>

## 폴더 구조
```
├─main
│  ├─java
│  │  └─com
│  │      └─donkeys_today
│  │          └─server
│  │              ├─application
│  │              │  ├─auth
│  │              │  └─user
│  │              │      └─sterategy
│  │              ├─common
│  │              │  └─constants
│  │              ├─domain
│  │              │  ├─alarm
│  │              │  ├─diary
│  │              │  ├─reply
│  │              │  └─user
│  │              ├─infrastructure
│  │              │  └─redis
│  │              ├─presentation
│  │              │  └─user
│  │              │      └─dto
│  │              │          ├─requset
│  │              │          └─response
│  │              └─support
│  │                  ├─auditing
│  │                  ├─config
│  │                  ├─dto
│  │                  │  └─type
│  │                  ├─exception
│  │                  ├─feign
│  │                  │  ├─apple
│  │                  │  ├─config
│  │                  │  ├─dto
│  │                  │  │  └─response
│  │                  │  │      ├─apple
│  │                  │  │      ├─google
│  │                  │  │      └─kakao
│  │                  │  ├─google
│  │                  │  └─kakao
│  │                  ├─jwt
│  │                  └─security
│  │                      ├─auth
│  │                      ├─config
│  │                      └─filter
│  └─resources
│      ├─static
│      │  └─apple
│      └─templates
└─test
    └─java
        ├─com
        │  └─donkeys_today
        │      └─server
        │          ├─application
        │          │  └─jwt
        │          └─docs
        │              └─user
        └─resources
            └─org
                └─springframework
                    └─restdocs
                        └─templates
```


## Architecture ✨

<div align=center>
  
<img width="943" src="https://github.com/Team-Clody/Clody_server/assets/107605573/5154cf45-588a-4974-a9b7-73fb82a541f5">

</div>

<br>


## ERD ✨

![Clody ERD](https://github.com/Team-Clody/Clody_server/assets/43662405/c3388692-2079-4ac2-8d0b-e7ca17d565f8)

<br>

## Teck Stack ✨

| IDE | IntelliJ |
|:---|:---|
| Language | Java 21 |
| Framework | Spring Boot 3.3.1, Gradle |
| Authentication | Spring Security, JSON Web Tokens |
| ORM | Spring Data JPA |
| Database | PostgreSQL |
| External | AWS EC2, AWS RDS, Nginx, Docker, Docker-Compose, Redis, FCM |
| CI/CD | Github Action |
| API Docs | Notion, Swagger |
| Other Tool | Discord, Postman, Figma |

<br>
