# The Missing Dollar Mystery - a JavaFX Game using OpenAI's GPT Model

Developed in collaboration with [Connor](https://github.com/LaSpruca) and [Perry](https://github.com/PerryXie123).

## To setup the API to access Chat Completions and TTS

- add in the root of the project (i.e. the same level where `pom.xml` is located) a file named `apiproxy.config`
- put inside an OpenAI access token:

  ```
  email: "EMAIL_ADDRESS" (see if it doesn't work - addresses of the form UPI@aucklanduni.ac.nz were always used during client meetings and development)
  apiKey: "YOUR_KEY"
  ```

If you don't have an API key to try out the application (which is likely the case), the video demo above  shows off most of the features of our game.

## To run the game

`./mvnw clean javafx:run`

## To debug the game

`./mvnw clean javafx:run@debug` then in VS Code "Run & Debug", then run "Debug JavaFX"

## To run codestyle

`./mvnw clean compile exec:java@style`
