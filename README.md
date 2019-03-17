## 빌드 및 실행 방법

```bash
# 실행 방법
cd api-server
mvn spring-boot:run
```

```bash
1번 문제

# Request
curl --request POST \
   -F file=@"src/main/resources/data.csv" http://localhost:8080/api/municipality/upload
   
# Response
{
  "name": "data.csv",
  "size": 13600,
  "rows": 98,
  "msg": "SUCCESS"
}
```



```bash
2번 문제

# Request
curl --request POST \
  --url http://localhost:8080/api/municipality/ \
  --header 'content-type: application/json'

# Response
[
  {
    "region": "강릉시",
    "target": "강릉시 소재 중소기업으로서 강릉시장이 추천한 자",
    "usage": "운전",
    "limit": "추천금액 이내",
    "rate": "3%",
    "institute": "강릉시",
    "mgmt": "강릉지점",
    "reception": "강릉시 소재 영업점"
  },
  ...
]
```



```bash
3번 문제

# Request
curl --request GET \
  --url http://localhost:8080/api/municipality/%EA%B0%95%EC%9B%90%EB%8F%84 \
  --header 'content-type: application/json'
  
# Response
{
  "region": "강원도",
  "target": "강원도 소재 중소기업으로서 강원도지사가 추천한 자",
  "usage": "운전",
  "limit": "8억원 이내",
  "rate": "3%~5%",
  "institute": "강원도",
  "mgmt": "춘천지점",
  "reception": "강원도 소재 영업점"
}

```



```bash
4번 문제

# Request
curl --request PUT \
  --url http://localhost:8080/api/municipality \
  --header 'content-type: application/json' \
  --data '{
	  "region": "강원도",
    "target": "강원도 소재 중소기업으로서 강원도지사가 추천한 자",
    "usage": "운전",
    "limit": "8억원 이내",
    "rate": "3%~12%",
    "institute": "강원도",
    "mgmt": "춘천지점",
    "reception": "강원도 소재 영업점"
}'

# Response
{
  "region": "강원도",
  "target": "강원도 소재 중소기업으로서 강원도지사가 추천한 자",
  "usage": "운전",
  "limit": "8억원 이내",
  "rate": "3%~12%",
  "institute": "강원도",
  "mgmt": "춘천지점",
  "reception": "강원도 소재 영업점"
}
```



```bash
5번 문제

# Request
curl --request GET \
  --url http://localhost:8080/api/municipality/sort/5
  
# Response
{
  "region": "경기도, 제주도, 국토교통부, 인천광역시, 안양시"
}
```



```bash
6번 문제

# Request
curl --request GET \
  --url http://localhost:8080/api/municipality/recommend

# Response
{
  "region": "안산시"
}

```



## 개발 프레임워크

- Java 1.8
- spring-boot 2.1.3 RELEASE
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- lombok
- h2-database



## 문제해결 방법

### Entity 정의

- AbstractTimestampEntity
  - 생성시간, 수정시간 
- MunicipalityInfoEntity
  - ID, 지자체 코드, 지원대상, 용도, 지원한도, 이차보전, 추천기관, 관리점, 취급점
- SupportMunicipalityInfoEntity
  - 지자체명, 지자체 코드



### 필수 문제

- 1번 문제
  - 파일 업로드
  - 개별 레코드 데이터를 Entity에 할당 후 repository에 저장
    - 개별 Insert 없기 때문에 code값 순차적인 패턴으로 생성(LC0, LC1, LC2 …)
- 2번 문제
  - Repository를 통한 전체 Entity 조회
  - 필요한 항목 Reponse에 할당
- 3번 문제
  - SupportMunicipalityRepository에서 지자체명으로 code 검색
  - MunicipalityRepository에서 code로 대상 Entity 검색 후
- 4번 문제
  - 3번과 동일한 과정으로 대상 entity 검색
  - 요청 받은 수정 내용을 검색한 Entity에 할당 후 업데이트
- 5번 문제
  - MunicipalityRepository에서 전체 entity 검색
    - 정렬 대상이 되는 지원한도, 이차보전을 정렬 가능한 데이터 형태로 변환하기 위해 전체 데이터 셋을 후 처리 하기 위함.
  - Entity에서 필요 정보 추출(지자체명(기관명), 지원한도, 이차보전) 및 데이터 변환
    - 지원금액에 <u>추천금액 이내</u>는 의미 판단 어려워 정렬 기준에서 제외
    - 이차보전에 <u>대출이자 전액</u> 은 의미 판단 어려워 정렬 기준에서 제외
  - 1차 정렬 (지원한도, 내림차순)
  - 2차 정렬 (이차보전, 오름차순)
  - TopN개 추출 후 response 생성
- 6번 문제
  - MunicipalityRepository에서 전체 entity 검색
  - Entity에서 필요 정보 추출(지자체명(기관명), 추천기관, 이차보전) 및 데이터 변환
    - 정렬을 위한 데이터 세팅
    - Key : 지자체명(기관명)
    - Value: 추천기관, 이차보전
      - 이차보전에 <u>대출이자 전액</u> 은 의미 판단 어려워 정렬 기준에서 제외
  - 이차보전 기준 오름차순 정렬 후, 이차보전 값이 가장 작은 추천기관명 반환