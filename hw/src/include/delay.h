#ifndef __DELAY_H
#define __DELAY_H

extern unsigned char delayus_variable;
#define PIC_CLK 8000000

#if PIC_CLK == 4000000
	#define DelayDivisor 4
	#define WaitFor1Us asm("nop")
	#define Jumpback asm("goto $ - 4")
#elif PIC_CLK == 8000000
	#define DelayDivisor 2
	#define WaitFor1Us asm("nop")
	#define Jumpback asm("goto $ - 4")
#elif PIC_CLK == 16000000
	#define DelayDivisor 1
	#define WaitFor1Us asm("nop")
	#define Jumpback asm("goto $ - 4")
#elif PIC_CLK == 20000000
	#define DelayDivisor 1
	#define WaitFor1Us asm("nop"); asm("nop")
	#define Jumpback asm("goto $ - 6")
#elif PIC_CLK == 32000000
	#define DelayDivisor 1
	#define WaitFor1Us asm("nop"); asm("nop"); asm("nop"); asm("nop"); asm("nop")
	#define Jumpback asm("goto $ - 12")
#else
	#error delay.h - please define PIC_CLK correctly
#endif

#define delayMicroseconds(x) { \
    delayus_variable=(unsigned char)(x/DelayDivisor); \
    asm("movlb (_delayus_variable) >> 8"); \
    WaitFor1Us; \
  } \
  asm("decfsz (_delayus_variable)&0ffh,f"); \
  Jumpback;

void delayMilliseconds(unsigned int);
void delaySeconds(unsigned char);

#endif
