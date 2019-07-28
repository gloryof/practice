db.createUser(
    {
      user:"test-user",
      pwd:"test-password",
      roles: [ {role: "readWrite", db: "single-db" }]
    }
 )


 db.createCollection("calendar")