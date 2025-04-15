# Start Up

1. Clone the repository

```
git clone https://github.com/project-deha/deha-be
```

2. Open the project in IDE
3. Run the application

Note: You need to set up the `application.properties` file inside the `src/main/resources` like below:

```properties
# Application
spring.application.name=deha-BE
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/deha
spring.datasource.username=<your_database_username>
spring.datasource.password=<your_password>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false
# Client
client.url=http://localhost:3000
# Email
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=<email_address>
spring.mail.from=Dynamic Earthquake Hazard Analysis
spring.mail.password=<email_password>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true