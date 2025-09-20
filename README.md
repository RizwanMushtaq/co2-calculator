## CO2 Calculator App (Java)

### Overview

This Java application calculates the CO2 emissions for a trip between two
locations using the OpenRouteService API.

### Prerequisites

* Java 21 or higher installed on your machine.
* Maven installed to manage dependencies and build the project.
* An API key from OpenRouteService.
* Git installed to clone the repository.
* A terminal or command prompt to run commands.
* (Optional) A text editor or IDE for viewing and editing the code.

### Cloning the Repository

1. Open your terminal or command prompt.
2. Navigate to the directory where you want to clone the repository.
3. Run:
   ```
   git clone https://github.com/RizwanMushtaq/co2-calculator.git
   cd co2-calculator
   ```
4. You can now view and edit the code using your preferred text editor or IDE.

### Setting Up Environment Variables

1. Sign up for an API key
   at [OpenRouteService](https://openrouteservice.org/sign-up/).
2. Set the `ORS_TOKEN` environment variable with your API key.
3. (Optional) Enable debug mode by setting the `CO2_DEBUG` environment
   variable to `true`.

**For Windows users:**

- **cmd.exe:**
  ```
  set ORS_TOKEN=your_api_key_here
  set CO2_DEBUG=true   # (optional)
  set CO2_DEBUG=false  # (to disable debug mode)
  ```
- **PowerShell:**
  ```
  $env:ORS_TOKEN="your_api_key_here"
  $env:CO2_DEBUG="true"   # (optional)
  Remove-Item Env:\CO2_DEBUG   # (to disable debug mode)
  ```

**For Linux or Mac users:**

  ```
  export ORS_TOKEN="your_api_key_here"
  export CO2_DEBUG="true"   # (optional)
  unset CO2_DEBUG           # (to disable debug mode)
  ```

### Building and Running the Application

1. Ensure you have Java 21 or higher and Maven installed.
2. Build the application using Maven:
   ```
   mvn clean package
   ```
3. After building, you can run the application using one of the following
   methods:

**On Unix-like systems (Linux/Mac):**

```
./co2-calculator --version
./co2-calculator --help
./co2-calculator --start Hamburg --end Berlin --transportation-method diesel-car-medium
```

**On Windows:**

- **PowerShell:**

```
./co2-calculator --version
./co2-calculator --help
./co2-calculator --start Hamburg --end Berlin --transportation-method diesel-car-medium
```

- **cmd.exe:**

```
co2-calculator.bat --version
co2-calculator.bat --help
co2-calculator.bat --start Hamburg --end Berlin --transportation-method diesel-car-medium
```

**Or run the jar directly:**

```
java -jar target/co2-calculator.jar --start Hamburg --end Berlin --transportation-method diesel-car-medium
```

### Running with Docker

**With docker-compose:**

1. Run:
   ```
   docker-compose up --build
   ```

### Running Tests

To run all tests:

```
mvn test
```

### Troubleshooting

- **Missing ORS_TOKEN:** Ensure the environment variable is set before running
  the app.
- **Network errors:** Check your internet connection and API key validity.
- **Java version errors:** Make sure you are using Java 21 or higher.

### Contributing

Contributions are welcome! Please open issues or submit pull requests.

### Support

For questions or support, please open an issue on the GitHub repository.
