#include "WiFi.h"
#include <HTTPClient.h>
#include <ArduinoJson.h>
#include <ArduinoJson.hpp>
#include <esp_wifi.h>

const char* ssid = "ESP32";
const char* password = "123456789";

void setup()
{
  Serial.begin(115200);
  WiFi.mode(WIFI_STA);
  Serial.println("\n[*] Creating ESP32 STA");
  WiFi.begin(ssid, password);
  Serial.println("\n[*] Connecting to WiFi Network");
    while(WiFi.status() != WL_CONNECTED)
    {
        Serial.print(".");
        delay(100);
    }
  Serial.print("\n[+] Connected to WiFi network with local IP : ");
  Serial.println(WiFi.localIP());
}

void loop()
{
  while(WiFi.status() == WL_CONNECTED)
  {
      sendData();
      delay(1000);
  }
  reconnect();
}

void reconnect() {
  WiFi.disconnect();
  WiFi.begin(ssid, password);
    while(WiFi.status() != WL_CONNECTED)
    {
        Serial.print(".");
        delay(100);
    }
  Serial.print("\n[+] Reconnected to WiFi network with local IP : ");
  Serial.println(WiFi.localIP());
}

void sendData() {
  WiFiClient client;
  HTTPClient http;

  String url = "http://192.168.137.1:9999/data";

  http.begin(client, url);
  http.addHeader("Content-Type", "application/json");

  // Creating JSON Data
  StaticJsonDocument<256> doc;
  char output[256];

  //Id
  String clientId = "ESP32Client-";
  clientId += WiFi.macAddress();
  doc["deviceId"] = clientId;

  //AmbientSensorValues
  float temp = random(0, 40);
  float humidity = random(30, 70);
  float pressure = 40;
  float gas = random(100, 500);
  JsonObject sensorValues = doc.createNestedObject("sensorValues");
  sensorValues["temp"] = temp;
  sensorValues["hum"] = humidity;
  sensorValues["press"] = pressure;
  sensorValues["gas"] = gas;
  
  //WifiValues;
  JsonObject wifiValues = doc.createNestedObject("wifiValues");
  wifiValues["rssi"] = WiFi.RSSI();
  //wifiValues["snr"] = WiFi.RSSI()-WiFi.noise();
  wifiValues["channel"] = WiFi.channel();
  wifiValues["TxPower"] = WiFi.getTxPower();
  //wifiValues["speed"] = WiFi.getSpeedMbps();
  //wifiValues["packetloss"] = WiFi.packetLoss();
  wifiValues["mode"] = "WIFI_STA";
  serializeJson(doc, output);
  Serial.println(output);

  int httpResponseCode = http.POST(output);


  if (httpResponseCode > 0) {
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
    String response = http.getString();
    Serial.println(response);
  } else {
    Serial.print("Error on HTTP request: ");
    Serial.println(httpResponseCode);
  }

  http.end();
}