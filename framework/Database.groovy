import groovy.sql.Sql

class Database {
    String driverClass
    def url
    @Lazy def client = { Class.forName driverClass; Sql.newInstance url }()
}
