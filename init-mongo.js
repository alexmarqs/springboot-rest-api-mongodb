db.createUser({
  user: "devuser",
  pwd: "devuser",
  roles: [ { role: "readWrite", db: "campaignsDB" } ]
})