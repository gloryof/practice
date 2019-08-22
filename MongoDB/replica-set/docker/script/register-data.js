
db.createCollection("language")
db.language.createIndex({ languageCode: 1 }, { unique: true })
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
