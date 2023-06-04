struct WifiSignal {
    int rssi;
    int txPower;
    int channel;
};
typedef struct WifiSignal WifiSignal;

struct SensorData {
    int noteId;
    int temperatureValue;
    int humidityValue;
    int tvocValue;
    WifiSignal wifiValues;
};
typedef struct SensorData SensorData;

