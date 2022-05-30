import tm1637
from machine import Pin, UART
tm = tm1637.TM1637(clk=Pin(16), dio=Pin(17))
uart = UART(0,9600)

LedGPIO = 16
led = Pin(LedGPIO, Pin.OUT)

while True:
    if uart.any():
        command = uart.readline()
        if command==b'\xd0':
            tm.show('ON')
            print("ON")
        elif command==b'\xd5':
            tm.show('OFF')
            print("OFF")