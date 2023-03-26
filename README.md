# chat-app

One to One real time chat application using Spring Boot, WebSocket and [STOMP](https://en.wikipedia.org/wiki/Streaming_Text_Oriented_Messaging_Protocol).
Consist of three independent services:
 - [authentication-service](https://github.com/ahmaddrabkah/chat-app/tree/master/authentication-service) :
    Service that authenticate the users and check if they are authorized, developed using Spring Security nad JWT.


 - [chat-service](https://github.com/ahmaddrabkah/chat-app/tree/master/chat-service) : 
    This service is the domain of the application that handle message sending and receiving. Developed using Spring Boot, WebSocket and STOMP.


 - [frontend](https://github.com/ahmaddrabkah/chat-app/tree/master/frontend) : 
    React application which is the UI.
-------------------------------------------------------------------------------------------------------

## Installation

To install and run the application locally : 
  - You could pull the repository then : 
    - run `mvn clean install`
    - navigate to the [authentication-service](https://github.com/ahmaddrabkah/chat-app/tree/master/authentication-service) then to `target` directory
    - run `java -jar auth-service-0.0.1-SNAPSHOT.jar`
    - then navigate to the [chat-service](https://github.com/ahmaddrabkah/chat-app/tree/master/chat-service) then to `target` directory
    - run `java -jar chat-service-0.0.1-SNAPSHOT.jar`
    - then navigate to the [frontend](https://github.com/ahmaddrabkah/chat-app/tree/master/frontend) directory
    - run `npm install`
    - run `npm start`
  
    
 - Using Docker :
   - inside the project directory run `docker-compse up`


-------------------------------------------------------------------------------------------------------

## Usage

After run the application there is two user you could use


| username | password |
| -------- | -------- |
| ahmaddrabkah | 12345 |
| mohammad | 00000 |