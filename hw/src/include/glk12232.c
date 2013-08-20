#include <pic18.h>
#include <string.h>
#include "delay.h"
#include "glk12232.h"

void sendI2C(unsigned char addr, char* data, unsigned char len) {
	unsigned char i;
	
	delayMilliseconds(250);
	SSPIF = 0; // Clear SSP flag
	SEN = 1; // Start Enable
	while(SEN); // Wait to finish
	SSPIF = 0; // Clear SSP flag
	SSPBUF = addr; // Send address
	while(!SSPIF); // Wait for SSPIF flag
	SSPIF = 0; // Clear SSP flag
	for(i = 0; i < len; i++) {
		SSPBUF = data[i]; // Send data
		while(!SSPIF); // Wait for SSPIF flag
		SSPIF = 0; // Clear SSP flag
//		delayMilliseconds(10); // Allow interpretation
	}
	PEN = 1;
	while(PEN); // Wait to finish
	SSPIF = 0; // Clear SSP flag
}

void sendByte(unsigned char x) {
}

void startCommand(){
	sendByte(GLK12232_CMD_BYTE);
}

void setAutoScroll(unsigned char on) {
	startCommand();
	if(on){
		sendByte(GLK12232_AUTO_SCROLL_ON);
	} else {
		sendByte(GLK12232_AUTO_SCROLL_OFF);
	}
}

void setTextInsertionPoint(unsigned char col, unsigned char row) {
	startCommand();
	sendByte(GLK12232_SET_TEXT_INS_PT);
	sendByte(col);
	sendByte(row);
}

void setTextInsertionPointTopLeft() {
	startCommand();
	sendByte(GLK12232_SET_TEXT_INS_PT_TL);
}

void setTextInsertionPointPixel(unsigned char x, unsigned char y) {
	unsigned char data[4];
	
	data[0] = GLK12232_CMD_BYTE;
	data[1] = GLK12232_SET_TEXT_INS_PT_PXL;
	data[2] = x;
	data[3] = y;

	sendI2C(GLK12232_I2C_ADDR, data, 4);
}

void setFont(unsigned char fontId) {
	unsigned char data[3];
	
	data[0] = GLK12232_CMD_BYTE;
	data[1] = GLK12232_SET_FONT;
	data[2] = fontId;
	
	sendI2C(GLK12232_I2C_ADDR, data, 3);
}

void setFontMetrics(unsigned char leftMargin, unsigned char topMargin,
					unsigned char xSpc, unsigned char ySpc, unsigned char scrollRow) {
	startCommand();
	sendByte(GLK12232_SET_FONT_METRICS);
	sendByte(leftMargin);
	sendByte(topMargin);
	sendByte(xSpc);
	sendByte(ySpc);
	sendByte(scrollRow);
}

void setDrawingColor(unsigned char color) {
	startCommand();
	sendByte(GLK12232_SET_DRAW_COLOR);
	sendByte(color);
}

void drawLine(unsigned char x1, unsigned char y1,
				unsigned char x2, unsigned char y2) {
	startCommand();
	sendByte(GLK12232_DRAW_LINE);
	sendByte(x1);
	sendByte(y1);
	sendByte(x2);
	sendByte(y2);
}

void continueLine(unsigned char x, unsigned char y) {
	startCommand();
	sendByte(GLK12232_CONTINUE_LINE);
	sendByte(x);
	sendByte(y);
}

void putPixel(unsigned char x, unsigned char y) {
	startCommand();
	sendByte(GLK12232_PUT_PIXEL);
	sendByte(x);
	sendByte(y);
}

void drawOutlineRectangle(unsigned char color, unsigned char x1, unsigned char y1,
							unsigned char x2, unsigned char y2) {
	startCommand();
	sendByte(GLK12232_DRAW_OUTLINE_RECT);
	sendByte(x1);
	sendByte(y1);
	sendByte(x2);
	sendByte(y2);
}
							
void drawSolidRectangle(unsigned char color, unsigned char x1, unsigned char y1,
						unsigned char x2, unsigned char y2) {
	startCommand();
	sendByte(GLK12232_DRAW_SOLID_RECT);
	sendByte(x1);
	sendByte(y1);
	sendByte(x2);
	sendByte(y2);
}

void initBarGraph(unsigned char num, unsigned char type,
					unsigned char x1, unsigned char y1,
					unsigned char x2, unsigned char y2) {
	startCommand();
	sendByte(GLK12232_INIT_BAR_GRAPH);
	sendByte(num);
	sendByte(type);
	sendByte(x1);
	sendByte(y1);
	sendByte(x2);
	sendByte(y2);
}

void writeToBarGraph(unsigned char num, unsigned char value) {
	startCommand();
	sendByte(GLK12232_WRITE_TO_BAR_GRAPH);
	sendByte(num);
	sendByte(value);
}

// tested OK
void displaySavedBitmap(unsigned char num, unsigned char x, unsigned char y) {
	unsigned char data[5];
	
	data[0] = GLK12232_CMD_BYTE;
	data[1] = GLK12232_DISPLAY_SAVED_BITMAP;
	data[2] = num;
	data[3] = x;
	data[4] = y;
	
	sendI2C(GLK12232_I2C_ADDR, data, 5);
}

void setAutoRepeat(unsigned char on, unsigned char mode) {
	startCommand();
	if(on){
		sendByte(GLK12232_AUTO_REPEAT_ON);
		sendByte(mode);
	} else {
		sendByte(GLK12232_AUTO_REPEAT_OFF);
	}
}

void setAutoTransmitKeypresses(unsigned char on) {
	startCommand();
	if(on){
		sendByte(GLK12232_AUTO_TRANSMIT_KEYS_ON);
	} else {
		sendByte(GLK12232_AUTO_TRANSMIT_KEYS_OFF);
	}
}

void clearKeyBuffer() {
	startCommand();
	sendByte(GLK12232_CLEAR_KEY_BUFFER);
}

unsigned char pollKeypad() {
	startCommand();
	return 0; // implement
}

void setDebounceTime(unsigned char time) {
	startCommand();
	sendByte(GLK12232_SET_DEBOUNCE_TIME);
	sendByte(time);
}

void eraseFile(unsigned char type, unsigned char num) {
	startCommand();
	sendByte(GLK12232_ERASE_FILE);
	sendByte(type);
	sendByte(num);
}

void purgeMemory() {
	startCommand();
	sendByte(GLK12232_PURGE_MEMORY1);
	sendByte(GLK12232_PURGE_MEMORY2);
	sendByte(GLK12232_PURGE_MEMORY3);
}

// tested OK
void clearDisplay() {
	char data[] = { GLK12232_CMD_BYTE, GLK12232_CLEAR_DISPLAY };
	sendI2C(GLK12232_I2C_ADDR, data, 2);
}

// tested unsaved OK
void setContrast(unsigned char contrast, unsigned char save) {
	unsigned char data[3];
	
	data[0] = GLK12232_CMD_BYTE;
	data[1] = save ? GLK12232_SET_CONTRAST_AND_SAVE : GLK12232_SET_CONTRAST;
	data[2] = contrast;
	
	sendI2C(GLK12232_I2C_ADDR, data, 3);
}

// tested OK
void setBacklight(unsigned char on, unsigned char duration) {
	unsigned char data[3];
	
	data[0] = GLK12232_CMD_BYTE;
	data[1] = on ? GLK12232_BACKLIGHT_ON : GLK12232_BACKLIGHT_OFF;
	data[2] = on ? duration : NULL;
	
	sendI2C(GLK12232_I2C_ADDR, data, (on ? 3 : 2));
}

void setGeneralPurposeOutput(unsigned char num, unsigned char on) {
	startCommand();
}

void setI2CAddress(unsigned char address) {
	startCommand();
}

unsigned char readModuleType() {
	startCommand();
	return 0; // implement
}

void setRS232PortSpeed(unsigned char speed) {
	startCommand();
}

void enterFlowControlMode(unsigned char full, unsigned char empty) {
	startCommand();
}

void exitFlowControlMode() {
	startCommand();
}

void setSerialNumber(unsigned int num) {
	startCommand();
}

unsigned int readSerialNumber() {
	startCommand();
	return 0; // implement
}

unsigned char readVersionNumber() {
	startCommand();
	return 0; // implement
}

void glk12232Print(unsigned char* text) {
	sendI2C(GLK12232_I2C_ADDR, text, strlen(text));
}
