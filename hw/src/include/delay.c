#include <pic18.h>
#include "delay.h"

unsigned char delayus_variable;

void delayMilliseconds(unsigned int ms){
	unsigned char i;
	do {
		i = 4;
		do {
			delayMicroseconds(250);
			CLRWDT();
		} while(--i);
	} while(--ms);
}

void delaySeconds(unsigned char s){
	unsigned char i;
	do {
		i = 4;
		do {
			delayMilliseconds(250);
			CLRWDT();
		} while(--i);
	} while(--s);
}
