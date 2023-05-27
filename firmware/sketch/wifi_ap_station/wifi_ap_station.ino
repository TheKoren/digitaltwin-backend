#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>
#include <ArduinoJson.hpp>
#include <esp_wifi.h>
#include <lwip\opt.h>
#include <ESPmDNS.h>

const char* wifi_network_ssid = "Koren";
const char* wifi_network_password = "Asd12345";

const char *soft_ap_ssid = "ESP32";
const char *soft_ap_password = "123456789";

void setup(){
    Serial.begin(115200);
    WiFi.mode(WIFI_AP_STA);
    Serial.println("\n[*] Creating ESP32 APSTA");
    WiFi.softAP(soft_ap_ssid, soft_ap_password);
    Serial.print("[+] AP Created with IP Gateway ");
    Serial.println(WiFi.softAPIP());
    WiFi.begin(wifi_network_ssid, wifi_network_password);
    Serial.println("\n[*] Connecting to WiFi Network");
    while(WiFi.status() != WL_CONNECTED)
    {
        Serial.print(".");
        delay(100);
    }
    Serial.print("\n[+] Connected to WiFi network with local IP : ");
    Serial.println(WiFi.localIP());

    if (!MDNS.begin("esp32_AP")) {
        Serial.println("Error setting up MDNS responder!");
        delay(100);
    }
}

void loop() {
  // put your main code here, to run repeatedly:
    while (WiFi.status() == WL_CONNECTED) {
      pollNodes("http", "tcp");
      delay(5000);
    }
    reconnect();
}

void pollNodes(const char * service, const char * proto) {
  Serial.printf("Browsing for service _%s._%s.local. ... ", service, proto);
    int n = MDNS.queryService(service, proto);
    if (n == 0) {
        Serial.println("no services found");
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
        String ip = String(MDNS.IP(0)[0]) + String(".") +\
            String(MDNS.IP(0)[1]) + String(".") +\
            String(MDNS.IP(0)[2]) + String(".") +\
            String(MDNS.IP(0)[3]) + String(":") +\
            String(MDNS.port(0));
        String data = requestData(ip);
        if (data != "") {
          sendData(data);
        }   
    }
  String dataAP = readFromAP();
  sendData(dataAP);
}

String getMacAddress(uint8_t macAddr[6]) {
  String macAddress;
  for (int i = 0; i < 6; i++) {
    if (macAddr[i] < 0x10) {
      macAddress += "0";
    }
    macAddress += String(macAddr[i], HEX);
    if (i < 5) {
      macAddress += ":";
    }
  }
  return macAddress;
}

String requestData(String ip) {
  WiFiClient client;
  HTTPClient http;

  String url = "http://";
  url += ip;
  url += "/";

  http.begin(url);
  int httpResponseCode = http.GET();
  
  if (httpResponseCode > 0) {
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
    String response = http.getString();
    http.end();
    Serial.println(response);
    return response;
  } else {
    Serial.print("Error on HTTP request: ");
    Serial.println(httpResponseCode);
    http.end();
    return "";
  }
}

String readFromAP () {
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
    JsonArray addressList = wifiValues.createNestedArray("addressList");
    wifiValues["rssi"] = WiFi.RSSI();
    //wifiValues["snr"] = WiFi.RSSI()-WiFi.noise();
    wifiValues["channel"] = WiFi.channel();
    wifiValues["TxPower"] = WiFi.getTxPower();
    //wifiValues["speed"] = WiFi.getSpeedMbps();
    //wifiValues["packetloss"] = WiFi.packetLoss();
    wifiValues["mode"] = "WIFI_AP_STA";

    wifi_sta_list_t stationList;
    memset(&stationList, 0, sizeof(stationList));
    esp_wifi_ap_get_sta_list(&stationList);
    
    for (int i = 0; i < WiFi.softAPgetStationNum(); i++) {
      addressList.add(getMacAddress(stationList.sta[i].mac));
    }
    serializeJson(doc, output);
    Serial.println(output);
    return output;
}

void sendData(String data) {
  WiFiClient client;
    HTTPClient http;
    String url = "http://192.168.137.1:9999/data";

    http.begin(client, url);
    http.addHeader("Content-Type", "application/json");
    int httpResponseCode = http.POST(data);

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

void reconnect() {
  WiFi.disconnect();
  WiFi.begin(wifi_network_ssid, wifi_network_password);
    while(WiFi.status() != WL_CONNECTED)
    {
        Serial.print(".");
        delay(100);
    }
  Serial.print("\n[+] Reconnected to WiFi network with local IP : ");
  Serial.println(WiFi.localIP());
}