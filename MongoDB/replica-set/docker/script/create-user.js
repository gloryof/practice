db.createUser(
  {
    user:"test-user",
    pwd:"test-password",
    roles: [
      {
        role: "readWrite", db: "replica-db"
      },
      {
        role: "clusterAdmin", db: "admin"
      },
    ]
  }
)
