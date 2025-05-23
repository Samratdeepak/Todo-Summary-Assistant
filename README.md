# Todo Summary Assistant

## Tech Stack

- Frontend: React, Tailwind CSS, React Icons
- Backend: Spring Boot (Java), MySQL, JWT, JavaMail
- LLM: Cohere
- Slack: Incoming Webhook
- SMTP: Gmail.

## Usage

- Register with your email, check your inbox for OTP.
- Login, manage todos.
- Click “Summarize & Send to Slack” to generate a summary and send to the configured Slack channel.


## Features

- Register/login/forgot password with email OTP
- Create, edit, delete todos
- Summarize pending todos (OpenAI) and send to Slack
- React frontend, Spring Boot backend, MySQL

## How to Run

1. Configure `application.properties` with MySQL, SMTP, JWT, Slack, and OpenAI keys
2. Start MySQL. Backend auto-creates schema.
3. cd todosummary -> Run backend: `./mvnw spring-boot:run`
4. Run frontend: `npm run dev`
5. Visit `http://localhost:5173`

## Environment Example

```
spring.datasource.url=jdbc:mysql://localhost:3306/todos_db?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
spring.mail.username=your_gmail@gmail.com
spring.mail.password=your_gmail_app_password
jwt.secret=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
openai.api.key=sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
slack.webhook.url=https://hooks.slack.com/services/xxxx/yyyy/zzzz
```

## Slack Setup

- Go to Slack > Apps > Incoming Webhook
- Create for your channel
- Copy webhook URL to backend configuration

## OpenAI Setup

- Go to https://dashboard.cohere.com/api-keys
- Get API key and add to backend config


## Jwt Secret Generation

- Go to windows powershell
- run [Convert]::ToBase64String((1..48 | ForEach-Object {Get-Random -Maximum 256}) -as [byte[]]) | ForEach-Object { $_.Substring(0,64) }
- Get JWT secret key and add to backend config


## Screenshots

![App Dashboard](https://i.ibb.co/Jwfhh2Sy/1.png)
![App Dashboard](https://i.ibb.co/ksPy46kw/2.png)
![App Dashboard](https://i.ibb.co/B2gycmcJ/3.png)
![App Dashboard](https://i.ibb.co/zWjJsxt0/4.png)
![App Dashboard](https://i.ibb.co/fVTBkGp3/5.png)
![App Dashboard](https://i.ibb.co/Q7kt1k8j/6.png)
![App Dashboard](https://i.ibb.co/7ty1ZYRR/7.png)
![App Dashboard](https://i.ibb.co/q3GyLTRW/8.png)
![App Dashboard](https://i.ibb.co/vx1hjsx6/9.png)
![App Dashboard](https://i.ibb.co/S4d7y7kC/10.png)

