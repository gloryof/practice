# findByUserId
## やりたかったこと
ユーザのデータからマスタ情報を紐付けて情報を出力する。
```json
{
    "name" : "テスト001",
    "userId" : 1,
    "birthDay" : ISODate("1986-12-16T00:00:00Z"),
    "myself" : "テスト1行目\\nテスト2行目\\nテスト3行目",
    "skil": {
        "languages": [
            {
                "languageCode" : 1,
                "name" : "Java",
                "experience" : {
                    "year" : 10,
                    "month" : 2
                }
            }
        ],
        "datastores": [
            {
                "dataStoreCode" : 1,
                "name" : "Oracle Database",
                "storeType": "RDB",
                "experience" : {
                    "year" : 3,
                    "month" : 11
                }
            }
        ]
    }
}
```
### SQLの場合
下記のSQLの組み合わせ。
```sql
SELECT
    name,
    user_id,
    birth_day,
    myself
FROM
    profile
WHERE
    user_id = 1;

SELECT
    p.lanugage_code,
    l.name,
    p.experience_year,
    p.experience_month
FROM
    profile AS p
    INNER JOIN language AS l ON
        p.language_code = l.language_code
WHERE
    p.user_id = 1;

SELECT
    p.data_store_code,
    d.name,
    dt.name AS data_type,
    p.experience_year,
    p.experience_month
FROM
    profile AS p
    INNER JOIN data_store AS d ON
        p.data_store_code = d.data_store_code
    INNER JOIN data_store_type AS dt ON
        d.data_store_type_code = dt.data_store_type_code
WHERE
    p.user_id = 1;

```
## 結論
INNER JOINのようにコード値から参照することはできたが、  
一部の項目だけ抜き取って既存のオブジェクトに追加することはできなさそう。  
無理に1回のクエリで取るよりはマスタ系のところだけ別クエリで取る方がシンプルになって使いやすそう。  
詳しく調べれば1回のクエリで取得できるかもしれないが、かける時間に対するリターンが少なさそうなのでやらない。  
## 詳細
### 作ったクエリ
[ここ](../query/findByUserId.js) を参照。
### 取得結果
```json
{
    "_id" : ObjectId("5d44e695f982da42f06781f8"),
    "name" : "テスト001",
    "userId" : 1,
    "birthDay" : ISODate("1986-12-16T00:00:00Z"),
    "myself" : "テスト1行目\\nテスト2行目\\nテスト3行目",
    "skill" : {
        "languages" : [
            {
                "languageCode" : 1,
                "experience" : {
                    "year" : 10,
                    "month" : 2
                }
            },
            {
                "languageCode" : 2,
                "experience" : {
                    "year" : 0,
                    "month" : 1
                }
            },
            {
                "languageCode" : 3,
                "experience" : {
                    "year" : 1,
                    "month" : 3
                }
            },
            {
                "languageCode" : 4,
                "experience" : {
                    "year" : 2,
                    "month" : 4
                }
            },
            {
                "languageCode" : 5,
                "experience" : {
                    "year" : 0,
                    "month" : 3
                }
            }
        ],
        "datastores" : [
            {
                "dataStoreCode" : 1,
                "experience" : {
                    "year" : 3,
                    "month" : 11
                }
            },
            {
                "dataStoreCode" : 2,
                "experience" : {
                    "year" : 2,
                    "month" : 7
                }
            },
            {
                "dataStoreCode" : 3,
                "experience" : {
                    "year" : 2,
                    "month" : 1
                }
            },
            {
                "dataStoreCode" : 4,
                "experience" : {
                    "year" : 8,
                    "month" : 7
                }
            },
            {
                "dataStoreCode" : 5,
                "experience" : {
                    "year" : 1,
                    "month" : 2
                }
            },
            {
                "dataStoreCode" : 6,
                "experience" : {
                    "year" : 0,
                    "month" : 1
                }
            },
            {
                "dataStoreCode" : 7,
                "experience" : {
                    "year" : 0,
                    "month" : 6
                }
            }
        ],
        "reference" : {
            "master" : {
                "language" : [
                    {
                        "_id" : ObjectId("5d44e695f982da42f06781fb"),
                        "languageCode" : 1,
                        "name" : "Java",
                        "sinceYear" : 1995
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f06781fc"),
                        "languageCode" : 2,
                        "name" : "Ruby",
                        "sinceYear" : 1995
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f06781fd"),
                        "languageCode" : 3,
                        "name" : "Scala",
                        "sinceYear" : 2003
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f06781fe"),
                        "languageCode" : 4,
                        "name" : "Go",
                        "sinceYear" : 2009
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f06781ff"),
                        "languageCode" : 5,
                        "name" : "Kotlin",
                        "sinceYear" : 2011
                    }
                ],
                "datastore" : [
                    {
                        "_id" : ObjectId("5d44e695f982da42f0678200"),
                        "dataStoreCode" : 1,
                        "name" : "Oracle Database",
                        "sinceYar" : 1977,
                        "dataStoreTypeCode" : 1
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f0678201"),
                        "dataStoreCode" : 2,
                        "name" : "Microsoft SQL Server",
                        "sinceYar" : 1989,
                        "dataStoreTypeCode" : 1
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f0678202"),
                        "dataStoreCode" : 3,
                        "name" : "MySQL",
                        "sinceYar" : 1995,
                        "dataStoreTypeCode" : 1
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f0678203"),
                        "dataStoreCode" : 4,
                        "name" : "PostgreSQL",
                        "sinceYar" : 1997,
                        "dataStoreTypeCode" : 1
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f0678204"),
                        "dataStoreCode" : 5,
                        "name" : "Neo4j",
                        "sinceYar" : 2007,
                        "dataStoreTypeCode" : 3
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f0678205"),
                        "dataStoreCode" : 6,
                        "name" : "Apache Cassandra",
                        "sinceYar" : 2008,
                        "dataStoreTypeCode" : 4
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f0678206"),
                        "dataStoreCode" : 7,
                        "name" : "MongoDB",
                        "sinceYar" : 2009,
                        "dataStoreTypeCode" : 2
                    }
                ],
                "dataStoreTypeCode" : [
                    {
                        "_id" : ObjectId("5d44e695f982da42f0678207"),
                        "dataStoreTypeCode" : 1,
                        "name" : "RDB"
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f0678208"),
                        "dataStoreTypeCode" : 2,
                        "name" : "ドキュメントストア"
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f0678209"),
                        "dataStoreTypeCode" : 3,
                        "name" : "グラフ"
                    },
                    {
                        "_id" : ObjectId("5d44e695f982da42f067820a"),
                        "dataStoreTypeCode" : 4,
                        "name" : "キーバリューストア"
                    }
                ]
            }
        }
    }
}
```
### クエリに対する考察
profileコレクションの配列の要素に対して$lookkupしているのことが難易度をあげている。  
配列でなければ、$lookupと$addFieldsを使えばやりたいことはできる。  

配列なので1番目の要素のコード値に紐付けられる情報を取ってくる。  
ということが1回のクエリでやるのが難しく実現できなかった。   

やるのであればプログラムコード側での紐付けをした方が良いが、  
`skill.reference.master` のようにマスタ情報が一つ一つ乗ってしまう。  
取得件数が少なければ問題ないが取得件数が多いとプログラム側のメモリを消費する。  
なので、プログラム側で紐付けをするのであれば別クエリで取ってくる方が良い。
