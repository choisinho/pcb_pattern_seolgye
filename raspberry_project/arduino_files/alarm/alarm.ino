#include <TM1637Display.h>

const int bzpin = 15; //buzzer pin
const int dpClk = 16; //display CLK
const int dpDio = 17; //display DIO
unsigned int cnt = 3600; //timer count

TM1637Display display(dpClk, dpDio);

void setup()
{
  display.setBrightness(0x0a);
  while (true) {
    if (cnt == 0) {
      display.showNumberDec(cnt)
      tone(bzpin, 500, 3000)
      display.show    
      break;
    }
    display.showNumberDec(cnt)
    cnt--;
    delay(1000);
  }
}

void loop()
{

}
