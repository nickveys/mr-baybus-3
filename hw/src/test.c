#include <pic18.h>
#include <string.h>
#include "include/glk12232.h"
#include "include/delay.h"

#define B0 RB0 // Button 0 (Up)
#define B1 RB1 // Button 1 (Go)
#define B2 RB2 // Button 2 (Down)

#define F0 RA7 // Fan 0
#define F1 RA6 // Fan 1
#define F2 RC0 // Fan 2
#define F3 RC1 // Fan 3

#define L0 RB4 // Light 0
#define L1 RB5 // Light 1

#define T0 RA0 // Temp. Sensor 0
#define T1 RA1 // Temp. Sensor 1
#define T2 RA2 // Temp. Sensor 2

__CONFIG(1, RCIO & FCMDIS & IESODIS); // Internal RC, Fail-Safe off, Switchover off
__CONFIG(2, BORV42 & PWRTEN & WDTDIS); // Brown-out 4.2V
__CONFIG(3, MCLRDIS); // MCLR disabled
__CONFIG(4, DEBUGDIS & LVPDIS & STVRDIS); // No debug, lvp or stack protection
__CONFIG(5, UNPROTECT); // No memory protection
__CONFIG(6, WRTEN); // Write enabled
__CONFIG(7, TRU); // Table read unprotected

#define LCD_ADDR		80
#define SCREEN_SPLASH	1
#define SCREEN_TEMPS	2
#define SCREEN_FANS		3
#define SCREEN_LIGHTS	4
#define SCREEN_CONFIG	5
#define SCREEN_ABOUT	6

unsigned char DC0	= 0b00000000; // 0x00, 0
unsigned char DC25	= 0b00010001; // 0x11, 17
unsigned char DC50	= 0b01010101; // 0x55, 85
unsigned char DC75	= 0b11101110; // 0xEE, 238
unsigned char DC100	= 0b11111111; // 0xFF, 255
unsigned char F0_DC, F1_DC, F2_DC, F3_DC;
unsigned char pwmPosition;

void init();
void initI2C();
void initPWM();
void initInterrupts();
void interrupt ISR();
void interrupt low_priority ISR_LOW();

main() {
	init(); // setup ports, registers, etc...
	initPWM(); // start PWM sequence
	initInterrupts(); // setup interrupt masks, etc
	initI2C(); // reset i2c bus

	delaySeconds(2); // wait for everyone to wake up
	
	glk12232Print("Hello, World!");
	
	while(1);
}

void init() {
	IRCF2 = IRCF1 = IRCF0 = 1; // 8MHz
	SCS1 = 1; // Internal oscillator select

	ADCON1 = 0x0C; // Internal ref, 2-0 Analog
	//ADCON2 = ??; //

	TRISA = 0b00111111; // See pinout
	TRISB = 0b11001111; // See pinout
	TRISC = 0b10000000; // See pinout
	
	SMP = 1; // Slew Rate Standard (100KHz - 1MHz)
	SSPADD = 19; // I2C clock = 8M/(4*(19+1)) -> 100KHz
	SSPCON1 = 0x08; // Master, clock from SSPADD
	SSPEN = 1; // Synch. Serial Port Enabled
}

void initI2C() {
	PEN = 1;
	while(PEN);
	SEN = 1;
	while(SEN);
	PEN = 1;
	while(PEN);
	SSPIF = 0;
}

void initPWM() {
	pwmPosition = 0; // start are 0 bit
	F0 = F1 = F2 = F3 = 0; // all fans start off
	F0_DC = DC25;
	F1_DC = DC50;
	F2_DC = DC75;
	F3_DC = DC100;

	T0CON = 0b01000110; // internal, 8-bit, 1:128 prescalar -> ~0.016384ms
	TMR0H = TMR0L = 0; // clear TMR0
	TMR0ON = 1; // turn on TMR0
}

void initInterrupts() {
	IPEN = 1; // enable interrupt priorities
    
	TMR0IP = 0; // TMR0 is low priority
	TMR0IE = 1; // enable TRM0 interrupt
	TMR0IF = 0; // clear pending interrupts
    
	GIEL = 1; // enable low priority interrupts
	GIEH = 1; // enable high priority interrupts
}

void interrupt ISR() {
}

void interrupt low_priority ISR_LOW() {
	if(TMR0IF) {
		TMR0IF = 0;
		F0 = (F0_DC & (1 << pwmPosition)) ? 1 : 0;
    	F1 = (F1_DC & (1 << pwmPosition)) ? 1 : 0;
    	F2 = (F2_DC & (1 << pwmPosition)) ? 1 : 0;
    	F3 = (F3_DC & (1 << pwmPosition)) ? 1 : 0;
		pwmPosition = pwmPosition >= 7 ? 0 : pwmPosition + 1;
	}
}
