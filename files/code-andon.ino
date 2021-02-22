/*
 * Programmable Tower Light System
 * Created by Jimmy Nguyen
 * 
 */

// Set relay pins
const int RELAY_PIN_LIGHT_GREEN = 2;
const int RELAY_PIN_LIGHT_YELLOW = 3;
const int RELAY_PIN_LIGHT_RED = 4;
const int BUTTON_PIN_OVERRIDE = 7;

// Set serial identifiers for comparison
const String IDENTIFIER_LIGHT_GREEN = "GREEN";
const String IDENTIFIER_LIGHT_YELLOW = "YELLOW";
const String IDENTIFIER_LIGHT_RED = "RED";
const String IDENTIFIER_BUTTON_OVERRIDE = "RESET";

// Indicator messages
const String BUFFER_START_TOKEN = "#";
const String BUFFER_END_TOKEN = "!";

void setup() {
  Serial.begin(9600);
  pinMode(RELAY_PIN_LIGHT_RED, OUTPUT);
  pinMode(RELAY_PIN_LIGHT_YELLOW, OUTPUT);
  pinMode(RELAY_PIN_LIGHT_GREEN, OUTPUT);
  pinMode(BUTTON_PIN_OVERRIDE, INPUT_PULLUP);
}

void loop() {
  Serial.println(BUFFER_START_TOKEN);

  if (digitalRead(BUTTON_PIN_OVERRIDE) == LOW) {
    Serial.println(IDENTIFIER_BUTTON_OVERRIDE);
    toggleOff();
  } else if (Serial.available() > 0) {
    String input = Serial.readString();
    Serial.println(input);

    if (input == IDENTIFIER_LIGHT_GREEN) {
      handlePin(RELAY_PIN_LIGHT_GREEN);
    } else if (input == IDENTIFIER_LIGHT_YELLOW) {
      handlePin(RELAY_PIN_LIGHT_YELLOW);
    } else if (input == IDENTIFIER_LIGHT_RED) {
      handlePin(RELAY_PIN_LIGHT_RED);
    }
  } else {
    toggleOff();
  } 
  
  Serial.println(BUFFER_END_TOKEN);

  delay(500);
}

bool isActivated(String input, String identifier, int relayPin) {
  return (input == identifier) && (currentActivePin != relayPin);
}

void handlePin(int pin) {
    switch (pin) {
      case RELAY_PIN_LIGHT_RED:
        triggerAndReset(RELAY_PIN_LIGHT_RED, RELAY_PIN_LIGHT_YELLOW, RELAY_PIN_LIGHT_GREEN);
        break;
      case RELAY_PIN_LIGHT_YELLOW:
        triggerAndReset(RELAY_PIN_LIGHT_YELLOW, RELAY_PIN_LIGHT_GREEN, RELAY_PIN_LIGHT_RED);
        break;
      case RELAY_PIN_LIGHT_GREEN:
        triggerAndReset(RELAY_PIN_LIGHT_GREEN, RELAY_PIN_LIGHT_RED, RELAY_PIN_LIGHT_YELLOW);
        break;
    }
}

void toggleOff() {
  digitalWrite(RELAY_PIN_LIGHT_RED, HIGH);
  digitalWrite(RELAY_PIN_LIGHT_YELLOW, HIGH);
  digitalWrite(RELAY_PIN_LIGHT_GREEN, HIGH);
}

void triggerAndReset(int activePin, int unactivePinA, int unactivePinB) {
  digitalWrite(activePin, LOW);
curr  digitalWrite(unactivePinA, HIGH);
  digitalWrite(unactivePinB, HIGH);
}
