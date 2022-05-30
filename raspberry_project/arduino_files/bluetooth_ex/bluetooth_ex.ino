#include <softwareserial.h>

const int btRxd = 0; //bluetooth RXD
const int btTxd = 1; //bluetooth TXD
SoftwareSerial bt(btTxd, btRxd);

void setup() {
  Serial.begin(9600);
  bt.begin(9600);
}

void loop() {
  if (bt.available()) {
    Serial.write(bt.read());
  }
  if (Serial.available()) {
    bt.write(Serial.read());
  }
}
