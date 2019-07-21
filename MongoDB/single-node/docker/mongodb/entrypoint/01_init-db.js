db.createUser(
    {
      user:"test-user",
      pwd:"test-password",
      roles: [ "readWrite" ]
    }
 )


 db.createCollection("listings")
 db.createCollection("calendar")
 db.createCollection("review")