#include <TM1637Display.h>
#include <SoftwareSerial.h>

const int btRxd = 0; //bluetooth RXD
const int btTxd = 1; //bluetooth TXD
const int dpClk = 16; //display CLK
const int dpDio = 17; //display DIO

SoftwareSerial bt(btRxd, btTxd);
TM1637Display display(dpClk, dpDio);

void setup()
{
  //Setup BT
  Serial.begin(9600);
  bt.begin(9600);

  //Setup DP
  display.setBrightness(0x0a);
}


void loop()
{
  
}
