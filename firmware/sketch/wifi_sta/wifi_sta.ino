#include "WiFi.h"
#include <HTTPClient.h>
#include <ArduinoJson.h>
#include <ArduinoJson.hpp>
#include <esp_wifi.h>
#include <ESPmDNS.h>
#include <WebServer.h>
#include <WiFiClient.h>

const char* ssid = "ESP32";
const char* password = "123456789";

WebServer server(80);

void handleRoot() {
  server.send(200, "application/json", readFromStation());
}

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

  if (!MDNS.begin("esp32_sta")) {
        Serial.println("Error setting up MDNS responder!");
        delay(1000);
  }
  Serial.println("mDNS responder started");
  server.begin();
  server.on("/", handleRoot);
  MDNS.addService("http", "tcp", 80);
  Serial.println("TCP server with service started");
}

void loop()
{
  while(WiFi.status() == WL_CONNECTED)
  {
    server.handleClient();
    delay(2);
  }
  reconnect();
}

String readFromStation() {
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
  return output;
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

/*
void sendData(String ip) {
  WiFiClient client;
  HTTPClient http;

  String url = "http://";
  url += ip;
  url += "/";

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

String browseService(const char * service, const char * proto){
    Serial.printf("Browsing for service _%s._%s.local. ... ", service, proto);
    int n = MDNS.queryService(service, proto);
    if (n == 0) {
        Serial.println("no services found");
        return "";
    } else {
        Serial.print(n);
        Serial.println(" service(s) found");
        for (int i = 0; i < n; ++i) {
            // Print details for each service found
            Serial.print("  ");
            Serial.print(i + 1);
            Serial.print(": ");
            Serial.print(MDNS.hostname(i));
            Serial.print(" (");
            Serial.print(MDNS.IP(i));
            Serial.print(":");
            Serial.print(MDNS.port(i));
            Serial.println(")");
        }
        return String(MDNS.IP(0)[0]) + String(".") +\
            String(MDNS.IP(0)[1]) + String(".") +\
            String(MDNS.IP(0)[2]) + String(".") +\
            String(MDNS.IP(0)[3]) + String(":") +\
            String(MDNS.port(0)); 
    }
}
*/