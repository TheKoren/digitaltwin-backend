### Arduino Sketch

#### wifi_ap_station

ESP32 has 2 WiFi interfaces, we use both of them in this version. It initializes as an Access Point and creates a local network, for nodes to connect to it, and as a note itself connects to a WiFi hotspot.
As all other nodes, it sends ambient measurement data, and low-level wifi phy data along with connected stations mac addresses.

#### wifi_sta

Connects to ESP32, sends data through AP to webserver.