## Scratch Game - README
### Overview
* This is a Java implementation of a scratch card game that generates a matrix of symbols and calculates rewards based on winning combinations and bonus symbols.

### Prerequisites
* Java JDK 1.8 or higher

* Maven 3.6.0 or higher

### Installation
* Clone the repository:

```
git clone https://github.com/yourusername/scratch-game.git
cd scratch-game
```
* Build the project:

```
mvn clean package
```

* Running the Game
After building, you can run the game with the following command:

```
java -jar target/scratch-game-1.0-SNAPSHOT.jar --config src/main/resources/config.json --betting-amount 100
```
* Command Line Arguments
### Parameter	            Description	Required
* --config	Path to configuration JSON file	Yes
* --betting-amount	Betting amount (must be positive)	Yes
### Configuration
The game uses a JSON configuration file (config.json) that defines:

* Game matrix dimensions (rows/columns)

* Symbols and their properties

* Probabilities for symbol generation

* Winning combinations

* Example configuration is provided in src/main/resources/config.json

### Output Format
The game outputs results in JSON format with the following structure:



json
```
{
"matrix": [["A","B","C"],["D","E","F"],["G","H","I"]],
"reward": 1000.0,
"applied_winning_combinations": {
"A": ["same_symbol_3_times"]
},
"applied_bonus_symbol": "10x"
}
```
### Testing
* Run unit tests with:

````
mvn test
````
Project Structure
text
scratch-game/
├── src/
│ ├── main/
│ │ ├── java/com/scratchgame/
│ │ │ ├── core/ # Core game logic
│ │ │ │ ├── GameConfig.java
│ │ │ │ ├── GameEngine.java
│ │ │ │ ├── MatrixGenerator.java
│ │ │ │ ├── RewardCalculator.java
│ │ │ │ ├── Symbol.java
│ │ │ │ └── WinCombination.java
│ │ │ ├── exceptions/ # Custom exceptions
│ │ │ │ └── GameException.java
│ │ │ └── Main.java # Entry point
│ │ └── resources/ # Configuration files
│ │ └── config.json
│ └── test/ # Unit tests
│ ├── GameEngineTest.java
│ ├── MatrixGeneratorTest.java
│ └── RewardCalculatorTest.java
├── pom.xml # Maven configuration
└── README.md # This file
