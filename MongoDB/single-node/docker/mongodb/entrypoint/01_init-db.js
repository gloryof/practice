db.createUser(
  {
    user:"test-user",
    pwd:"test-password",
    roles: [ {role: "readWrite", db: "single-db" }]
  }
)


db.createCollection("profile")
db.profile.insertMany([
  {
    "name": "テスト001",
    "birthDay": new Date("1986-12-16"),
    "myself": "テスト1行目\\nテスト2行目\\nテスト3行目",
    "skill": {
      "languages": [
        {
          "languageCode": 1,
          "experience": {
            "year": 10,
            "month": 2
          }
        },
        {
          "languageCode": 2,
          "experience": {
            "year": 0,
            "month": 1
          }
        },
        {
          "languageCode": 3,
          "experience": {
            "year": 1,
            "month": 3
          }
        },
        {
          "languageCode": 4,
          "experience": {
            "year": 2,
            "month": 4
          }
        },
        {
          "languageCode": 5,
          "experience": {
            "year": 0,
            "month": 3
          }
        }
      ],
      "datastores": [
        {
          "languageCode": 1,
          "experience": {
            "year": 3,
            "month": 11
          }
        },
        {
          "languageCode": 2,
          "experience": {
            "year": 2,
            "month": 7
          }
        },
        {
          "languageCode": 3,
          "experience": {
            "year": 2,
            "month": 1
          }
        },
        {
          "languageCode": 4,
          "experience": {
            "year": 8,
            "month": 7
          }
        },
        {
          "languageCode": 5,
          "experience": {
            "year": 1,
            "month": 2
          }
        },
        {
          "languageCode": 6,
          "experience": {
            "year": 0,
            "month": 1
          }
        },
        {
          "languageCode": 7,
          "experience": {
            "year": 0,
            "month": 6
          }
        },
      ]
    }
  },
  {
    "name": "テスト001",
    "birthDay": new Date("1958-01-02"),
    "myself": "テスト1行目",
    "skill": {
      "languages": [
        {
          "languageCode": 1,
          "experience": {
            "year": 2,
            "month": 10
          }
        },
        {
          "languageCode": 3,
          "experience": {
            "year": 1,
            "month": 3
          }
        },
        {
          "languageCode": 5,
          "experience": {
            "year": 3,
            "month": 0
          }
        }
      ],
      "datastores": [
        {
          "languageCode": 1,
          "experience": {
            "year": 11,
            "month": 3
          }
        },
        {
          "languageCode": 3,
          "experience": {
            "year": 1,
            "month": 2
          }
        },
        {
          "languageCode": 5,
          "experience": {
            "year": 2,
            "month": 1
          }
        },
        {
          "languageCode": 7,
          "experience": {
            "year": 6,
            "month": 0
          }
        },
      ]
    }
  },
  {
    "name": "テスト003",
    "birthDay": new Date("1972-05-11"),
    "myself": "テスト1行目",
    "skill": {
      "languages": [
        {
          "languageCode": 2,
          "experience": {
            "year": 1,
            "month": 0
          }
        },
        {
          "languageCode": 4,
          "experience": {
            "year": 4,
            "month": 2
          }
        }
      ],
      "datastores": [
        {
          "languageCode": 2,
          "experience": {
            "year": 7,
            "month": 2
          }
        },
        {
          "languageCode": 4,
          "experience": {
            "year": 7,
            "month": 8
          }
        },
        {
          "languageCode": 6,
          "experience": {
            "year": 1,
            "month": 0
          }
        }
      ]
    }
  }
])

db.createCollection("language")
db.language.createIndex({ languageCode: 1 })
db.language.insertMany([
  {
    "languageCode": 1,
    "name": "Java",
    "sinceYear": 1995
  },
  {
    "languageCode": 2,
    "name": "Ruby",
    "sinceYear": 1995},
  {
    "languageCode": 3,
    "name": "Scala",
    "sinceYear": 2003},
  {
    "languageCode": 4,
    "name": "Go",
    "sinceYear": 2009},
  {
    "languageCode": 5,
    "name": "Kotlin",
    "sinceYear": 2011
  }
])

db.createCollection("dataStore")
db.dataStore.createIndex({ dataStoreTypeCode: 1 })
db.dataStore.insertMany([

  {
    "dataStoreCode": 1,
    "name": "Oracle Database",
    "sinceYar": 1977,
    "dataStoreTypeCode": 1
  },
  {
    "dataStoreCode": 2,
    "name": "Microsoft SQL Server",
    "sinceYar": 1989,
    "dataStoreTypeCode": 1
  },
  {
    "dataStoreCode": 3,
    "name": "MySQL",
    "sinceYar": 1995,
    "dataStoreTypeCode": 1
  },
  {
    "dataStoreCode": 4,
    "name": "PostgreSQL",
    "sinceYar": 1997,
    "dataStoreTypeCode": 1
  },
  {
    "dataStoreCode": 5,
    "name": "Neo4j",
    "sinceYar": 2007,
    "dataStoreTypeCode": 3
  },
  {
    "dataStoreCode": 6,
    "name": "Apache Cassandra",
    "sinceYar": 2008,
    "dataStoreTypeCode": 4
  },
  {
    "dataStoreCode": 7,
    "name": "MongoDB",
    "sinceYar": 2009,
    "dataStoreTypeCode": 2
  }
])

db.createCollection("dataStoreType")
db.dataStoreType.createIndex({ dataStoreTypeCode: 1 })
db.dataStoreType.insertMany([
  {
    "dataStoreTypeCode": 1,
    "name":"RDB"
  },
  {
    "dataStoreTypeCode": 2,
    "name":"ドキュメントストア"
  },
  {
    "dataStoreTypeCode": 3,
    "name":"グラフ"
  },
  {
    "dataStoreTypeCode": 4,
    "name":"キーバリューストア"
  },
])



