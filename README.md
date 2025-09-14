# JSONPlaceholder Post Fetcher

## Project Description

A simple Java application that fetches posts from the public [JSONPlaceholder](https://jsonplaceholder.typicode.com/) API and saves each of them as a separate JSON file on the local filesystem.

## Features

- Fetches all posts from the `https://jsonplaceholder.typicode.com/posts` endpoint.
- Saves each post as a separate `{id}.json` file in the `output` directory.
- Automatically creates the output directory if it does not exist.
- Includes a comprehensive set of unit tests to verify the functionality of individual components.

## Technologies

- **Java 17**
- **Maven** - for dependency management and building the project.
- **Gson** - for processing JSON data.
- **JUnit 5** - for writing unit tests.
- **Mockito** - for creating mocks and simulating dependencies in tests.

## How to Run

The application can be run directly from the command line using Maven.

1.  **Build the project:**
    ```bash
    mvn clean install
    ```
2.  **Run the application:**
    ```bash
    mvn exec:java -Dexec.mainClass="PostApplication"
    ```
    The application will fetch the posts and save them into the `output` directory in the project's root.

## Testing

The project includes a complete set of unit tests. To run them, execute the following Maven command:

```bash
mvn test
```