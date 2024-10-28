# The Missing Dollar Mystery -<br>a JavaFX Game using OpenAI's GPT Model

Developed by:
-  [Brandon Chan](https://github.com/br-Chan)
-  [Connor Hare](https://github.com/LaSpruca)
-  [Perry Xie](https://github.com/PerryXie123)

Game demo (6 mins):

[![The Missing Dollar Mystery](https://markdown-videos-api.jorgenkh.no/url?url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3Dtjx5raeqHC4)](https://www.youtube.com/watch?v=tjx5raeqHC4)

## To setup the API to access Chats with ChatGPT

- add in the root of the project (i.e. the same level where `pom.xml` is located) a file named `apiproxy.config`
- put inside an OpenAI access token:

  ```
  email: "EMAIL_ADDRESS" (see if it doesn't work - addresses of the form UPI@aucklanduni.ac.nz were always used during client meetings and development)
  apiKey: "YOUR_KEY"
  ```

If you don't have an API key to try out the application (which is likely the case), the video demo above shows off most of the features of our game.

## To run the game

- Clone the repository with: `https://github.com/br-Chan/Missing-Dollar-Mystery-with-GPT.git`
- Run the game with: `./mvnw clean javafx:run`
- To debug the game, run `./mvnw clean javafx:run@debug` then in VS Code "Run & Debug", then run "Debug JavaFX"
